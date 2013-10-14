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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField_1;
	private JLabel promptRegister;
	private JLabel wrongInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setBackground(Color.GRAY);
		textField.setBounds(190, 65, 165, 28);
		contentPane.add(textField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(190, 115, 165, 28);
		contentPane.add(passwordField_1);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(80, 71, 98, 16);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(80, 121, 98, 16);
		contentPane.add(lblPassword);
		
		JButton login = new JButton("Login");
		login.setBounds(106, 211, 75, 29);
		contentPane.add(login);
		
		JButton register = new JButton("Register");
		register.setBounds(250, 211, 90, 29);
		contentPane.add(register);
		
		wrongInfo = new JLabel("Wrong username or password");
		wrongInfo.setForeground(Color.RED);
		wrongInfo.setBounds(190, 154, 215, 14);
		contentPane.add(wrongInfo);
		wrongInfo.setVisible(false);
		
		promptRegister = new JLabel("Would you like to register");
		promptRegister.setForeground(Color.RED);
		promptRegister.setBounds(190, 154, 150, 14);
		contentPane.add(promptRegister);
		promptRegister.setVisible(false);

		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	            	
            	try {
					if(login()){
										
						Conversation convo = new Conversation();
		            	convo.getFrmBluetext().setVisible(true);
		            	phoneIP();						
					}
					else{
						System.out.println("FAIL");
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	      
            }
        });
		

		
		register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	  
            	Register reg = new Register();
            	reg.setVisible(true);
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
		String user = textField.getText();
		//takes out all not  digits
		user = user.replaceAll("\\D+","");
		return  user.trim();	
	}
	
	
	public void phoneIP(){
		
		
	}
	
	//returns row number of users database row
	public boolean login() throws ClassNotFoundException, SQLException, UnknownHostException{
		
		
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db30901", "adm309", "EXbDqudt4");
		Statement stmt = con.createStatement();
		InetAddress iP =InetAddress.getLocalHost();
		
		
		ResultSet rs = con.createStatement().executeQuery("select * from testTable where phoneNumber='" + getUsername() + "'");
	
		if(rs.next() == false){
			promptRegister.setVisible(true);
			rs.close();		
			con.close();
			return false;
		}
		if(rs.getString("password") == null){
			promptRegister.setVisible(true);
			rs.close();		
			con.close();
			return false;			
		}
			
		else if(!getUsername().equals(rs.getString("phonenumber")) || !getPassword(passwordField_1).equals(rs.getString("password"))){
			wrongInfo.setVisible(true);
			rs.close();		
			con.close();
			return false;
		}

		
		else if(!getUsername().equals(rs.getString("phonenumber")) && !getPassword(passwordField_1).equals(rs.getString("password"))){
			promptRegister.setVisible(true);
			rs.close();		
			con.close();
			return false;
		}

		
		//stmt.executeUpdate("UPDATE testTable SET phoneNumber='" + getUsername() + "' WHERE IP_Computer='" + iP + "';");
		
		rs.close();		
		con.close();
		return true;
		
	}
}
