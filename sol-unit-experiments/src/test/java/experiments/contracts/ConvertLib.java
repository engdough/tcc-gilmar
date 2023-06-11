package experiments.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
public class ConvertLib extends Contract {
    private static final String BINARY = "60b461002f600b82828239805160001a6073146000811461001f57610021565bfe5b5030600052607381538281f300730000000000000000000000000000000000000000301460806040526004361060555763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166396e4ee3d8114605a575b600080fd5b818015606557600080fd5b5060726004356024356084565b60408051918252519081900360200190f35b02905600a165627a7a72305820e5808a2cea9e87d79eddc4dd23f0b68ea26635f2e165c146dd5f052aa93494f80029";

    public static final String FUNC_CONVERT = "convert";

    protected ConvertLib(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ConvertLib(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> convert(BigInteger amount, BigInteger conversionRate) {
        final Function function = new Function(
                FUNC_CONVERT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(conversionRate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<ConvertLib> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ConvertLib.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ConvertLib> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ConvertLib.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static ConvertLib load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConvertLib(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ConvertLib load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ConvertLib(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
