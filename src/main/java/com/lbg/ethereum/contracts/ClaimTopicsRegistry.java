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
public class ClaimTopicsRegistry extends Contract {
    public static final String BINARY = "\"0x608060405234801561001057600080fd5b506107c3806100206000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c8063c7b225511161005b578063c7b22551146100bf578063df09d604146100d2578063e1c7392a146100e7578063f2fde38b146100ef57600080fd5b80630829784614610082578063715018a6146100975780638da5cb5b1461009f575b600080fd5b610095610090366004610641565b610102565b005b6100956101f3565b6033546040516001600160a01b0390911681526020015b60405180910390f35b6100956100cd366004610641565b610207565b6100da61035c565b6040516100b6919061065a565b6100956103b4565b6100956100fd36600461069e565b6104c5565b61010a61053b565b60655460005b818110156101ee57826065828154811061012c5761012c6106ce565b9060005260206000200154036101dc5760656101496001846106fa565b81548110610159576101596106ce565b906000526020600020015460658281548110610177576101776106ce565b600091825260209091200155606580548061019457610194610713565b60019003818190600052602060002001600090559055827f0b1381093c776453c1bbe54fd68be1b235c65db61d099cb50d194b2991e0eec560405160405180910390a2505050565b806101e681610729565b915050610110565b505050565b6101fb61053b565b6102056000610595565b565b61020f61053b565b606554600f81106102725760405162461bcd60e51b815260206004820152602260248201527f63616e6e6f742072657175697265206d6f7265207468616e20313520746f7069604482015261637360f01b60648201526084015b60405180910390fd5b60005b818110156102fb578260658281548110610291576102916106ce565b9060005260206000200154036102e95760405162461bcd60e51b815260206004820152601960248201527f636c61696d546f70696320616c726561647920657869737473000000000000006044820152606401610269565b806102f381610729565b915050610275565b506065805460018101825560009182527f8ff97419363ffd7000167f130ef7168fbea05faf9251824ca5043f113cc6a7c70183905560405183917f01c928b7f7ade2949e92366aa9454dbef3a416b731cf6ec786ba9595bbd814d691a25050565b606060658054806020026020016040519081016040528092919081815260200182805480156103aa57602002820191906000526020600020905b815481526020019060010190808311610396575b5050505050905090565b600054610100900460ff16158080156103d45750600054600160ff909116105b806103ee5750303b1580156103ee575060005460ff166001145b6104515760405162461bcd60e51b815260206004820152602e60248201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160448201526d191e481a5b9a5d1a585b1a5e995960921b6064820152608401610269565b6000805460ff191660011790558015610474576000805461ff0019166101001790555b61047c6105e7565b80156104c2576000805461ff0019169055604051600181527f7f26b83ff96e1f2b6a682f133852f6798a09c465da95921460cefb38474024989060200160405180910390a15b50565b6104cd61053b565b6001600160a01b0381166105325760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b6064820152608401610269565b6104c281610595565b6033546001600160a01b031633146102055760405162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e65726044820152606401610269565b603380546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b600054610100900460ff1661060e5760405162461bcd60e51b815260040161026990610742565b610205600054610100900460ff166106385760405162461bcd60e51b815260040161026990610742565b61020533610595565b60006020828403121561065357600080fd5b5035919050565b6020808252825182820181905260009190848201906040850190845b8181101561069257835183529284019291840191600101610676565b50909695505050505050565b6000602082840312156106b057600080fd5b81356001600160a01b03811681146106c757600080fd5b9392505050565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b8181038181111561070d5761070d6106e4565b92915050565b634e487b7160e01b600052603160045260246000fd5b60006001820161073b5761073b6106e4565b5060010190565b6020808252602b908201527f496e697469616c697a61626c653a20636f6e7472616374206973206e6f74206960408201526a6e697469616c697a696e6760a81b60608201526080019056fea26469706673582212209bec37cad533f574694f227243c0bab5433feae61f514c11cc342e873eb0d04564736f6c63430008110033\"";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDCLAIMTOPIC = "addClaimTopic";

