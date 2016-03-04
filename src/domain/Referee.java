package domain;

public class Referee {
	private String firstName;
	private String lastName;
	private String gender;
	private int age;
	private long identityCardId;
	
	public Referee(long identityCardId, String firstName, String lastName, int age, String gender){
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getIdentityCardId() {
		return identityCardId;
	}

	public void setIdentityCardId(long identityCardId) {
		this.identityCardId = identityCardId;
	}
	
	

}
