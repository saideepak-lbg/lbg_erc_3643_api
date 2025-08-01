package com.lbg.ethereum.contracts;

import io.reactivex.Flowable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class TrustedIssuersRegistry extends Contract {
    public static final String BINARY = "\"0x608060405234801561001057600080fd5b50611451806100206000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c8063b93d28eb11610071578063b93d28eb1461014c578063c28fb2781461015f578063d9dd24c51461017f578063e1c7392a14610187578063ef2ed1a41461018f578063f2fde38b146101a257600080fd5b806304bc7e84146100b957806334a89987146100ce57806352c111d1146100f6578063715018a6146101165780638da5cb5b1461011e5780639f63ea9814610139575b600080fd5b6100cc6100c7366004611137565b6101b5565b005b6100e16100dc3660046111bf565b610551565b60405190151581526020015b60405180910390f35b6101096101043660046111eb565b610611565b6040516100ed9190611204565b6100cc61067d565b6033546040516001600160a01b0390911681526020016100ed565b6100cc610147366004611137565b610691565b6100cc61015a366004611251565b610918565b61017261016d366004611251565b610cb9565b6040516100ed9190611275565b610109610d8b565b6100cc610ded565b6100e161019d366004611251565b610efe565b6100cc6101b0366004611251565b610f2c565b6101bd610fa2565b6001600160a01b0383166101ec5760405162461bcd60e51b81526004016101e3906112ad565b60405180910390fd5b6001600160a01b038316600090815260666020526040812054900361024a5760405162461bcd60e51b81526020600482015260146024820152732727aa1030903a393ab9ba32b21034b9b9bab2b960611b60448201526064016101e3565b600f81111561026b5760405162461bcd60e51b81526004016101e3906112e4565b806102b85760405162461bcd60e51b815260206004820152601c60248201527f636c61696d20746f706963732063616e6e6f7420626520656d7074790000000060448201526064016101e3565b60005b6001600160a01b03841660009081526066602052604090205481101561046c576001600160a01b038416600090815260666020526040812080548390811061030557610305611329565b600091825260208083209091015480835260679091526040822054909250905b8181101561045657600083815260676020526040902080546001600160a01b03891691908390811061035957610359611329565b6000918252602090912001546001600160a01b03160361044457600083815260676020526040902061038c600184611355565b8154811061039c5761039c611329565b60009182526020808320909101548583526067909152604090912080546001600160a01b0390921691839081106103d5576103d5611329565b600091825260208083209190910180546001600160a01b0319166001600160a01b03949094169390931790925584815260679091526040902080548061041d5761041d611368565b600082815260209020810160001990810180546001600160a01b0319169055019055610456565b8061044e8161137e565b915050610325565b50505080806104649061137e565b9150506102bb565b506001600160a01b03831660009081526066602052604090206104909083836110a8565b5060005b8181101561050857606760008484848181106104b2576104b2611329565b6020908102929092013583525081810192909252604001600090812080546001810182559082529190200180546001600160a01b0319166001600160a01b038616179055806105008161137e565b915050610494565b50826001600160a01b03167fec753cfc52044f61676f18a11e500093a9f2b1cd5e4942bc476f2b0438159bcf8383604051610544929190611397565b60405180910390a2505050565b6001600160a01b03821660009081526066602090815260408083208054825181850281018501909352808352928492919084908301828280156105b357602002820191906000526020600020905b81548152602001906001019080831161059f575b5050505050905060005b8281101561060357848282815181106105d8576105d8611329565b6020026020010151036105f1576001935050505061060b565b806105fb8161137e565b9150506105bd565b506000925050505b92915050565b60008181526067602090815260409182902080548351818402810184019094528084526060939283018282801561067157602002820191906000526020600020905b81546001600160a01b03168152600190910190602001808311610653575b50505050509050919050565b610685610fa2565b61068f6000610ffc565b565b610699610fa2565b6001600160a01b0383166106bf5760405162461bcd60e51b81526004016101e3906112ad565b6001600160a01b038316600090815260666020526040902054156107255760405162461bcd60e51b815260206004820152601d60248201527f747275737465642049737375657220616c72656164792065786973747300000060448201526064016101e3565b8061077e5760405162461bcd60e51b8152602060048201526024808201527f7472757374656420636c61696d20746f706963732063616e6e6f7420626520656044820152636d70747960e01b60648201526084016101e3565b600f81111561079f5760405162461bcd60e51b81526004016101e3906112e4565b6065546032116108025760405162461bcd60e51b815260206004820152602860248201527f63616e6e6f742068617665206d6f7265207468616e2035302074727573746564604482015267206973737565727360c01b60648201526084016101e3565b60658054600181019091557f8ff97419363ffd7000167f130ef7168fbea05faf9251824ca5043f113cc6a7c70180546001600160a01b0319166001600160a01b03851690811790915560009081526066602052604090206108649083836110a8565b5060005b818110156108dc576067600084848481811061088657610886611329565b6020908102929092013583525081810192909252604001600090812080546001810182559082529190200180546001600160a01b0319166001600160a01b038616179055806108d48161137e565b915050610868565b50826001600160a01b03167ffedc33fd34859594822c0ff6f3f4f9fc279cc6d5cae53068f706a088e45008728383604051610544929190611397565b610920610fa2565b6001600160a01b0381166109465760405162461bcd60e51b81526004016101e3906112ad565b6001600160a01b03811660009081526066602052604081205490036109a45760405162461bcd60e51b81526020600482015260146024820152732727aa1030903a393ab9ba32b21034b9b9bab2b960611b60448201526064016101e3565b60655460005b81811015610aaa57826001600160a01b0316606582815481106109cf576109cf611329565b6000918252602090912001546001600160a01b031603610a985760656109f6600184611355565b81548110610a0657610a06611329565b600091825260209091200154606580546001600160a01b039092169183908110610a3257610a32611329565b9060005260206000200160006101000a8154816001600160a01b0302191690836001600160a01b031602179055506065805480610a7157610a71611368565b600082815260209020810160001990810180546001600160a01b0319169055019055610aaa565b80610aa28161137e565b9150506109aa565b5060005b6001600160a01b038316600090815260666020526040902054811015610c5f576001600160a01b0383166000908152606660205260408120805483908110610af857610af8611329565b600091825260208083209091015480835260679091526040822054909250905b81811015610c4957600083815260676020526040902080546001600160a01b038816919083908110610b4c57610b4c611329565b6000918252602090912001546001600160a01b031603610c37576000838152606760205260409020610b7f600184611355565b81548110610b8f57610b8f611329565b60009182526020808320909101548583526067909152604090912080546001600160a01b039092169183908110610bc857610bc8611329565b600091825260208083209190910180546001600160a01b0319166001600160a01b039490941693909317909255848152606790915260409020805480610c1057610c10611368565b600082815260209020810160001990810180546001600160a01b0319169055019055610c49565b80610c418161137e565b915050610b18565b5050508080610c579061137e565b915050610aae565b506001600160a01b0382166000908152606660205260408120610c81916110f3565b6040516001600160a01b038316907f2214ded40113cc3fb63fc206cafee88270b0a903dac7245d54efdde30ebb032190600090a25050565b6001600160a01b03811660009081526066602052604081205460609103610d225760405162461bcd60e51b815260206004820152601c60248201527f747275737465642049737375657220646f65736e27742065786973740000000060448201526064016101e3565b6001600160a01b0382166000908152606660209081526040918290208054835181840281018401909452808452909183018282801561067157602002820191906000526020600020905b815481526020019060010190808311610d6c5750505050509050919050565b60606065805480602002602001604051908101604052809291908181526020018280548015610de357602002820191906000526020600020905b81546001600160a01b03168152600190910190602001808311610dc5575b5050505050905090565b600054610100900460ff1615808015610e0d5750600054600160ff909116105b80610e275750303b158015610e27575060005460ff166001145b610e8a5760405162461bcd60e51b815260206004820152602e60248201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160448201526d191e481a5b9a5d1a585b1a5e995960921b60648201526084016101e3565b6000805460ff191660011790558015610ead576000805461ff0019166101001790555b610eb561104e565b8015610efb576000805461ff0019169055604051600181527f7f26b83ff96e1f2b6a682f133852f6798a09c465da95921460cefb38474024989060200160405180910390a15b50565b6001600160a01b03811660009081526066602052604081205415610f2457506001919050565b506000919050565b610f34610fa2565b6001600160a01b038116610f995760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084016101e3565b610efb81610ffc565b6033546001600160a01b0316331461068f5760405162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657260448201526064016101e3565b603380546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b600054610100900460ff166110755760405162461bcd60e51b81526004016101e3906113d0565b61068f600054610100900460ff1661109f5760405162461bcd60e51b81526004016101e3906113d0565b61068f33610ffc565b8280548282559060005260206000209081019282156110e3579160200282015b828111156110e35782358255916020019190600101906110c8565b506110ef92915061110d565b5090565b5080546000825590600052602060002090810190610efb91905b5b808211156110ef576000815560010161110e565b6001600160a01b0381168114610efb57600080fd5b60008060006040848603121561114c57600080fd5b833561115781611122565b9250602084013567ffffffffffffffff8082111561117457600080fd5b818601915086601f83011261118857600080fd5b81358181111561119757600080fd5b8760208260051b85010111156111ac57600080fd5b6020830194508093505050509250925092565b600080604083850312156111d257600080fd5b82356111dd81611122565b946020939093013593505050565b6000602082840312156111fd57600080fd5b5035919050565b6020808252825182820181905260009190848201906040850190845b818110156112455783516001600160a01b031683529284019291840191600101611220565b50909695505050505050565b60006020828403121561126357600080fd5b813561126e81611122565b9392505050565b6020808252825182820181905260009190848201906040850190845b8181101561124557835183529284019291840191600101611291565b6020808252601f908201527f696e76616c696420617267756d656e74202d207a65726f206164647265737300604082015260600190565b60208082526025908201527f63616e6e6f742068617665206d6f7265207468616e20313520636c61696d20746040820152646f7069637360d81b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b8181038181111561060b5761060b61133f565b634e487b7160e01b600052603160045260246000fd5b6000600182016113905761139061133f565b5060010190565b6020808252810182905260006001600160fb1b038311156113b757600080fd5b8260051b80856040850137919091016040019392505050565b6020808252602b908201527f496e697469616c697a61626c653a20636f6e7472616374206973206e6f74206960408201526a6e697469616c697a696e6760a81b60608201526080019056fea264697066735822122010f56c7a600e929ae1c35374added5243b4f2458bb5aa8e81c04f7280c667f9464736f6c63430008110033\"";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDTRUSTEDISSUER = "addTrustedIssuer";

    public static final String FUNC_GETTRUSTEDISSUERCLAIMTOPICS = "getTrustedIssuerClaimTopics";

    public static final String FUNC_GETTRUSTEDISSUERS = "getTrustedIssuers";

    public static final String FUNC_GETTRUSTEDISSUERSFORCLAIMTOPIC = "getTrustedIssuersForClaimTopic";

    public static final String FUNC_HASCLAIMTOPIC = "hasClaimTopic";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_ISTRUSTEDISSUER = "isTrustedIssuer";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVETRUSTEDISSUER = "removeTrustedIssuer";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPDATEISSUERCLAIMTOPICS = "updateIssuerClaimTopics";

    public static final Event CLAIMTOPICSUPDATED_EVENT = new Event("ClaimTopicsUpdated",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<DynamicArray<Uint256>>() {
            }));
    ;

    public static final Event INITIALIZED_EVENT = new Event("Initialized",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
            }));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    public static final Event TRUSTEDISSUERADDED_EVENT = new Event("TrustedIssuerAdded",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<DynamicArray<Uint256>>() {
            }));
    ;

    public static final Event TRUSTEDISSUERREMOVED_EVENT = new Event("TrustedIssuerRemoved",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }));
    ;

    @Deprecated
    protected TrustedIssuersRegistry(String contractAddress, Web3j web3j, Credentials credentials,
                                     BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TrustedIssuersRegistry(String contractAddress, Web3j web3j, Credentials credentials,
                                     ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TrustedIssuersRegistry(String contractAddress, Web3j web3j,
                                     TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TrustedIssuersRegistry(String contractAddress, Web3j web3j,
                                     TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<ClaimTopicsUpdatedEventResponse> getClaimTopicsUpdatedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLAIMTOPICSUPDATED_EVENT, transactionReceipt);
//        ArrayList<ClaimTopicsUpdatedEventResponse> responses = new ArrayList<ClaimTopicsUpdatedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            ClaimTopicsUpdatedEventResponse typedResponse = new ClaimTopicsUpdatedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.trustedIssuer = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.claimTopics = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static ClaimTopicsUpdatedEventResponse getClaimTopicsUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLAIMTOPICSUPDATED_EVENT, log);
        ClaimTopicsUpdatedEventResponse typedResponse = new ClaimTopicsUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.trustedIssuer = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.claimTopics = convertToNative((List<Type>) ((DynamicArray) eventValues.getNonIndexedValues().get(0)).getValue());
        return typedResponse;
    }

    public Flowable<ClaimTopicsUpdatedEventResponse> claimTopicsUpdatedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClaimTopicsUpdatedEventFromLog(log));
    }

    public Flowable<ClaimTopicsUpdatedEventResponse> claimTopicsUpdatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMTOPICSUPDATED_EVENT));
        return claimTopicsUpdatedEventFlowable(filter);
    }

