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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint16;
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
public class IdentityRegistry extends Contract {
    public static final String BINARY = "\"0x608060405234801561001057600080fd5b50611afe806100206000396000f3fe608060405234801561001057600080fd5b50600436106101425760003560e01c806384e79842116100b8578063b4f3fcb71161007c578063b4f3fcb7146102a6578063b9209e33146102b7578063e744d789146102ca578063f0eb5e54146102dd578063f11abfd8146102f0578063f2fde38b1461030157600080fd5b806384e79842146102495780638da5cb5b1461025c5780638e098ca11461026d57806397a6278e14610280578063a8d29d1d1461029357600080fd5b8063454a03e01161010a578063454a03e0146101cf5780635dbe47e8146101e2578063653dc9f1146101f5578063670af6a914610208578063715018a61461021b5780637e42683b1461022357600080fd5b8063184b9559146101475780631ffbb0641461015c57806326d941ae146101845780633b239a7f146101975780633b3e12f4146101aa575b600080fd5b61015a6101553660046113d1565b610314565b005b61016f61016a36600461141c565b610549565b60405190151581526020015b60405180910390f35b61015a61019236600461141c565b61055c565b61015a6101a5366004611450565b6105ae565b6066546001600160a01b03165b6040516001600160a01b03909116815260200161017b565b61015a6101dd366004611489565b61067a565b61016f6101f036600461141c565b61074d565b61015a610203366004611515565b610777565b61015a61021636600461141c565b610815565b61015a610867565b61023661023136600461141c565b61087b565b60405161ffff909116815260200161017b565b61015a61025736600461141c565b6108ea565b6033546001600160a01b03166101b7565b61015a61027b3660046115af565b61095a565b61015a61028e36600461141c565b610a30565b61015a6102a136600461141c565b610aa0565b6067546001600160a01b03166101b7565b61016f6102c536600461141c565b610b6d565b61015a6102d836600461141c565b610ff6565b6101b76102eb36600461141c565b611048565b6068546001600160a01b03166101b7565b61015a61030f36600461141c565b6110b7565b600054610100900460ff16158080156103345750600054600160ff909116105b8061034e5750303b15801561034e575060005460ff166001145b6103b65760405162461bcd60e51b815260206004820152602e60248201527f496e697469616c697a61626c653a20636f6e747261637420697320616c72656160448201526d191e481a5b9a5d1a585b1a5e995960921b60648201526084015b60405180910390fd5b6000805460ff1916600117905580156103d9576000805461ff0019166101001790555b6001600160a01b038416158015906103f957506001600160a01b03831615155b801561040d57506001600160a01b03821615155b6104295760405162461bcd60e51b81526004016103ad906115dd565b606680546001600160a01b038086166001600160a01b031992831681179093556067805488831690841617905560688054918616919092161790556040517f7170bf15b246e880b2369cd7c67d057760d8a35149e8c64dde91efa22bcc76d090600090a26040516001600160a01b038516907f1b98cb79e6f73020175fe87333f1b91ad6a881519c0afe30340c2599b2b4bde090600090a26040516001600160a01b038316907f2fa8b95c1db7afe99e3398f3792f008135cedc1fa26b0bb2ecd2352cd166d53c90600090a26104fd611130565b8015610543576000805461ff0019169055604051600181527f7f26b83ff96e1f2b6a682f133852f6798a09c465da95921460cefb38474024989060200160405180910390a15b50505050565b600061055660658361115f565b92915050565b6105646111e2565b606880546001600160a01b0319166001600160a01b0383169081179091556040517f2fa8b95c1db7afe99e3398f3792f008135cedc1fa26b0bb2ecd2352cd166d53c90600090a250565b6105b733610549565b6105d35760405162461bcd60e51b81526004016103ad90611614565b606854604051639f3418d560e01b81526001600160a01b03848116600483015261ffff8416602483015290911690639f3418d590604401600060405180830381600087803b15801561062457600080fd5b505af1158015610638573d6000803e3d6000fd5b505060405161ffff841692506001600160a01b03851691507f04ed3b726495c2dca1ff1215d9ca54e1a4030abb5e82b0f6ce55702416cee85390600090a35050565b61068333610549565b61069f5760405162461bcd60e51b81526004016103ad90611614565b60685460405163a53410dd60e01b81526001600160a01b038581166004830152848116602483015261ffff841660448301529091169063a53410dd90606401600060405180830381600087803b1580156106f857600080fd5b505af115801561070c573d6000803e3d6000fd5b50506040516001600160a01b038086169350861691507f6ae73635c50d24a45af6fbd5e016ac4bed179addbc8bf24e04ff0fcc6d33af1990600090a3505050565b60008061075983611048565b6001600160a01b03160361076f57506000919050565b506001919050565b60005b8581101561080c576107fa87878381811061079757610797611662565b90506020020160208101906107ac919061141c565b8686848181106107be576107be611662565b90506020020160208101906107d3919061141c565b8585858181106107e5576107e5611662565b90506020020160208101906101dd9190611678565b80610804816116ab565b91505061077a565b50505050505050565b61081d6111e2565b606680546001600160a01b0319166001600160a01b0383169081179091556040517f7170bf15b246e880b2369cd7c67d057760d8a35149e8c64dde91efa22bcc76d090600090a250565b61086f6111e2565b610879600061123c565b565b606854604051631c9f84ef60e21b81526001600160a01b038381166004830152600092169063727e13bc90602401602060405180830381865afa1580156108c6573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061055691906116c4565b6108f26111e2565b6001600160a01b0381166109185760405162461bcd60e51b81526004016103ad906115dd565b61092360658261128e565b6040516001600160a01b038216907ff68e73cec97f2d70aa641fb26e87a4383686e2efacb648f2165aeb02ac562ec590600090a250565b61096333610549565b61097f5760405162461bcd60e51b81526004016103ad90611614565b600061098a83611048565b606854604051637402e7c360e11b81526001600160a01b038681166004830152858116602483015292935091169063e805cf8690604401600060405180830381600087803b1580156109db57600080fd5b505af11580156109ef573d6000803e3d6000fd5b50506040516001600160a01b038086169350841691507fe98082932c8056a0f514da9104e4a66bc2cbaef102ad59d90c4b24220ebf601090600090a3505050565b610a386111e2565b6001600160a01b038116610a5e5760405162461bcd60e51b81526004016103ad906115dd565b610a6960658261130a565b6040516001600160a01b038216907fed9c8ad8d5a0a66898ea49d2956929c93ae2e8bd50281b2ed897c5d1a6737e0b90600090a250565b610aa933610549565b610ac55760405162461bcd60e51b81526004016103ad90611614565b6000610ad082611048565b60685460405163cf191bcd60e01b81526001600160a01b03858116600483015292935091169063cf191bcd90602401600060405180830381600087803b158015610b1957600080fd5b505af1158015610b2d573d6000803e3d6000fd5b50506040516001600160a01b038085169350851691507f59d6590e225b81befe259af056324092801080acbb7feab310eb34678871f32790600090a35050565b600080610b7983611048565b6001600160a01b031603610b8f57506000919050565b606654604080516337c2758160e21b815290516000926001600160a01b03169163df09d60491600480830192869291908290030181865afa158015610bd8573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052610c00919081019061174c565b90508051600003610c145750600192915050565b600080600060608060005b8651811015610fe75760675487516000916001600160a01b0316906352c111d1908a9085908110610c5257610c52611662565b60200260200101516040518263ffffffff1660e01b8152600401610c7891815260200190565b600060405180830381865afa158015610c95573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052610cbd91908101906117e2565b90508051600003610cd8575060009998505050505050505050565b6000815167ffffffffffffffff811115610cf457610cf46116e1565b604051908082528060200260200182016040528015610d1d578160200160208202803683370190505b50905060005b8251811015610dca57828181518110610d3e57610d3e611662565b60200260200101518a8581518110610d5857610d58611662565b6020026020010151604051602001610d859291906001600160a01b03929092168252602082015260400190565b60405160208183030381529060405280519060200120828281518110610dad57610dad611662565b602090810291909101015280610dc2816116ab565b915050610d23565b5060005b8151811015610fd157610de08c611048565b6001600160a01b031663c9100bcb838381518110610e0057610e00611662565b60200260200101516040518263ffffffff1660e01b8152600401610e2691815260200190565b600060405180830381865afa158015610e43573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f19168201604052610e6b9190810190611904565b508e51949d50929b50909950975095508a9085908110610e8d57610e8d611662565b60200260200101518903610f9957866001600160a01b031663c0969a6e610eb38e611048565b8c8781518110610ec557610ec5611662565b602002602001015189896040518563ffffffff1660e01b8152600401610eee94939291906119f2565b602060405180830381865afa925050508015610f27575060408051601f3d908101601f19168201909252610f2491810190611a2b565b60015b610f565760018251610f399190611a4d565b8103610f51575060009b9a5050505050505050505050565b610fbf565b8015610f6157825191505b80158015610f7b575060018351610f789190611a4d565b82145b15610f93575060009c9b505050505050505050505050565b50610fbf565b60018251610fa79190611a4d565b8103610fbf575060009b9a5050505050505050505050565b80610fc9816116ab565b915050610dce565b5050508080610fdf906116ab565b915050610c1f565b50600198975050505050505050565b610ffe6111e2565b606780546001600160a01b0319166001600160a01b0383169081179091556040517f1b98cb79e6f73020175fe87333f1b91ad6a881519c0afe30340c2599b2b4bde090600090a250565b606854604051637988d3a560e01b81526001600160a01b0383811660048301526000921690637988d3a590602401602060405180830381865afa158015611093573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906105569190611a60565b6110bf6111e2565b6001600160a01b0381166111245760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084016103ad565b61112d8161123c565b50565b600054610100900460ff166111575760405162461bcd60e51b81526004016103ad90611a7d565b61087961138c565b60006001600160a01b0382166111c25760405162461bcd60e51b815260206004820152602260248201527f526f6c65733a206163636f756e7420697320746865207a65726f206164647265604482015261737360f01b60648201526084016103ad565b506001600160a01b03166000908152602091909152604090205460ff1690565b6033546001600160a01b031633146108795760405162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657260448201526064016103ad565b603380546001600160a01b038381166001600160a01b0319831681179093556040519116919082907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a35050565b611298828261115f565b156112e55760405162461bcd60e51b815260206004820152601f60248201527f526f6c65733a206163636f756e7420616c72656164792068617320726f6c650060448201526064016103ad565b6001600160a01b0316600090815260209190915260409020805460ff19166001179055565b611314828261115f565b61136a5760405162461bcd60e51b815260206004820152602160248201527f526f6c65733a206163636f756e7420646f6573206e6f74206861766520726f6c6044820152606560f81b60648201526084016103ad565b6001600160a01b0316600090815260209190915260409020805460ff19169055565b600054610100900460ff166113b35760405162461bcd60e51b81526004016103ad90611a7d565b6108793361123c565b6001600160a01b038116811461112d57600080fd5b6000806000606084860312156113e657600080fd5b83356113f1816113bc565b92506020840135611401816113bc565b91506040840135611411816113bc565b809150509250925092565b60006020828403121561142e57600080fd5b8135611439816113bc565b9392505050565b61ffff8116811461112d57600080fd5b6000806040838503121561146357600080fd5b823561146e816113bc565b9150602083013561147e81611440565b809150509250929050565b60008060006060848603121561149e57600080fd5b83356114a9816113bc565b925060208401356114b9816113bc565b9150604084013561141181611440565b60008083601f8401126114db57600080fd5b50813567ffffffffffffffff8111156114f357600080fd5b6020830191508360208260051b850101111561150e57600080fd5b9250929050565b6000806000806000806060878903121561152e57600080fd5b863567ffffffffffffffff8082111561154657600080fd5b6115528a838b016114c9565b9098509650602089013591508082111561156b57600080fd5b6115778a838b016114c9565b9096509450604089013591508082111561159057600080fd5b5061159d89828a016114c9565b979a9699509497509295939492505050565b600080604083850312156115c257600080fd5b82356115cd816113bc565b9150602083013561147e816113bc565b6020808252601f908201527f696e76616c696420617267756d656e74202d207a65726f206164647265737300604082015260600190565b6020808252602e908201527f4167656e74526f6c653a2063616c6c657220646f6573206e6f7420686176652060408201526d746865204167656e7420726f6c6560901b606082015260800190565b634e487b7160e01b600052603260045260246000fd5b60006020828403121561168a57600080fd5b813561143981611440565b634e487b7160e01b600052601160045260246000fd5b6000600182016116bd576116bd611695565b5060010190565b6000602082840312156116d657600080fd5b815161143981611440565b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff81118282101715611720576117206116e1565b604052919050565b600067ffffffffffffffff821115611742576117426116e1565b5060051b60200190565b6000602080838503121561175f57600080fd5b825167ffffffffffffffff81111561177657600080fd5b8301601f8101851361178757600080fd5b805161179a61179582611728565b6116f7565b81815260059190911b820183019083810190878311156117b957600080fd5b928401925b828410156117d7578351825292840192908401906117be565b979650505050505050565b600060208083850312156117f557600080fd5b825167ffffffffffffffff81111561180c57600080fd5b8301601f8101851361181d57600080fd5b805161182b61179582611728565b81815260059190911b8201830190838101908783111561184a57600080fd5b928401925b828410156117d7578351611862816113bc565b8252928401929084019061184f565b60005b8381101561188c578181015183820152602001611874565b50506000910152565b600067ffffffffffffffff8311156118af576118af6116e1565b6118c2601f8401601f19166020016116f7565b90508281528383830111156118d657600080fd5b611439836020830184611871565b600082601f8301126118f557600080fd5b61143983835160208501611895565b60008060008060008060c0878903121561191d57600080fd5b86519550602087015194506040870151611936816113bc565b606088015190945067ffffffffffffffff8082111561195457600080fd5b6119608a838b016118e4565b9450608089015191508082111561197657600080fd5b6119828a838b016118e4565b935060a089015191508082111561199857600080fd5b508701601f810189136119aa57600080fd5b6119b989825160208401611895565b9150509295509295509295565b600081518084526119de816020860160208601611871565b601f01601f19169290920160200192915050565b60018060a01b0385168152836020820152608060408201526000611a1960808301856119c6565b82810360608401526117d781856119c6565b600060208284031215611a3d57600080fd5b8151801515811461143957600080fd5b8181038181111561055657610556611695565b600060208284031215611a7257600080fd5b8151611439816113bc565b6020808252602b908201527f496e697469616c697a61626c653a20636f6e7472616374206973206e6f74206960408201526a6e697469616c697a696e6760a81b60608201526080019056fea2646970667358221220583d888c260bd7d31cf30a390c36eb34a2f1bca1ba894cecf1631ef2d4d233a764736f6c63430008110033\"";

