const Ballot = artifacts.require("Ballot");

contract('Ballot', (accounts) => {

  //instancia atual do contrato
  var contract_instance;

  function toUTF8Array(str) {
    var utf8 = [];
    for (var i=0; i < str.length; i++) {
        var charcode = str.charCodeAt(i);
        if (charcode < 0x80) utf8.push(charcode);
        else if (charcode < 0x800) {
            utf8.push(0xc0 | (charcode >> 6), 
                      0x80 | (charcode & 0x3f));
        }
        else if (charcode < 0xd800 || charcode >= 0xe000) {
            utf8.push(0xe0 | (charcode >> 12), 
                      0x80 | ((charcode>>6) & 0x3f), 
                      0x80 | (charcode & 0x3f));
        }
        // surrogate pair
        else {
            i++;
            // UTF-16 encodes 0x10000-0x10FFFF by
            // subtracting 0x10000 and splitting the
            // 20 bits of 0x0-0xFFFFF into two halves
            charcode = 0x10000 + (((charcode & 0x3ff)<<10)
                      | (str.charCodeAt(i) & 0x3ff));
            utf8.push(0xf0 | (charcode >>18), 
                      0x80 | ((charcode>>12) & 0x3f), 
                      0x80 | ((charcode>>6) & 0x3f), 
                      0x80 | (charcode & 0x3f));
        }
    }
    return utf8;
  }

  function millisToMinutesAndSeconds(millis) {
    var minutes = Math.floor(millis / 60000);
    var seconds = ((millis % 60000) / 1000).toFixed(0);
    return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
  }

  beforeEach(async () => {
    var inicio = new Date(); 

    contract_instance = await Ballot.new();

    lista = [
      toUTF8Array("test"),
      toUTF8Array("test2")
    ];

		await contract_instance.init( lista );
		await contract_instance.vote( 1 );

    
    var fim = new Date(); 
    var Difference_In_Time = fim.getTime() - inicio.getTime(); 
    console.info("beforeEach: (" + Difference_In_Time + "ms)" );
    console.info("beforeEach (min:seg): " + millisToMinutesAndSeconds(Difference_In_Time)  );
  });

  it('should_initialize_the_owner_as_the_chairperson', async () => {
    let person = await contract_instance.getChairPerson();
    assert.equal(accounts[0], person);
  });

  it('should_be_initialized_with_two_proposals_using_the_init', async () => {
   let count = await contract_instance.getProposalsCount();
   assert.equal(count.valueOf(), 2);
  });

  it('can_create_a_proposal_on_the_fly', async () => {
   await contract_instance.createProposal(  toUTF8Array("test 3") );
   let count = await contract_instance.getProposalsCount();
   assert.equal(count.valueOf(), 3);
  });

  it('should_allow_a_user_to_vote_on_a_proposal', async () => {
   await contract_instance.giveRightToVote( accounts[1] );
   let allowed = await contract_instance.getVoterWeight( accounts[1] );
   assert.equal(allowed.valueOf(), 1);

   let notallowed = await contract_instance.getVoterWeight( accounts[2] );
   assert.equal(notallowed.valueOf(), 0);
  });

  it('should_be_able_to_effectively_delegate', async () => {
   await contract_instance.giveRightToVote( accounts[3] );
   await contract_instance.giveRightToVote( accounts[4] );

   //delegate from 4 to 3
   await contract_instance.delegate( accounts[3], { from: accounts[4] } );

   let allowed = await contract_instance.getVoterWeight( accounts[3] );
   assert.equal(allowed.valueOf(), 2);
  });

  it('should_not_allow_a_voter_to_delegate_to_themselves', async () => {
    try {
      await contract_instance.giveRightToVote( accounts[0] );
      assert.fail('Nao deveria chegar aqui');
    } catch (error) {
    }
  });

  it('should_be_able_view_proposal_votes', async () => {
   let votes = await contract_instance.getProposalVoteCount( 1 );
   assert.equal(votes.valueOf(), 1);
  });


  it('should_be_able_view_winning_proposal', async () => {
   let index = await contract_instance.winningProposal();
   assert.equal(index.valueOf(), 1);
  });

});
