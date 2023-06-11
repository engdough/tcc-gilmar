package experiments.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class Ballot extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610a5a806100206000396000f3006080604052600436106100cf5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630121b93f81146100d4578063013cf08b146100ee5780630645b8db1461011f5780632542bf06146101495780633368e96814610161578063429b92bf146101b65780635c19a95c146101ce578063609ff1bd146101ef57806398e527d3146102045780639e7b8d61146102195780639e7e2b091461023a578063a3ec138d1461026b578063d85c91ae146102ba578063e2ba53f0146102db575b600080fd5b3480156100e057600080fd5b506100ec6004356102f0565b005b3480156100fa57600080fd5b506101066004356103fb565b6040805192835260208301919091528051918290030190f35b34801561012b57600080fd5b50610137600435610427565b60408051918252519081900360200190f35b34801561015557600080fd5b506100ec60043561046d565b34801561016d57600080fd5b50604080516020600480358082013583810280860185019096528085526100ec953695939460249493850192918291850190849080828437509497506104fe9650505050505050565b3480156101c257600080fd5b5061013760043561059d565b3480156101da57600080fd5b506100ec600160a060020a03600435166105e3565b3480156101fb57600080fd5b506101376107f6565b34801561021057600080fd5b50610137610860565b34801561022557600080fd5b506100ec600160a060020a0360043516610866565b34801561024657600080fd5b5061024f6109a3565b60408051600160a060020a039092168252519081900360200190f35b34801561027757600080fd5b5061028c600160a060020a03600435166109b2565b604080519485529215156020850152600160a060020a03909116838301526060830152519081900360800190f35b3480156102c657600080fd5b50610137600160a060020a03600435166109e6565b3480156102e757600080fd5b50610137610a01565b33600090815260016020526040902080541515610357576040805160e560020a62461bcd02815260206004820152601460248201527f486173206e6f20726967687420746f20766f7465000000000000000000000000604482015290519081900360640190fd5b600181015460ff16156103b4576040805160e560020a62461bcd02815260206004820152600e60248201527f416c726561647920766f7465642e000000000000000000000000000000000000604482015290519081900360640190fd5b6001818101805460ff19169091179055600280820183905581548154909190849081106103dd57fe5b60009182526020909120600160029092020101805490910190555050565b600280548290811061040957fe5b60009182526020909120600290910201805460019091015490915082565b600254600090821061043857600080fd5b600082101561044657600080fd5b600280548390811061045457fe5b9060005260206000209060020201600101549050919050565b600054600160a060020a0316331461048457600080fd5b6040805180820190915290815260006020820181815260028054600181018255928190529251919092027f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace81019190915590517f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5acf90910155565b6000805473ffffffffffffffffffffffffffffffffffffffff19163317808255600160a060020a031681526001602081905260408220555b81518110156105995760026040805190810160405280848481518110151561055a57fe5b60209081029091018101518252600091810182905283546001818101865594835291819020835160029093020191825591909101519082015501610536565b5050565b60025460009082106105ae57600080fd5b60008210156105bc57600080fd5b60028054839081106105ca57fe5b9060005260206000209060020201600001549050919050565b33600090815260016020819052604082209081015490919060ff1615610653576040805160e560020a62461bcd02815260206004820152601260248201527f596f7520616c726561647920766f7465642e0000000000000000000000000000604482015290519081900360640190fd5b600160a060020a0383163314156106b4576040805160e560020a62461bcd02815260206004820152601e60248201527f53656c662d64656c65676174696f6e20697320646973616c6c6f7765642e0000604482015290519081900360640190fd5b600160a060020a03838116600090815260016020819052604090912001546101009004161561076157600160a060020a03928316600090815260016020819052604090912001546101009004909216913383141561075c576040805160e560020a62461bcd02815260206004820152601960248201527f466f756e64206c6f6f7020696e2064656c65676174696f6e2e00000000000000604482015290519081900360640190fd5b6106b4565b506001818101805460ff1916821774ffffffffffffffffffffffffffffffffffffffff001916610100600160a060020a0386169081029190911790915560009081526020829052604090209081015460ff16156107e9578154600282810154815481106107ca57fe5b60009182526020909120600160029092020101805490910190556107f1565b815481540181555b505050565b600080805b60025481101561085b578160028281548110151561081557fe5b906000526020600020906002020160010154111561085357600280548290811061083b57fe5b90600052602060002090600202016001015491508092505b6001016107fb565b505090565b60025490565b600054600160a060020a031633146108ee576040805160e560020a62461bcd02815260206004820152602860248201527f4f6e6c79206368616972706572736f6e2063616e20676976652072696768742060448201527f746f20766f74652e000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600160a060020a0381166000908152600160208190526040909120015460ff1615610963576040805160e560020a62461bcd02815260206004820152601860248201527f54686520766f74657220616c726561647920766f7465642e0000000000000000604482015290519081900360640190fd5b600160a060020a0381166000908152600160205260409020541561098657600080fd5b600160a060020a0316600090815260016020819052604090912055565b600054600160a060020a031690565b600160208190526000918252604090912080549181015460029091015460ff8216916101009004600160a060020a03169084565b600160a060020a031660009081526001602052604090205490565b60006002610a0d6107f6565b81548110610a1757fe5b9060005260206000209060020201600001549050905600a165627a7a723058202b5ea25a4eab1fdde57a13bf4df8de7d1bfbc12014c459e24f46ec39c8cc44540029";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_PROPOSALS = "proposals";

    public static final String FUNC_GETPROPOSALVOTECOUNT = "getProposalVoteCount";

    public static final String FUNC_CREATEPROPOSAL = "createProposal";

    public static final String FUNC_INIT = "init";

    public static final String FUNC_GETPROPOSALNAME = "getProposalName";

    public static final String FUNC_DELEGATE = "delegate";

    public static final String FUNC_WINNINGPROPOSAL = "winningProposal";

    public static final String FUNC_GETPROPOSALSCOUNT = "getProposalsCount";

    public static final String FUNC_GIVERIGHTTOVOTE = "giveRightToVote";

    public static final String FUNC_GETCHAIRPERSON = "getChairPerson";

    public static final String FUNC_VOTERS = "voters";

    public static final String FUNC_GETVOTERWEIGHT = "getVoterWeight";

    public static final String FUNC_WINNERNAME = "winnerName";

    protected Ballot(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ballot(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger proposal) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposal)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<byte[], BigInteger>> proposals(BigInteger param0) {
        final Function function = new Function(FUNC_PROPOSALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<byte[], BigInteger>>(
                new Callable<Tuple2<byte[], BigInteger>>() {
                    @Override
                    public Tuple2<byte[], BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<byte[], BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getProposalVoteCount(BigInteger index) {
        final Function function = new Function(FUNC_GETPROPOSALVOTECOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> createProposal(byte[] proposalName) {
        final Function function = new Function(
                FUNC_CREATEPROPOSAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(proposalName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> init(List<byte[]> proposalNames) {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.Utils.typeMap(proposalNames, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<byte[]> getProposalName(BigInteger index) {
        final Function function = new Function(FUNC_GETPROPOSALNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> delegate(String to) {
        final Function function = new Function(
                FUNC_DELEGATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> winningProposal() {
        final Function function = new Function(FUNC_WINNINGPROPOSAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getProposalsCount() {
        final Function function = new Function(FUNC_GETPROPOSALSCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> giveRightToVote(String voter) {
        final Function function = new Function(
                FUNC_GIVERIGHTTOVOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(voter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getChairPerson() {
        final Function function = new Function(FUNC_GETCHAIRPERSON, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple4<BigInteger, Boolean, String, BigInteger>> voters(String param0) {
        final Function function = new Function(FUNC_VOTERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple4<BigInteger, Boolean, String, BigInteger>>(
                new Callable<Tuple4<BigInteger, Boolean, String, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, Boolean, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, Boolean, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getVoterWeight(String voter) {
        final Function function = new Function(FUNC_GETVOTERWEIGHT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(voter)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<byte[]> winnerName() {
        final Function function = new Function(FUNC_WINNERNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public static RemoteCall<Ballot> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Ballot.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Ballot> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Ballot.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Ballot load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ballot(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Ballot load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ballot(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
