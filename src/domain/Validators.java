package domain;

public class Validators {

	public Validators() {

	}

	public boolean validatePlayer(Player player) {
		if (Long.toString(player.getIdentityCardId()) == ""
				|| Long.toString(player.getIdentityCardId()) == " ") {
			return false;
		}

		if (!player.getFirstName().matches("^[A-Za-z]+$")
				|| !player.getLastName().matches("^[A-Za-z]+$")) {
			return false;
		}
		return true;
	}

	public boolean validateDate(String date) {
		if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			return false;
		}
		return true;

	}

	public boolean validateReferee(Referee referee) {
		if (referee.getAge() < 22 || referee.getAge() > 40)
			return false;
		return true;
	}
}
