const Democracy = artifacts.require("Democracy");

contract('Democracy', (accounts) => {

    //instancia atual do contrato
    var contract_instance;

    var account = accounts[0];

    const TOTAL_PROPOSALS = 5;
	
	const VOTE_FAVOR = 1;
	const VOTE_AGAINST = 2;

    const PROPOSAL_1 = 0;
	const PROPOSAL_2 = 1;
	const PROPOSAL_3 = 2;
	const PROPOSAL_4 = 3;
    const PROPOSAL_5 = 4;
    

    function toProposal(data){
        return {
            id: data[0].toNumber(),
            titulo: data[1],
            descricao: data[2],
            criador: data[3],
            visivelAte: data[4].toNumber(),
            totalVotos: data[5].toNumber(),
            votosFavor: data[6].toNumber(),
            votosContra: data[7].toNumber()
        };
    }

    function millisToMinutesAndSeconds(millis) {
        var minutes = Math.floor(millis / 60000);
        var seconds = ((millis % 60000) / 1000).toFixed(0);
        return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
    }

   beforeEach(async () => {
       contract_instance = await Democracy.new();


       var inicio = new Date(); 
       //console.info("START: " + inicio);

        function timeout(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }   


        for ( let i = 1; i <= TOTAL_PROPOSALS; i++ ) {

            await contract_instance.createProposal("Proposal " + i, 
                                        "Description of proposal number  " + i, 
                                        (100 * i), 
                                        (100 * i) );
            await timeout(1000);
        }

        
        await contract_instance.voteOnProposal( PROPOSAL_2, VOTE_AGAINST);
        await contract_instance.voteOnProposal( PROPOSAL_2, VOTE_AGAINST);
        await contract_instance.voteOnProposal( PROPOSAL_2, VOTE_FAVOR);
        
        await contract_instance.voteOnProposal( PROPOSAL_3, VOTE_AGAINST);
        await contract_instance.voteOnProposal( PROPOSAL_3, VOTE_FAVOR);
        
        await contract_instance.voteOnProposal( PROPOSAL_4, VOTE_FAVOR);
        await contract_instance.voteOnProposal( PROPOSAL_4, VOTE_FAVOR);
        await contract_instance.voteOnProposal( PROPOSAL_4, VOTE_FAVOR);
        await contract_instance.voteOnProposal( PROPOSAL_4, VOTE_FAVOR);
        await contract_instance.voteOnProposal( PROPOSAL_4, VOTE_AGAINST);
        await contract_instance.voteOnProposal( PROPOSAL_5, VOTE_AGAINST);
        await contract_instance.voteOnProposal( PROPOSAL_5, VOTE_AGAINST);
        await contract_instance.voteOnProposal( PROPOSAL_5, VOTE_AGAINST);
        await contract_instance.voteOnProposal( PROPOSAL_5, VOTE_AGAINST);
        await contract_instance.voteOnProposal( PROPOSAL_5, VOTE_FAVOR);

        var fim = new Date(); 
        var Difference_In_Time = fim.getTime() - inicio.getTime(); 

        console.info("beforeEach: (" + Difference_In_Time + "ms)" );
        console.info("beforeEach (min:seg): " + millisToMinutesAndSeconds(Difference_In_Time)  );

        //totais
        //proposta 2: 2 contra, 1 favor
        //proposta 3: 1 favor, 1 contra
        //proposta 4: 1 contra, 4 favor
        //proposta 5: 4 contra, 1 favor
   });


  it('verifica se o total de propostas esta correto', async () => {
//    const dInstance = await Democracy.deployed();
    const total = await contract_instance.getProposalsLength();

    assert.equal(total.valueOf(), TOTAL_PROPOSALS, TOTAL_PROPOSALS + " nao eh o total de propostas");
  });

  it('busca a primeira proposta cadastrada', async () => {
    //const dInstance = await Democracy.deployed();
    const proposal = toProposal( await contract_instance.getProposal(PROPOSAL_1) );

    //console.info(proposal);

    assert.equal(proposal.titulo, "Proposal 1");
    assert.equal(proposal.descricao, "Description of proposal number  1");
  });

  it('busca a segunda proposta cadastrada', async () => {
    //const dInstance = await Democracy.deployed();
    const proposal = toProposal( await contract_instance.getProposal(PROPOSAL_2) );

    assert.equal(proposal.titulo, "Proposal 2");
    assert.equal(proposal.descricao, "Description of proposal number  2");
    assert.equal(proposal.votosFavor, 1);
    assert.equal(proposal.votosContra, 2);
  });

  it('busca a terceira proposta cadastrada', async () => {
    //const dInstance = await Democracy.deployed();
    const proposal = toProposal( await contract_instance.getProposal(PROPOSAL_3) );

    assert.equal(proposal.titulo, "Proposal 3");
    assert.equal(proposal.descricao, "Description of proposal number  3");
  });

  
  it('efetua um voto na primeira proposta', async () => {
    const proposal = toProposal( await contract_instance.getProposal(PROPOSAL_1) );
    await contract_instance.voteOnProposal( proposal.id, VOTE_FAVOR);
  });
  

  it('efetua dois votos com a mesma carteira na primeira proposta', async () => {
    const proposal = toProposal( await contract_instance.getProposal(PROPOSAL_1) );

    await contract_instance.voteOnProposal( proposal.id, VOTE_FAVOR);
    await contract_instance.voteOnProposal( proposal.id, VOTE_AGAINST);

    const proposalAfter = toProposal( await contract_instance.getProposal(PROPOSAL_1) );

    //console.info(proposalAfter);

    assert.equal(proposalAfter.votosFavor, 1);
    assert.equal(proposalAfter.votosContra, 1);
  });

  it('efetua dois votos com a mesma carteira em propostas diferentes', async () => {
    const proposal = toProposal( await contract_instance.getProposal(PROPOSAL_1) );
    await contract_instance.voteOnProposal( proposal.id, VOTE_FAVOR);

    const proposalAfter = toProposal( await contract_instance.getProposal(PROPOSAL_1) );
    assert.equal(proposalAfter.votosFavor, 1);

    const proposal2 = toProposal( await contract_instance.getProposal(PROPOSAL_2) );
    await contract_instance.voteOnProposal( proposal2.id, VOTE_AGAINST);

    const proposal2After = toProposal( await contract_instance.getProposal(PROPOSAL_2) );
    assert.equal(proposal2After.votosContra, 3); //ja tinha 2 votos contra

  });

  it('busca a quarta proposta cadastrada', async () => {
    const proposal = toProposal( await contract_instance.getProposal(PROPOSAL_4) );
    assert.equal(proposal.titulo, "Proposal 4");
    assert.equal(proposal.descricao, "Description of proposal number  4");
  });

  it('busca a quinta proposta cadastrada', async () => {
    const proposal = toProposal( await contract_instance.getProposal(PROPOSAL_5) );
    assert.equal(proposal.titulo, "Proposal 5");
    assert.equal(proposal.descricao, "Description of proposal number  5");
  });

  
});