    private static String librariesLinkedBinary;

    public static final String FUNC_ADDAGENT = "addAgent";

    public static final String FUNC_BATCHREGISTERIDENTITY = "batchRegisterIdentity";

    public static final String FUNC_CONTAINS = "contains";

    public static final String FUNC_DELETEIDENTITY = "deleteIdentity";

    public static final String FUNC_IDENTITY = "identity";

    public static final String FUNC_IDENTITYSTORAGE = "identityStorage";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_INVESTORCOUNTRY = "investorCountry";

    public static final String FUNC_ISAGENT = "isAgent";

    public static final String FUNC_ISVERIFIED = "isVerified";

    public static final String FUNC_ISSUERSREGISTRY = "issuersRegistry";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REGISTERIDENTITY = "registerIdentity";

    public static final String FUNC_REMOVEAGENT = "removeAgent";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SETCLAIMTOPICSREGISTRY = "setClaimTopicsRegistry";

    public static final String FUNC_SETIDENTITYREGISTRYSTORAGE = "setIdentityRegistryStorage";

    public static final String FUNC_SETTRUSTEDISSUERSREGISTRY = "setTrustedIssuersRegistry";

    public static final String FUNC_TOPICSREGISTRY = "topicsRegistry";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPDATECOUNTRY = "updateCountry";

