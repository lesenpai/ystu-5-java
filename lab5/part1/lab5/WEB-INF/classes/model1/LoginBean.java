package model1;
public class LoginBean {
public boolean login(String userName, String password) {
 if (userName==null || password==null ||
!(userName.equals("GashkovAlexey") && password.equals("qwerty")))
return false;
 else
return true;
}
}
