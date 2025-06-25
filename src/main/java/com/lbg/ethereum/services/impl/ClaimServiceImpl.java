package com.lbg.ethereum.services.impl;

import com.lbg.ethereum.DTOs.*;
import com.lbg.ethereum.contracts.ClaimTopicsRegistry;
import com.lbg.ethereum.contracts.Identity;
import com.lbg.ethereum.contracts.TrustedIssuersRegistry;
import com.lbg.ethereum.services.ClaimService;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Bytes;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final Web3j web3j;
    Environment environment;

    public ClaimServiceImpl(Web3j web3j, Environment environment) {
        this.web3j = web3j;
        this.environment = environment;
    }

    public ResponseEntity<AddClaimResponseDto> addClaim(AddClaimDto addClaimDto) {
        AddClaimResponseDto response = new AddClaimResponseDto();
        try {
            Credentials userSigner = Credentials.create(environment.getProperty(addClaimDto.getSigner() + "PrivateKey"));
            Credentials claimIssuer = Credentials.create(environment.getProperty("claimIssuerSignPrivateKey"));

            // Load the Identity contract
            Identity identity = Identity.load(addClaimDto.getIdentityAddress(), web3j, userSigner, new DefaultGasProvider());

            // Hash the topic
            byte[] topicHash = org.web3j.crypto.Hash.sha3(addClaimDto.getTopic().getBytes());
            BigInteger topicBigInt = new BigInteger(1, topicHash);

            // Prepare claim data
            byte[] dataBytes = addClaimDto.getData().getBytes("UTF-8");

            // Encode and hash for signature
            Function function = new Function(
                    "", // method name is not used for parameter encoding
                    Arrays.asList(
                            new Address(addClaimDto.getIdentityAddress()),
                            new Uint256(topicBigInt),
                            new DynamicBytes(dataBytes)
                    ),
                    Collections.emptyList()
            );

// Encode the function and remove the method selector (first 4 bytes)
            String encodedHex = FunctionEncoder.encode(function).substring(8); // skip "0x" and 4 bytes (8 hex chars)
            byte[] encoded = Numeric.hexStringToByteArray(encodedHex);
            byte[] hash = Hash.sha3(encoded);
            // Sign the claim
            org.web3j.crypto.Sign.SignatureData signatureData = Sign.signMessage(hash, claimIssuer.getEcKeyPair());
            String signature = Numeric.toHexString(signatureData.getR()) +
                    Numeric.toHexString(signatureData.getS()).substring(2) +
                    Numeric.toHexString(new byte[]{signatureData.getV()[0]}).substring(2);

            // Call addClaim on the contract
            TransactionReceipt tx = identity.addClaim(
                    topicBigInt,
                    BigInteger.valueOf(addClaimDto.getScheme()),
                    claimIssuer.getAddress(),
                    signature.getBytes(),
                    dataBytes,
                    ""
            ).send();
            response.setStatusCode(200);
            response.setMessage("Claim added successfully");
            response.setReceipt(tx);


        } catch (Exception e) {
            throw new RuntimeException("Failed to add claim: " + e.getMessage(), e);
        }
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<AddClaimTopicReponseDto> addClaimTopic(AddClaimTopicDto addClaimTopicDto) {
        try {
            // 1. Hash the topic string to uint256 (like ethers.utils.id)
            String topicHash = Hash.sha3String(addClaimTopicDto.getTopic()); // returns 0x...
            BigInteger topic = new BigInteger(topicHash.substring(2), 16);

            // 2. Load the ClaimTopicsRegistry contract
            String registryAddress = environment.getProperty("claimTopicsRegistry_smart_contract_address");
            Credentials signer = Credentials.create(environment.getProperty(addClaimTopicDto.getSigner() + "PrivateKey")); // Signer credentials
            ClaimTopicsRegistry registry = ClaimTopicsRegistry.load(
                    registryAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // 3. Call addClaimTopic on the contract
            TransactionReceipt tx = registry.addClaimTopic(topic).send();
            return ResponseEntity.ok(new AddClaimTopicReponseDto() {{
                ;
                setMessage("Claim topic added successfully");
                setTransactionReceipt(tx);
            }});
        } catch (Exception e) {
            throw new RuntimeException("Failed to add claim topic: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<RemoveClaimTopicResponseDto> removeClaimTopic(RemoveClaimTopicDto removeClaimTopicDto) {
        RemoveClaimTopicResponseDto response = new RemoveClaimTopicResponseDto();
        try {
            // Get contract address from DTO or environment
            String claimTopicsRegistryAddress = removeClaimTopicDto.getClaimTopicsRegistryAddress();
            if (claimTopicsRegistryAddress == null || claimTopicsRegistryAddress.isEmpty()) {
                claimTopicsRegistryAddress = environment.getProperty("claimTopicsRegistry_smart_contract_address");
            }

            // Get signer credentials
            String privateKey = environment.getProperty(removeClaimTopicDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            // Load the ClaimTopicsRegistry contract
            ClaimTopicsRegistry registry = ClaimTopicsRegistry.load(
                    claimTopicsRegistryAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call removeClaimTopic
            BigInteger claimTopic = topicToUint256(removeClaimTopicDto.getTopic());
            TransactionReceipt tx = registry.removeClaimTopic(claimTopic).send();

            response.setMessage("Claim removed successfully, user won't require this topic claim for the token minting and transfer");
            response.setTransactionReceipt(tx);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("Failed to remove claim topic: " + e.getMessage());
            response.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @Override
    public ResponseEntity<GetClaimTopicsResponseDto> getClaimTopics(GetClaimTopicsDto getClaimTopicsDto) {
        try {
            // Load the ClaimTopicsRegistry contract
            String registryAddress = environment.getProperty("claimTopicsRegistry_smart_contract_address");
            //lbg_privateKey is the signer address
            Credentials signer = Credentials.create(environment.getProperty(getClaimTopicsDto.getSigner() + "PrivateKey"));
            ClaimTopicsRegistry registry = ClaimTopicsRegistry.load(
                    registryAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );
            // Call getClaimTopics (returns List<BigInteger>)
            List<BigInteger> claimTopics = registry.getClaimTopics().send();
            return ResponseEntity.ok(new GetClaimTopicsResponseDto() {{
                setMessage("Claim topics retrieved successfully");
                setClaimTopics(claimTopics.stream()
                        .map(topic -> Numeric.toHexString(Bytes.trimLeadingZeroes(topic.toByteArray())))
                        .collect(Collectors.toList()));
            }});
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : "Failed to get claim topic", e);
        }
    }

    @Override
    public ResponseEntity<AddTrustedIssuerClaimTopicResponseDto> addTrustedIssuerClaimTopic(AddTrustedIssuerClaimTopicsDto addTrustedIssuerClaimTopicDto) {
        AddTrustedIssuerClaimTopicResponseDto response = new AddTrustedIssuerClaimTopicResponseDto();
        try {
            // Get contract address from body or environment
            String registryAddress = addTrustedIssuerClaimTopicDto.getTrustedIssuersRegistryContractAddress();
            if (registryAddress == null || registryAddress.isEmpty()) {
                registryAddress = environment.getProperty("trustedIssuersRegistry_smart_contract_address");
            }

            // Get signer credentials
            String privateKey = environment.getProperty(addTrustedIssuerClaimTopicDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            // Load the TrustedIssuersRegistry contract
            TrustedIssuersRegistry registry = TrustedIssuersRegistry.load(
                    registryAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Convert topics to BigInteger list
            List<BigInteger> claimTopics = addTrustedIssuerClaimTopicDto.getTopics().stream()
                    .map(BigInteger::new)
                    .collect(Collectors.toList());

            // Get claimIssuerContractAddress from body or environment
            String claimIssuerAddress = addTrustedIssuerClaimTopicDto.getClaimIssuerContractAddress();
            if (claimIssuerAddress == null || claimIssuerAddress.isEmpty()) {
                claimIssuerAddress = environment.getProperty("claimIssuerContract_smart_contract_address");
            }

            // Call addTrustedIssuer
            TransactionReceipt tx = registry.addTrustedIssuer(
                    claimIssuerAddress,
                    claimTopics
            ).send();

            response.setMessage("Trusted issuer claim topics added successfully");
            response.setTransactionReceipt(tx);
        } catch (Exception e) {
            response.setMessage("Failed to add trusted issuer claim topics: " + e.getMessage());
            response.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UpdateTrustedIssuerClaimTopicResponseDto> updateTrustedIssuerClaimTopic(UpdateTrustedIssuerClaimTopicsDto updateTrustedIssuerClaimTopicsDto) {
        UpdateTrustedIssuerClaimTopicResponseDto response = new UpdateTrustedIssuerClaimTopicResponseDto();
        try {
            // Get contract address from body or environment
            String registryAddress = updateTrustedIssuerClaimTopicsDto.getTrustedIssuersRegistryContractAddress();
            if (registryAddress == null || registryAddress.isEmpty()) {
                registryAddress = environment.getProperty("trustedIssuersRegistry_smart_contract_address");
            }

            // Get signer credentials
            String privateKey = environment.getProperty(updateTrustedIssuerClaimTopicsDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            // Load the TrustedIssuersRegistry contract
            TrustedIssuersRegistry registry = TrustedIssuersRegistry.load(
                    registryAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Convert topics to BigInteger list
            List<BigInteger> claimTopics = updateTrustedIssuerClaimTopicsDto.getTopics().stream()
                    .map(BigInteger::new)
                    .collect(Collectors.toList());

            // Get claimIssuerContractAddress from body or environment
            String claimIssuerAddress = updateTrustedIssuerClaimTopicsDto.getClaimIssuerContractAddress();
            if (claimIssuerAddress == null || claimIssuerAddress.isEmpty()) {
                claimIssuerAddress = environment.getProperty("claimIssuerContract_smart_contract_address");
            }

            // Call updateIssuerClaimTopics
            TransactionReceipt tx = registry.updateIssuerClaimTopics(
                    claimIssuerAddress,
                    claimTopics
            ).send();

            response.setMessage("Trusted issuer claim topics updated successfully");
            response.setTransactionReceipt(tx);
        } catch (Exception e) {
            response.setMessage("Failed to update trusted issuer claim topics: " + e.getMessage());
            response.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok(response);
    }

    public static BigInteger topicToUint256(String topic) {
        String hash = Hash.sha3String(topic);
        return new BigInteger(hash.substring(2), 16);
    }
}


