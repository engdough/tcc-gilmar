package experiments.util;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import solunit.constants.Config;
import solunit.internal.utilities.PropertiesReader;

public class TransactionUtils {

	public static EthSendTransaction sendToWallet( Credentials from, Credentials to, BigInteger value ) throws Exception {
		Web3j web3j = getWeb3j();
		
		EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
				from.getAddress(), DefaultBlockParameterName.LATEST)
				.sendAsync().get();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();

		RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
				nonce, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, 
				to.getAddress(), value);

		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, from);
		String hexValue = Numeric.toHexString(signedMessage);

		return web3j.ethSendRawTransaction(hexValue).sendAsync().get();
	}

	public static void sendToContract( Credentials from, Contract to, BigInteger value ) throws Exception {
		Web3j web3j = getWeb3j();
		
		EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
				from.getAddress(), DefaultBlockParameterName.LATEST)
				.sendAsync().get();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();

		RawTransaction rawTransaction  = RawTransaction.createEtherTransaction(
				nonce, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, 
				to.getContractAddress(), value);

		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, from);
		String hexValue = Numeric.toHexString(signedMessage);

		web3j.ethSendRawTransaction(hexValue).sendAsync().get();
	}

	public static BigInteger getBalance(String address) throws Exception {
		Web3j web3j = getWeb3j();
		
		return web3j.ethGetBalance(address, 
								DefaultBlockParameterName.LATEST).send().getBalance();
	}
	
	private static Web3j getWeb3j() throws IOException {
		return Web3j.build(new HttpService(new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty(Config.WEB3_HOST)));
	}
}
