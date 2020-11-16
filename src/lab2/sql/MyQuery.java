package lab2.sql;

public class MyQuery {
	public static final String
		selectFromTeam = "SELECT * FROM Team",
		selectFromPlayer = "SELECT * FROM Player",
		selectFromPlayerWhere = "SELECT * FROM Player WHERE Surname='%s' AND Name='%s' AND Patronymic='%s' AND TeamID=%s",
		insertIntoTeam = "INSERT INTO Team (Name) VALUES ('%s')",
		selectFromPlayerWhereTeamId = "SELECT * FROM Player WHERE TeamID=(SELECT ID FROM Team WHERE Name='%s')",
		selectIdFromTeamWhereName = "SELECT ID FROM Team WHERE Name='%s'",
		insertIntoPlayer = "INSERT INTO Player (Surname, Name, Patronymic, TeamID) VALUES ('%s','%s','%s',%s)",
		deleteFromTeamWhereName = "DELETE FROM Team WHERE Name='%s'",
		deleteFromPlayerWhere = "DELETE FROM Player WHERE Surname='%s' AND Name='%s' AND Patronymic='%s' AND TeamID=%s";
}
