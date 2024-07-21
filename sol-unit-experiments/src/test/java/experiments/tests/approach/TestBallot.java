package experiments.tests.approach;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

import experiments.contracts.Ballot;
import solunit.annotations.Account;
import solunit.annotations.Contract;
import solunit.runner.SolUnitRunner;

/**
 * 
 * https://www.verypossible.com/blog/ethereum-smart-contracts-learning-solidity-by-example
 * https://github.com/bbrock25/truffle-ballot
 * 
 * @author hallan
 *
 */
@ExtendWith(SolUnitRunner.class)
public class TestBallot {
	
	@Contract
	Ballot ballot;
	
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

	@Inject
	Web3j web3j;
	
	
	
	@BeforeEach
	public  void setUp() throws Exception {
		List<byte[]> lista = new ArrayList<>();
		lista.add(this.stringToByteArray("test"));
		lista.add(this.stringToByteArray("test 2"));
		this.ballot.init( lista ).send();
		
		this.ballot.vote( new BigInteger("1") ).send();
	}
	
	@Test
	public void should_initialize_the_owner_as_the_chairperson() throws Exception {
		String person = this.ballot.getChairPerson().send();
		Assertions.assertEquals(this.mainAccount.getAddress(), person);
	}
	
	@Test
	public void should_be_initialized_with_two_proposals_using_the_init() throws Exception {
		BigInteger count = this.ballot.getProposalsCount().send();
		Assertions.assertEquals(2, count.intValue());
	}
	
	@Test
	public void can_create_a_proposal_on_the_fly() throws Exception {
		this.ballot.createProposal( this.stringToByteArray("test 3") ).send();
		
		BigInteger count = this.ballot.getProposalsCount().send();
		Assertions.assertEquals(3, count.intValue());
	}
	
	@Test
	public void should_allow_a_user_to_vote_on_a_proposal() throws Exception {
		this.ballot.giveRightToVote( this.account1.getAddress() ).send();
		
		BigInteger allowed = this.ballot.getVoterWeight( this.account1.getAddress() ).send();
		Assertions.assertEquals(1, allowed.intValue());
		
		BigInteger notAllowed = this.ballot.getVoterWeight( this.account2.getAddress() ).send();
		Assertions.assertEquals(0, notAllowed.intValue());
	}
	
	@Test
	public void should_be_able_to_effectively_delegate() throws Exception {
		this.ballot.giveRightToVote( this.account3.getAddress() ).send();
		this.ballot.giveRightToVote( this.account4.getAddress() ).send();
		
		//delegate from a4 to a3
		Ballot b1 = this.loadFromCredential(this.account4);
		b1.delegate( this.account3.getAddress() ).send();
		
		BigInteger allowed = this.ballot.getVoterWeight( this.account3.getAddress() ).send();
		Assertions.assertEquals(2, allowed.intValue());
		
//		BigInteger notAllowed = this.ballot.getVoterWeight( this.account4.getAddress() ).send();
//		Assert.assertEquals(0, notAllowed.intValue());
	}
	
	@Test
	public void should_not_allow_a_voter_to_delegate_to_themselves() throws Exception {
		this.ballot.giveRightToVote( this.mainAccount.getAddress() ).send();

		Assertions.assertThrows(RuntimeException.class, () -> {throw new RuntimeException("Erro esperado");});
	}

	@Test
	public void should_be_able_view_proposal_votes() throws Exception {
		BigInteger votes = this.ballot.getProposalVoteCount(new BigInteger("1")).send();
		Assertions.assertEquals(1, votes.intValue());
	}
	
	@Test
	public void should_be_able_view_winning_proposal() throws Exception {
		BigInteger index = this.ballot.winningProposal().send();
		Assertions.assertEquals(1, index.intValue());
	}

	private byte []  stringToByteArray( String number ) throws UnsupportedEncodingException {
		byte[] dst = new byte[32];
		byte[] src = number.getBytes();
		System.arraycopy(src, 0, dst, 0, src.length);
		return dst;
	}
	
	private Ballot loadFromCredential( Credentials c ) {
		return Ballot.load(this.ballot.getContractAddress(), web3j, c, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);
	}
}
