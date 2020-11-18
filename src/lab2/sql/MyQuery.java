package lab2.sql;

public class MyQuery {
	public static final String
		SELECT_FROM_TEAM = "SELECT * FROM Team",
		SELECT_FROM_PLAYER = "SELECT * FROM Player",
		SELECT_FROM_PLAYER_WHERE = "SELECT * FROM Player WHERE Surname='%s' AND Name='%s' AND Patronymic='%s' AND TeamID=%s",
		//SELECT_FROM_PLAYER_WHERE_TEAMID = "SELECT * FROM Player WHERE TeamID=(SELECT ID FROM Team WHERE Name='%s')",
			SELECT_FROM_PLAYER_WHERE_TEAMID = "SELECT * FROM Player WHERE TeamID=%s",
			SELECT_ID_FROM_TEAM_WHERE_NAME = "SELECT ID FROM Team WHERE Name='%s'",
			INSERT_INTO_TEAM = "INSERT INTO Team (Name) VALUES ('%s')",
			INSERT_INTO_PLAYER = "INSERT INTO Player (Surname, Name, Patronymic, TeamID) VALUES ('%s','%s','%s',%s)",
		DELETE_FROM_TEAM_WHERE_NAME = "DELETE FROM Team WHERE Name='%s'",
		DELETE_FROM_PLAYER_WHERE = "DELETE FROM Player WHERE Surname='%s' AND Name='%s' AND Patronymic='%s' AND TeamID=%s";
}
