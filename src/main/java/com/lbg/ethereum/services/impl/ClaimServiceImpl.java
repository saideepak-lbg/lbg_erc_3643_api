package com.lbg.ethereum.services.impl;

import com.lbg.ethereum.DTOs.*;
import com.lbg.ethereum.contracts.ClaimTopicsRegistry;
import com.lbg.ethereum.contracts.Identity;
import com.lbg.ethereum.contracts.TrustedIssuersRegistry;
import com.lbg.ethereum.entities.claims.Claim;
import com.lbg.ethereum.entities.claims.UserClaim;
import com.lbg.ethereum.entities.transactions.Transaction;
import com.lbg.ethereum.entities.users.UserEntity;
import com.lbg.ethereum.enums.TransactionType;
import com.lbg.ethereum.exception.handlers.ClaimException;
import com.lbg.ethereum.repository.*;
import com.lbg.ethereum.services.ClaimService;
import com.lbg.ethereum.utils.TransactionMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final Web3j web3j;
    Environment environment;
    UserRepository userRepository;
    TransactionRepository transactionRepository;
    ClaimRepository claimRepository;
    UserIdentityRepository userIdentityRepository;
    UserClaimRepository userClaimRepository;

    public ClaimServiceImpl(Web3j web3j, Environment environment,
                            UserRepository userRepository,
                            TransactionRepository transactionRepository,
                            ClaimRepository claimRepository,
                            UserIdentityRepository userIdentityRepository,
                            UserClaimRepository userClaimRepository) {
        this.web3j = web3j;
        this.environment = environment;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.claimRepository = claimRepository;
        this.userIdentityRepository = userIdentityRepository;
        this.userClaimRepository = userClaimRepository;
    }

    public ResponseEntity<AddClaimResponseDto> addClaim(AddClaimDto addClaimDto) {
        AddClaimResponseDto response = new AddClaimResponseDto();
        TransactionReceipt txReciept;
        try {
            Credentials userSigner = Credentials.create(environment.getProperty(addClaimDto.getSigner() + "PrivateKey"));
            Credentials claimIssuer = Credentials.create(environment.getProperty("claimIssuerSignPrivateKey"));

            // Load the Identity contract
            Identity identity = Identity.load(addClaimDto.getIdentityAddress(), web3j, userSigner, new DefaultGasProvider());

            // Hash the topic
            byte[] topicHash = Hash.sha3(addClaimDto.getTopic().getBytes(StandardCharsets.UTF_8));
            BigInteger topicBigInt = new BigInteger(1, topicHash);

            // Prepare claim data
            String data = addClaimDto.getData();
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);



            // Encode and hash for signature
            List<Type> inputParameters = Arrays.asList(
                    new Uint256(topicBigInt),
                    new Address(addClaimDto.getIdentityAddress()),
                    new DynamicBytes(dataBytes)
            );

            String encodedData = FunctionEncoder.encodeConstructor(inputParameters);
            System.out.println("Encoded claim data: " + encodedData);

            byte[] hash = Hash.sha3(Numeric.hexStringToByteArray(encodedData));
            // Sign the claim
            Sign.SignatureData signatureData = Sign.signMessage(hash, claimIssuer.getEcKeyPair());

            String signature = Numeric.toHexString(signatureData.getR()) +
                    Numeric.cleanHexPrefix(Numeric.toHexString(signatureData.getS()))+
                    Numeric.cleanHexPrefix(Numeric.toHexString(signatureData.getV()));

            byte[] signatureBytes = Numeric.hexStringToByteArray(signature);

            System.out.println("topicBigInt: " + topicBigInt);
            System.out.println("scheme: " + BigInteger.valueOf(addClaimDto.getScheme()));
            System.out.println("claimIssuer.getAddress(): " + claimIssuer.getAddress());
            System.out.println("signatureBytes: " + signatureBytes);
            System.out.println("dataBytes: " + dataBytes);


            // Call addClaim on the contract
             txReciept = identity.addClaim(
                    topicBigInt,
                    BigInteger.valueOf(addClaimDto.getScheme()),
                    claimIssuer.getAddress(),
                    signatureBytes,
                    dataBytes,
                    addClaimDto.getUri()
            ).send();
            if(txReciept == null || !txReciept.isStatusOK()) {
                throw new ClaimException("unable to add claim, transaction receipt is null or status is not OK.");
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(txReciept);
            transaction.setTransactionType(TransactionType.ADD_CLAIM);
            transactionRepository.save(transaction);

            UserClaim userClaim = new UserClaim();
            Claim claim = claimRepository.findByTopicName(addClaimDto.getTopic());
            userClaim.setClaim(claim);
            userClaim.setClaimAddedDate(LocalDateTime.now());
            userClaim.setUserIdentity(addClaimDto.getIdentityAddress());
            userClaim.setTransaction(transaction);
            userClaim.setData(addClaimDto.getData());
            userClaim.setScheme(addClaimDto.getScheme());
            userClaimRepository.save(userClaim);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ClaimException("Failed to add claim: " + e.getMessage(), e);
        }
        response.setStatusCode(200);
        response.setMessage("Claim added successfully");
        response.setReceipt(txReciept);
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<AddClaimTopicReponseDto> addClaimTopic(AddClaimTopicDto addClaimTopicDto) {
        TransactionReceipt txReciept;
        AddClaimTopicReponseDto response = new AddClaimTopicReponseDto();
        try {

            String topicString = addClaimTopicDto.getTopic();
            if(topicString == null || topicString.isEmpty()) {
                throw new ClaimException("Topic cannot be null or empty");
            }

            // 1. Hash the topic string to uint256 (like ethers.utils.id)
            String topicHash = Hash.sha3String(topicString); // returns 0x...
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

            if(registry == null) {
                throw new ClaimException("ClaimTopicsRegistry contract not found at address: " + registryAddress);
            }

            // 3. Call addClaimTopic on the contract
          txReciept = registry.addClaimTopic(topic).send();

          if(txReciept == null || !txReciept.isStatusOK()) {
              throw new ClaimException("unable to add claim topic, transaction receipt is null or status is not OK.");
          }

          // 4. Map the transaction receipt to a Transaction entity
          Transaction trasaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(txReciept);

          trasaction.setTransactionType(TransactionType.ADD_CLAIM_TOPIC);

          Claim claim = new Claim();
          claim.setTopicName(topicString);
          claim.setSigner(userRepository.findByUserName(addClaimTopicDto.getSigner()).orElseThrow(
                  () -> new ClaimException("No signer exists for the given signer: " + addClaimTopicDto.getSigner())));

          // 5. Save the transaction and claim to the database
          transactionRepository.save(trasaction);
          claimRepository.save(claim);

        } catch (Exception e) {
            throw new ClaimException("Failed to add claim topic: " + e.getMessage(), e);
        }
        response.setMessage("Claim topic added successfully");
        response.setTransactionReceipt(txReciept);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RemoveClaimTopicResponseDto> removeClaimTopic(RemoveClaimTopicDto removeClaimTopicDto) {
        RemoveClaimTopicResponseDto response = new RemoveClaimTopicResponseDto();
        TransactionReceipt txReciept;
        try {
            // Get contract address from DTO or environment
            String claimTopicsRegistryAddress = removeClaimTopicDto.getClaimTopicsRegistryAddress();
            if (claimTopicsRegistryAddress == null || claimTopicsRegistryAddress.isEmpty()) {
                claimTopicsRegistryAddress = environment.getProperty("claimTopicsRegistry_smart_contract_address");

            }
            if(claimTopicsRegistryAddress==null || claimTopicsRegistryAddress.isEmpty()) {
                throw new ClaimException("ClaimTopicsRegistry contract address is not configured");
            }
            // Get signerCreds credentials
            String privateKey = environment.getProperty(removeClaimTopicDto.getSigner() + "PrivateKey");
            Credentials signerCreds = Credentials.create(privateKey);

            // Load the ClaimTopicsRegistry contract
            ClaimTopicsRegistry registry = ClaimTopicsRegistry.load(
                    claimTopicsRegistryAddress,
                    web3j,
                    signerCreds,
                    new DefaultGasProvider()
            );
            if(registry == null) {
                throw new ClaimException("ClaimTopicsRegistry contract not found at address: " + claimTopicsRegistryAddress);
            }
            // Call removeClaimTopic
            BigInteger claimTopic = topicToUint256(removeClaimTopicDto.getTopic());
            txReciept = registry.removeClaimTopic(claimTopic).send();

            if(txReciept == null || !txReciept.isStatusOK()) {
                throw new ClaimException("unable to remove claim topic, transaction receipt is null or status is not OK.");
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(txReciept);
            transaction.setTransactionType(TransactionType.REMOVE_CLAIM_TOPIC);
            transactionRepository.save(transaction);

            // Remove the claim topic from the database
            UserEntity signer = userRepository.findByUserName(removeClaimTopicDto.getSigner()).orElseThrow(
                    () -> new ClaimException("No signer exists for the given signer: " + removeClaimTopicDto.getSigner()));
            claimRepository.deleteByTopicNameAndSignerId(removeClaimTopicDto.getTopic(), signer.getUserId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new ClaimException("Failed to remove claim topic: " + e.getMessage(), e);
        }
        response.setMessage("Claim removed successfully, user won't require this topic claim for the token minting and transfer");
        response.setTransactionReceipt(txReciept);
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<GetClaimTopicsResponseDto> getClaimTopics(GetClaimTopicsDto getClaimTopicsDto) {
        GetClaimTopicsResponseDto getClaimTopicsResponseDto = new GetClaimTopicsResponseDto();
        try {
            // Load the ClaimTopicsRegistry contract
            String registryAddress = environment.getProperty("claimTopicsRegistry_smart_contract_address");

            if (registryAddress == null || registryAddress.isEmpty()) {
                throw new ClaimException("ClaimTopicsRegistry contract address is not configured");
            }
            //lbg_privateKey is the signer address
            Credentials signerCreds = Credentials.create(environment.getProperty(getClaimTopicsDto.getSigner() + "PrivateKey"));
            ClaimTopicsRegistry registry = ClaimTopicsRegistry.load(
                    registryAddress,
                    web3j,
                    signerCreds,
                    new DefaultGasProvider()
            );

            if(registry == null) {
                throw new ClaimException("ClaimTopicsRegistry contract not found at address: " + registryAddress);
            }
            //not a state change operation, so no transaction receipt is needed
            // Call getClaimTopics (returns List<BigInteger>)
            List<BigInteger> claimTopicHashes = registry.getClaimTopics().send();

            if(claimTopicHashes == null || claimTopicHashes.isEmpty()) {
                getClaimTopicsResponseDto.setMessage("No claim topics found");
                getClaimTopicsResponseDto.setClaimTopicsHashes(Collections.emptyList());
                getClaimTopicsResponseDto.setClaimTopics(Collections.emptyList());
                return ResponseEntity.ok(getClaimTopicsResponseDto);
            } else {
                // Convert BigInteger claim topics to String format
                getClaimTopicsResponseDto.setClaimTopicsHashes(
                        claimTopicHashes.stream()
                                .map(hash -> Numeric.toHexStringWithPrefix(hash))
                                .collect(Collectors.toList())
                );
                UserEntity signer = userRepository.findByUserName(getClaimTopicsDto.getSigner())
                        .orElseThrow(() -> new ClaimException("No signer exists for the given signer: " + getClaimTopicsDto.getSigner()));
                List<String> claimsbySigner = claimRepository.findBySignerId(signer.getUserId());

                if(claimsbySigner == null || claimsbySigner.isEmpty()) {
                    getClaimTopicsResponseDto.setMessage("No claim topics found for the signer: " + getClaimTopicsDto.getSigner());
                    getClaimTopicsResponseDto.setClaimTopics(Collections.emptyList());
                    getClaimTopicsResponseDto.setClaimTopicsHashes(Collections.emptyList());
                    return ResponseEntity.ok(getClaimTopicsResponseDto);
                } else {
                    // Map claim topics to their names
                    List<String> claimTopics = claimsbySigner;
                    getClaimTopicsResponseDto.setClaimTopics(claimTopics);
                }
            }
        } catch (Exception e) {
            throw new ClaimException(e.getMessage() != null ? e.getMessage() : "Failed to get claim topic", e);
        }
        getClaimTopicsResponseDto.setMessage("Claim topics retrieved successfully");
        return ResponseEntity.ok(getClaimTopicsResponseDto);
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

            List<BigInteger> claimTopics = addTrustedIssuerClaimTopicDto.getTopics().stream()
                    .map(topic -> new BigInteger(Hash.sha3String(topic).substring(2), 16))
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
            throw new ClaimException("Failed to add trusted issuer claim topics: " + e.getMessage(), e);
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
                    .map(topic -> new BigInteger(Hash.sha3String(topic).substring(2), 16))
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
            throw new ClaimException("Failed to update trusted issuer claim topics: " + e.getMessage(), e);
        }
        return ResponseEntity.ok(response);
    }

    public static BigInteger topicToUint256(String topic) {
        String hash = Hash.sha3String(topic);
        return new BigInteger(hash.substring(2), 16);
    }
}


