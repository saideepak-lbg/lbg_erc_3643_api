package com.lbg.ethereum.services.impl;

import com.lbg.ethereum.DTOs.*;
import com.lbg.ethereum.contracts.IImplementationAuthority;
import com.lbg.ethereum.contracts.Identity;
import com.lbg.ethereum.contracts.IdentityProxy;
import com.lbg.ethereum.contracts.IdentityRegistry;
import com.lbg.ethereum.entities.transactions.Transaction;
import com.lbg.ethereum.entities.users.UserEntity;
import com.lbg.ethereum.entities.users.UserIdentity;
import com.lbg.ethereum.enums.TransactionType;
import com.lbg.ethereum.exception.handlers.OnChainIdCreationException;
import com.lbg.ethereum.exception.handlers.RegisterOnChainIdException;
import com.lbg.ethereum.repository.TransactionRepository;
import com.lbg.ethereum.repository.UserIdentityRepository;
import com.lbg.ethereum.repository.UserRepository;
import com.lbg.ethereum.services.UserService;
import com.lbg.ethereum.utils.TransactionMapper;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Web3j web3j;
    Environment environment;
    UserRepository userRepository;
    ModelMapper modelMapper;
    UserIdentityRepository userIdentityRepository;
    TransactionRepository transactionRepository;

    public UserServiceImpl(Web3j web3j, Environment environment,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           UserIdentityRepository userIdentityRepository,
                           TransactionRepository transactionRepository) {
        this.web3j = web3j;
        this.environment = environment;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userIdentityRepository = userIdentityRepository;
        this.transactionRepository = transactionRepository;
    }


    public CreateOnChainIdResponse createOnChainId(OnChainIdCreationDto onChainIdCreationDto) {
        String onchainIdAddress;
        TransactionReceipt txReceipt;
        final String identityImplementationAuthorityAddress = environment.getProperty("identityImplementationAuthority_smart_contract_address");
        try {
            Credentials credentials = Credentials.create(environment.getProperty(onChainIdCreationDto.getSigner()+"PrivateKey"));
            // This is your "signer"
            IdentityProxy identityProxy = IdentityProxy.deploy(
                    web3j,
                    credentials,
                    new DefaultGasProvider(),
                    identityImplementationAuthorityAddress,
                    onChainIdCreationDto.getUserAddress()
            ).send();
            onchainIdAddress = identityProxy.getContractAddress();
            txReceipt = identityProxy.getTransactionReceipt().orElse(null);

            if (onchainIdAddress == null) {
                throw new OnChainIdCreationException("unable to create onchain-id, onchain-id address is null.");
            }
            if (txReceipt == null || !txReceipt.isStatusOK()) {
                throw new OnChainIdCreationException("Transaction failed or receipt not found.");
            }
            Optional<UserEntity> user = userRepository.findByWalletKey(onChainIdCreationDto.getUserAddress());
            if (user.isEmpty()) {
                throw new OnChainIdCreationException("No user exists for the given wallet key: " + onChainIdCreationDto.getUserAddress());
            }
            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(txReceipt);
            transaction.setTransactionType(TransactionType.ON_CHAIN_ID_CREATION);

            UserEntity userEntity = user.get();
            UserIdentity userIdentity = new UserIdentity();
            UserIdentity userIdentityEntity = userIdentityRepository.findByIdentityAddressAndUserId(onchainIdAddress,userEntity.getUserId()).
                    orElse(null);
            if(userIdentityEntity != null) {
                throw new OnChainIdCreationException("OnChain ID already exists for the given address: " + onchainIdAddress);
            }

            userIdentity.setUser(userEntity);
            userIdentity.setIdentityAddress(onchainIdAddress);
            userIdentity.setRegistered(false);
            userIdentity.setTransaction(transaction);
            userIdentity.setSignerId(userRepository.findByUserName(onChainIdCreationDto.getSigner()).orElseThrow(
                    () -> new OnChainIdCreationException("No signer exists for the given signer: " + onChainIdCreationDto.getSigner())).getUserId());

            //write to DB
            Transaction transactionEntity = transactionRepository.findById(transaction.getTransactionHash()).orElse(null);
            if(transactionEntity != null && transactionEntity.getTransactionHash().equals(transaction.getTransactionHash()) && transactionEntity.getTransactionType() == TransactionType.ON_CHAIN_ID_CREATION) {
                throw new OnChainIdCreationException("Transaction already exists for the given hash: " + transaction.getTransactionHash());
            }
            transactionRepository.save(transaction);
            userIdentityRepository.save(userIdentity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OnChainIdCreationException("Failed to create OnChain ID: " + e.getMessage(), e);
        }
        CreateOnChainIdResponse response = new CreateOnChainIdResponse();
        response.setAddress(onchainIdAddress);
        response.setTx(txReceipt);
        return response;
    }



    public ResponseEntity<RegisterIdentityReponseDto> registerIdentity(RegisterIdentityDto registerIdentityDto) {
        RegisterIdentityReponseDto response = new RegisterIdentityReponseDto();
        TransactionReceipt txReciept;
        try {
            // Get contract address from DTO or environment
            String identityRegistryAddress = registerIdentityDto.getIdentityRegistryAddress();
            if (identityRegistryAddress == null || identityRegistryAddress.isEmpty()) {
                identityRegistryAddress = environment.getProperty("identityRegistry_smart_contract_address");
            }

            // Get signer credentials (tokenAgent)
            //token agent already present in the database, but the private key is stored in the environment - later may use fireblock to fetch the private key
            String tokenAgentprivateKey = environment.getProperty("tokenAgentPrivateKey");
            Credentials tokenAgent = Credentials.create(tokenAgentprivateKey);

            // Load the IdentityRegistry contract
            IdentityRegistry registry = IdentityRegistry.load(
                    identityRegistryAddress,
                    web3j,
                    tokenAgent,
                    new DefaultGasProvider()
            );

            // Call registerIdentity(userAddress, userIdentity, country)
            txReciept = registry.registerIdentity(
                    registerIdentityDto.getUserAddress(),
                    registerIdentityDto.getUserIdentity(),
                    BigInteger.valueOf(registerIdentityDto.getCountryCode())
            ).send();

            if (txReciept == null || !txReciept.isStatusOK()) {
                throw new RegisterOnChainIdException("unable to register identity, transaction receipt is null or status is not OK.");
            }

            Transaction transaction = TransactionMapper.GenerateTransactionFromTransactionReceipt(txReciept);
            transaction.setTransactionType(TransactionType.REGISTER_ON_CHAIN_ID);
            transactionRepository.save(transaction);
            UserEntity userEntity = userRepository.findByWalletKey(registerIdentityDto.getUserAddress())
                    .orElseThrow(() -> new RegisterOnChainIdException("No user exists for the given wallet key: " + registerIdentityDto.getUserAddress()));
            // Update the user identity in the database, set registered to true
            UserIdentity userIdentity = userIdentityRepository.findByIdentityAddressAndUserId(registerIdentityDto.getUserIdentity(),userEntity.getUserId()).
                    orElseThrow(() -> new RegisterOnChainIdException("No user identity found for the given address: " + registerIdentityDto.getUserAddress()));
            userIdentity.setRegistered(true);
            userIdentityRepository.save(userIdentity);
        } catch (Exception e) {
            throw new RegisterOnChainIdException("Failed to register identity: " + e.getMessage(), e);
        }
        response.setMessage("Identity registered successfully");
        response.setTransactionReceipt(txReciept);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<GetUserClaimsResponseDto> getUserClaims(UserClaimDto userClaimDto) {
        GetUserClaimsResponseDto response = new GetUserClaimsResponseDto();
        try {
            Credentials credentials = Credentials.create(environment.getProperty("lbgPrivateKey"));
            Identity identity = Identity.load(
                    userClaimDto.getIdentityAddress(), // contract address
                    web3j,
                    credentials, // your signer
                    new DefaultGasProvider()
            );
            String topicHash = Hash.sha3String(userClaimDto.getTopic()); // returns 0x...
            BigInteger topicBigInt = new BigInteger(topicHash.substring(2), 16);
            // Call the contract method (assuming topic is a BigInteger)
            List<byte[]> claimIds = identity.getClaimIdsByTopic(topicBigInt).send();

            // Convert byte[] to hex string for JSON response
            List<String> claimIdsHex = claimIds.stream()
                    .map(org.web3j.utils.Numeric::toHexString)
                    .collect(Collectors.toList());

            response.setMessage("Claims retrieved successfully");
            response.setStatusCode(200);
            response.setReceipt(claimIdsHex);
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            ;
            response.setStatusCode(400);
            response.setMessage("Error retrieving claims: " + e.getMessage());
            response.setReceipt(List.of());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
