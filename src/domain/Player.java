package domain;

public class Player {
	private long identityCardId;
	private String firstName;
	private String lastName;
	private int age;
	private int scoredGoals;
	private String position;
	private int teamId;

	public Player(long identityCardId, String firstName, String lastName,
			int age, int scoredGoals, String position, int teamId) {
		this.identityCardId = identityCardId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.scoredGoals = scoredGoals;
		this.position = position;
		this.teamId = teamId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public long getIdentityCardId() {
		return identityCardId;
	}

	public void setIdentityCardId(long identityCardId) {
		this.identityCardId = identityCardId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getScoredGoals() {
		return scoredGoals;
	}

	public void setScoredGoals(int scoredGoals) {
		this.scoredGoals = scoredGoals;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String toString() {
		return firstName + " " + lastName + " : " + "\n" + "Age:" + age + "\n"
				+ "Scored Goals:" + scoredGoals + "\n" + "Position:" + position+"\n";
	}
}
