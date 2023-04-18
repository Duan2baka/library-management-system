package lab2;

class User{
	private String username = "", password = "";
	User(String name, String pw){
		username = new String(name);
		password = new String(pw);
	}
	boolean checkpw(String un,String tmp) {
		return un.equals(username) && password.equals(tmp);
	}
}