    public static final String FUNC_UPDATEIDENTITY = "updateIdentity";

    public static final Event AGENTADDED_EVENT = new Event("AgentAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event AGENTREMOVED_EVENT = new Event("AgentRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event CLAIMTOPICSREGISTRYSET_EVENT = new Event("ClaimTopicsRegistrySet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event COUNTRYUPDATED_EVENT = new Event("CountryUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint16>(true) {}));
    ;

    public static final Event IDENTITYREGISTERED_EVENT = new Event("IdentityRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event IDENTITYREMOVED_EVENT = new Event("IdentityRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event IDENTITYSTORAGESET_EVENT = new Event("IdentityStorageSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event IDENTITYUPDATED_EVENT = new Event("IdentityUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event INITIALIZED_EVENT = new Event("Initialized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TRUSTEDISSUERSREGISTRYSET_EVENT = new Event("TrustedIssuersRegistrySet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected IdentityRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected IdentityRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected IdentityRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected IdentityRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

//    public static List<AgentAddedEventResponse> getAgentAddedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AGENTADDED_EVENT, transactionReceipt);
//        ArrayList<AgentAddedEventResponse> responses = new ArrayList<AgentAddedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            AgentAddedEventResponse typedResponse = new AgentAddedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse._agent = (String) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static AgentAddedEventResponse getAgentAddedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AGENTADDED_EVENT, log);
        AgentAddedEventResponse typedResponse = new AgentAddedEventResponse();
        typedResponse.log = log;
        typedResponse._agent = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AgentAddedEventResponse> agentAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAgentAddedEventFromLog(log));
    }

    public Flowable<AgentAddedEventResponse> agentAddedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AGENTADDED_EVENT));
        return agentAddedEventFlowable(filter);
    }

