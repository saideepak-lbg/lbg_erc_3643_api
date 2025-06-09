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
    public static final String BINARY = "0x608060405234801561001057600080fd5b5060405161048938038061048983398101604081905261002f91610271565b6001600160a01b03821661008a5760405162461bcd60e51b815260206004820152601f60248201527f696e76616c696420617267756d656e74202d207a65726f20616464726573730060448201526064015b60405180910390fd5b6001600160a01b0381166100e05760405162461bcd60e51b815260206004820152601f60248201527f696e76616c696420617267756d656e74202d207a65726f2061646472657373006044820152606401610081565b817f821f3e4d3d679f19eacc940c87acf846ea6eae24a63058ea750304437a62aafc556000826001600160a01b031663aaf10f426040518163ffffffff1660e01b8152600401602060405180830381865afa158015610143573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061016791906102a4565b6040516001600160a01b03848116602483015291925060009183169060440160408051601f198184030181529181526020820180516001600160e01b031663189acdbd60e31b179052516101bb91906102c6565b600060405180830381855af49150503d80600081146101f6576040519150601f19603f3d011682016040523d82523d6000602084013e6101fb565b606091505b505090508061024c5760405162461bcd60e51b815260206004820152601660248201527f496e697469616c697a6174696f6e206661696c65642e000000000000000000006044820152606401610081565b505050506102f5565b80516001600160a01b038116811461026c57600080fd5b919050565b6000806040838503121561028457600080fd5b61028d83610255565b915061029b60208401610255565b90509250929050565b6000602082840312156102b657600080fd5b6102bf82610255565b9392505050565b6000825160005b818110156102e757602081860181015185830152016102cd565b506000920191825250919050565b610185806103046000396000f3fe60806040526004361061001e5760003560e01c80632307f882146100d4575b60006100487f821f3e4d3d679f19eacc940c87acf846ea6eae24a63058ea750304437a62aafc5490565b6001600160a01b031663aaf10f426040518163ffffffff1660e01b8152600401602060405180830381865afa158015610085573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100a9919061011f565b90503660008037600080366000846127105a03f43d806000803e8180156100cf57816000f35b816000fd5b3480156100e057600080fd5b507f821f3e4d3d679f19eacc940c87acf846ea6eae24a63058ea750304437a62aafc546040516001600160a01b03909116815260200160405180910390f35b60006020828403121561013157600080fd5b81516001600160a01b038116811461014857600080fd5b939250505056fea26469706673582212203f2fd61e8fff82351e823f64699edffb2501a2a71f786bc82bed230383796ef564736f6c63430008110033";

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
}