//    public static List<InitializedEventResponse> getInitializedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(INITIALIZED_EVENT, transactionReceipt);
//        ArrayList<InitializedEventResponse> responses = new ArrayList<InitializedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            InitializedEventResponse typedResponse = new InitializedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static InitializedEventResponse getInitializedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(INITIALIZED_EVENT, log);
        InitializedEventResponse typedResponse = new InitializedEventResponse();
        typedResponse.log = log;
        typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<InitializedEventResponse> initializedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getInitializedEventFromLog(log));
    }

    public Flowable<InitializedEventResponse> initializedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(INITIALIZED_EVENT));
        return initializedEventFlowable(filter);
    }

//    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
//        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

//    public static List<TrustedIssuerAddedEventResponse> getTrustedIssuerAddedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRUSTEDISSUERADDED_EVENT, transactionReceipt);
//        ArrayList<TrustedIssuerAddedEventResponse> responses = new ArrayList<TrustedIssuerAddedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            TrustedIssuerAddedEventResponse typedResponse = new TrustedIssuerAddedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.trustedIssuer = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.claimTopics = (List<BigInteger>) ((Array) eventValues.getNonIndexedValues().get(0)).getNativeValueCopy();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static TrustedIssuerAddedEventResponse getTrustedIssuerAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRUSTEDISSUERADDED_EVENT, log);
        TrustedIssuerAddedEventResponse typedResponse = new TrustedIssuerAddedEventResponse();
        typedResponse.log = log;
        typedResponse.trustedIssuer = (String) eventValues.getIndexedValues().get(0).getValue();
