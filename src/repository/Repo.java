package repository;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.omg.CORBA.Request;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import domain.MyException;
import domain.Player;
import domain.Referee;
import domain.RentRequest;
import domain.Team;

public class Repo {
	ConnectDatabase databaseConnection = new ConnectDatabase();
	Statement statement = null;
	ResultSet data = null;
	List<Team> teamList = null;
	List<Player> playerList = null;
	List<Referee> refereeList = null;
	List<RentRequest> planning = null;
	List<String> gamesDates = null;

	public Repo() {
		teamList = new ArrayList<Team>();
		playerList = new ArrayList<Player>();
		refereeList = new ArrayList<Referee>();
		planning = new ArrayList<RentRequest>();
		gamesDates = new ArrayList<String>();
		loadTeams();
		loadPlayers();
		loadReferees();
		loadListOfRentRequests();
		loadGamesDates();
	}

	/*
	 * Aceasta metoda extrage din baza de date toti arbitrii si informatiile
	 * aferente lor. Input: Baza de date; Output: refereeList - lista cu toti
	 * arbitrii existenti;
	 */
	private void loadReferees() {
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			data = statement
					.executeQuery("SELECT `identityCardId`, `firstName`,`lastName`, `age`, `gender` FROM `referee`");

			while (data.next()) {
				refereeList.add(new Referee(data.getLong("identityCardId"),
						data.getString("firstName"),
						data.getString("lastName"), data.getInt("age"), data
								.getString("gender")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Aceasta metoda extrage din baza de date toate echipele si informatiile
	 * aferente lor. Input: Baza de date; Output: teamList<Team> - lista cu
	 * toate echipele existente
	 */
	public void loadTeams() {
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			data = statement
					.executeQuery("SELECT distinct `idTeam`,`name`,`victories`, `draws` , `losses`, `receivedGoals`,`scoredGoals`"
							+ " FROM `team` "
							+ "inner join `game` on `game`.`idHostTeam` = `team`.`idTeam` or `game`.`idGuestTeam` = `team`.`idTeam`"
							+ " where `game`.`idPlayingField`= '1'");

			while (data.next()) {
				teamList.add(new Team(data.getInt("idTeam"), data
						.getString("name"), data.getInt("victories"), data
						.getInt("draws"), data.getInt("losses"), data
						.getInt("receivedGoals"), data.getInt("scoredGoals")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Aceasta metoda extrage din baza de date toti jucatorii si informatiile
	 * aferente lor. Input: Baza de date; Output: playerList<Player> - lista cu
	 * toti jucatorii existenti;
	 */
	public void loadPlayers() {
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			data = statement
					.executeQuery("SELECT `identityCardId`,`firstName`,`lastName`,`age`,`scoredGoals`,`position`,`teamId`"
							+ " FROM `player`");

			while (data.next()) {
				playerList.add(new Player(data.getLong("identityCardId"), data
						.getString("firstName"), data.getString("lastName"),
						data.getInt("age"), data.getInt("scoredGoals"), data
								.getString("position"), data.getInt("teamId")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Aceasta metoda permite cautarea unui jucator dupa cartea de identitate
	 * Input: identityCardId de tip long Output: true, daca exista in baza de
	 * date, false altfel
	 */
	public boolean searchPlayerById(long playerId) {
		for (Player player : playerList) {
			if (player.getIdentityCardId() == playerId)
				return true;
		}
		return false;
	}

	/*
	 * Aceasta metoda permite cautarea unui arbitru dupa cartea de identitate
	 * Input: identityCardId de tip long Output: true, daca exista in baza de
	 * date, false altfel
	 */
	private boolean searchRefereeById(long identityCardId) {
		for (Referee referee : refereeList) {
			if (referee.getIdentityCardId() == identityCardId)
				return true;
		}
		return false;
	}

	/*
	 * Functionalitatea 1: Permite afisarea in fisier a tuturor echipelor
	 * ordonate descrescator dupa numarul victoriilor; Pentru fiecare echipa, se
	 * vor afisa si jucatorii aferenti ei si datele acestora. Input: teamList,
	 * lista cu toate echipele; Output: Fisierul FootballTeamsRanking.txt, ce va
	 * contine echipele afisate in ordine descrecatoare dupa numarul
	 * victoriilor, cat si jucatorii aferenti lor cu datele acestora.
	 */

	/*
	 * Aceasta metoda sorteaza lista echipelor descrescator, in functie de
	 * numarul victoriilor obtinute; Input: teamList - lista echipelor; Output:
	 * teamList<Team> - lista echipelor sortata descrescator dupa numarul
	 * victoriilor;
	 */
	public void sortDescendingByVictoriesNumber() {
		Collections.sort(teamList);
		Collections.reverse(teamList);
	}

	/*
	 * Aceasta metoda va afisa in fisier lista echipelor ordonate descrescator,
	 * in functie de numarul victoriilor, impreuna cu toti jucatorii aferenti
	 * fiecarei echipe; Input: teamList<Team> - lista echipelor ordonata
	 * descrescator dupa numarul victoriilor playerList -lista tuturor
	 * jucatorilor din echipe; Output: Fisierul FootballTeamsRanking.txt
	 */
	public void loadFootballTeamsRanking() {
		cleanFile();
		sortDescendingByVictoriesNumber();
		for (Team team : teamList)
			try {

				BufferedWriter out = new BufferedWriter(new FileWriter(
						"src\\data\\FootballTeamsRanking.txt", true));
				out.write(team.getTeamName() + " -  " + "Victories Number:"
						+ team.getVictoriesNumber());
				out.newLine();

				out.write("\t Jucatori: \n");
				for (Player player : playerList) {
					if (player.getTeamId() == team.getTeamId())
						out.write(player.toString() + "\n");
				}
				out.newLine();
				out.close();
			} catch (IOException ex) {
			}
	}

	/*
	 * Functionalitatea 2- permite afisarea echipelor care au pierdut cele mai
	 * multe meciuri; Input: teamList<Team> - lista cu echipele si datele
	 * aferente lor; Output: Afisarea la consola a echipelor care au pierdut
	 * cele mai multe jocuri;
	 */

	/*
	 * Aceasta metoda returneaza maximul dintr-o lista Input: teamList<Team> -
	 * lista cu echipele si datele aferente lor; Output: maximul max, de tip
	 * int;
	 */
	public int getMaximumLosses() {
		int max = -1;
		for (Team team : teamList) {
			if (team.getLossesNumber() > max)
				max = team.getLossesNumber();
		}
		return max;
	}

	/*
	 * Aceasta metoda returneaza echipele care au pierdut cele mai multe meciuri
	 * Input: teamList<Team> - lista cu echipele si datele aferente lor; Output:
	 * m;
	 */
	public List<Team> showTeamsWithMaximumLosses() {

		int max = getMaximumLosses();
		List<Team> losses = new ArrayList<Team>();
		for (Team team : teamList)
			if (team.getLossesNumber() == max)
				losses.add(team);
		return losses;

	}

	/*
	 * Functionalitatea 3- permite calcularea castigului total al complexului
	 * sportiv, in urma meciurilor jucate Input: Baza de date; Output: variabila
	 * totalGain -de tip float, care returneaza castigul total;
	 */
	public float calculateComplexTotalGain() {
		float totalGain = 0;
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			data = statement
					.executeQuery("SELECT `rentCost`,`refereeCost`, `electricityCost` FROM `cost` "
							+ "inner join `game` on `cost`.`idGameType` = `game`.`idGameType`");

			while (data.next()) {
				totalGain = totalGain + data.getFloat("rentCost")
						- data.getFloat("refereeCost")
						- data.getFloat("electricityCost");

			}
		} catch (SQLException e) {
		}
		return totalGain;
	}

	/*
	 * Functionalitatea 4 - permite afisarea numelor echipelor care au jucat
	 * intr-o anumita data Input: variabila date- de tip String Output:
	 * List<String> teams - o lista cu numele echipelor care au jucat in data
	 * respectiva
	 */

	/*
	 * Aceasta metoda incarca din baza de date, toate datele in care au avut loc
	 * meciuri Input: Baza de date Output: gamesDates- o list de string, care
	 * contine datele in care au avut loc meciuri
	 */
	private List<String> loadGamesDates() {
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			data = statement.executeQuery("SELECT `date` FROM `game`");

			while (data.next()) {
				gamesDates.add(data.getString("date"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return gamesDates;
	}

	/*
	 * Aceasta metoda returneaza o lista cu numele echipelor care au jucat
	 * intr-o anumita data; Input - date de tip string Output - teams- lista cu
	 * stringuri reprezentand numele echipelor
	 */
	public boolean checkDatas(String date){
		for (String gameDate : gamesDates) {
			if (gameDate.equalsIgnoreCase(date))
				return true;
		}
		return false;
	}
	public List<String> showTeamsThatPlayedInAGivenDate(String date)
			throws MyException {

			if (!checkDatas(date)) {
				throw new MyException(
						"This date doesn't exist! Please enter another date...");
		}
		List<String> teams = new ArrayList<String>();
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			data = statement
					.executeQuery(String
							.format("SELECT `name`,`date`"
									+ " FROM `team` inner join `game` "
									+ "on `team`.`idTeam` = `game`.`idHostTeam` or `team`.`idTeam` = `game`.`idGuestTeam` "
									+ "where `game`.`date` = '%s'", date));
			while (data.next()) {
				teams.add(data.getString("name"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teams;
	}

	/*
	 * Functionalitatea 5: Aceasta functionalitate permite adaugarea unui nou
	 * jucator in baza de date;Input : un obiect de tip player-valid. In cazul
	 * in care in baza de date se regaseste un jucator cu acelasi id, atunci
	 * metoda arunca exceptie Output: playerList- lista cu noul jucator adaugat
	 */
	public void addNewPlayer(Player player) throws MyException {
		if (searchPlayerById(player.getIdentityCardId()))
			throw new MyException("Duplicate key!Please enter valid data!");
		int countMembers = 0;
		for (Player checkPlayer : playerList) {
			if (checkPlayer.getTeamId() == player.getTeamId())
				countMembers++;
		}
		if (countMembers == 7)
			throw new MyException(
					"This team already has 7 members. Choose another team please...");

		playerList.add(player);
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			statement
					.executeUpdate(String
							.format("INSERT INTO `footballsportscomplex`.`player` (`identityCardId`, `firstName`, `lastName`, `age`, `scoredGoals`, `position`, `teamId`) "
									+ "VALUES ('%d ', '%s ', '%s ', '%d ', '%d ', '%s', '%d')",
									player.getIdentityCardId(),
									player.getFirstName(),
									player.getLastName(), player.getAge(),
									player.getScoredGoals(),
									player.getPosition(), player.getTeamId()));

		} catch (MySQLIntegrityConstraintViolationException e) {
			e.getMessage();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Functionalitatea 6: Aceasta functionalitate permite adaugarea unui nou
	 * arbitru in baza de date;Input : un obiect de tip arbitru-valid. In cazul
	 * in care in baza de date se regaseste un arbitru cu acelasi id, atunci
	 * metoda arunca exceptie Output: refereeList- lista cu noul arbitru adaugat
	 */
	public void addNewReferee(Referee referee) throws MyException {
		if (searchRefereeById(referee.getIdentityCardId()))
			throw new MyException("Duplicate key!Please enter valid data!");
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			statement
					.executeUpdate(String
							.format("INSERT INTO `footballsportscomplex`.`referee` (`identityCardId`, `firstName`, "
									+ "`lastName`,`age`, `gender`) VALUES ('%d ', '%s ', '%s ', '%d ', '%s ')",
									referee.getIdentityCardId(),
									referee.getFirstName(),
									referee.getLastName(), referee.getAge(),
									referee.getGender()));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Functionalitatea 7: Aceasta functionalitate permite modificarea datelor
	 * unui jucator in baza de date;Input : un obiect de tip jucator-valid. In
	 * cazul in care in baza de date se regaseste un jucator cu acelasi id,
	 * atunci se va modifica; Output: playerList- lista cu noul jucator
	 * modificat
	 */

	public void updatePlayer(Player newPlayer) throws MyException {
		if (!searchPlayerById(newPlayer.getIdentityCardId()))
			throw new MyException(
					"Identity card doesn't match! Please type valid data!");
		for (Player oldPlayer : playerList)
			if (newPlayer.getIdentityCardId() == oldPlayer.getIdentityCardId()) {
				oldPlayer.setFirstName(newPlayer.getFirstName());
				oldPlayer.setLastName(newPlayer.getLastName());
				oldPlayer.setAge(newPlayer.getAge());
				oldPlayer.setPosition(newPlayer.getPosition());
				oldPlayer.setScoredGoals(newPlayer.getScoredGoals());
				oldPlayer.setTeamId(newPlayer.getTeamId());
			}
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			statement
					.executeUpdate(String.format(
							"UPDATE `footballsportscomplex`.`player` SET `firstName`='%s', `lastName`='%s', "
									+ "`age` = '%d', `scoredGoals`='%d', `position` = '%s', `teamId` = '%d'"
									+ " 	WHERE `identityCardId` = '%d'",

							newPlayer.getFirstName(), newPlayer.getLastName(),
							newPlayer.getAge(), newPlayer.getScoredGoals(),
							newPlayer.getPosition(), newPlayer.getTeamId(),
							newPlayer.getIdentityCardId()));

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out
					.println("The position you have introduced is incorrect. Please try again.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Functionalitatea 8: Aceasta functionalitate permite modificarea datelor
	 * unui arbitru in baza de date;Input : un obiect de tip referee-valid. In
	 * cazul in care in baza de date se regaseste un arbitru cu acelasi id,
	 * atunci se va modifica; Output: refereeList- lista cu noul arbitru
	 * modificat
	 */
	public void updateReferee(Referee newReferee) throws MyException {
		if (!searchRefereeById(newReferee.getIdentityCardId()))
			throw new MyException(
					"Identity card doesn't match! Please type valid data!");
		for (Referee oldReferee : refereeList)
			if (newReferee.getIdentityCardId() == oldReferee
					.getIdentityCardId()) {
				oldReferee.setFirstName(newReferee.getFirstName());
				oldReferee.setLastName(newReferee.getLastName());
				oldReferee.setAge(newReferee.getAge());
				oldReferee.setGender(newReferee.getGender());

			}
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			statement
					.executeUpdate(String.format(
							"UPDATE `footballsportscomplex`.`referee` SET `firstName`='%s', `lastName`='%s', "
									+ "`age` = '%d', `gender`='%s'"
									+ " 	WHERE `identityCardId` = '%d'",

							newReferee.getFirstName(),
							newReferee.getLastName(), newReferee.getAge(),
							newReferee.getGender(),
							newReferee.getIdentityCardId()));

		} catch (SQLIntegrityConstraintViolationException e) {
			System.out
					.println("The position you have introduced is incorrect. Please try again.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Functionalitatea 9: Aceasta functionalitate permite stergerea datelor
	 * unui jucator in baza de date;Input : un playerId-identityCardId-de tip
	 * long. In cazul in care in baza de date se regaseste un jucator cu acelasi
	 * id, atunci se va sterge; Output: refereeList- lista cu noul arbitru
	 * modificat
	 */
	public void deletePlayer(long playerId) throws MyException {
		if (!searchPlayerById(playerId))
			throw new MyException(
					"Identity card doesn't exist! Please try again...");
		try {
			statement = (databaseConnection.getConnection()).createStatement();
			statement
					.executeUpdate(String
							.format("DELETE FROM `footballsportscomplex`.`player` WHERE `player`.`identityCardId`='%d'",
									playerId));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Aceasta metoda incarca din fisier lista cu programarile la meciuri,
	 * aferenta punctului 5
	 */
	public void loadListOfRentRequests() {
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(
					new FileInputStream("src\\data\\ListOfRentRequests.txt")));
			while (in.available() != 0) {
				@SuppressWarnings("deprecation")
				StringTokenizer str = new StringTokenizer(in.readLine(), ":;");

				int idRequest = Integer.parseInt(str.nextToken());
				String startingHour = str.nextToken();
				String startingMinute = str.nextToken();

				String finishingHour = str.nextToken();
				String finishingMinute = str.nextToken();

				planning.add(new RentRequest(idRequest, Integer
						.parseInt(startingHour)
						* 60
						+ Integer.parseInt(startingMinute), Integer
						.parseInt(finishingHour)
						* 60
						+ Integer.parseInt(finishingMinute)));
			}

			in.close();
		} catch (IOException e) {
			/*
			 * throw new RepoException("Eroare la citirea din fisier"); *
			 */
		}
	}

	/*
	 * Prin aceasta metoda se rezolva scenariul din problema. Am considerat un
	 * fisier in care se da ora de inceput a meciul si cea de sfarsit. Am sortat
	 * dupa data finalizarii fiecarui meci; Am ales primult element din lista ca
	 * fiind cel de la care pornim Am verificat daca data de inceput a meciului
	 * din noua lista sortata este mai mare decat data de sfarsit a meciului
	 * anterior etc...
	 */
	public List<RentRequest> getRequestsThatEnsureTheMaximumProfit() {
		Collections.sort(planning);
		List<RentRequest> finalPlanning = new ArrayList<RentRequest>();
		finalPlanning.add(planning.get(0));
		planning.remove(0);
		for (RentRequest rent : planning) {
			if (finalPlanning.get(finalPlanning.size() - 1)
					.getFinishingMinutes() <= rent.getStartingMinutes()) {
				finalPlanning.add(rent);
			}
		}
		return finalPlanning;
	}

	private void cleanFile() {
		PrintWriter writer1;
		try {
			writer1 = new PrintWriter("src\\data\\FootballTeamsRanking.txt");
			writer1.print("");
			writer1.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}