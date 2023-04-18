package lab2;

public class Main {
	
	static void createLoginUI(MyLinkedList<User> usernameList) {
		new LoginUI(usernameList);
	}
    
    public static void main(String[] args){
        MyLinkedList<User> usernameList = new MyLinkedList<>();
        usernameList.addLast(new User("admin", "admin"));
    	createLoginUI(usernameList);
    }
}
