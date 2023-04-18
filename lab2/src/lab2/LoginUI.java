package lab2;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginUI {
	JLabel title = new JLabel("Library", JLabel.CENTER);
	JLabel username = new JLabel("username:");
	JLabel password = new JLabel("password:");
	
	JTextField userField = new JTextField(10);
	JPasswordField passwordField = new JPasswordField(10);
	JButton jbt_Login = new JButton("Login");
	JLabel hint = new JLabel("username and password is 'admin'", JLabel.CENTER);
    JFrame loginFrame = new JFrame();
    JButton jbt_Exit = new JButton("Exit");
    
    ///// login UI:
    
    LoginUI(MyLinkedList<User> usernameList) {
    	JPanel main = new JPanel();
    	Font myFont = new Font("SansSerif", Font.BOLD, 20);
    	
    	title.setFont(myFont);
    	main.setLayout(new GridLayout(5, 1));
    	main.add(title);
    	JPanel p1 = new JPanel();
    	p1.add(username);
    	p1.add(userField);
    	JPanel p2 = new JPanel();
    	p2.add(password);
    	p2.add(passwordField);
    	JPanel p3 = new JPanel();
    	p3.add(jbt_Login);
    	p3.add(jbt_Exit);
    	main.add(p1);
    	main.add(p2);
    	main.add(p3);
    	main.add(hint);
        jbt_Login.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Iterator<User> it = usernameList.iterator();
        		String name = userField.getText();
        		String pw = new String(passwordField.getPassword());
        		while(it.hasNext()) {
        			User tmp = it.next();
        			if(tmp.checkpw(name, pw)) {
    					JOptionPane.showMessageDialog(loginFrame, "Login successfully!");
    					new InitialUI();
    					loginFrame.setVisible(false);
    					return;
        			}
        		}
				JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!");
        	}
        });
        jbt_Exit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                System.exit(0);
        	}
        });
    	
    	loginFrame.add(main);
    	loginFrame.setTitle("Login session");
    	loginFrame.setSize(300, 200);
    	loginFrame.setLocationRelativeTo(null);
    	loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	loginFrame.setVisible(true);
    }
}
