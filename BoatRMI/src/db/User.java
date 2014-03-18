package db;

public class User {

	private String login;
	private String password;
	
	public User(String log, String pwd){
		this.login = log;
		this.password = pwd;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
