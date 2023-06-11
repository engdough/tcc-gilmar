package experiments.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class SimpleAgenda extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610840806100206000396000f3006080604052600436106100775763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166327108501811461007c5780633187d15a14610096578063723f513f146101475780638caf451a1461023d578063d710b89114610255578063f87215411461027f575b600080fd5b34801561008857600080fd5b50610094600435610294565b005b3480156100a257600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261013394369492936024939284019190819084018382808284375050604080516020601f818a01358b0180359182018390048302840183018552818452989b8a359b909a9099940197509195509182019350915081908401838280828437509497506103609650505050505050565b604080519115158252519081900360200190f35b34801561015357600080fd5b5061015f600435610540565b604051808060200180602001838103835285818151815260200191508051906020019080838360005b838110156101a0578181015183820152602001610188565b50505050905090810190601f1680156101cd5780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b838110156102005781810151838201526020016101e8565b50505050905090810190601f16801561022d5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561024957600080fd5b5061013360043561068e565b34801561026157600080fd5b5061026d600435610703565b60408051918252519081900360200190f35b34801561028b57600080fd5b5061026d610725565b6000806102a08361068e565b156100775760008381526001602090815260409182902060030154825186815291820181905282519094507fe6659593063b1307f11217004b27fa27e0f4938e93c006402fb27ca63d8985c1929181900390910190a160008054600019810190811061030857fe5b906000526020600020015490508060008381548110151561032557fe5b600091825260208083209091019290925582815260019091526040812060030183905580549061035990600019830161072c565b505b505050565b600061036a610750565b6103738461068e565b15156100775750604080516080810182528581526020808201869052818301859052600080546060840152868152600182529290922081518051929384936103be928492019061077c565b506020828101516001830155604083015180516103e1926002850192019061077c565b50606091820151600390910155600080546001810182558180527f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e5630186905580546040805160208181018a905294810183905260808082528a519082015289517fbf3498253fa22004864a7f47122a58e603d7703dc019e1fdefc47d74394aedab958b958b958b9590949384939184019260a08501928a0191908190849084905b8381101561049a578181015183820152602001610482565b50505050905090810190601f1680156104c75780820380516001836020036101000a031916815260200191505b50838103825285518152855160209182019187019080838360005b838110156104fa5781810151838201526020016104e2565b50505050905090810190601f1680156105275780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a1509392505050565b606080600061054e8461068e565b156100775750600083815260016020818152604092839020805484516002600019958316156101000295909501909116849004601f8101849004840282018401909552848152909384938401928491908301828280156105ef5780601f106105c4576101008083540402835291602001916105ef565b820191906000526020600020905b8154815290600101906020018083116105d257829003601f168201915b5050845460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529597508694509250840190508282801561067d5780601f106106525761010080835404028352916020019161067d565b820191906000526020600020905b81548152906001019060200180831161066057829003601f168201915b505050505090509250925050915091565b6000805415156106a0575060006106fe565b604080518381528151602091819003820190206000858152600190925291812060030154815481106106ce57fe5b60009182526020918290200154604080519182525190819003909101902014156106fa575060016106fe565b5060005b919050565b6000808281548110151561071357fe5b90600052602060002001549050919050565b6000545b90565b81548183558181111561035b5760008381526020902061035b9181019083016107fa565b608060405190810160405280606081526020016000801916815260200160608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106107bd57805160ff19168380011785556107ea565b828001600101855582156107ea579182015b828111156107ea5782518255916020019190600101906107cf565b506107f69291506107fa565b5090565b61072991905b808211156107f657600081556001016108005600a165627a7a723058200092e834be7f3ac719a205ba4fb747c1d8d5785c0efc524f36eac293f35b39610029";

    public static final String FUNC_DELETECONTACT = "deleteContact";

    public static final String FUNC_NEWCONTACT = "newContact";

    public static final String FUNC_GETCONTACT = "getContact";

    public static final String FUNC_CONTACTEXIST = "contactExist";

    public static final String FUNC_GETNUMBERATINDEX = "getNumberAtIndex";

    public static final String FUNC_COUNTCONTACTS = "countContacts";

    public static final Event ADDNEWCONTACT_EVENT = new Event("AddNewContact", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DELETECONTACT_EVENT = new Event("DeleteContact", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    protected SimpleAgenda(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleAgenda(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> deleteContact(byte[] _number) {
        final Function function = new Function(
                FUNC_DELETECONTACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_number)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> newContact(String _name, byte[] _number, String _email) {
        final Function function = new Function(
                FUNC_NEWCONTACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.generated.Bytes32(_number), 
                new org.web3j.abi.datatypes.Utf8String(_email)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<String, String>> getContact(byte[] _number) {
        final Function function = new Function(FUNC_GETCONTACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_number)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<String, String>>(
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<Boolean> contactExist(byte[] _number) {
        final Function function = new Function(FUNC_CONTACTEXIST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_number)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<byte[]> getNumberAtIndex(BigInteger index) {
        final Function function = new Function(FUNC_GETNUMBERATINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<BigInteger> countContacts() {
        final Function function = new Function(FUNC_COUNTCONTACTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<AddNewContactEventResponse> getAddNewContactEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDNEWCONTACT_EVENT, transactionReceipt);
        ArrayList<AddNewContactEventResponse> responses = new ArrayList<AddNewContactEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddNewContactEventResponse typedResponse = new AddNewContactEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.number = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.email = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AddNewContactEventResponse> addNewContactEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, AddNewContactEventResponse>() {
            @Override
            public AddNewContactEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDNEWCONTACT_EVENT, log);
                AddNewContactEventResponse typedResponse = new AddNewContactEventResponse();
                typedResponse.log = log;
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.number = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.email = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<AddNewContactEventResponse> addNewContactEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDNEWCONTACT_EVENT));
        return addNewContactEventObservable(filter);
    }

    public List<DeleteContactEventResponse> getDeleteContactEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DELETECONTACT_EVENT, transactionReceipt);
        ArrayList<DeleteContactEventResponse> responses = new ArrayList<DeleteContactEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DeleteContactEventResponse typedResponse = new DeleteContactEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.number = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DeleteContactEventResponse> deleteContactEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, DeleteContactEventResponse>() {
            @Override
            public DeleteContactEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DELETECONTACT_EVENT, log);
                DeleteContactEventResponse typedResponse = new DeleteContactEventResponse();
                typedResponse.log = log;
                typedResponse.number = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<DeleteContactEventResponse> deleteContactEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DELETECONTACT_EVENT));
        return deleteContactEventObservable(filter);
    }

    public static RemoteCall<SimpleAgenda> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleAgenda.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SimpleAgenda> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleAgenda.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static SimpleAgenda load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleAgenda(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SimpleAgenda load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleAgenda(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class AddNewContactEventResponse {
        public Log log;

        public String name;

        public byte[] number;

        public String email;

        public BigInteger index;
    }

    public static class DeleteContactEventResponse {
        public Log log;

        public byte[] number;

        public BigInteger index;
    }
}
