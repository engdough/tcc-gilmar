package experiments.tests.approach;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Convert;

import experiments.contracts.Crowdsale;
import experiments.util.TransactionUtils;
import solunit.annotations.Account;
import solunit.annotations.Contract;
import solunit.runner.SolUnitRunner;

@RunWith(SolUnitRunner.class)
public class TestCrowdsale {
	
	@Contract
	Crowdsale crowdsale;
	
	@Account(id="main")
	Credentials mainAccount;
	
	@Account(id="1")
	Credentials account1;
	
	@Account(id="2")
	Credentials account2;
	
	@Account(id="3")
	Credentials account3;
	
	@Account(id="4")
	Credentials accountDestino;
	
	@Before
	public void setUp() throws Exception {
		this.crowdsale.init(this.accountDestino.getAddress(), 
							new BigInteger("100"), //goal
							new BigInteger("10"), //minutes
							new BigInteger("5"), //etherCostOfEachToken
							this.account3.getAddress());
	}
	
	@Test
	public void test() throws Exception {
		
		TransactionUtils.sendToContract(this.account1, this.crowdsale, 
				Convert.toWei("1", Convert.Unit.ETHER).toBigInteger() );
		
		BigInteger contractBalanceBefore = TransactionUtils.getBalance(this.crowdsale.getContractAddress());
		System.out.println( contractBalanceBefore );
		
		System.out.println( this.crowdsale.viewAmountRaised().send() );
		
	}
	

}
