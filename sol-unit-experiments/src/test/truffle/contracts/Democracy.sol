pragma solidity ^0.4.16;

contract Democracy {

    address owner = msg.sender;
    
    struct Proposal {
        string title;
        string description;
        address creator;
        uint expirationDate;
        uint neededVotes;
        address[] votesFavor;
        address[] votesAgainst;
        uint status;
    }
    
    struct Vote {
    	address voter;
    	bool favor;
    }

    Proposal[] public proposals;


    function createProposal(string title, string description, uint expirationDate, uint neededVotes) public {
        
        Proposal memory p;
        p.title = title;
        p.description = description;
        p.expirationDate = expirationDate;
        p.neededVotes = neededVotes;
        p.creator = msg.sender;
        p.status = 1;

        proposals.push(p);

    }

    function getProposalsLength() public view returns (uint) {
        return proposals.length;
    }
    
    function getProposal( uint index ) public view returns (uint, string, string, address, uint, uint, uint, uint, uint) {
        if ( proposals.length >= index ) {
            Proposal storage p = proposals[index];
            return (index, p.title, p.description, p.creator, p.expirationDate, p.neededVotes, p.votesFavor.length, p.votesAgainst.length, p.status);
        }
    }

	function voteOnProposal( uint index, uint vote ) public {
		
		//valida o tipo de voto
		require(vote == 1 || vote == 2);
			
		//busca a proposta
		Proposal storage p = proposals[index];
	
		//valida a data do voto
		//require( now < p.expirationDate );
		
		//valida voto duplicado
		//address votou = p.votosFavor.push[msg.sender] || p.votosContra.push[msg.sender];
		//require(!votou, "Já votou.");
		
		//p.totalVotos+= 1;
		
		//efetua o voto
		if (vote == 1) {
        	p.votesFavor.push(msg.sender);
        } else {
        	p.votesAgainst.push(msg.sender);
        }
		
	}  

    function kill() public { //encerra o contrato (somente o owner pode fazer isso)
        if(msg.sender == owner) {
            selfdestruct(owner); 
        }
    }

}
