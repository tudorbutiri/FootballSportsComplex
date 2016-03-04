package controller;

import java.util.List;

import domain.MyException;
import domain.Player;
import domain.Referee;
import domain.RentRequest;
import domain.Validators;
import domain.Team;
import repository.Repo;

public class Ctrl {
	Repo repo;
	Validators validate = new Validators();

	public Ctrl(Repo repo) {
		this.repo = repo;
	}

	public void getFootballTeamsRanking() {
		repo.loadFootballTeamsRanking();
	}

	public List<Team> getTeamsThatLostTheMostGames() {
		return repo.showTeamsWithMaximumLosses();
	}

	public float getTotalGain() {
		return repo.calculateComplexTotalGain();
	}

	public List<String> getTeamsThatPlayedInAGivenDate(String date)
			throws MyException {
		if (validate.validateDate(date)) {
			return repo.showTeamsThatPlayedInAGivenDate(date);
		} else
			throw new MyException("Please enter a correct date!");
	}

	public void insertPlayer(long id, String firstName, String lastName,
			int age, int scoredGoals, String position, int teamId)
			throws MyException {
		Player player = new Player(id, firstName, lastName, age, scoredGoals,
				position, teamId);
		if (validate.validatePlayer(player)) {
			repo.addNewPlayer(player);
		}
	}

	public void insertReferee(long id, String firstName, String lastName,
			int age, String gender) throws MyException {

		Referee referee = new Referee(id, firstName, lastName, age, gender);
		if (validate.validateReferee(referee)) {
			repo.addNewReferee(referee);
		}
	}

	public void updatePlayer(long id, String firstName, String lastName,
			int age, int scoredGoals, String position, int teamId)
			throws MyException {
		Player player = new Player(id, firstName, lastName, age, scoredGoals,
				position, teamId);
		if (validate.validatePlayer(player)) {
			repo.updatePlayer(player);
		}
	}

	public void updateReferee(long id, String firstName, String lastName,
			int age, String gender) throws MyException {

		Referee referee = new Referee(id, firstName, lastName, age, gender);
		if (validate.validateReferee(referee)) {
			repo.updateReferee(referee);
		}
	}

	public void deletePlayer(long playerId) throws MyException{
		repo.deletePlayer(playerId);
	}
	
	public List<RentRequest> getRentRequests(){
		return repo.getRequestsThatEnsureTheMaximumProfit();
	}
}