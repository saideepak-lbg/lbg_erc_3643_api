package com.lbg.ethereum.services.impl;

import com.lbg.ethereum.DTOs.*;
import com.lbg.ethereum.contracts.*;
import com.lbg.ethereum.entities.transactions.Transaction;
import com.lbg.ethereum.entities.users.UserEntity;
import com.lbg.ethereum.entities.users.UserIdentity;
import com.lbg.ethereum.enums.TransactionType;
import com.lbg.ethereum.exception.handlers.TokenException;
import com.lbg.ethereum.repository.*;
import com.lbg.ethereum.services.TokenService;
import com.lbg.ethereum.entities.Tokens.*;
import com.lbg.ethereum.utils.TransactionMapper;
import org.apache.catalina.User;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    private final Web3j web3j;
    Environment environment;
    TokenRepository tokenRepository;
    TokenTransactionRepository tokenTransactionRepository;
    UserRepository userRepository;
    UserIdentityRepository userIdentityRepository;
    TransactionRepository transactionRepository;

    public TokenServiceImpl(Web3j web3j,
                            Environment environment,
                            TokenRepository tokenRepository,
                            TokenTransactionRepository tokenTransactionRepository,
                            UserRepository userRepository,
                            UserIdentityRepository userIdentityRepository,
                            TransactionRepository transactionRepository) {
        this.web3j = web3j;
        this.environment = environment;
        this.tokenRepository = tokenRepository;
        this.tokenTransactionRepository = tokenTransactionRepository;
        this.userRepository = userRepository;
        this.userIdentityRepository = userIdentityRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public ResponseEntity<GetUserTokenResponseDto> getUserTokenBalance(GetUserTokensDto getUserTokensDto) {
        GetUserTokenResponseDto response;
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

            //userAddress = environment.getProperty(getUserTokensDto.getUserAddress());


            // Call balanceOf
            BigInteger balance = token.balanceOf(userAddress).send();

            // Return as a response object
            response = new GetUserTokenResponseDto();
            response.setMessage("User token balance retrieved successfully");
            response.setBalance(balance.toString());

        } catch (Exception e) {
            throw new TokenException(e.getMessage() != null ? e.getMessage() : "Failed to get user tokens", e);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApproveUserTokensForTransferResponse> approveUserTokensForTransfer(ApproveUserTokensForTransferDto approveUserTokensForTransferDto) {
        ApproveUserTokensForTransferResponse response = new ApproveUserTokensForTransferResponse();
        TransactionReceipt tx;
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

            String userAddress = approveUserTokensForTransferDto.getUserAddress();//identity address
            tx = token.approve(userAddress, approveUserTokensForTransferDto.getAmount()).send();

            if (tx == null || !tx.isStatusOK()) {
                throw new RuntimeException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }
            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.APPROVE_TOKENS_FOR_TRANSFER);
            transactionRepository.save(transaction);

            UserEntity userEntity = userRepository.findByWalletKey(approveUserTokensForTransferDto.getUserAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + approveUserTokensForTransferDto.getUserAddress()));

            TokenEntity tokenEntity = tokenRepository.findByUserId(userEntity.getUserId()).orElseThrow(() ->
                    new TokenException("Token not found for user: " + userEntity.getUserName()));

            TokenTransaction tokenTransaction = new TokenTransaction();
            tokenTransaction.setTokenId(tokenEntity.getTokenId());
            tokenTransaction.setTransactionHash(transaction.getTransactionHash());
            tokenTransactionRepository.save(tokenTransaction);

        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenException("Failed to approve user tokens for transfer: " + e.getMessage(), e);
        }
        response.setMessage("User is approved to transfer the requested amount of tokens successfully");
        response.setReceipt(tx);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TransferTokensReponseDto> transferTokens(TransferTokensDto transferTokensDto) {
        TransferTokensReponseDto transferTokensReponseDto = new TransferTokensReponseDto();
        TransactionReceipt tx;
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
            tx = token.transfer(userAddress, amount).send();
            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }
            UserEntity userEntity = userRepository.findByWalletKey(transferTokensDto.getUserAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + transferTokensDto.getUserAddress()));

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.TRANSFER_TOKENS);
            transactionRepository.save(transaction);
            TokenEntity tokenEntity = tokenRepository.findByUserId(userEntity.getUserId()).orElseThrow(() ->
                    new TokenException("Token not found for user: " + userEntity.getUserName()));

            tokenEntity.setTokenName(environment.getProperty("token_name"));
            tokenEntity.setTokenBalance(tokenEntity.getTokenBalance().equals(BigDecimal.ZERO) ? BigDecimal.valueOf(transferTokensDto.getAmount()) :
                    tokenEntity.getTokenBalance().subtract(BigDecimal.valueOf(transferTokensDto.getAmount())));
            tokenRepository.save(tokenEntity);

            TokenTransaction tokenTransaction = new TokenTransaction();
            tokenTransaction.setTokenId(tokenEntity.getTokenId());
            tokenTransaction.setTransactionHash(transaction.getTransactionHash());
            tokenTransactionRepository.save(tokenTransaction);

        } catch (Exception e) {
            throw new TokenException("Failed to transfer tokens: " + e.getMessage(), e);
        }
        transferTokensReponseDto.setMessage("able to transfer tokens successfully");
        transferTokensReponseDto.setTransactionReceipt(tx);
        return ResponseEntity.ok(transferTokensReponseDto);
    }

    @Override
    public ResponseEntity<FreezeTokenResponseDto> freezeToken(FreezeTokensDto freezeTokensDto) {
        FreezeTokenResponseDto freezeTokenResponseDto = new FreezeTokenResponseDto();
        TransactionReceipt tx;
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
            String userAddress = freezeTokensDto.getUserAddress();//identity address
            BigInteger amount = freezeTokensDto.getFreezeAmount();

            // Call freezePartialTokens
            tx = token.freezePartialTokens(userAddress, amount).send();
            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }
            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.FREEZE_TOKENS);
            transactionRepository.save(transaction);
            UserEntity userEntity = userRepository.findByWalletKey(freezeTokensDto.getUserAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + freezeTokensDto.getUserAddress()));
            TokenEntity tokenEntity = tokenRepository.findByUserId(userEntity.getUserId()).orElseThrow(() ->
                    new TokenException("Token not found for user: " + userEntity.getUserName()));
            tokenEntity.setTokenName(environment.getProperty("token_name"));
            tokenEntity.setTokenBalance(tokenEntity.getTokenBalance().subtract(
                    new BigDecimal(freezeTokensDto.getFreezeAmount())));
            tokenRepository.save(tokenEntity);

            TokenTransaction tokenTransaction = new TokenTransaction();
            tokenTransaction.setTokenId(tokenEntity.getTokenId());
            tokenTransaction.setTransactionHash(transaction.getTransactionHash());
            tokenTransactionRepository.save(tokenTransaction);

        } catch (Exception e) {
            freezeTokenResponseDto.setMessage("Failed to freeze tokens: " + e.getMessage());
            freezeTokenResponseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(freezeTokenResponseDto);
        }
        freezeTokenResponseDto.setMessage("User tokens frozen successfully");
        freezeTokenResponseDto.setTransactionReceipt(tx);
        return ResponseEntity.ok(freezeTokenResponseDto);
    }

    @Override
    public ResponseEntity<UnFreezeTokenResponseDto> unFreezeToken(UnFreezeTokensDto unFreezeTokensDto) {
        UnFreezeTokenResponseDto responseDto = new UnFreezeTokenResponseDto();
        TransactionReceipt tx;
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
            tx = token.unfreezePartialTokens(
                    unFreezeTokensDto.getUserAddress(),
                    unFreezeTokensDto.getUnFreezeAmount()
            ).send();
            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }
            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.UNFREEZE_TOKENS);
            transactionRepository.save(transaction);

            UserEntity userEntity = userRepository.findByWalletKey(unFreezeTokensDto.getUserAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + unFreezeTokensDto.getUserAddress()));

            TokenEntity tokenEntity = tokenRepository.findByUserId(userEntity.getUserId()).orElseThrow(() ->
                    new TokenException("Token not found for user: " + userEntity.getUserName()));
            tokenEntity.setTokenName(environment.getProperty("token_name"));
            tokenEntity.setTokenBalance(tokenEntity.getTokenBalance().add(
                    new BigDecimal(unFreezeTokensDto.getUnFreezeAmount())));
            tokenRepository.save(tokenEntity);

            TokenTransaction tokenTransaction = new TokenTransaction();
            tokenTransaction.setTokenId(tokenEntity.getTokenId());
            tokenTransaction.setTransactionHash(transaction.getTransactionHash());
            tokenTransactionRepository.save(tokenTransaction);

        } catch (Exception e) {
            responseDto.setMessage("Failed to unfreeze tokens: " + e.getMessage());
            responseDto.setTransactionReceipt(null);
            return ResponseEntity.internalServerError().body(responseDto);
        }

        responseDto.setMessage("User tokens unfreezed successfully");
        responseDto.setTransactionReceipt(tx);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<PauseTokenResponseDto> pauseToken(PauseTokenDto pauseTokensDto) {
        PauseTokenResponseDto pauseTokenResponseDto = new PauseTokenResponseDto();
        TransactionReceipt tx;
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
            tx = token.pause().send();
            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.PAUSE_TOKEN);
            transactionRepository.save(transaction);

        } catch (Exception e) {
            throw new TokenException("Failed to pause token: " + e.getMessage(), e);
        }
        pauseTokenResponseDto.setMessage("Token paused successfully");
        pauseTokenResponseDto.setTransactionReceipt(tx);
        return ResponseEntity.ok(pauseTokenResponseDto);
    }

    @Override
    public ResponseEntity<UnPauseTokenResponseDto> unPauseToken(UnPauseTokenDto unPauseTokenDto) {
        UnPauseTokenResponseDto unPauseTokenResponseDto = new UnPauseTokenResponseDto();
        TransactionReceipt tx;
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
            tx = token.unpause().send();
            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.UNPAUSE_TOKEN);
            transactionRepository.save(transaction);

        } catch (Exception e) {
            throw new TokenException("Failed to unpause token: " + e.getMessage(), e);
        }
        unPauseTokenResponseDto.setMessage("Token unpaused successfully");
        unPauseTokenResponseDto.setTransactionReceipt(tx);
        return ResponseEntity.ok(unPauseTokenResponseDto);
    }

    @Override
    public ResponseEntity<BurnTokensResponseDto> burnTokens(BurnTokensDto burnTokensDto) {
        BurnTokensResponseDto burnTokensResponseDto = new BurnTokensResponseDto();
        TransactionReceipt tx;
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
            tx = token.burn(
                    environment.getProperty(burnTokensDto.getUserAddress()),
                    BigInteger.valueOf(burnTokensDto.getAmount())
            ).send();

            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.BURN_TOKENS);
            transactionRepository.save(transaction);

            UserEntity userEntity = userRepository.findByWalletKey(burnTokensDto.getUserAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + burnTokensDto.getUserAddress()));
            TokenEntity tokenEntity = tokenRepository.findByUserId(userEntity.getUserId()).orElseThrow(() ->
                    new TokenException("Token not found for user: " + userEntity.getUserName()));

            tokenEntity.setTokenBalance(tokenEntity.getTokenBalance().subtract(BigDecimal.valueOf(burnTokensDto.getAmount())));
            tokenRepository.save(tokenEntity);

            TokenTransaction tokenTransaction = new TokenTransaction();
            tokenTransaction.setTokenId(tokenEntity.getTokenId());
            tokenTransaction.setTransactionHash(transaction.getTransactionHash());

            tokenTransactionRepository.save(tokenTransaction);

        } catch (Exception e) {
            throw new TokenException("Failed to burn tokens: " + e.getMessage(), e);
        }
        burnTokensResponseDto.setMessage("User tokens burned successfully");
        burnTokensResponseDto.setTransactionReceipt(tx);
        return ResponseEntity.ok(burnTokensResponseDto);
    }

    @Override
    public ResponseEntity<TransferApprovedTokensResponseDto> transferApprovedTokens(TransferApprovedTokensDto transferApprovedTokensDto) {
        TransferApprovedTokensResponseDto transferApprovedTokensResponseDto = new TransferApprovedTokensResponseDto();
        TransactionReceipt tx;
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
            tx = token.transferFrom(
                    transferApprovedTokensDto.getFromAddress(),
                    transferApprovedTokensDto.getToAddress(),
                    BigInteger.valueOf(transferApprovedTokensDto.getTransferAmount())
            ).send();
            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.TRANSFER_APPROVED_TOKENS);
            transactionRepository.save(transaction);

            UserEntity sender = userRepository.findByWalletKey(transferApprovedTokensDto.getFromAddress()).
                    orElseThrow(() -> new TokenException("sender not found for address: " + transferApprovedTokensDto.getFromAddress()));
            UserEntity recipient = userRepository.findByWalletKey(transferApprovedTokensDto.getToAddress()).
                    orElseThrow(() -> new TokenException("recipient not found for address: " + transferApprovedTokensDto.getToAddress()));

            // Update token balances
            TokenEntity senderTokenEntity = tokenRepository.findByUserId(sender.getUserId()).orElseThrow(() ->
                    new TokenException("Token not found for sender: " + sender.getUserName()));

            TokenEntity recipientTokenEntity = tokenRepository.findByUserId(recipient.getUserId()).orElseThrow(() ->
                    new TokenException("Token not found for recipient: " + recipient.getUserName()));

            // Update sender's token balance
            senderTokenEntity.setTokenBalance(senderTokenEntity.getTokenBalance().subtract(BigDecimal.valueOf(transferApprovedTokensDto.getTransferAmount())));
            // Update recipient's token balance
            recipientTokenEntity.setTokenBalance(recipientTokenEntity.getTokenBalance().add(BigDecimal.valueOf(transferApprovedTokensDto.getTransferAmount())));

            // Save updated token entities
            tokenRepository.save(senderTokenEntity);
            tokenRepository.save(recipientTokenEntity);

            // Create and save token transaction
            TokenTransaction senderTokenTransaction = new TokenTransaction();
            senderTokenTransaction.setTokenId(senderTokenEntity.getTokenId());
            senderTokenTransaction.setTransactionHash(transaction.getTransactionHash());
            tokenTransactionRepository.save(senderTokenTransaction);

            TokenTransaction recipientTokenTransaction = new TokenTransaction();
            recipientTokenTransaction.setTokenId(recipientTokenEntity.getTokenId());
            recipientTokenTransaction.setTransactionHash(transaction.getTransactionHash());
            tokenTransactionRepository.save(recipientTokenTransaction);


        } catch (Exception e) {
            throw new TokenException("Failed to transfer approved tokens: " + e.getMessage(), e);
        }

        transferApprovedTokensResponseDto.setMessage("Approved tokens transferred successfully");
        transferApprovedTokensResponseDto.setTransactionReceipt(tx);
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
            throw new TokenException("Failed to get token details: " + e.getMessage(), e);
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<MintTokenResponseDto>
    mintTokens(MintTokensDto mintTokensDto) {
        MintTokenResponseDto response = new MintTokenResponseDto();
        TransactionReceipt tx;
        try {
            String tokenAddress = mintTokensDto.getTokenAddress();
            if (tokenAddress == null || tokenAddress.isEmpty()) {
                tokenAddress = environment.getProperty("token_smart_contract_address");
            }

            // Get signer credentials
            String privateKey = environment.getProperty(mintTokensDto.getSigner() + "PrivateKey");
            Credentials tokenIssuer = Credentials.create(privateKey);

            // Load the token contract
            Token token = Token.load(
                    tokenAddress,
                    web3j,
                    tokenIssuer,
                    new DefaultGasProvider()
            );

            // Call mint(recipientAddress, amount)
            tx = token.mint(
                    mintTokensDto.getRecipientAddress(),
                    BigInteger.valueOf(mintTokensDto.getAmount())
            ).send();
            if (tx == null || !tx.isStatusOK()) {
                throw new RuntimeException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.MINT_TOKEN);

            UserEntity userEntity = userRepository.findByWalletKey(mintTokensDto.getRecipientAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + mintTokensDto.getRecipientAddress()));

            TokenEntity tokenEntity = tokenRepository.findByUserId(userEntity.getUserId()).orElseGet(TokenEntity::new);
            tokenEntity.setUser(userEntity);
            tokenEntity.setTokenBalance(tokenEntity.getTokenBalance().equals(BigDecimal.ZERO) ? BigDecimal.valueOf(mintTokensDto.getAmount()) :
                    tokenEntity.getTokenBalance().add(BigDecimal.valueOf(mintTokensDto.getAmount())));
            tokenEntity.setTokenName(environment.getProperty("token_name"));

            tokenEntity = tokenRepository.save(tokenEntity);
            transactionRepository.save(transaction);

            TokenTransaction tokenTransaction = new TokenTransaction();
            tokenTransaction.setTokenId(tokenEntity.getTokenId());
            tokenTransaction.setTransactionHash(transaction.getTransactionHash());
            tokenTransactionRepository.save(tokenTransaction);

        } catch (Exception e) {
            throw new TokenException("Failed to mint tokens: " + e.getMessage(), e);
        }
        response.setMessage("Tokens minted successfully");
        response.setTransactionReceipt(tx);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RecoverAccountResponseDto> recoverAccount(RecoverAccountDto recoverAccountDto) {
        RecoverAccountResponseDto response = new RecoverAccountResponseDto();
        TransactionReceipt tx;
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
            tx = token.recoveryAddress(
                    recoverAccountDto.getLostWalletAddress(),
                    recoverAccountDto.getNewWalletAddress(),
                    recoverAccountDto.getUserIdentity()
            ).send();
            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }
            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.RECOVER_ACCOUNT);
            transactionRepository.save(transaction);

            UserEntity userEntity = userRepository.findByWalletKey(recoverAccountDto.getLostWalletAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + recoverAccountDto.getNewWalletAddress()));
            userEntity.setWalletKey(recoverAccountDto.getNewWalletAddress());
            userRepository.save(userEntity);

        } catch (Exception e) {
            throw new TokenException("Failed to recover account: " + e.getMessage(), e);
        }
        response.setMessage("User account recovered successfully");
        response.setTransactionReceipt(tx);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<FreezeAccountResponseDto> freezeAccount(FreezeAccountDto freezeAccountDto) {
        FreezeAccountResponseDto response = new FreezeAccountResponseDto();
        TransactionReceipt tx;
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
            tx = token.setAddressFrozen(
                    freezeAccountDto.getUserAddress(),
                    freezeAccountDto.getStatus() != null ? freezeAccountDto.getStatus() : Boolean.TRUE // default to true if null
            ).send();

            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.FREEZE_ACCOUNT);
            transactionRepository.save(transaction);

            UserEntity userEntity = userRepository.findByWalletKey(freezeAccountDto.getUserAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + freezeAccountDto.getUserAddress()));

            userEntity.setFrozen(true);
            userRepository.save(userEntity);

        } catch (Exception e) {
            throw new TokenException("Failed to freeze account: " + e.getMessage(), e);
        }
        response.setMessage("User account frozen successfully");
        response.setTransactionReceipt(tx);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UnfreezeAccountResponseDto> unFreezeAccount(UnFreezeAccountDto unFreezeAccountDto) {
        UnfreezeAccountResponseDto response = new UnfreezeAccountResponseDto();
        TransactionReceipt tx;
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
            tx = token.setAddressFrozen(
                    unFreezeAccountDto.getUserAddress(),
                    unFreezeAccountDto.getStatus() != null ? unFreezeAccountDto.getStatus() : Boolean.FALSE // default to false if null
            ).send();

            if (tx == null || !tx.isStatusOK()) {
                throw new TokenException("Transaction failed: " + (tx != null ? tx.getStatus() : "Unknown error"));
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(tx);
            transaction.setTransactionType(TransactionType.UNFREEZE_ACCOUNT);
            transactionRepository.save(transaction);

            UserEntity userEntity = userRepository.findByWalletKey(unFreezeAccountDto.getUserAddress()).
                    orElseThrow(() -> new TokenException("User not found for address: " + unFreezeAccountDto.getUserAddress()));

            userEntity.setFrozen(false);
            userRepository.save(userEntity);

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

            String key = Hash.sha3(
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
        } catch (Exception e) {
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
