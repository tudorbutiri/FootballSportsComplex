package ui;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import controller.Ctrl;
import domain.MyException;
import domain.RentRequest;
import domain.Team;




public class Console {
	private Ctrl ctrl;
	private boolean show;
	private Scanner sc;

	public Console(Ctrl c) {
		ctrl = c;
		show = true;
		sc = new Scanner(System.in);
	}
	
	public void run() {
		try {
			while (show) {
				menuShow();
				System.out.println("Select your option: ");
				int optiune = sc.nextInt();
				menuHandle(optiune);
			}
		} catch (InputMismatchException e) {
			System.out.println("Enter a number please! ");
		}

		sc.close();
	}

	private void menuShow() {
		System.out.println("\n     =====  MENU  =====\n");
		System.out.println("\t1. Save into a file all teams that played on the field,"
								+ " sorting them by victories number in descending order");
		System.out.println("\t2. Show teams that lost the most games");
		System.out.println("\t3. Show complex total gain from all played games");
		System.out.println("\t4. Show teams that played in a given date");
		System.out.println("\t5. Insert player");
		System.out.println("\t6. Insert referee");
		System.out.println("\t7. Update player");
		System.out.println("\t8. Update referee");
		System.out.println("\t9. Delete player");
		System.out.println("\t10. Show rent requests that ensure the maximum profit");
		System.out.println("\t0. Exit\n");
	}
	
	private void menuHandle(int o) {
		switch (o) {
			case 1: {
				menuSaveTeamsIntoAFile();
				break;
			}
			case 2: {
				menuShowTeamsThatLostTheMostGames();
				break;
			}
			case 3: {
				menuShowComplexTotalGain();
				break;
			}
			case 4: {
				menuShowTeamsThatPlayerInAGivenDate();
				break;
			}
			case 5: {
				menuInsertPlayer();
				break;
			}
			case 6: {
				menuInsertReferee();
				break;
			}
			case 7: {
				menuUpdatePlayer();
				break;
			}
			case 8: {
				menuUpdateReferee();
				break;
			}
			case 9: {
				menuDeletePlayer();
				break;
			}
			case 10: {
				menuShowRentRequests();
				break;
			}
			case 0: {
				show = false;
				break;
			}
		}
	}

	private void menuShowTeamsThatPlayerInAGivenDate() {
		System.out.println("Please enter a date: yyyy-mm-dd");
		String startDateString = sc.next();
		try {
			List<String> teams = new ArrayList<String>();
			teams=ctrl.getTeamsThatPlayedInAGivenDate(startDateString);
			for (String team:teams){
				System.out.println(team);
			}
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}

	private void menuShowRentRequests() {
		List<RentRequest> rents = new ArrayList<RentRequest>();
		rents = ctrl.getRentRequests();
		System.out.println("Rent ids:");
		for (RentRequest rent:rents){
			System.out.print(rent.getIdRequest()+ " ");
		}
	}

	private void menuDeletePlayer() {
		System.out.println("Please enter identity card for the player you want to delete:");
		long id = sc.nextLong();
		try {
			ctrl.deletePlayer(id);
			System.out.println("Player successfully deleted!");
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}

	private void menuUpdateReferee() {
		System.out.println("Please enter identity card for the referee you want to modify:");
		long id = sc.nextLong();
		System.out.println("Please enter old/new first name:");
		String firstName = sc.next();
		System.out.println("Please enter old/new last name:");
		String lastName = sc.next();
		System.out.println("Please enter old/new age:");
		int age = sc.nextInt();
		System.out.println("Please enter old/new gender:");
		String gender = sc.next();
		try {
			ctrl.updateReferee(id, firstName, lastName, age, gender);
			System.out.println("Referee successfully updated!");
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private void menuUpdatePlayer() {
		System.out.println("Please enter identity card for the player you want to modify:");
		long id = sc.nextLong();
		System.out.println("Please  enter old/new first name:");
		String firstName = sc.next();
		System.out.println("Please enter old/new last name:");
		String lastName = sc.next();
		System.out.println("Please enter old/new age:");
		int age = sc.nextInt();
		System.out.println("Please enter old/new scored goals number:");
		int goals = sc.nextInt();
		System.out.println("Please enter old/new position: goalkeeper/midfielder/offensive/defender");
		String position = sc.next();
		System.out.println("Please enter old/new team id: from 1 to 14");
		int teamid = sc.nextInt();
		try {
			ctrl.updatePlayer(id, firstName, lastName, age, goals, position, teamid);
			System.out.println("Player successfully updated!");
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private void menuInsertReferee() {
		System.out.println("Please enter identity card:");
		long id = sc.nextLong();
		System.out.println("Please enter first name:");
		String firstName = sc.next();
		System.out.println("Please enter last name:");
		String lastName = sc.next();
		System.out.println("Please enter age:");
		int age = sc.nextInt();
		System.out.println("Please enter gender:");
		String gender = sc.next();
		try {
			ctrl.insertReferee(id, firstName, lastName, age, gender);
			System.out.println("Referee successfully added!");
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private void menuInsertPlayer() {
		System.out.println("Please enter identity card:");
		long id = sc.nextLong();
		System.out.println("Please enter first name:");
		String firstName = sc.next();
		System.out.println("Please enter last name:");
		String lastName = sc.next();
		System.out.println("Please enter age:");
		int age = sc.nextInt();
		System.out.println("Please enter scored goals number:");
		int goals = sc.nextInt();
		System.out.println("Please enter position: goalkeeper/midfielder/offensive/defender");
		String position = sc.next();
		System.out.println("Please enter team id: from 1 to 14");
		int teamid = sc.nextInt();
		try {
			ctrl.insertPlayer(id, firstName, lastName, age, goals, position, teamid);
			System.out.println("Player successfully added!");
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}


	private void menuShowComplexTotalGain() {
		 System.out.println("Total gain:" + ctrl.getTotalGain()+ "RON");
		
	}

	private void menuShowTeamsThatLostTheMostGames() {
		List<Team> teamList = new ArrayList<Team>();
		teamList = ctrl.getTeamsThatLostTheMostGames();
		for (Team team: teamList)
			System.out.println(team.getTeamName()+"-" + "Losses Number: "+ team.getLossesNumber());;
	}

	private void menuSaveTeamsIntoAFile() {
		ctrl.getFootballTeamsRanking();
		
	}
	
}
