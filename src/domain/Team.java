package domain;

public class Team implements Comparable<Team>{
	private int teamId;
	private String teamName;
	private int victoriesNumber;
	private int drawsNumber;
	private int lossesNumber;
	private int receivedGoalsNumber;
	private int scoredGoalsNumber;

	public Team(int teamId, String teamName, int victoriesNumber,
			int drawsNumber, int lossesNumber, int receivedGoalsNumber,
			int scoredGoalsNumber) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.victoriesNumber = victoriesNumber;
		this.drawsNumber = drawsNumber;
		this.lossesNumber = lossesNumber;
		this.receivedGoalsNumber = receivedGoalsNumber;
		this.scoredGoalsNumber = scoredGoalsNumber;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getVictoriesNumber() {
		return victoriesNumber;
	}

	public void setVictoriesNumber(int victoriesNumber) {
		this.victoriesNumber = victoriesNumber;
	}

	public int getDrawsNumber() {
		return drawsNumber;
	}

	public void setDrawsNumber(int drawsNumber) {
		this.drawsNumber = drawsNumber;
	}

	public int getLossesNumber() {
		return lossesNumber;
	}

	public void setLossesNumber(int lossesNumber) {
		this.lossesNumber = lossesNumber;
	}

	public int getReceivedGoalsNumber() {
		return receivedGoalsNumber;
	}

	public void setReceivedGoalsNumber(int receivedGoalsNumber) {
		this.receivedGoalsNumber = receivedGoalsNumber;
	}

	public int getScoredGoalsNumber() {
		return scoredGoalsNumber;
	}

	public void setScoredGoalsNumber(int scoredGoalsNumber) {
		this.scoredGoalsNumber = scoredGoalsNumber;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int idTeam) {
		this.teamId = idTeam;
	}

	@Override
	public int compareTo(Team other) {
		return ((Integer)victoriesNumber).compareTo((Integer)other.victoriesNumber);
	}

}