//    public static List<AgentRemovedEventResponse> getAgentRemovedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(AGENTREMOVED_EVENT, transactionReceipt);
//        ArrayList<AgentRemovedEventResponse> responses = new ArrayList<AgentRemovedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            AgentRemovedEventResponse typedResponse = new AgentRemovedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse._agent = (String) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static AgentRemovedEventResponse getAgentRemovedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(AGENTREMOVED_EVENT, log);
        AgentRemovedEventResponse typedResponse = new AgentRemovedEventResponse();
        typedResponse.log = log;
        typedResponse._agent = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AgentRemovedEventResponse> agentRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAgentRemovedEventFromLog(log));
    }

    public Flowable<AgentRemovedEventResponse> agentRemovedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AGENTREMOVED_EVENT));
        return agentRemovedEventFlowable(filter);
    }

//    public static List<ClaimTopicsRegistrySetEventResponse> getClaimTopicsRegistrySetEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CLAIMTOPICSREGISTRYSET_EVENT, transactionReceipt);
//        ArrayList<ClaimTopicsRegistrySetEventResponse> responses = new ArrayList<ClaimTopicsRegistrySetEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            ClaimTopicsRegistrySetEventResponse typedResponse = new ClaimTopicsRegistrySetEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.claimTopicsRegistry = (String) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static ClaimTopicsRegistrySetEventResponse getClaimTopicsRegistrySetEventFromLog(
            Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CLAIMTOPICSREGISTRYSET_EVENT, log);
        ClaimTopicsRegistrySetEventResponse typedResponse = new ClaimTopicsRegistrySetEventResponse();
        typedResponse.log = log;
        typedResponse.claimTopicsRegistry = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ClaimTopicsRegistrySetEventResponse> claimTopicsRegistrySetEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getClaimTopicsRegistrySetEventFromLog(log));
    }

    public Flowable<ClaimTopicsRegistrySetEventResponse> claimTopicsRegistrySetEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CLAIMTOPICSREGISTRYSET_EVENT));
        return claimTopicsRegistrySetEventFlowable(filter);
    }

