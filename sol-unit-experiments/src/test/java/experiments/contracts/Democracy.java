package experiments.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple9;
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
public class Democracy extends Contract {
    private static final String BINARY = "608060405260008054600160a060020a0319163317905534801561002257600080fd5b50610ad7806100326000396000f3006080604052600436106100775763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663013cf08b811461007c57806341c0e1b5146101a0578063556f6cc0146101b75780636b5e1766146101d2578063bc378a7314610272578063c7f758a814610299575b600080fd5b34801561008857600080fd5b506100946004356103d2565b60405180806020018060200187600160a060020a0316600160a060020a03168152602001868152602001858152602001848152602001838103835289818151815260200191508051906020019080838360005b838110156100ff5781810151838201526020016100e7565b50505050905090810190601f16801561012c5780820380516001836020036101000a031916815260200191505b5083810382528851815288516020918201918a019080838360005b8381101561015f578181015183820152602001610147565b50505050905090810190601f16801561018c5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b3480156101ac57600080fd5b506101b5610544565b005b3480156101c357600080fd5b506101b5600435602435610567565b3480156101de57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101b594369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375094975050843595505050602090920135915061062f9050565b34801561027e57600080fd5b50610287610757565b60408051918252519081900360200190f35b3480156102a557600080fd5b506102b160043561075e565b604051808a8152602001806020018060200189600160a060020a0316600160a060020a0316815260200188815260200187815260200186815260200185815260200184815260200183810383528b818151815260200191508051906020019080838360005b8381101561032e578181015183820152602001610316565b50505050905090810190601f16801561035b5780820380516001836020036101000a031916815260200191505b5083810382528a5181528a516020918201918c019080838360005b8381101561038e578181015183820152602001610376565b50505050905090810190601f1680156103bb5780820380516001836020036101000a031916815260200191505b509b50505050505050505050505060405180910390f35b60018054829081106103e057fe5b60009182526020918290206008919091020180546040805160026001841615610100026000190190931692909204601f8101859004850283018501909152808252919350918391908301828280156104795780601f1061044e57610100808354040283529160200191610479565b820191906000526020600020905b81548152906001019060200180831161045c57829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105175780601f106104ec57610100808354040283529160200191610517565b820191906000526020600020905b8154815290600101906020018083116104fa57829003601f168201915b505050506002830154600384015460048501546007909501549394600160a060020a039092169390925086565b600054600160a060020a031633141561056557600054600160a060020a0316ff5b565b600081600114806105785750816002145b151561058357600080fd5b600180548490811061059157fe5b906000526020600020906008020190508060030154421015156105b357600080fd5b81600114156105f5576005810180546001810182556000918252602090912001805473ffffffffffffffffffffffffffffffffffffffff19163317905561062a565b6006810180546001810182556000918252602090912001805473ffffffffffffffffffffffffffffffffffffffff1916331790555b505050565b610637610925565b84815260208082018590526060820184905260808201839052336040830152600160e083018190528054808201808355600092909252835180519293859360089093027fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf601926106aa9284920190610974565b5060208281015180516106c39260018501920190610974565b50604082015160028201805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909216919091179055606082015160038201556080820151600482015560a082015180516107269160058401916020909101906109f2565b5060c082015180516107429160068401916020909101906109f2565b5060e082015181600701555050505050505050565b6001545b90565b600060608060008060008060008060008a60018054905010151561091757600180548c90811061078a57fe5b906000526020600020906008020190508a81600001826001018360020160009054906101000a9004600160a060020a031684600301548560040154866005018054905087600601805490508860070154878054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561086f5780601f106108445761010080835404028352916020019161086f565b820191906000526020600020905b81548152906001019060200180831161085257829003601f168201915b50508a5460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152959d508c9450925084019050828280156108fd5780601f106108d2576101008083540402835291602001916108fd565b820191906000526020600020905b8154815290600101906020018083116108e057829003601f168201915b505050505096509950995099509950995099509950995099505b509193959799909294969850565b6101006040519081016040528060608152602001606081526020016000600160a060020a0316815260200160008152602001600081526020016060815260200160608152602001600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109b557805160ff19168380011785556109e2565b828001600101855582156109e2579182015b828111156109e25782518255916020019190600101906109c7565b506109ee929150610a60565b5090565b828054828255906000526020600020908101928215610a54579160200282015b82811115610a54578251825473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03909116178255602090920191600190910190610a12565b506109ee929150610a7a565b61075b91905b808211156109ee5760008155600101610a66565b61075b91905b808211156109ee57805473ffffffffffffffffffffffffffffffffffffffff19168155600101610a805600a165627a7a7230582028816eeea1a3bc908fc4f20638ee0c30ac3dee86cb49047b4fabb93fc1568bba0029";

    public static final String FUNC_PROPOSALS = "proposals";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_VOTEONPROPOSAL = "voteOnProposal";

    public static final String FUNC_CREATEPROPOSAL = "createProposal";

    public static final String FUNC_GETPROPOSALSLENGTH = "getProposalsLength";

    public static final String FUNC_GETPROPOSAL = "getProposal";

    protected Democracy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Democracy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>> proposals(BigInteger param0) {
        final Function function = new Function(FUNC_PROPOSALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<String, String, String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, String, String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> kill() {
        final Function function = new Function(
                FUNC_KILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> voteOnProposal(BigInteger index, BigInteger vote) {
        final Function function = new Function(
                FUNC_VOTEONPROPOSAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index), 
                new org.web3j.abi.datatypes.generated.Uint256(vote)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> createProposal(String title, String description, BigInteger expirationDate, BigInteger neededVotes) {
        final Function function = new Function(
                FUNC_CREATEPROPOSAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(title), 
                new org.web3j.abi.datatypes.Utf8String(description), 
                new org.web3j.abi.datatypes.generated.Uint256(expirationDate), 
                new org.web3j.abi.datatypes.generated.Uint256(neededVotes)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getProposalsLength() {
        final Function function = new Function(FUNC_GETPROPOSALSLENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getProposal(BigInteger index) {
        final Function function = new Function(FUNC_GETPROPOSAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (BigInteger) results.get(8).getValue());
                    }
                });
    }

    public static RemoteCall<Democracy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Democracy.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Democracy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Democracy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Democracy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Democracy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Democracy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Democracy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
