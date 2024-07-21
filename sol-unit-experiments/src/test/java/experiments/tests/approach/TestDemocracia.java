package experiments.tests.approach;

import java.math.BigInteger;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import experiments.contracts.Democracy;
import experiments.democracy.entity.Proposal;
import experiments.democracy.factory.TestDemocracyFactory;
import solunit.annotations.Account;
import solunit.annotations.Contract;
import solunit.runner.SolUnitRunner;

@ExtendWith(SolUnitRunner.class)
@TestMethodOrder(SolUnitRunner.class)
public class TestDemocracia {

	@Contract
	Democracy democracy;
	
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
	
	private static final int TOTAL_PROPOSALS = 5;
	
	private static final BigInteger VOTE_FAVOR = new BigInteger("1");
	private static final BigInteger VOTE_AGAINST = new BigInteger("2");
	
	private static final int PROPOSAL_1 = 0;
	private static final int PROPOSAL_2 = 1;
	private static final int PROPOSAL_3 = 2;
	private static final int PROPOSAL_4 = 3;
	private static final int PROPOSAL_5 = 4;
	
	@BeforeEach
	public void setUp() throws Exception {
		
		//seta o contrato da conta principal
		TestDemocracyFactory.setMainAddressContract(this.democracy);
		
		//cria 5 propostas
		int total = TOTAL_PROPOSALS;
		
		for ( int i = 1; i <= total; i++ ) {
			TransactionReceipt receipt =
					TestDemocracyFactory.createProposal("Proposal " + i, 
														"Description of proposal number  " + i, 
														new Date(), 
														(100 * i) );
			Assertions.assertNotNull( receipt );
		}
		
		TestDemocracyFactory.createVote(PROPOSAL_3, VOTE_FAVOR);
		TestDemocracyFactory.createVote(PROPOSAL_2, VOTE_AGAINST);
		
		TestDemocracyFactory.createVote(this.account2, PROPOSAL_2, VOTE_AGAINST);
		TestDemocracyFactory.createVote(this.account2, PROPOSAL_3, VOTE_AGAINST);
		
		TestDemocracyFactory.createVote(this.account3, PROPOSAL_2, VOTE_FAVOR);
		
		TestDemocracyFactory.createVote(PROPOSAL_4, VOTE_FAVOR);
		TestDemocracyFactory.createVote(this.account1, PROPOSAL_4, VOTE_FAVOR);
		TestDemocracyFactory.createVote(this.account2, PROPOSAL_4, VOTE_FAVOR);
		TestDemocracyFactory.createVote(this.account3, PROPOSAL_4, VOTE_FAVOR);
		TestDemocracyFactory.createVote(this.account4, PROPOSAL_4, VOTE_AGAINST);
		
		TestDemocracyFactory.createVote(PROPOSAL_5, VOTE_AGAINST);
		TestDemocracyFactory.createVote(this.account1, PROPOSAL_5, VOTE_AGAINST);
		TestDemocracyFactory.createVote(this.account2, PROPOSAL_5, VOTE_AGAINST);
		TestDemocracyFactory.createVote(this.account3, PROPOSAL_5, VOTE_AGAINST);
		TestDemocracyFactory.createVote(this.account4, PROPOSAL_5, VOTE_FAVOR);
		//totais
		//proposta 2: 2 contra, 1 favor
		//proposta 3: 1 favor, 1 contra
		//proposta 4: 1 contra, 4 favor
		//proposta 5: 4 contra, 1 favor
	}


	@Test
	public void verifica_se_o_total_de_propostas_esta_correto() throws Exception  {
		BigInteger total = this.democracy.getProposalsLength().send();
		Assertions.assertEquals(TOTAL_PROPOSALS, total.intValue());
	}
	
