package experiments.tests.simple;

import java.math.BigInteger;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import experiments.contracts.Democracy;
import experiments.democracy.entity.Proposal;

public class TestSimpleForSlides {
	
	Web3j web3j;
	Credentials credentials;
	
	Democracy democracy;
	
	@BeforeEach
	public void setUp() throws Exception {
		web3j = Web3j.build(new HttpService("http://localhost:7545"));
		credentials = Credentials.create("1234");
		democracy = Democracy.deploy(web3j, credentials, 
										DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT)
										.send();
		
		democracy.createProposal("Title", "Description", 
									BigInteger.valueOf(new Date().getTime()), BigInteger.TEN)
									.send();
		
		democracy.voteOnProposal(BigInteger.ONE, BigInteger.ONE).send();
	}
	
	@Test
	public void verify_registered_proposal() throws Exception {
		Proposal p = new Proposal( this.democracy.getProposal(BigInteger.ONE).send() );
		Assertions.assertNotNull( p );
		Assertions.assertEquals("Title", p.getTitle());
	}

}