typedResponse.claimTopics = convertToNative((List<Type>) ((DynamicArray) eventValues.getNonIndexedValues().get(0)).getValue());        return typedResponse;
    }

    public Flowable<TrustedIssuerAddedEventResponse> trustedIssuerAddedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTrustedIssuerAddedEventFromLog(log));
    }

    public Flowable<TrustedIssuerAddedEventResponse> trustedIssuerAddedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRUSTEDISSUERADDED_EVENT));
        return trustedIssuerAddedEventFlowable(filter);
    }

//    public static List<TrustedIssuerRemovedEventResponse> getTrustedIssuerRemovedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRUSTEDISSUERREMOVED_EVENT, transactionReceipt);
//        ArrayList<TrustedIssuerRemovedEventResponse> responses = new ArrayList<TrustedIssuerRemovedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            TrustedIssuerRemovedEventResponse typedResponse = new TrustedIssuerRemovedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.trustedIssuer = (String) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static TrustedIssuerRemovedEventResponse getTrustedIssuerRemovedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRUSTEDISSUERREMOVED_EVENT, log);
        TrustedIssuerRemovedEventResponse typedResponse = new TrustedIssuerRemovedEventResponse();
        typedResponse.log = log;
        typedResponse.trustedIssuer = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TrustedIssuerRemovedEventResponse> trustedIssuerRemovedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTrustedIssuerRemovedEventFromLog(log));
    }

    public Flowable<TrustedIssuerRemovedEventResponse> trustedIssuerRemovedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRUSTEDISSUERREMOVED_EVENT));
        return trustedIssuerRemovedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addTrustedIssuer(String _trustedIssuer,
                                                                   List<BigInteger> _claimTopics) {
        final Function function = new Function(
                FUNC_ADDTRUSTEDISSUER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _trustedIssuer),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                                org.web3j.abi.datatypes.generated.Uint256.class,
                                org.web3j.abi.Utils.typeMap(_claimTopics, org.web3j.abi.datatypes.generated.Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getTrustedIssuerClaimTopics(String _trustedIssuer) {
        final Function function = new Function(FUNC_GETTRUSTEDISSUERCLAIMTOPICS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _trustedIssuer)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {
                }));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getTrustedIssuers() {
        final Function function = new Function(FUNC_GETTRUSTEDISSUERS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {
                }));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getTrustedIssuersForClaimTopic(BigInteger claimTopic) {
        final Function function = new Function(FUNC_GETTRUSTEDISSUERSFORCLAIMTOPIC,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(claimTopic)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {
                }));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Boolean> hasClaimTopic(String _issuer, BigInteger _claimTopic) {
        final Function function = new Function(FUNC_HASCLAIMTOPIC,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _issuer),
                        new org.web3j.abi.datatypes.generated.Uint256(_claimTopic)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> init() {
        final Function function = new Function(
                FUNC_INIT,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isTrustedIssuer(String _issuer) {
        final Function function = new Function(FUNC_ISTRUSTEDISSUER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _issuer)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeTrustedIssuer(String _trustedIssuer) {
        final Function function = new Function(
                FUNC_REMOVETRUSTEDISSUER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _trustedIssuer)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateIssuerClaimTopics(String _trustedIssuer,
                                                                          List<BigInteger> _claimTopics) {
        final Function function = new Function(
                FUNC_UPDATEISSUERCLAIMTOPICS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _trustedIssuer),
                        new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                                org.web3j.abi.datatypes.generated.Uint256.class,
                                org.web3j.abi.Utils.typeMap(_claimTopics, org.web3j.abi.datatypes.generated.Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TrustedIssuersRegistry load(String contractAddress, Web3j web3j,
                                              Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TrustedIssuersRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TrustedIssuersRegistry load(String contractAddress, Web3j web3j,
                                              TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TrustedIssuersRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TrustedIssuersRegistry load(String contractAddress, Web3j web3j,
                                              Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TrustedIssuersRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TrustedIssuersRegistry load(String contractAddress, Web3j web3j,
                                              TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TrustedIssuersRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TrustedIssuersRegistry> deploy(Web3j web3j, Credentials credentials,
                                                            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TrustedIssuersRegistry.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<TrustedIssuersRegistry> deploy(Web3j web3j, Credentials credentials,
                                                            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TrustedIssuersRegistry.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<TrustedIssuersRegistry> deploy(Web3j web3j,
                                                            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TrustedIssuersRegistry.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<TrustedIssuersRegistry> deploy(Web3j web3j,
                                                            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TrustedIssuersRegistry.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

//    public static void linkLibraries(List<Contract.LinkReference> references) {
//        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
//    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class ClaimTopicsUpdatedEventResponse extends BaseEventResponse {
        public String trustedIssuer;

        public List<BigInteger> claimTopics;
    }

    public static class InitializedEventResponse extends BaseEventResponse {
        public BigInteger version;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TrustedIssuerAddedEventResponse extends BaseEventResponse {
        public String trustedIssuer;

        public List<BigInteger> claimTopics;
    }

    public static class TrustedIssuerRemovedEventResponse extends BaseEventResponse {
        public String trustedIssuer;
    }
}
