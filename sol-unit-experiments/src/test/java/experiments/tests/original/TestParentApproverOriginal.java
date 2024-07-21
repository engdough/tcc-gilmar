package experiments.tests.original;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.utils.Convert;

import experiments.contracts.ParentApprover;
import experiments.util.TransactionUtils;
import solunit.annotations.Account;
import solunit.annotations.Contract;
import solunit.constants.Config;
import solunit.internal.utilities.PropertiesReader;
import solunit.runner.SolUnitRunner;

/**
 * https://github.com/tbohnen/solidity-test-examples/blob/master/test/parent-approve-transaction-tests.js
 * 
 * @author hallan
 *
 */
@ExtendWith(SolUnitRunner.class)
public class TestParentApproverOriginal {
	
	@Contract
	ParentApprover pa;
	
	@Account(id="1")
	Credentials accountParent;
	
	@Account(id="2")
	Credentials accountChild;
	
	@Account(id="3")
	Credentials account3;
	
	Web3j web3j;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.web3j = Web3j.build(new HttpService(new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty(Config.WEB3_HOST)));
		
		TransactionUtils.sendToContract(this.accountParent, this.pa, 
				Convert.toWei("1", Convert.Unit.ETHER).toBigInteger() );
		
		this.pa.setParent( this.accountParent.getAddress() ).send();
		this.pa.setChild(this.accountChild.getAddress()).send();
		
		this.pa.submitTransaction(this.account3.getAddress(), Convert.toWei("1", Convert.Unit.ETHER).toBigInteger() ).send();
		
	}

	@Test
	public void parent_can_approve_transaction() throws Exception {
		BigInteger contractBalanceBefore = TransactionUtils.getBalance(this.pa.getContractAddress());
		System.out.println( contractBalanceBefore );
		
		this.pa.submitTransaction(this.account3.getAddress(), 
				Convert.toWei("0.1", Convert.Unit.ETHER).toBigInteger() ).send();
		
		this.pa.executeTransaction(new BigInteger("1")).send();
		
		BigInteger contractBalanceAfter= TransactionUtils.getBalance(this.pa.getContractAddress());
		System.out.println( contractBalanceAfter );
		
		BigInteger acb = TransactionUtils.getBalance(this.account3.getAddress());
		System.out.println( acb );
	}
	
	@Test
	public void parent_can_get_details_of_transaction_to_approve() throws Exception {
		Tuple3<String, BigInteger, Boolean> ta =
				this.pa.getTransactionDetail( new BigInteger("1") ).send();
		
		System.out.println( ta );
		Assertions.assertEquals(this.account3.getAddress(), ta.getValue1());
	}
	
	@Test
	public void parent_can_get_list_of_transactions_to_approve() throws Exception {
		List<RemoteCall> list = this.pa.getTransactionsToApprove().send();
		System.out.println( list );
		Assertions.assertEquals(2, list.size());
	}
	
	@Test
	public void parent_should_not_be_able_to_approve_same_transation_twice() throws Exception {
		this.pa.executeTransaction( new BigInteger(("1") ) ).send();
		this.pa.executeTransaction( new BigInteger(("1") ) ).send();
	}
	
	@Test
	public void should_be_able_to_view_parent_address() throws Exception {
		Assertions.assertEquals( this.accountParent.getAddress() , this.pa.getParent().send());
	}
	
	@Test
	public void should_be_able_to_view_child_address() throws Exception {
		Assertions.assertEquals( this.accountChild.getAddress() , this.pa.getChild().send());
	}
}
