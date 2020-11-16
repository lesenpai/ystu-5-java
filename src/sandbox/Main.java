package sandbox;

import com.sun.source.tree.NewArrayTree;

import java.sql.*;
import static java.lang.System.out;

public class Main {

	private static Statement stmt;

	public static void main(String[] args) {
		String db = "java_football", user = "alex", password = "pass",
			url = String.format(
				"jdbc:sqlserver://localhost;database=%s;user=%s;password=%s",
				db, user, password);

		try (Connection con = DriverManager.getConnection(url, user, password)) {
			stmt = con.createStatement();
			out.println("[Connected]");

			out.println("*** показать все команды");
			showAllTeams();

			out.println();
			out.println("*** показать всех игроков");
			showAllPlayers();

			out.println();
			out.println("*** показать игроков из команды Denver Broncos");
			showPlayers("Denver Broncos");
			var newTeam = "Patriots";

			out.println();
			out.println("*** добавить команду "+newTeam);
			addTeam(newTeam);
			out.println("[показать все команды]");
			showAllTeams();

			out.println();
			out.println("*** добавить игрока в команду "+newTeam);
			String pName1="Тарасов", pName2="Вальтер", pName3="Давидович";
			addPlayer(pName1, pName2, pName3, newTeam);
			out.println("[показать игроков из команды "+newTeam+"]");
			showPlayers(newTeam);

			out.println();
			out.println("*** удалить игрока из команды "+newTeam);
			deletePlayer(pName1, pName2, pName3, newTeam);
			out.println("[показать игроков из команды "+newTeam+"]");
			showPlayers(newTeam);

			out.println();
			out.println("*** удалить команду "+newTeam);
			out.println("[показать все команды]");
			showAllTeams();
			out.println("[удаление команды]");
			deleteTeam(newTeam);
			out.println("[показать все команды]");
			showAllTeams();
		}
		catch (Exception e) {
			out.println("[ERROR]");
			e.printStackTrace();
		}
	}

	private static void showAllTeams() throws SQLException {
		printListRs(stmt.executeQuery("SELECT * FROM Team"));
	}

	private static void showAllPlayers() throws SQLException {
		printListRs(stmt.executeQuery("SELECT * FROM Player"));
	}

	private static void showPlayers(String teamName) throws SQLException {
		printListRs(stmt.executeQuery(String.format(
			"SELECT * FROM player WHERE TeamID = (SELECT ID FROM Team WHERE TeamName = '%s');",
			teamName
		)));
	}

	private static void addTeam(String teamName) throws SQLException {
		if (stmt.executeQuery(String.format(
			"SELECT 1 FROM Team WHERE TeamName = '%s';",
			teamName
		)).next()) {
			out.println("Ошибка: такая команда уже существует");
			return;
		}
		stmt.execute(String.format(
			"INSERT INTO Team (TeamName) VALUES ('%s');",
			teamName
		));
	}

	private static void addPlayer(String pName, String pSurname, String pPatronymic, String teamName) throws SQLException {
		var rs = stmt.executeQuery(String.format(
			"SELECT ID FROM Team WHERE TeamName = '%s'",
			teamName
		));
		String teamId;
		if (!rs.next()) {
			out.println("Ошибка: такая команда не существует");
			return;
		}
		teamId = rs.getString(1);

		if (exists(String.format(
			"SELECT 1 FROM Player WHERE TeamID=%s AND PlayerName='%s' AND PlayerSurname='%s' AND PlayerPatronymic='%s';",
			teamId, pName, pSurname, pPatronymic
		))) {
			out.println("Ошибка: такой игрок уже существует");
			return;
		}
		stmt.execute(String.format(
			"INSERT INTO Player (PlayerName, PlayerSurname, PlayerPatronymic, TeamID) VALUES ('%s', '%s', '%s', %s);",
			pName, pSurname, pPatronymic, teamId
		));
	}

	private static void deleteTeam(String teamName) throws SQLException {
		if (!exists(String.format("SELECT 1 FROM Team WHERE TeamName = '%s';", teamName))) {
			out.println("Ошибка: такая команда не существует");
			return;
		}
		stmt.execute(String.format("DELETE FROM Team WHERE TeamName = '%s';", teamName));
	}

	private static void deletePlayer(String pName, String pSurname, String pPatronymic, String teamName) throws SQLException {
		var rs = stmt.executeQuery(String.format(
			"SELECT ID FROM Team WHERE TeamName = '%s'",
			teamName
		));
		String teamId;
		if (!rs.next()) {
			out.println("Ошибка: такая команда не существует");
			return;
		}
		teamId = rs.getString(1);
		if (!exists(String.format(
			"SELECT 1 FROM Player WHERE TeamID=%s AND PlayerName='%s' AND PlayerSurname='%s' AND PlayerPatronymic='%s';",
			teamId, pName, pSurname, pPatronymic
		))) {
			out.println("Ошибка: такой игрок не существует");
			return;
		}
		stmt.execute(String.format(
			"DELETE FROM Player WHERE TeamID=%s AND PlayerName='%s' AND PlayerSurname='%s' AND PlayerPatronymic='%s';",
			teamId, pName, pSurname, pPatronymic
		));
	}

	private static boolean exists(String sql) throws SQLException {
		return stmt.executeQuery(sql).next();
	}

	private static void printListRs(ResultSet rs) throws SQLException {
		int columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				out.print(rs.getString(i) + " ");
			}
			out.println();
		}
	}
}
