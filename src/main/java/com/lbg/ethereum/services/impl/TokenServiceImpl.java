package com.lbg.ethereum.services.impl;

import com.lbg.ethereum.DTOs.*;
import com.lbg.ethereum.contracts.*;
import com.lbg.ethereum.services.TokenService;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Numeric;
import org.web3j.utils.Bytes;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    private final Web3j web3j;
    Environment environment;

    public TokenServiceImpl(Web3j web3j, Environment environment) {
        this.web3j = web3j;
        this.environment = environment;
    }





    @Override
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
//            TransactionReceipt tx = identity.addClaim(
//                    topicBigInt,
//                    BigInteger.valueOf(addClaimDto.getScheme()),
//                    claimIssuer.getAddress(),
//                    signature.getBytes(),
//                    dataBytes,
//                    ""
//            ).send();
//            response.setStatusCode(200);
//            response.setMessage("Claim added successfully");
//            response.setReceipt(tx);


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

    @Override
    public ResponseEntity<GetUserTokenResponseDto> getUserTokenBalance(GetUserTokensDto getUserTokensDto) {
        try {
            // Use token address from body or from environment/config
            String tokenAddress = getUserTokensDto.getTokenAddress();
            if (tokenAddress == null || tokenAddress.isEmpty()) {
                tokenAddress = environment.getProperty("token_smart_contract_address");
            }


            Credentials signer = Credentials.create(environment.getProperty(getUserTokensDto.getSigner() + "PrivateKey"));

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Get the user's address (from body or env)
            String userAddress = getUserTokensDto.getUserAddress();

            userAddress = environment.getProperty(getUserTokensDto.getUserAddress());


            // Call balanceOf
            BigInteger balance = token.balanceOf(userAddress).send();

            // Return as a response object
            GetUserTokenResponseDto response = new GetUserTokenResponseDto();
            response.setMessage("User token balance retrieved successfully");
            response.setBalance(balance.toString());
            // or set tx details if needed

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : "Failed to get user tokens", e);
        }
    }

    @Override
    public ResponseEntity<ApproveUserTokensForTransferResponse> approveUserTokensForTransfer(ApproveUserTokensForTransferDto approveUserTokensForTransferDto) {
        ApproveUserTokensForTransferResponse response = new ApproveUserTokensForTransferResponse();
        try {
            //if needed can be taken from request body
            String tokenAddress = environment.getProperty("token_smart_contract_address");
            Credentials signer = Credentials.create(environment.getProperty(approveUserTokensForTransferDto.getSigner() + "PrivateKey"));

            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            String userAddress = approveUserTokensForTransferDto.getUserAddress();
            TransactionReceipt tx = token.approve(environment.getProperty(userAddress), approveUserTokensForTransferDto.getAmount()).send();


            response.setMessage("User is approved to transfer the requested amount of tokens successfully");
            response.setReceipt(tx);
        } catch (Exception e) {
            response.setMessage("Failed to get user tokens: " + e.getMessage());
            response.setReceipt(null);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransferTokensReponseDto> transferTokens(TransferTokensDto transferTokensDto) {
        TransferTokensReponseDto transferTokensReponseDto = new TransferTokensReponseDto();
        try {
            // Use token address from body or from environment/config
            String tokenAddress = transferTokensDto.getTokenAddress();
            tokenAddress = environment.getProperty("token_smart_contract_address");


            // Use signer credentials
            Credentials signer = Credentials.create(environment.getProperty(transferTokensDto.getSigner() + "PrivateKey"));

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Get the recipient's address
            String userAddress = transferTokensDto.getUserAddress();

            // Convert amount to BigInteger (if needed)
            BigInteger amount = BigInteger.valueOf(transferTokensDto.getAmount());

            // Call transfer
            TransactionReceipt tx = token.transfer(environment.getProperty(userAddress), amount).send();

            transferTokensReponseDto.setMessage("able to transfer tokens successfully");
            transferTokensReponseDto.setTransactionReceipt(tx);
        } catch (Exception e) {
            transferTokensReponseDto.setMessage("Failed to transfer tokens: " + e.getMessage());
            transferTokensReponseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(transferTokensReponseDto);
        }
        return ResponseEntity.ok(transferTokensReponseDto);
    }

    @Override
    public ResponseEntity<FreezeTokenResponseDto> freezeToken(FreezeTokensDto freezeTokensDto) {
        FreezeTokenResponseDto freezeTokenResponseDto = new FreezeTokenResponseDto();
        try {
            // Use token address from body or from environment/config
            String tokenAddress = environment.getProperty("token_smart_contract_address");

            // Use signer credentials
            Credentials signer = Credentials.create(environment.getProperty(freezeTokensDto.getSigner() + "PrivateKey"));

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Get the user's address and amount
            String userAddress = freezeTokensDto.getUserAddress();
            BigInteger amount = freezeTokensDto.getFreezeAmount();

            // Call freezePartialTokens
            TransactionReceipt tx = token.freezePartialTokens(environment.getProperty(userAddress), amount).send();

            freezeTokenResponseDto.setMessage("User tokens frozen successfully");
            freezeTokenResponseDto.setTransactionReceipt(tx);

        } catch (Exception e) {
            freezeTokenResponseDto.setMessage("Failed to freeze tokens: " + e.getMessage());
            freezeTokenResponseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(freezeTokenResponseDto);
        }
        return ResponseEntity.ok(freezeTokenResponseDto);
    }

    @Override
    public ResponseEntity<UnFreezeTokenResponseDto> unFreezeToken(UnFreezeTokensDto unFreezeTokensDto) {
        UnFreezeTokenResponseDto responseDto = new UnFreezeTokenResponseDto();
        try {
            // Get token address from body or environment
            String tokenAddress = unFreezeTokensDto.getTokenAddress();
            if (tokenAddress == null || tokenAddress.isEmpty()) {
                tokenAddress = environment.getProperty("token_smart_contract_address");
            }

            // Get signer credentials
            String privateKey = environment.getProperty(unFreezeTokensDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call unfreezePartialTokens(userAddress, amount)
            TransactionReceipt tx = token.unfreezePartialTokens(
                    environment.getProperty(unFreezeTokensDto.getUserAddress()),
                    unFreezeTokensDto.getUnFreezeAmount()
            ).send();

            responseDto.setMessage("User tokens unfreezed successfully");
            responseDto.setTransactionReceipt(tx);
        } catch (Exception e) {
            responseDto.setMessage("Failed to unfreeze tokens: " + e.getMessage());
            responseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(responseDto);
        }
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<PauseTokenResponseDto> pauseToken(PauseTokenDto pauseTokensDto) {
        PauseTokenResponseDto pauseTokenResponseDto = new PauseTokenResponseDto();
        try {
            // Get token address from environment or config
            String tokenAddress = environment.getProperty("token_smart_contract_address");

            // Get signer credentials
            String privateKey = environment.getProperty(pauseTokensDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call pause()
            TransactionReceipt tx = token.pause().send();

            pauseTokenResponseDto.setMessage("Token paused successfully");
            pauseTokenResponseDto.setTransactionReceipt(tx);
        } catch (Exception e) {
            pauseTokenResponseDto.setMessage("Failed to pause token: " + e.getMessage());
            pauseTokenResponseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(pauseTokenResponseDto);
        }
        return ResponseEntity.ok(pauseTokenResponseDto);
    }

    @Override
    public ResponseEntity<UnPauseTokenResponseDto> unPauseToken(UnPauseTokenDto unPauseTokenDto) {
        UnPauseTokenResponseDto unPauseTokenResponseDto = new UnPauseTokenResponseDto();
        try {
            // Get token address from environment or config
            String tokenAddress = environment.getProperty("token_smart_contract_address");

            // Get signer credentials
            String privateKey = environment.getProperty(unPauseTokenDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call unpause()
            TransactionReceipt tx = token.unpause().send();

            unPauseTokenResponseDto.setMessage("Token unpaused successfully");
            unPauseTokenResponseDto.setTransactionReceipt(tx);
        } catch (Exception e) {
            unPauseTokenResponseDto.setMessage("Failed to unpause token: " + e.getMessage());
            unPauseTokenResponseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(unPauseTokenResponseDto);
        }
        return ResponseEntity.ok(unPauseTokenResponseDto);
    }

    @Override
    public ResponseEntity<BurnTokensResponseDto> burnTokens(BurnTokensDto burnTokensDto) {
        BurnTokensResponseDto burnTokensResponseDto = new BurnTokensResponseDto();
        try {
            // Get token address from body or environment
            String tokenAddress = burnTokensDto.getTokenAddress();
            if (tokenAddress == null || tokenAddress.isEmpty()) {
                tokenAddress = environment.getProperty("token_smart_contract_address");
            }

            // Get signer credentials
            String privateKey = environment.getProperty(burnTokensDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call burn(userAddress, amount)
            TransactionReceipt tx = token.burn(
                    environment.getProperty(burnTokensDto.getUserAddress()),
                    BigInteger.valueOf(burnTokensDto.getAmount())
            ).send();

            burnTokensResponseDto.setMessage("User tokens burned successfully");
            burnTokensResponseDto.setTransactionReceipt(tx);
        } catch (Exception e) {
            burnTokensResponseDto.setMessage("Failed to burn tokens: " + e.getMessage());
            burnTokensResponseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(burnTokensResponseDto);
        }
        return ResponseEntity.ok(burnTokensResponseDto);
    }

    @Override
    public ResponseEntity<TransferApprovedTokensResponseDto> transferApprovedTokens(TransferApprovedTokensDto transferApprovedTokensDto) {
        TransferApprovedTokensResponseDto transferApprovedTokensResponseDto = new TransferApprovedTokensResponseDto();
        try {
            // Get token address from body or environment
            String tokenAddress = transferApprovedTokensDto.getTokenAddress();
            if (tokenAddress == null || tokenAddress.isEmpty()) {
                tokenAddress = environment.getProperty("token_smart_contract_address");
            }

            // Get signer credentials
            String privateKey = environment.getProperty(transferApprovedTokensDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call transferFrom(from, to, amount)
            TransactionReceipt tx = token.transferFrom(
                    environment.getProperty(transferApprovedTokensDto.getFromAddress()),
                    environment.getProperty(transferApprovedTokensDto.getToAddress()),
                    BigInteger.valueOf(transferApprovedTokensDto.getTransferAmount())
            ).send();
            transferApprovedTokensResponseDto.setMessage("Approved tokens transferred successfully");
            transferApprovedTokensResponseDto.setTransactionReceipt(tx);

        } catch (Exception e) {
            transferApprovedTokensResponseDto.setMessage("Failed to transfer approved tokens: " + e.getMessage());
            transferApprovedTokensResponseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(transferApprovedTokensResponseDto);
        }
        return ResponseEntity.ok(transferApprovedTokensResponseDto);
    }

    @Override
    public ResponseEntity<TokenDetailsResponseDto> getTokenDetails(TokenDetailsRequestDto tokenDetailsRequestDto) {
        TokenDetailsResponseDto response = new TokenDetailsResponseDto();
        try {
            // Get token address from parameter or environment
            String tokenAddress = tokenDetailsRequestDto.getTokenAddress();
            if (tokenAddress == null || tokenAddress.isEmpty()) {
                tokenAddress = environment.getProperty("token_smart_contract_address");
            }
            String privateKey = environment.getProperty(tokenDetailsRequestDto.getSigner() + "PrivateKey");
            Credentials signer = Credentials.create(privateKey);

            Token token = Token.load(
                    tokenAddress,
                    web3j,
                   signer, // No credentials needed for view methods
                    new DefaultGasProvider()
            );

            String name = token.name().send();
            String symbol = token.symbol().send();
            String owner = token.owner().send();
            Boolean paused = token.paused().send();
            BigInteger totalSupply = token.totalSupply().send();


            response.setName(name);
            response.setSymbol(symbol);
            response.setOwner(owner);
            response.setPaused(paused);
            response.setTotalSupply(totalSupply.toString());
            response.setMessage("Token details retrieved successfully");
        } catch (Exception e) {
            response.setMessage("Failed to retrieve token details: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<MintTokenResponseDto> mintTokens(MintTokensDto mintTokensDto) {
        MintTokenResponseDto response = new MintTokenResponseDto();
        try{
            String tokenAddress = mintTokensDto.getTokenAddress();
        if (tokenAddress == null || tokenAddress.isEmpty()) {
            tokenAddress = environment.getProperty("token_smart_contract_address");
        }

        // Get signer credentials
        String privateKey = environment.getProperty(mintTokensDto.getSigner()+"PrivateKey");
        Credentials tokenIssuer = Credentials.create(privateKey);

        // Load the token contract
        Token token = Token.load(
                tokenAddress,
                web3j,
                tokenIssuer,
                new DefaultGasProvider()
        );

        // Call mint(recipientAddress, amount)
        TransactionReceipt tx = token.mint(
                environment.getProperty(mintTokensDto.getRecipientAddress()),
                BigInteger.valueOf(mintTokensDto.getAmount())
        ).send();

        response.setMessage("Tokens minted successfully");
        response.setTransactionReceipt(tx);
    } catch (Exception e) {
        throw new RuntimeException("Failed to mint tokens: " + e.getMessage(), e);
    }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RecoverAccountResponseDto> recoverAccount(RecoverAccountDto recoverAccountDto) {
        RecoverAccountResponseDto response = new RecoverAccountResponseDto();
        try {
            // Use token address from environment/config
            String tokenAddress = environment.getProperty("token_smart_contract_address");

            // Use signer credentials
            Credentials signer = Credentials.create(environment.getProperty(recoverAccountDto.getSigner() + "PrivateKey"));

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call recoveryAddress with lostWallet, newWallet, and userIdentity
            TransactionReceipt tx = token.recoveryAddress(
                    environment.getProperty(recoverAccountDto.getLostWalletAddress()),
                    environment.getProperty(recoverAccountDto.getNewWalletAddress()),
                    recoverAccountDto.getUserIdentity()
            ).send();

            response.setMessage("User account recovered successfully");
            response.setTransactionReceipt(tx);
        } catch (Exception e) {
            response.setMessage("Failed to recover account: " + e.getMessage());
            response.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<FreezeAccountResponseDto> freezeAccount(FreezeAccountDto freezeAccountDto) {
        FreezeAccountResponseDto response = new FreezeAccountResponseDto();
        try {
            // Use token address from body or from environment/config
            String tokenAddress = freezeAccountDto.getTokenAddress();
            if (tokenAddress == null || tokenAddress.isEmpty()) {
                tokenAddress = environment.getProperty("token_smart_contract_address");
            }

            // Use signer credentials
            Credentials signer = Credentials.create(environment.getProperty(freezeAccountDto.getSigner() + "PrivateKey"));

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call setAddressFrozen with userAddress and status
            TransactionReceipt tx = token.setAddressFrozen(
                    environment.getProperty(freezeAccountDto.getUserAddress()),
                    freezeAccountDto.getStatus() != null ? freezeAccountDto.getStatus() : Boolean.TRUE // default to true if null
            ).send();

            response.setMessage("User account frozen successfully");
            response.setTransactionReceipt(tx);
        } catch (Exception e) {
            response.setMessage("Failed to freeze account: " + e.getMessage());
            response.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UnfreezeAccountResponseDto> unFreezeAccount(UnFreezeAccountDto unFreezeAccountDto) {
        UnfreezeAccountResponseDto response = new UnfreezeAccountResponseDto();
        try {
            // Use token address from body or from environment/config
            String tokenAddress = unFreezeAccountDto.getTokenAddress();
            if (tokenAddress == null || tokenAddress.isEmpty()) {
                tokenAddress = environment.getProperty("token_smart_contract_address");
            }

            // Use signer credentials
            Credentials signer = Credentials.create(environment.getProperty(unFreezeAccountDto.getSigner() + "PrivateKey"));

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    signer,
                    new DefaultGasProvider()
            );

            // Call setAddressFrozen with userAddress and status
            TransactionReceipt tx = token.setAddressFrozen(
                    environment.getProperty(unFreezeAccountDto.getUserAddress()),
                    unFreezeAccountDto.getStatus() != null ? unFreezeAccountDto.getStatus() : Boolean.FALSE // default to false if null
            ).send();

            response.setMessage("User account unfrozen successfully");
            response.setTransactionReceipt(tx);
        } catch (Exception e) {
            response.setMessage("Failed to unfreeze account: " + e.getMessage());
            response.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AddKeyResponseDto> addKey(AddkeyDto addkeyDto) {
       AddKeyResponseDto addKeyResponseDto = new AddKeyResponseDto();
       try {
           Credentials managementSigner = Credentials.create(environment.getProperty("lbgPrivateKey"));

           String key =Hash.sha3(
                  TypeEncoder.encode(new org.web3j.abi.datatypes.Address(addkeyDto.getKey()))
           );

           byte[] keyBytes = Numeric.hexStringToByteArray(key);
           // 2. Load the Identity contract
           Identity identity = Identity.load(
                   addkeyDto.getIdentityAddress(),
                   web3j,
                   managementSigner,
                   new DefaultGasProvider()
           );

           // 3. Call addKey on the contract
           TransactionReceipt tx = identity.addKey(
                   keyBytes, // Should be a 32-byte hex string (keccak256 hash)
                   BigInteger.valueOf(addkeyDto.getPurpose()),
                   BigInteger.valueOf(addkeyDto.getKeyType())
           ).send();

           addKeyResponseDto.setMessage("Key added successfully");
              addKeyResponseDto.setTransactionReceipt(tx);
       } catch(Exception e) {
           throw new RuntimeException("Failed to add key: " + e.getMessage(), e);
       }
       return ResponseEntity.ok(addKeyResponseDto);
    }


    private String signMessage(Credentials credentials, byte[] message) {
        Sign.SignatureData signatureData = Sign.signMessage(message, credentials.getEcKeyPair(), false);
        byte[] r = signatureData.getR();
        byte[] s = signatureData.getS();
        byte v = signatureData.getV()[0]; // get the first byte, not the array!
        byte[] signature = new byte[r.length + s.length + 1];
        System.arraycopy(r, 0, signature, 0, r.length);
        System.arraycopy(s, 0, signature, r.length, s.length);
        signature[64] = v; // v is always 1 byte
        return Numeric.toHexString(signature);
    }
    public static BigInteger topicToUint256(String topic) {
        String hash = Hash.sha3String(topic);
        return new BigInteger(hash.substring(2), 16);
    }
}