	@Test
	public void busca_a_primeira_proposta_cadastrada() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );

		Assertions.assertNotNull( p );
		Assertions.assertEquals("Proposal 1", p.getTitle() );
		Assertions.assertEquals("Description of proposal number  1", p.getDescription() );
		Assertions.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assertions.assertEquals(100l, p.getNeededVotes() );
		Assertions.assertEquals(0l, p.getTotalVotesFavor() );
		Assertions.assertEquals(0l, p.getTotalVotesAgainst());
		Assertions.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void busca_a_segunda_proposta_cadastrada() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_2) ).send() );

		Assertions.assertNotNull( p );
		Assertions.assertEquals("Proposal 2", p.getTitle() );
		Assertions.assertEquals("Description of proposal number  2", p.getDescription() );
		Assertions.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assertions.assertEquals(200l, p.getNeededVotes() );
		Assertions.assertEquals(1l, p.getTotalVotesFavor() );
		Assertions.assertEquals(2l, p.getTotalVotesAgainst());
		Assertions.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void busca_a_terceira_proposta_cadastrada() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_3) ).send() );

		Assertions.assertNotNull( p );
		Assertions.assertEquals("Proposal 3", p.getTitle() );
		Assertions.assertEquals("Description of proposal number  3", p.getDescription() );
		Assertions.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assertions.assertEquals(300l, p.getNeededVotes() );
		Assertions.assertEquals(1l, p.getTotalVotesFavor() );
		Assertions.assertEquals(1l, p.getTotalVotesAgainst());

		Assertions.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void efetua_um_voto_na_primeira_proposta() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assertions.assertNotNull( p );

		this.democracy.voteOnProposal(p.getIndex(), VOTE_FAVOR);
	}
	
	@Test
	public void efetua_dois_votos_com_a_mesma_carteira_na_primeira_proposta() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assertions.assertNotNull( p );

		this.democracy.voteOnProposal(p.getIndex(), VOTE_FAVOR).send();
		this.democracy.voteOnProposal(p.getIndex(), VOTE_AGAINST).send();

		Proposal fim = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assertions.assertEquals(fim.getTotalVotesAgainst(), 1);
		Assertions.assertEquals(fim.getTotalVotesFavor(), 1);
	}
	
	@Test
	public void efetua_dois_votos_com_a_mesma_carteira_em_propostas_diferentes() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assertions.assertNotNull( p );

		this.democracy.voteOnProposal(p.getIndex(), VOTE_FAVOR).send();

		Democracy d = TestDemocracyFactory.loadFromAddress(this.account1);
		Assertions.assertNotNull( d );

		d.voteOnProposal(p.getIndex(), VOTE_AGAINST).send();

		Proposal fim = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assertions.assertEquals(fim.getTotalVotesAgainst(), 1);
		Assertions.assertEquals(fim.getTotalVotesFavor(), 1);
	}
	
	@Test
	public void busca_a_quarta_proposta_cadastrada() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_4) ).send() );

		Assertions.assertNotNull( p );
		Assertions.assertEquals("Proposal 4", p.getTitle() );
		Assertions.assertEquals("Description of proposal number  4", p.getDescription() );
		Assertions.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assertions.assertEquals(400l, p.getNeededVotes() );
		Assertions.assertEquals(4l, p.getTotalVotesFavor() );
		Assertions.assertEquals(1l, p.getTotalVotesAgainst());
		Assertions.assertEquals(1, p.getStatus() );
	}
	
	@Test
	public void busca_a_quinta_proposta_cadastrada() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_5) ).send() );

		Assertions.assertNotNull( p );
		Assertions.assertEquals("Proposal 5", p.getTitle() );
		Assertions.assertEquals("Description of proposal number  5", p.getDescription() );
		Assertions.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assertions.assertEquals(500l, p.getNeededVotes() );
		Assertions.assertEquals(1l, p.getTotalVotesFavor() );
		Assertions.assertEquals(4l, p.getTotalVotesAgainst());
		Assertions.assertEquals(1, p.getStatus() );
	}
}