//    public static List<CountryUpdatedEventResponse> getCountryUpdatedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(COUNTRYUPDATED_EVENT, transactionReceipt);
//        ArrayList<CountryUpdatedEventResponse> responses = new ArrayList<CountryUpdatedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            CountryUpdatedEventResponse typedResponse = new CountryUpdatedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.investorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.country = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static CountryUpdatedEventResponse getCountryUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(COUNTRYUPDATED_EVENT, log);
        CountryUpdatedEventResponse typedResponse = new CountryUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.investorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.country = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<CountryUpdatedEventResponse> countryUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCountryUpdatedEventFromLog(log));
    }

    public Flowable<CountryUpdatedEventResponse> countryUpdatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(COUNTRYUPDATED_EVENT));
        return countryUpdatedEventFlowable(filter);
    }

//    public static List<IdentityRegisteredEventResponse> getIdentityRegisteredEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(IDENTITYREGISTERED_EVENT, transactionReceipt);
//        ArrayList<IdentityRegisteredEventResponse> responses = new ArrayList<IdentityRegisteredEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            IdentityRegisteredEventResponse typedResponse = new IdentityRegisteredEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.investorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.identity = (String) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static IdentityRegisteredEventResponse getIdentityRegisteredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(IDENTITYREGISTERED_EVENT, log);
        IdentityRegisteredEventResponse typedResponse = new IdentityRegisteredEventResponse();
        typedResponse.log = log;
        typedResponse.investorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.identity = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IdentityRegisteredEventResponse> identityRegisteredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getIdentityRegisteredEventFromLog(log));
    }

    public Flowable<IdentityRegisteredEventResponse> identityRegisteredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYREGISTERED_EVENT));
        return identityRegisteredEventFlowable(filter);
    }

