package experiments.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
public class ParentApprover extends Contract {
    private static final String BINARY = "6080604052600160025534801561001557600080fd5b50610599806100256000396000f3006080604052600436106100985763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631499c59281146100b15780631d43b653146100d257806325aee269146101085780632a43f3381461016d5780633998f59d1461019e57806380f76021146101bf5780639ace38c2146101d45780639ff512fa14610216578063ee22610b1461022e575b600054600160a060020a031615156100af57600080fd5b005b3480156100bd57600080fd5b506100af600160a060020a0360043516610246565b3480156100de57600080fd5b506100f6600160a060020a036004351660243561028b565b60408051918252519081900360200190f35b34801561011457600080fd5b5061011d61033a565b60408051602080825283518183015283519192839290830191858101910280838360005b83811015610159578181015183820152602001610141565b505050509050019250505060405180910390f35b34801561017957600080fd5b506101826103af565b60408051600160a060020a039092168252519081900360200190f35b3480156101aa57600080fd5b506100af600160a060020a03600435166103be565b3480156101cb57600080fd5b50610182610403565b3480156101e057600080fd5b506101ec600435610412565b60408051600160a060020a0390941684526020840192909252151582820152519081900360600190f35b34801561022257600080fd5b506101ec600435610440565b34801561023a57600080fd5b506100af60043561049a565b600054600160a060020a03161561025c57600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b6002805460408051606081018252600160a060020a0386811682526020808301878152600084860181815287825260039093528581209451855473ffffffffffffffffffffffffffffffffffffffff19169416939093178455516001808501919091559051928601805460ff191693151593909317909255845490910190935551909182917fc0ba8fe4b176c1714197d43b9cc6bcf797a4a7461c5fe8d0ef6e184ae7601e519190a292915050565b60608060008060025460405190808252806020026020018201604052801561036c578160200160208202803883390190505b50925060009150600090505b6002548110156103a75780838381518110151561039157fe5b6020908102909101015260019182019101610378565b509092915050565b600154600160a060020a031690565b600154600160a060020a0316156103d457600080fd5b6001805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b600054600160a060020a031690565b600360205260009081526040902080546001820154600290920154600160a060020a03909116919060ff1683565b600080600061044d61054d565b5050506000918252506003602090815260409182902082516060810184528154600160a060020a0316808252600183015493820184905260029092015460ff161515930183905292909190565b6104a261054d565b5060008181526003602090815260409182902082516060810184528154600160a060020a031681526001820154928101929092526002015460ff16151591810182905290610549576001604080830191909152815160208301519151600160a060020a039091169190600081818185875af192505050156105495760405182907f33e13ecb54c3076d8e8bb8c2881800a4d972b792045ffae98fdf46df365fed7590600090a25b5050565b6040805160608101825260008082526020820181905291810191909152905600a165627a7a72305820fcdfe93e2ffae397387b7d226f98b178a9b47fa99b3eeb268e9580f7d7198ae50029";

    public static final String FUNC_SETPARENT = "setParent";

    public static final String FUNC_SUBMITTRANSACTION = "submitTransaction";

    public static final String FUNC_GETTRANSACTIONSTOAPPROVE = "getTransactionsToApprove";

    public static final String FUNC_GETCHILD = "getChild";

    public static final String FUNC_SETCHILD = "setChild";

    public static final String FUNC_GETPARENT = "getParent";

    public static final String FUNC_TRANSACTIONS = "transactions";

    public static final String FUNC_GETTRANSACTIONDETAIL = "getTransactionDetail";

    public static final String FUNC_EXECUTETRANSACTION = "executeTransaction";

    public static final Event SUBMISSION_EVENT = new Event("Submission", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    public static final Event EXECUTION_EVENT = new Event("Execution", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}));
    ;

    protected ParentApprover(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ParentApprover(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> setParent(String parentAddress) {
        final Function function = new Function(
                FUNC_SETPARENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(parentAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> submitTransaction(String destination, BigInteger value) {
        final Function function = new Function(
                FUNC_SUBMITTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(destination), 
                new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> getTransactionsToApprove() {
        final Function function = new Function(FUNC_GETTRANSACTIONSTOAPPROVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<String> getChild() {
        final Function function = new Function(FUNC_GETCHILD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setChild(String childAddress) {
        final Function function = new Function(
                FUNC_SETCHILD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(childAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getParent() {
        final Function function = new Function(FUNC_GETPARENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<String, BigInteger, Boolean>> transactions(BigInteger param0) {
        final Function function = new Function(FUNC_TRANSACTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple3<String, BigInteger, Boolean>>(
                new Callable<Tuple3<String, BigInteger, Boolean>>() {
                    @Override
                    public Tuple3<String, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<Tuple3<String, BigInteger, Boolean>> getTransactionDetail(BigInteger transactionId) {
        final Function function = new Function(FUNC_GETTRANSACTIONDETAIL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(transactionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple3<String, BigInteger, Boolean>>(
                new Callable<Tuple3<String, BigInteger, Boolean>>() {
                    @Override
                    public Tuple3<String, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> executeTransaction(BigInteger transactionId) {
        final Function function = new Function(
                FUNC_EXECUTETRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(transactionId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<SubmissionEventResponse> getSubmissionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SUBMISSION_EVENT, transactionReceipt);
        ArrayList<SubmissionEventResponse> responses = new ArrayList<SubmissionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SubmissionEventResponse typedResponse = new SubmissionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.transactionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SubmissionEventResponse> submissionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SubmissionEventResponse>() {
            @Override
            public SubmissionEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SUBMISSION_EVENT, log);
                SubmissionEventResponse typedResponse = new SubmissionEventResponse();
                typedResponse.log = log;
                typedResponse.transactionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SubmissionEventResponse> submissionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUBMISSION_EVENT));
        return submissionEventObservable(filter);
    }

    public List<ExecutionEventResponse> getExecutionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EXECUTION_EVENT, transactionReceipt);
        ArrayList<ExecutionEventResponse> responses = new ArrayList<ExecutionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ExecutionEventResponse typedResponse = new ExecutionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.transactionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ExecutionEventResponse> executionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ExecutionEventResponse>() {
            @Override
            public ExecutionEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(EXECUTION_EVENT, log);
                ExecutionEventResponse typedResponse = new ExecutionEventResponse();
                typedResponse.log = log;
                typedResponse.transactionId = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ExecutionEventResponse> executionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXECUTION_EVENT));
        return executionEventObservable(filter);
    }

    public static RemoteCall<ParentApprover> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ParentApprover.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ParentApprover> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ParentApprover.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static ParentApprover load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ParentApprover(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ParentApprover load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ParentApprover(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class SubmissionEventResponse {
        public Log log;

        public BigInteger transactionId;
    }

    public static class ExecutionEventResponse {
        public Log log;

        public BigInteger transactionId;
    }
}
