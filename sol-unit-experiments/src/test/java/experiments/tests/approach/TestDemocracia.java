package experiments.tests.approach;

import java.math.BigInteger;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import experiments.contracts.Democracy;
import experiments.democracy.entity.Proposal;
import experiments.democracy.factory.TestDemocracyFactory;
import solunit.annotations.Account;
import solunit.annotations.Contract;
import solunit.annotations.Print;
import solunit.annotations.Safe;
import solunit.runner.SolUnitRunner;

@RunWith(SolUnitRunner.class)
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
	
	@Before
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
			Thread.sleep(1000);
			Assert.assertNotNull( receipt );
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
	@Print(print = "PRIMEIRO")
	public void verifica_se_o_total_de_propostas_esta_correto() throws Exception  {
		Thread.sleep(5000);
		BigInteger total = this.democracy.getProposalsLength().send();
		Assert.assertEquals(TOTAL_PROPOSALS, total.intValue() );
	}
	
	@Test
	@Print(print = "SEGUNDO")
	public void busca_a_primeira_proposta_cadastrada() throws Exception  {
		Thread.sleep(1000);
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );

		Assert.assertNotNull( p );
		Assert.assertEquals("Proposal 1", p.getTitle() );
		Assert.assertEquals("Description of proposal number  1", p.getDescription() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assert.assertEquals(100l, p.getNeededVotes() );
		Assert.assertEquals(0l, p.getTotalVotesFavor() );
		Assert.assertEquals(0l, p.getTotalVotesAgainst());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	@Print(print = "TERCEIRO")
	public void busca_a_segunda_proposta_cadastrada() throws Exception  {
		Thread.sleep(2000);
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_2) ).send() );

		Assert.assertNotNull( p );
		Assert.assertEquals("Proposal 2", p.getTitle() );
		Assert.assertEquals("Description of proposal number  2", p.getDescription() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assert.assertEquals(200l, p.getNeededVotes() );
		Assert.assertEquals(1l, p.getTotalVotesFavor() );
		Assert.assertEquals(2l, p.getTotalVotesAgainst());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	@Print(print = "QUARTO")
	public void busca_a_terceira_proposta_cadastrada() throws Exception  {
		Thread.sleep(4000);
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_3) ).send() );

		Assert.assertNotNull( p );
		Assert.assertEquals("Proposal 3", p.getTitle() );
		Assert.assertEquals("Description of proposal number  3", p.getDescription() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assert.assertEquals(300l, p.getNeededVotes() );
		Assert.assertEquals(1l, p.getTotalVotesFavor() );
		Assert.assertEquals(1l, p.getTotalVotesAgainst());

		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	@Print(print = "QUINTO")
	public void efetua_um_voto_na_primeira_proposta() throws Exception  {
		Thread.sleep(2000);
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assert.assertNotNull( p );

		this.democracy.voteOnProposal(p.getIndex(), VOTE_FAVOR);
	}
	
	@Test
	@Print(print = "SEXTO")
	public void efetua_dois_votos_com_a_mesma_carteira_na_primeira_proposta() throws Exception  {
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assert.assertNotNull( p );

		this.democracy.voteOnProposal(p.getIndex(), VOTE_FAVOR).send();
		this.democracy.voteOnProposal(p.getIndex(), VOTE_AGAINST).send();

		Proposal fim = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assert.assertEquals(fim.getTotalVotesAgainst(), 1);
		Assert.assertEquals(fim.getTotalVotesFavor(), 1);
	}
	
	@Test
	@Print(print = "SETIMO")
	public void efetua_dois_votos_com_a_mesma_carteira_em_propostas_diferentes() throws Exception  {
		Thread.sleep(7000);
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assert.assertNotNull( p );

		this.democracy.voteOnProposal(p.getIndex(), VOTE_FAVOR).send();

		Democracy d = TestDemocracyFactory.loadFromAddress(this.account1);
		Assert.assertNotNull( d );

		d.voteOnProposal(p.getIndex(), VOTE_AGAINST).send();

		Proposal fim = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_1) ).send() );
		Assert.assertEquals(fim.getTotalVotesAgainst(), 1);
		Assert.assertEquals(fim.getTotalVotesFavor(), 1);
	}
	
	@Test
	@Print(print = "OITAVO")
	public void busca_a_quarta_proposta_cadastrada() throws Exception  {
		Thread.sleep(3000);
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_4) ).send() );

		Assert.assertNotNull( p );
		Assert.assertEquals("Proposal 4", p.getTitle() );
		Assert.assertEquals("Description of proposal number  4", p.getDescription() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assert.assertEquals(400l, p.getNeededVotes() );
		Assert.assertEquals(4l, p.getTotalVotesFavor() );
		Assert.assertEquals(1l, p.getTotalVotesAgainst());
		Assert.assertEquals(1, p.getStatus() );
	}
	
	@Test
	//@Print(print = "NONO")
	public void busca_a_quinta_proposta_cadastrada() throws Exception  {
		System.out.println("teste");
		Proposal p = new Proposal( this.democracy.getProposal( BigInteger.valueOf(PROPOSAL_5) ).send() );

		Assert.assertNotNull( p );
		Assert.assertEquals("Proposal 5", p.getTitle() );
		Assert.assertEquals("Description of proposal number  5", p.getDescription() );
		Assert.assertEquals(mainAccount.getAddress().toLowerCase(), p.getCreator() );
		Assert.assertEquals(500l, p.getNeededVotes() );
		Assert.assertEquals(1l, p.getTotalVotesFavor() );
		Assert.assertEquals(4l, p.getTotalVotesAgainst());
		Assert.assertEquals(1, p.getStatus() );
	}
}