//    public static List<IdentityRemovedEventResponse> getIdentityRemovedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(IDENTITYREMOVED_EVENT, transactionReceipt);
//        ArrayList<IdentityRemovedEventResponse> responses = new ArrayList<IdentityRemovedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            IdentityRemovedEventResponse typedResponse = new IdentityRemovedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.investorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.identity = (String) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static IdentityRemovedEventResponse getIdentityRemovedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(IDENTITYREMOVED_EVENT, log);
        IdentityRemovedEventResponse typedResponse = new IdentityRemovedEventResponse();
        typedResponse.log = log;
        typedResponse.investorAddress = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.identity = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IdentityRemovedEventResponse> identityRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getIdentityRemovedEventFromLog(log));
    }

    public Flowable<IdentityRemovedEventResponse> identityRemovedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYREMOVED_EVENT));
        return identityRemovedEventFlowable(filter);
    }

//    public static List<IdentityStorageSetEventResponse> getIdentityStorageSetEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(IDENTITYSTORAGESET_EVENT, transactionReceipt);
//        ArrayList<IdentityStorageSetEventResponse> responses = new ArrayList<IdentityStorageSetEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            IdentityStorageSetEventResponse typedResponse = new IdentityStorageSetEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.identityStorage = (String) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static IdentityStorageSetEventResponse getIdentityStorageSetEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(IDENTITYSTORAGESET_EVENT, log);
        IdentityStorageSetEventResponse typedResponse = new IdentityStorageSetEventResponse();
        typedResponse.log = log;
        typedResponse.identityStorage = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<IdentityStorageSetEventResponse> identityStorageSetEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getIdentityStorageSetEventFromLog(log));
    }

    public Flowable<IdentityStorageSetEventResponse> identityStorageSetEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYSTORAGESET_EVENT));
        return identityStorageSetEventFlowable(filter);
    }

