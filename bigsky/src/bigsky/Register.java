package bigsky;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.SystemColor;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField primaryPhone;
	private JTextField secondaryPhone;
	private JPasswordField password;
	private JPasswordField confirmPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Register() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		getContentPane().setLayout(null);
		contentPane = new JPanel();

		firstName = new JTextField();
		firstName.setForeground(SystemColor.inactiveCaptionText);
		firstName.setBounds(78, 75, 134, 28);
		getContentPane().add(firstName);
		firstName.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(5, 81, 73, 16);
		getContentPane().add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(5, 109, 73, 16);
		getContentPane().add(lblLastName);
		
		lastName = new JTextField();
		lastName.setForeground(SystemColor.inactiveCaptionText);
		lastName.setColumns(10);
		lastName.setBounds(78, 103, 134, 28);
		getContentPane().add(lastName);
		
		JLabel lblPrimaryPhoneNumber = new JLabel("Primary Phone Number (Username)");
		lblPrimaryPhoneNumber.setBounds(19, 27, 221, 16);
		getContentPane().add(lblPrimaryPhoneNumber);
		
		primaryPhone = new JTextField();
		primaryPhone.setForeground(SystemColor.inactiveCaptionText);
		primaryPhone.setColumns(10);
		primaryPhone.setBounds(252, 21, 134, 28);
		getContentPane().add(primaryPhone);
		
		JLabel lblSecondaryPhoneNumber = new JLabel("Secondary Phone Number");
		lblSecondaryPhoneNumber.setBounds(5, 201, 160, 16);
		getContentPane().add(lblSecondaryPhoneNumber);
		
		secondaryPhone = new JTextField();
		secondaryPhone.setForeground(SystemColor.inactiveCaptionText);
		secondaryPhone.setColumns(10);
		secondaryPhone.setBounds(170, 195, 134, 28);
		getContentPane().add(secondaryPhone);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(6, 261, 61, 16);
		getContentPane().add(lblPassword);
		
		password = new JPasswordField();
		password.setToolTipText("\n");
		password.setBounds(121, 255, 134, 28);
		getContentPane().add(password);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(5, 313, 123, 16);
		getContentPane().add(lblConfirmPassword);
		
		confirmPassword = new JPasswordField();
		confirmPassword.setBounds(121, 307, 134, 28);
		getContentPane().add(confirmPassword);
		
		JButton Register = new JButton("Register");
		Register.setBounds(138, 379, 117, 29);
		getContentPane().add(Register);
		
		
		
	
	
		Register.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        
	        	if(registerChecks()){
	        		
	        		System.out.println(getPassword(password));
	        		System.out.println(getPassword(confirmPassword));
	        		System.out.println(getUsername());
	        		System.out.println(getFirstName());
	        		System.out.println(getLastName());
	        		System.out.println(getSecondaryPhone());
	        		System.out.println("-----------------");
	        		System.out.println("-----------------");
	        		System.out.println("-----------------");
	        		System.out.println("-----------------");
	        		System.out.println("-----------------");
	        		System.out.println("-----------------");

	        		try {
						putInSystem();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        		        		
	        	}
	        	else{
	        		retry();
	        	}
	        	
	        	try {
					wait(5000);
					System.exit(0);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        
	        }

		
	    });

	}
	
	
	private String getPassword(JPasswordField pass){
		String password = "";
		char [] word = pass.getPassword();
		for(int i = 0; i < word.length; i++){
			password = password + word[i];
		}
		return password;	
	}
	
	private String getUsername(){
	String user = primaryPhone.getText();
	//takes out all not  digits
	user = user.replaceAll("\\D+","");
		return  user.trim();	
	}
	private String getFirstName(){
		String user = firstName.getText();
			return  user.trim();	
		}
	private String getLastName(){
		String user = lastName.getText();
			return  user.trim();	
		}
	private String getSecondaryPhone(){
		String user = secondaryPhone.getText();
		//takes out all not  digits
		user = user.replaceAll("\\D+","");
			return  user.trim();	
		}
	
	
	
	private boolean registerChecks(){
		
		if(!getPassword(password).equals(getPassword(confirmPassword))){
			return false;
		}
		
		if(getUsername().length() != 10){
			return false;
		}
		
		return true;
	}
	
	private void putInSystem() throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db30901", "adm309", "EXbDqudt4");
		Statement stmt = con.createStatement();
		
		String query = "INSERT INTO testTable  (phoneNumber, lastName, firstName, password) VALUES " +
				"('" + getUsername() + "', '" + getLastName() + "', '" + getFirstName() + "','" + getPassword(password) +
				"')";
		
		System.out.println(query);
		System.out.println(getUsername().length());
		
		stmt.executeUpdate(query);
		
		
		
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection con = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db30901", "adm309", "EXbDqudt4");
//		Statement stmt = con.createStatement();
//		
//		//stmt.executeUpdate("INSERT INTO testTable (phoneNumber, lastName, firstName, password, secondaryPhone) VALUES ('2222222222','Bismark','Clark','secret','3333333333')");
//		System.out.println("Got HEre");
//		stmt.executeUpdate("INSERT INTO 	testTable (`phoneNumber`, `lastName`, `firstName`,`password`) VALUES ('1231231239', 'lastname', 'firstname','secret')");

		con.close();		
	}
	
	
	private void retry() {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
