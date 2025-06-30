package com.lbg.ethereum.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class IImplementationAuthority extends Contract {
    public static final String BINARY = "\"0x\"";

    private static String librariesLinkedBinary;

    public static final String FUNC_GETIMPLEMENTATION = "getImplementation";

    public static final String FUNC_UPDATEIMPLEMENTATION = "updateImplementation";

    public static final Event UPDATEDIMPLEMENTATION_EVENT = new Event("UpdatedImplementation", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected IImplementationAuthority(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IImplementationAuthority(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IImplementationAuthority(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IImplementationAuthority(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<UpdatedImplementationEventResponse> getUpdatedImplementationEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UPDATEDIMPLEMENTATION_EVENT, transactionReceipt);
//        ArrayList<UpdatedImplementationEventResponse> responses = new ArrayList<UpdatedImplementationEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            UpdatedImplementationEventResponse typedResponse = new UpdatedImplementationEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.newAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static UpdatedImplementationEventResponse getUpdatedImplementationEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UPDATEDIMPLEMENTATION_EVENT, log);
        UpdatedImplementationEventResponse typedResponse = new UpdatedImplementationEventResponse();
        typedResponse.log = log;
        typedResponse.newAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<UpdatedImplementationEventResponse> updatedImplementationEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUpdatedImplementationEventFromLog(log));
    }

    public Flowable<UpdatedImplementationEventResponse> updatedImplementationEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPDATEDIMPLEMENTATION_EVENT));
        return updatedImplementationEventFlowable(filter);
    }

    public RemoteFunctionCall<String> getImplementation() {
        final Function function = new Function(FUNC_GETIMPLEMENTATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateImplementation(String _newImplementation) {
        final Function function = new Function(
                FUNC_UPDATEIMPLEMENTATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _newImplementation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IImplementationAuthority load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IImplementationAuthority(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IImplementationAuthority load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IImplementationAuthority(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IImplementationAuthority load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IImplementationAuthority(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IImplementationAuthority load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IImplementationAuthority(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IImplementationAuthority> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IImplementationAuthority.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<IImplementationAuthority> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IImplementationAuthority.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<IImplementationAuthority> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IImplementationAuthority.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<IImplementationAuthority> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IImplementationAuthority.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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

    public static class UpdatedImplementationEventResponse extends BaseEventResponse {
        public String newAddress;
    }
}
