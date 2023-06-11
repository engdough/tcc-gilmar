package experiments.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
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
public class TokenRecipient extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506102ed806100206000396000f3006080604052600436106100405763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416638f4ffcb1811461007c575b6040805133815234602082015281517fa398b89ba344a0b23a0b9de53db298b2a1a868b396c1878b7e9dcbafecd49b13929181900390910190a1005b34801561008857600080fd5b50604080516020601f6064356004818101359283018490048402850184019095528184526100f99473ffffffffffffffffffffffffffffffffffffffff8135811695602480359660443590931695369560849492019181908401838280828437509497506100fb9650505050505050565b005b604080517f23b872dd00000000000000000000000000000000000000000000000000000000815273ffffffffffffffffffffffffffffffffffffffff868116600483015230602483015260448201869052915184928316916323b872dd9160648083019260209291908290030181600087803b15801561017a57600080fd5b505af115801561018e573d6000803e3d6000fd5b505050506040513d60208110156101a457600080fd5b505115156101b157600080fd5b7f0eeb71b8926d7ed8f47a2cedf6b9b204e2001344c7fa20c696c9f06ea7c413c685858585604051808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561027d578181015183820152602001610265565b50505050905090810190601f1680156102aa5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a150505050505600a165627a7a723058203da75883c8abd97b179f914f2fadcdf95f4083fdb031e84c093fe7d0bccc56fd0029";

    public static final String FUNC_RECEIVEAPPROVAL = "receiveApproval";

    public static final Event RECEIVEDETHER_EVENT = new Event("receivedEther", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RECEIVEDTOKENS_EVENT = new Event("receivedTokens", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    protected TokenRecipient(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TokenRecipient(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> receiveApproval(String _from, BigInteger _value, String _token, byte[] _extraData) {
        final Function function = new Function(
                FUNC_RECEIVEAPPROVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.Address(_token), 
                new org.web3j.abi.datatypes.DynamicBytes(_extraData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<ReceivedEtherEventResponse> getReceivedEtherEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVEDETHER_EVENT, transactionReceipt);
        ArrayList<ReceivedEtherEventResponse> responses = new ArrayList<ReceivedEtherEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReceivedEtherEventResponse typedResponse = new ReceivedEtherEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ReceivedEtherEventResponse> receivedEtherEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ReceivedEtherEventResponse>() {
            @Override
            public ReceivedEtherEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVEDETHER_EVENT, log);
                ReceivedEtherEventResponse typedResponse = new ReceivedEtherEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ReceivedEtherEventResponse> receivedEtherEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVEDETHER_EVENT));
        return receivedEtherEventObservable(filter);
    }

    public List<ReceivedTokensEventResponse> getReceivedTokensEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVEDTOKENS_EVENT, transactionReceipt);
        ArrayList<ReceivedTokensEventResponse> responses = new ArrayList<ReceivedTokensEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReceivedTokensEventResponse typedResponse = new ReceivedTokensEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._token = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._extraData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ReceivedTokensEventResponse> receivedTokensEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ReceivedTokensEventResponse>() {
            @Override
            public ReceivedTokensEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVEDTOKENS_EVENT, log);
                ReceivedTokensEventResponse typedResponse = new ReceivedTokensEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._token = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._extraData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ReceivedTokensEventResponse> receivedTokensEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVEDTOKENS_EVENT));
        return receivedTokensEventObservable(filter);
    }

    public static RemoteCall<TokenRecipient> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TokenRecipient.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TokenRecipient> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TokenRecipient.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static TokenRecipient load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokenRecipient(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static TokenRecipient load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokenRecipient(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class ReceivedEtherEventResponse {
        public Log log;

        public String sender;

        public BigInteger amount;
    }

    public static class ReceivedTokensEventResponse {
        public Log log;

        public String _from;

        public BigInteger _value;

        public String _token;

        public byte[] _extraData;
    }
}