//    public static List<IdentityUpdatedEventResponse> getIdentityUpdatedEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(IDENTITYUPDATED_EVENT, transactionReceipt);
//        ArrayList<IdentityUpdatedEventResponse> responses = new ArrayList<IdentityUpdatedEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            IdentityUpdatedEventResponse typedResponse = new IdentityUpdatedEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.oldIdentity = (String) eventValues.getIndexedValues().get(0).getValue();
//            typedResponse.newIdentity = (String) eventValues.getIndexedValues().get(1).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static IdentityUpdatedEventResponse getIdentityUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(IDENTITYUPDATED_EVENT, log);
        IdentityUpdatedEventResponse typedResponse = new IdentityUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldIdentity = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newIdentity = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<IdentityUpdatedEventResponse> identityUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getIdentityUpdatedEventFromLog(log));
    }

    public Flowable<IdentityUpdatedEventResponse> identityUpdatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(IDENTITYUPDATED_EVENT));
        return identityUpdatedEventFlowable(filter);
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

//    public static List<TrustedIssuersRegistrySetEventResponse> getTrustedIssuersRegistrySetEvents(
//            TransactionReceipt transactionReceipt) {
//        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRUSTEDISSUERSREGISTRYSET_EVENT, transactionReceipt);
//        ArrayList<TrustedIssuersRegistrySetEventResponse> responses = new ArrayList<TrustedIssuersRegistrySetEventResponse>(valueList.size());
//        for (Contract.EventValuesWithLog eventValues : valueList) {
//            TrustedIssuersRegistrySetEventResponse typedResponse = new TrustedIssuersRegistrySetEventResponse();
//            typedResponse.log = eventValues.getLog();
//            typedResponse.trustedIssuersRegistry = (String) eventValues.getIndexedValues().get(0).getValue();
//            responses.add(typedResponse);
//        }
//        return responses;
//    }

    public static TrustedIssuersRegistrySetEventResponse getTrustedIssuersRegistrySetEventFromLog(
            Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRUSTEDISSUERSREGISTRYSET_EVENT, log);
        TrustedIssuersRegistrySetEventResponse typedResponse = new TrustedIssuersRegistrySetEventResponse();
        typedResponse.log = log;
        typedResponse.trustedIssuersRegistry = (String) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TrustedIssuersRegistrySetEventResponse> trustedIssuersRegistrySetEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTrustedIssuersRegistrySetEventFromLog(log));
    }

    public Flowable<TrustedIssuersRegistrySetEventResponse> trustedIssuersRegistrySetEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRUSTEDISSUERSREGISTRYSET_EVENT));
        return trustedIssuersRegistrySetEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addAgent(String _agent) {
        final Function function = new Function(
                FUNC_ADDAGENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _agent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> batchRegisterIdentity(List<String> _userAddresses,
            List<String> _identities, List<BigInteger> _countries) {
        final Function function = new Function(
                FUNC_BATCHREGISTERIDENTITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_userAddresses, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_identities, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint16>(
                        org.web3j.abi.datatypes.generated.Uint16.class,
                        org.web3j.abi.Utils.typeMap(_countries, org.web3j.abi.datatypes.generated.Uint16.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> contains(String _userAddress) {
        final Function function = new Function(FUNC_CONTAINS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _userAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deleteIdentity(String _userAddress) {
        final Function function = new Function(
                FUNC_DELETEIDENTITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _userAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> identity(String _userAddress) {
        final Function function = new Function(FUNC_IDENTITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _userAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> identityStorage() {
        final Function function = new Function(FUNC_IDENTITYSTORAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> init(String _trustedIssuersRegistry,
            String _claimTopicsRegistry, String _identityStorage) {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _trustedIssuersRegistry), 
                new org.web3j.abi.datatypes.Address(160, _claimTopicsRegistry), 
                new org.web3j.abi.datatypes.Address(160, _identityStorage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> investorCountry(String _userAddress) {
        final Function function = new Function(FUNC_INVESTORCOUNTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _userAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint16>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isAgent(String _agent) {
        final Function function = new Function(FUNC_ISAGENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _agent)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isVerified(String _userAddress) {
        final Function function = new Function(FUNC_ISVERIFIED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _userAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> issuersRegistry() {
        final Function function = new Function(FUNC_ISSUERSREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerIdentity(String _userAddress,
            String _identity, BigInteger _country) {
        final Function function = new Function(
                FUNC_REGISTERIDENTITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _userAddress), 
                new org.web3j.abi.datatypes.Address(160, _identity), 
                new org.web3j.abi.datatypes.generated.Uint16(_country)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAgent(String _agent) {
        final Function function = new Function(
                FUNC_REMOVEAGENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _agent)), 
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

    public RemoteFunctionCall<TransactionReceipt> setClaimTopicsRegistry(
            String _claimTopicsRegistry) {
        final Function function = new Function(
                FUNC_SETCLAIMTOPICSREGISTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _claimTopicsRegistry)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setIdentityRegistryStorage(
            String _identityRegistryStorage) {
        final Function function = new Function(
                FUNC_SETIDENTITYREGISTRYSTORAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _identityRegistryStorage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setTrustedIssuersRegistry(
            String _trustedIssuersRegistry) {
        final Function function = new Function(
                FUNC_SETTRUSTEDISSUERSREGISTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _trustedIssuersRegistry)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> topicsRegistry() {
        final Function function = new Function(FUNC_TOPICSREGISTRY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCountry(String _userAddress,
            BigInteger _country) {
        final Function function = new Function(
                FUNC_UPDATECOUNTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _userAddress), 
                new org.web3j.abi.datatypes.generated.Uint16(_country)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateIdentity(String _userAddress,
            String _identity) {
        final Function function = new Function(
                FUNC_UPDATEIDENTITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _userAddress), 
                new org.web3j.abi.datatypes.Address(160, _identity)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static IdentityRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new IdentityRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static IdentityRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new IdentityRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static IdentityRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new IdentityRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static IdentityRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new IdentityRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<IdentityRegistry> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IdentityRegistry.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<IdentityRegistry> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IdentityRegistry.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<IdentityRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(IdentityRegistry.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<IdentityRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(IdentityRegistry.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }



    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class AgentAddedEventResponse extends BaseEventResponse {
        public String _agent;
    }

    public static class AgentRemovedEventResponse extends BaseEventResponse {
        public String _agent;
    }

    public static class ClaimTopicsRegistrySetEventResponse extends BaseEventResponse {
        public String claimTopicsRegistry;
    }

    public static class CountryUpdatedEventResponse extends BaseEventResponse {
        public String investorAddress;

        public BigInteger country;
    }

    public static class IdentityRegisteredEventResponse extends BaseEventResponse {
        public String investorAddress;

        public String identity;
    }

    public static class IdentityRemovedEventResponse extends BaseEventResponse {
        public String investorAddress;

        public String identity;
    }

    public static class IdentityStorageSetEventResponse extends BaseEventResponse {
        public String identityStorage;
    }

    public static class IdentityUpdatedEventResponse extends BaseEventResponse {
        public String oldIdentity;

        public String newIdentity;
    }

    public static class InitializedEventResponse extends BaseEventResponse {
        public BigInteger version;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TrustedIssuersRegistrySetEventResponse extends BaseEventResponse {
        public String trustedIssuersRegistry;
    }
}
