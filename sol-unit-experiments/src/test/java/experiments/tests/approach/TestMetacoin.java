package experiments.tests.approach;

import java.math.BigInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.web3j.crypto.Credentials;

import experiments.contracts.MetaCoin;
import solunit.annotations.Account;
import solunit.annotations.Contract;
import solunit.runner.SolUnitRunner;

@ExtendWith(SolUnitRunner.class)
public class TestMetacoin {
	
	@Contract
	MetaCoin metacoin;
	
	@Account(id="main")
	Credentials mainAccount;
	
	@Account(id="1")
	Credentials account1;
	
	@Account(id="2")
	Credentials account2;
	
	@Account(id="3")
	Credentials account3;
	
	@Account(id="4")
	Credentials account4;
	
	@BeforeEach
	public  void setUp() throws Exception {
		this.metacoin.setInitialBalance().send();
	}
	
	@Test
	public void should_have_balance_in_main_account() throws Exception {
		BigInteger i = this.metacoin.getBalance(this.mainAccount.getAddress()).send();
		Assertions.assertEquals(10000, i.intValue());
	}
	
	@Test
	public void should_have_balance_in_main_account_in_eth() throws Exception {
		BigInteger i = this.metacoin.getBalanceInEth(this.mainAccount.getAddress()).send();
		Assertions.assertEquals(20000, i.intValue());
	}
	
	@Test
	public void should_send_coin() throws Exception {
		BigInteger i = this.metacoin.getBalance(this.account1.getAddress()).send();
		Assertions.assertEquals(0, i.intValue());
		
		this.metacoin.sendCoin(this.account1.getAddress(), new BigInteger("100")).send();
		
		i = this.metacoin.getBalance(this.account1.getAddress()).send();
		Assertions.assertEquals(100, i.intValue());
		
		i = this.metacoin.getBalance(this.mainAccount.getAddress()).send();
		Assertions.assertEquals(9900, i.intValue());
	}

}
