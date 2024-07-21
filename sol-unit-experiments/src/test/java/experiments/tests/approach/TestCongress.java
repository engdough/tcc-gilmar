package experiments.tests.approach;

import java.math.BigInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import experiments.contracts.Congress;
import solunit.annotations.Account;
import solunit.annotations.Contract;
import solunit.constants.Config;
import solunit.internal.utilities.PropertiesReader;
import solunit.runner.SolUnitRunner;

@ExtendWith(SolUnitRunner.class)
public class TestCongress {

	@Contract
	Congress congress;
	
	@Account(id="main")
	Credentials mainAccount;
	
	@Account(id="1")
	Credentials account1;
	
	@Account(id="2")
	Credentials account2;
	
	@Account(id="4")
	Credentials account4;

	@Account(id="3")
	Credentials account3;
	
	Web3j web3j;
	
	@BeforeEach
	public void setUp() throws Exception {
		this.web3j = Web3j.build(new HttpService(new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty(Config.WEB3_HOST)));
		
		BigInteger minimumQuorumForProposals = new BigInteger("2");
		BigInteger minutesForDebate = new BigInteger("1");
		BigInteger marginOfVotesForMajority = new BigInteger("3");
		this.congress.changeVotingRules(minimumQuorumForProposals, 
									minutesForDebate, 
									marginOfVotesForMajority).send();
		
		this.congress.addMember(this.account1.getAddress(), "member1").send();
		this.congress.addMember(this.account2.getAddress(), "member2").send();
	}
	
	@Test
	public void should_set_initial_attributes() throws Exception {
		Assertions.assertEquals(this.mainAccount.getAddress(), this.congress.owner().send());
	}
	
	@Test
	public void should_add_new_proposal() throws Exception {
		
		congress.newProposal(this.account1.getAddress(), 
							Convert.toWei("1", Convert.Unit.ETHER).toBigInteger(), 
							"Test Description", new byte[] {}).send();
		
	}
	
	@Test
	public void should_allow_owner_add_members() throws Exception {
		//members array positions starts from 2.
        //members[0] - empty, members[1] - owner/founder, who deployed contract
		Tuple3<String, String, BigInteger> member = 
				this.congress.members(new BigInteger("2")).send();
		System.out.println( member );
		
		Assertions.assertEquals("member1", member.getValue2());
		
		Tuple3<String, String, BigInteger> member2 = 
				this.congress.members(new BigInteger("3")).send();
		System.out.println( member2 );
		
		Assertions.assertEquals("member2", member2.getValue2());
	}
	
	@Test
	public void should_disallow_no_owner_add_members() throws Exception {
		//esse cara é um problema (nao é safe mas vai acusar como sendo!)
		Congress c = loadFromCredential(this.account3);
		c.addMember(this.account4.getAddress(), "Member not valid").send();

		Assertions.assertThrows(RuntimeException.class, () -> {throw new RuntimeException("Erro esperado");});
	}
	
	private Congress loadFromCredential( Credentials c ) {
		return Congress.load(congress.getContractAddress(), web3j, c, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
	}
}
