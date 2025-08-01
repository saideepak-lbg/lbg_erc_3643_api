package com.lbg.ethereum.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class IdentityProxy extends Contract {
    public static final String BINARY = "\"0x608060405234801561001057600080fd5b506040516107723803806107728339818101604052810190610032919061034a565b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16036100a1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610098906103e7565b60405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff1603610110576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610107906103e7565b60405180910390fd5b817f821f3e4d3d679f19eacc940c87acf846ea6eae24a63058ea750304437a62aafc5560008273ffffffffffffffffffffffffffffffffffffffff1663aaf10f426040518163ffffffff1660e01b8152600401602060405180830381865afa158015610180573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101a49190610407565b905060008173ffffffffffffffffffffffffffffffffffffffff16836040516024016101d09190610443565b6040516020818303038152906040527fc4d66de8000000000000000000000000000000000000000000000000000000007bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19166020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff838183161783525050505060405161025a91906104cf565b600060405180830381855af49150503d8060008114610295576040519150601f19603f3d011682016040523d82523d6000602084013e61029a565b606091505b50509050806102de576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102d590610532565b60405180910390fd5b50505050610552565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610317826102ec565b9050919050565b6103278161030c565b811461033257600080fd5b50565b6000815190506103448161031e565b92915050565b60008060408385031215610361576103606102e7565b5b600061036f85828601610335565b925050602061038085828601610335565b9150509250929050565b600082825260208201905092915050565b7f696e76616c696420617267756d656e74202d207a65726f206164647265737300600082015250565b60006103d1601f8361038a565b91506103dc8261039b565b602082019050919050565b60006020820190508181036000830152610400816103c4565b9050919050565b60006020828403121561041d5761041c6102e7565b5b600061042b84828501610335565b91505092915050565b61043d8161030c565b82525050565b60006020820190506104586000830184610434565b92915050565b600081519050919050565b600081905092915050565b60005b83811015610492578082015181840152602081019050610477565b60008484015250505050565b60006104a98261045e565b6104b38185610469565b93506104c3818560208601610474565b80840191505092915050565b60006104db828461049e565b915081905092915050565b7f496e697469616c697a6174696f6e206661696c65642e00000000000000000000600082015250565b600061051c60168361038a565b9150610527826104e6565b602082019050919050565b6000602082019050818103600083015261054b8161050f565b9050919050565b610211806105616000396000f3fe6080604052600436106100225760003560e01c80632307f882146100c857610023565b5b600061002d6100f3565b73ffffffffffffffffffffffffffffffffffffffff1663aaf10f426040518163ffffffff1660e01b8152600401602060405180830381865afa158015610077573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061009b9190610184565b90503660008037600080366000846127105a03f43d806000803e81600081146100c357816000f35b816000fd5b3480156100d457600080fd5b506100dd6100f3565b6040516100ea91906101c0565b60405180910390f35b6000807f821f3e4d3d679f19eacc940c87acf846ea6eae24a63058ea750304437a62aafc5490508091505090565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061015182610126565b9050919050565b61016181610146565b811461016c57600080fd5b50565b60008151905061017e81610158565b92915050565b60006020828403121561019a57610199610121565b5b60006101a88482850161016f565b91505092915050565b6101ba81610146565b82525050565b60006020820190506101d560008301846101b1565b9291505056fea264697066735822122087108c0e411bfe27737deb09117917821789f7599cced8134d2683b7969e34ae64736f6c63430008110033\"";

    private static String librariesLinkedBinary;

    public static final String FUNC_IMPLEMENTATIONAUTHORITY = "implementationAuthority";

    @Deprecated
    protected IdentityProxy(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IdentityProxy(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IdentityProxy(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IdentityProxy(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> implementationAuthority() {
        final Function function = new Function(FUNC_IMPLEMENTATIONAUTHORITY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static IdentityProxy load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new IdentityProxy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IdentityProxy load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IdentityProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IdentityProxy load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new IdentityProxy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IdentityProxy load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IdentityProxy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IdentityProxy> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _implementationAuthority,
            String initialManagementKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _implementationAuthority), 
                new org.web3j.abi.datatypes.Address(160, initialManagementKey)));
        return deployRemoteCall(IdentityProxy.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<IdentityProxy> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String _implementationAuthority, String initialManagementKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _implementationAuthority), 
                new org.web3j.abi.datatypes.Address(160, initialManagementKey)));
        return deployRemoteCall(IdentityProxy.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<IdentityProxy> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _implementationAuthority,
            String initialManagementKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _implementationAuthority), 
                new org.web3j.abi.datatypes.Address(160, initialManagementKey)));
        return deployRemoteCall(IdentityProxy.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<IdentityProxy> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String _implementationAuthority, String initialManagementKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _implementationAuthority), 
                new org.web3j.abi.datatypes.Address(160, initialManagementKey)));
        return deployRemoteCall(IdentityProxy.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

/*    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }*/

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}
