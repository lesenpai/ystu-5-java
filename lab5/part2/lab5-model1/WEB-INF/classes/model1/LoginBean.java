package model1;

public class LoginBean {
	public boolean login(String username, String password) {
		if (username == null || password == null ||
			!(username.equals("lev") && password.equals("root"))
		)
			return false;
		else
			return true;
	}
}
