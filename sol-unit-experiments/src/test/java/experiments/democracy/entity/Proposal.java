package experiments.democracy.entity;

import java.math.BigInteger;

import org.web3j.tuples.generated.Tuple9;

public class Proposal {
	/**
	 * string titulo;
        string descricao;
        address criador;
        uint dataFinal;
        uint totalVotos;
        address[] votosFavor;
        address[] votosContra;
        uint status;
	 */
	
	private BigInteger index;
	private String title;
	private String description;
	private String creator;
	private long expirationDate;
	private long neededVotes;
	
	private long totalVotesFavor;
	private long totalVotesAgainst;
	
	private int status;
	
	public Proposal() {
		super();
	}
	
	public Proposal(Tuple9<BigInteger, String, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> tuple) {
		super();
		this.index = tuple.getValue1();
		this.title = tuple.getValue2();
		this.description = tuple.getValue3();
		this.creator = tuple.getValue4();
		this.expirationDate = tuple.getValue5().longValue();
		this.neededVotes = tuple.getValue6().longValue();
		this.totalVotesFavor = tuple.getValue7().longValue();
		this.totalVotesAgainst = tuple.getValue8().longValue();
		this.status = tuple.getValue9().intValue();
	}

	@Override
	public String toString() {
		return "Proposta [index=" + index + ", titulo=" + title + ", descricao=" + description + ", criador=" + creator + ", dataFinal=" + expirationDate + ", totalVotos=" + neededVotes + ", votosFavor="
				+ totalVotesFavor + ", votosContra=" + totalVotesAgainst + ", status=" + status + "]";
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreator() {
		return creator;
	}
	public long getExpirationDate() {
		return expirationDate;
	}
	public long getNeededVotes() {
		return neededVotes;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BigInteger getIndex() {
		return index;
	}
	public void setIndex(BigInteger index) {
		this.index = index;
	}
	public long getTotalVotesFavor() {
		return totalVotesFavor;
	}
	public long getTotalVotesAgainst() {
		return totalVotesAgainst;
	}
	

}