    public static final String FUNC_GETCLAIMTOPICS = "getClaimTopics";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVECLAIMTOPIC = "removeClaimTopic";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event CLAIMTOPICADDED_EVENT = new Event("ClaimTopicAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event CLAIMTOPICREMOVED_EVENT = new Event("ClaimTopicRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event INITIALIZED_EVENT = new Event("Initialized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected ClaimTopicsRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ClaimTopicsRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ClaimTopicsRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ClaimTopicsRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<ClaimTopicAddedEventResponse> getClaimTopicAddedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLAIMTOPICADDED_EVENT, transactionReceipt);
//        ArrayList<ClaimTopicAddedEventResponse> responses = new ArrayList<ClaimTopicAddedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            ClaimTopicAddedEventResponse typedResponse = new ClaimTopicAddedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.claimTopic = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static ClaimTopicAddedEventResponse getClaimTopicAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLAIMTOPICADDED_EVENT, log);
        ClaimTopicAddedEventResponse typedResponse = new ClaimTopicAddedEventResponse();
        typedResponse.log = log;
        typedResponse.claimTopic = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ClaimTopicAddedEventResponse> claimTopicAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClaimTopicAddedEventFromLog(log));
    }

    public Flowable<ClaimTopicAddedEventResponse> claimTopicAddedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMTOPICADDED_EVENT));
        return claimTopicAddedEventFlowable(filter);
    }

//    public static List<ClaimTopicRemovedEventResponse> getClaimTopicRemovedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLAIMTOPICREMOVED_EVENT, transactionReceipt);
//        ArrayList<ClaimTopicRemovedEventResponse> responses = new ArrayList<ClaimTopicRemovedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            ClaimTopicRemovedEventResponse typedResponse = new ClaimTopicRemovedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.claimTopic = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static ClaimTopicRemovedEventResponse getClaimTopicRemovedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLAIMTOPICREMOVED_EVENT, log);
        ClaimTopicRemovedEventResponse typedResponse = new ClaimTopicRemovedEventResponse();
        typedResponse.log = log;
        typedResponse.claimTopic = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ClaimTopicRemovedEventResponse> claimTopicRemovedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClaimTopicRemovedEventFromLog(log));
    }

    public Flowable<ClaimTopicRemovedEventResponse> claimTopicRemovedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMTOPICREMOVED_EVENT));
        return claimTopicRemovedEventFlowable(filter);
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

    public RemoteFunctionCall<TransactionReceipt> addClaimTopic(BigInteger _claimTopic) {
        final Function function = new Function(
                FUNC_ADDCLAIMTOPIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_claimTopic)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getClaimTopics() {
        final Function function = new Function(FUNC_GETCLAIMTOPICS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> init() {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeClaimTopic(BigInteger _claimTopic) {
        final Function function = new Function(
                FUNC_REMOVECLAIMTOPIC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_claimTopic)), 
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

    @Deprecated
    public static ClaimTopicsRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ClaimTopicsRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ClaimTopicsRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ClaimTopicsRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ClaimTopicsRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ClaimTopicsRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ClaimTopicsRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ClaimTopicsRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ClaimTopicsRegistry> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ClaimTopicsRegistry.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<ClaimTopicsRegistry> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ClaimTopicsRegistry.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<ClaimTopicsRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ClaimTopicsRegistry.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<ClaimTopicsRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ClaimTopicsRegistry.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    public static class ClaimTopicAddedEventResponse extends BaseEventResponse {
        public BigInteger claimTopic;
    }

    public static class ClaimTopicRemovedEventResponse extends BaseEventResponse {
        public BigInteger claimTopic;
    }

    public static class InitializedEventResponse extends BaseEventResponse {
        public BigInteger version;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
