package experiments.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
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
public class SimpleAuction extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405160408061057383398101604052805160209091015160008054600160a060020a03909216600160a060020a031990921691909117905542016001556105158061005e6000396000f3006080604052600436106100825763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631998aeef81146100875780632a24f46c1461009157806338af3eed146100a65780633ccfd60b146100e45780634b449cba1461010d57806391f9015714610134578063d57bde7914610149575b600080fd5b61008f61015e565b005b34801561009d57600080fd5b5061008f6102b0565b3480156100b257600080fd5b506100bb61043b565b6040805173ffffffffffffffffffffffffffffffffffffffff9092168252519081900360200190f35b3480156100f057600080fd5b506100f9610457565b604080519115158252519081900360200190f35b34801561011957600080fd5b506101226104c1565b60408051918252519081900360200190f35b34801561014057600080fd5b506100bb6104c7565b34801561015557600080fd5b506101226104e3565b6001544211156101b8576040805160e560020a62461bcd02815260206004820152601660248201527f41756374696f6e20616c726561647920656e6465642e00000000000000000000604482015290519081900360640190fd5b6003543411610211576040805160e560020a62461bcd02815260206004820152601e60248201527f546865726520616c7265616479206973206120686967686572206269642e0000604482015290519081900360640190fd5b6003541561024b5760035460025473ffffffffffffffffffffffffffffffffffffffff166000908152600460205260409020805490910190555b6002805473ffffffffffffffffffffffffffffffffffffffff19163390811790915534600381905560408051928352602083019190915280517ff4757a49b326036464bec6fe419a4ae38c8a02ce3e68bf0809674f6aab8ad3009281900390910190a1565b60015442101561030a576040805160e560020a62461bcd02815260206004820152601660248201527f41756374696f6e206e6f742079657420656e6465642e00000000000000000000604482015290519081900360640190fd5b60055460ff161561038b576040805160e560020a62461bcd02815260206004820152602360248201527f61756374696f6e456e642068617320616c7265616479206265656e2063616c6c60448201527f65642e0000000000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b6005805460ff191660011790556002546003546040805173ffffffffffffffffffffffffffffffffffffffff9093168352602083019190915280517fdaec4582d5d9595688c8c98545fdd1c696d41c6aeaeb636737e84ed2f5c00eda9281900390910190a16000805460035460405173ffffffffffffffffffffffffffffffffffffffff9092169281156108fc029290818181858888f19350505050158015610438573d6000803e3d6000fd5b50565b60005473ffffffffffffffffffffffffffffffffffffffff1681565b33600090815260046020526040812054818111156104b857336000818152600460205260408082208290555183156108fc0291849190818181858888f1935050505015156104b85733600090815260046020526040812082905591506104bd565b600191505b5090565b60015481565b60025473ffffffffffffffffffffffffffffffffffffffff1681565b600354815600a165627a7a723058204897f9343ce63cb22766c6f69a22b7c7de80f64b8f04bcdfd93171d2e7fa0e680029";

    public static final String FUNC_BID = "bid";

    public static final String FUNC_AUCTIONEND = "auctionEnd";

    public static final String FUNC_BENEFICIARY = "beneficiary";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_AUCTIONENDTIME = "auctionEndTime";

    public static final String FUNC_HIGHESTBIDDER = "highestBidder";

    public static final String FUNC_HIGHESTBID = "highestBid";

    public static final Event HIGHESTBIDINCREASED_EVENT = new Event("HighestBidIncreased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONENDED_EVENT = new Event("AuctionEnded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected SimpleAuction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleAuction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> bid(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> auctionEnd() {
        final Function function = new Function(
                FUNC_AUCTIONEND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> beneficiary() {
        final Function function = new Function(FUNC_BENEFICIARY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> withdraw() {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> auctionEndTime() {
        final Function function = new Function(FUNC_AUCTIONENDTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> highestBidder() {
        final Function function = new Function(FUNC_HIGHESTBIDDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> highestBid() {
        final Function function = new Function(FUNC_HIGHESTBID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<SimpleAuction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _biddingTime, String _beneficiary) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_biddingTime), 
                new org.web3j.abi.datatypes.Address(_beneficiary)));
        return deployRemoteCall(SimpleAuction.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<SimpleAuction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _biddingTime, String _beneficiary) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_biddingTime), 
                new org.web3j.abi.datatypes.Address(_beneficiary)));
        return deployRemoteCall(SimpleAuction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<HighestBidIncreasedEventResponse> getHighestBidIncreasedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HIGHESTBIDINCREASED_EVENT, transactionReceipt);
        ArrayList<HighestBidIncreasedEventResponse> responses = new ArrayList<HighestBidIncreasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HighestBidIncreasedEventResponse typedResponse = new HighestBidIncreasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<HighestBidIncreasedEventResponse> highestBidIncreasedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, HighestBidIncreasedEventResponse>() {
            @Override
            public HighestBidIncreasedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(HIGHESTBIDINCREASED_EVENT, log);
                HighestBidIncreasedEventResponse typedResponse = new HighestBidIncreasedEventResponse();
                typedResponse.log = log;
                typedResponse.bidder = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<HighestBidIncreasedEventResponse> highestBidIncreasedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HIGHESTBIDINCREASED_EVENT));
        return highestBidIncreasedEventObservable(filter);
    }

    public List<AuctionEndedEventResponse> getAuctionEndedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONENDED_EVENT, transactionReceipt);
        ArrayList<AuctionEndedEventResponse> responses = new ArrayList<AuctionEndedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.winner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AuctionEndedEventResponse> auctionEndedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, AuctionEndedEventResponse>() {
            @Override
            public AuctionEndedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(AUCTIONENDED_EVENT, log);
                AuctionEndedEventResponse typedResponse = new AuctionEndedEventResponse();
                typedResponse.log = log;
                typedResponse.winner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<AuctionEndedEventResponse> auctionEndedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(AUCTIONENDED_EVENT));
        return auctionEndedEventObservable(filter);
    }

    public static SimpleAuction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleAuction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SimpleAuction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleAuction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class HighestBidIncreasedEventResponse {
        public Log log;

        public String bidder;

        public BigInteger amount;
    }

    public static class AuctionEndedEventResponse {
        public Log log;

        public String winner;

        public BigInteger amount;
    }
}
