package experiments.contracts;

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
public class MetaCoin extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610248806100206000396000f3006080604052600436106100615763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416637bd703e8811461006657806390b98a11146100a6578063b9983327146100eb578063f8b2cb4f14610100575b600080fd5b34801561007257600080fd5b5061009473ffffffffffffffffffffffffffffffffffffffff6004351661012e565b60408051918252519081900360200190f35b3480156100b257600080fd5b506100d773ffffffffffffffffffffffffffffffffffffffff60043516602435610142565b604080519115158252519081900360200190f35b3480156100f757600080fd5b506100d76101db565b34801561010c57600080fd5b5061009473ffffffffffffffffffffffffffffffffffffffff600435166101f4565b6000610139826101f4565b60020292915050565b33600090815260208190526040812054821115610161575060006101d5565b336000818152602081815260408083208054879003905573ffffffffffffffffffffffffffffffffffffffff871680845292819020805487019055805186815290519293927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929181900390910190a35060015b92915050565b3260009081526020819052604090206127109055600190565b73ffffffffffffffffffffffffffffffffffffffff16600090815260208190526040902054905600a165627a7a72305820413173e39fd61b11adf907c58de15fb70374f3e9bb2a5bacffe26cd3c1ffe1230029";

    public static final String FUNC_GETBALANCEINETH = "getBalanceInEth";

    public static final String FUNC_SENDCOIN = "sendCoin";

    public static final String FUNC_SETINITIALBALANCE = "setInitialBalance";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    protected MetaCoin(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MetaCoin(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> getBalanceInEth(String addr) {
        final Function function = new Function(FUNC_GETBALANCEINETH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> sendCoin(String receiver, BigInteger amount) {
        final Function function = new Function(
                FUNC_SENDCOIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(receiver), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setInitialBalance() {
        final Function function = new Function(
                FUNC_SETINITIALBALANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getBalance(String addr) {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(addr)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<MetaCoin> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MetaCoin.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<MetaCoin> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MetaCoin.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public static MetaCoin load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MetaCoin(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static MetaCoin load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MetaCoin(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TransferEventResponse {
        public Log log;

        public String _from;

        public String _to;

        public BigInteger _value;
    }
}
