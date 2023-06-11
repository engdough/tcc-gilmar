package experiments.democracy.factory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.Properties;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import experiments.contracts.Democracy;
import solunit.constants.Config;
import solunit.internal.utilities.PropertiesReader;

public class TestDemocracyFactory {
	
	static Properties testProperties;
	
	static Web3j web3j;
	static Admin web3Admin;
	
	static Democracy Democracy;
	static String DemocracyAddress;

	public static void setMainAddressContract( Democracy d ) {
		Democracy = d;
		DemocracyAddress = d.getContractAddress();
	}
	
	public static TransactionReceipt createProposal( String title, String description, Date expirationDate, int neeededVotes ) throws Exception {
		RemoteCall<TransactionReceipt> call = Democracy.createProposal(title, description, BigInteger.valueOf(expirationDate.getTime()), BigInteger.valueOf(neeededVotes) );
		TransactionReceipt receipt = call.send();
		return receipt;
	}
	
	public static TransactionReceipt createVote( int proposalId, BigInteger vote ) throws Exception {
		RemoteCall<TransactionReceipt> call = Democracy.voteOnProposal(BigInteger.valueOf(proposalId), vote);
		TransactionReceipt receipt = call.send();
		return receipt;
	}
	
	public static TransactionReceipt createVote( Credentials credentials, int proposalId, BigInteger vote ) throws Exception {
		Democracy d = loadFromAddress(credentials);
		RemoteCall<TransactionReceipt> call = d.voteOnProposal(BigInteger.valueOf(proposalId), vote);
		TransactionReceipt receipt = call.send();
		return receipt;
	}
	
	public static Democracy loadFromAddress( String privateKey ) {
		init();
		Credentials credentials = Credentials.create(privateKey);
		return Democracy.load(DemocracyAddress, web3j, credentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
	}
	
	public static Democracy loadFromAddress( Credentials credentials ) {
		init();
		return Democracy.load(DemocracyAddress, web3j, credentials, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
	}
	
	private static void init() {
		if (testProperties == null) {
			try {
				testProperties = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE);
				web3Admin = Admin.build(new HttpService(testProperties.getProperty(Config.WEB3_HOST))); 
		    	web3j = Web3j.build(new HttpService(testProperties.getProperty(Config.WEB3_HOST)));
		    	
			} catch (IOException e) {
				 throw new RuntimeException(new IOException("Error reading properties file", e));
			}
		}
	}
	
}

