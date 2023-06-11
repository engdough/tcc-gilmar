package experiments.contracts;

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
public class Crowdsale extends Contract {
    private static final String BINARY = "60806040526007805461ffff1916905534801561001b57600080fd5b506105c08061002b6000396000f3006080604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166301cb3b2081146101d457806329dcb0cf146101eb57806338af3eed1461021257806339bd0430146102435780636e66f6e91461027657806370a082311461028b5780637a3a0e84146102ac5780637b3e5e7b146102c1578063a035b1fe146102d6578063db1ac505146102eb578063fd6b7ef814610300575b600754600090610100900460ff16156100c657600080fd5b5033600081815260066020526040902080543490810190915560028054820190556005546004549192600160a060020a039091169163a9059cbb91908481151561010c57fe5b046040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018083600160a060020a0316600160a060020a0316815260200182815260200192505050600060405180830381600087803b15801561017857600080fd5b505af115801561018c573d6000803e3d6000fd5b5050604080513381526020810185905260018183015290517fe842aea7a5f1b01049d752008c53c52890b1a6daf660cf39e8eec506112bbdf69350908190036060019150a150005b3480156101e057600080fd5b506101e9610315565b005b3480156101f757600080fd5b50610200610394565b60408051918252519081900360200190f35b34801561021e57600080fd5b5061022761039a565b60408051600160a060020a039092168252519081900360200190f35b34801561024f57600080fd5b506101e9600160a060020a03600435811690602435906044359060643590608435166103a9565b34801561028257600080fd5b50610227610404565b34801561029757600080fd5b50610200600160a060020a0360043516610413565b3480156102b857600080fd5b50610200610425565b3480156102cd57600080fd5b5061020061042b565b3480156102e257600080fd5b50610200610431565b3480156102f757600080fd5b50610200610437565b34801561030c57600080fd5b506101e961043c565b60035442106103925760015460025410610382576007805460ff1916600117905560005460025460408051600160a060020a039093168352602083019190915280517fec3f991caf7857d61663fd1bba1739e04abd4781238508cde554bb849d790c859281900390910190a15b6007805461ff0019166101001790555b565b60035481565b600054600160a060020a031681565b6000805473ffffffffffffffffffffffffffffffffffffffff19908116600160a060020a0397881617909155670de0b6b3a7640000948502600155603c93909302420160035592026004556005805490911691909216179055565b600554600160a060020a031681565b60066020526000908152604090205481565b60015481565b60025481565b60045481565b600090565b60035460009042106105915760075460ff1615156104eb5750336000908152600660205260408120805490829055908111156104eb57604051339082156108fc029083906000818181858888f19350505050156104d857604080513381526020810183905260008183015290517fe842aea7a5f1b01049d752008c53c52890b1a6daf660cf39e8eec506112bbdf69181900360600190a16104eb565b3360009081526006602052604090208190555b60075460ff1680156105075750600054600160a060020a031633145b1561059157600254604051339180156108fc02916000818181858888f1935050505015610586576000805460025460408051600160a060020a03909316835260208301919091528181019290925290517fe842aea7a5f1b01049d752008c53c52890b1a6daf660cf39e8eec506112bbdf69181900360600190a1610591565b6007805460ff191690555b505600a165627a7a723058207491357c73eed029a4038c7ffb817abaf6b76bc84a553c70835ffccc361c705c0029";

    public static final String FUNC_CHECKGOALREACHED = "checkGoalReached";

    public static final String FUNC_DEADLINE = "deadline";

    public static final String FUNC_BENEFICIARY = "beneficiary";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_TOKENREWARD = "tokenReward";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_FUNDINGGOAL = "fundingGoal";

    public static final String FUNC_AMOUNTRAISED = "amountRaised";

    public static final String FUNC_PRICE = "price";

    public static final String FUNC_VIEWAMOUNTRAISED = "viewAmountRaised";

    public static final String FUNC_SAFEWITHDRAWAL = "safeWithdrawal";

    public static final Event GOALREACHED_EVENT = new Event("GoalReached", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FUNDTRANSFER_EVENT = new Event("FundTransfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    protected Crowdsale(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Crowdsale(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> checkGoalReached() {
        final Function function = new Function(
                FUNC_CHECKGOALREACHED, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> deadline() {
        final Function function = new Function(FUNC_DEADLINE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> beneficiary() {
        final Function function = new Function(FUNC_BENEFICIARY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> init(String ifSuccessfulSendTo, BigInteger fundingGoalInEthers, BigInteger durationInMinutes, BigInteger etherCostOfEachToken, String addressOfTokenUsedAsReward) {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ifSuccessfulSendTo), 
                new org.web3j.abi.datatypes.generated.Uint256(fundingGoalInEthers), 
                new org.web3j.abi.datatypes.generated.Uint256(durationInMinutes), 
                new org.web3j.abi.datatypes.generated.Uint256(etherCostOfEachToken), 
                new org.web3j.abi.datatypes.Address(addressOfTokenUsedAsReward)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> tokenReward() {
        final Function function = new Function(FUNC_TOKENREWARD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> balanceOf(String param0) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> fundingGoal() {
        final Function function = new Function(FUNC_FUNDINGGOAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> amountRaised() {
        final Function function = new Function(FUNC_AMOUNTRAISED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> price() {
        final Function function = new Function(FUNC_PRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> viewAmountRaised() {
        final Function function = new Function(FUNC_VIEWAMOUNTRAISED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> safeWithdrawal() {
        final Function function = new Function(
                FUNC_SAFEWITHDRAWAL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Crowdsale> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Crowdsale.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Crowdsale> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Crowdsale.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<GoalReachedEventResponse> getGoalReachedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(GOALREACHED_EVENT, transactionReceipt);
        ArrayList<GoalReachedEventResponse> responses = new ArrayList<GoalReachedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            GoalReachedEventResponse typedResponse = new GoalReachedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.totalAmountRaised = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<GoalReachedEventResponse> goalReachedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, GoalReachedEventResponse>() {
            @Override
            public GoalReachedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(GOALREACHED_EVENT, log);
                GoalReachedEventResponse typedResponse = new GoalReachedEventResponse();
                typedResponse.log = log;
                typedResponse.recipient = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.totalAmountRaised = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<GoalReachedEventResponse> goalReachedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(GOALREACHED_EVENT));
        return goalReachedEventObservable(filter);
    }

    public List<FundTransferEventResponse> getFundTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(FUNDTRANSFER_EVENT, transactionReceipt);
        ArrayList<FundTransferEventResponse> responses = new ArrayList<FundTransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundTransferEventResponse typedResponse = new FundTransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.backer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.isContribution = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<FundTransferEventResponse> fundTransferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, FundTransferEventResponse>() {
            @Override
            public FundTransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(FUNDTRANSFER_EVENT, log);
                FundTransferEventResponse typedResponse = new FundTransferEventResponse();
                typedResponse.log = log;
                typedResponse.backer = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.isContribution = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<FundTransferEventResponse> fundTransferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDTRANSFER_EVENT));
        return fundTransferEventObservable(filter);
    }

    public static Crowdsale load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Crowdsale(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Crowdsale load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Crowdsale(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class GoalReachedEventResponse {
        public Log log;

        public String recipient;

        public BigInteger totalAmountRaised;
    }

    public static class FundTransferEventResponse {
        public Log log;

        public String backer;

        public BigInteger amount;

        public Boolean isContribution;
    }
}
