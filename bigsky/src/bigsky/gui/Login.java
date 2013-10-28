package bigsky.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JButton;

import bigsky.TaskBar;
import bigsky.messaging.*;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField_1;
	private JLabel promptRegister;
	private JLabel wrongInfo;
	public static MessageHost messageHost = null;
	private JRadioButton saveInfo;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/bigsky/BlueText.gif")));
		setResizable(false);
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds((width/2)-325, (height/2)-200, 650, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setBackground(Color.GRAY);
		textField.setBounds(239, 97, 165, 28);
		contentPane.add(textField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(239, 149, 165, 28);
		contentPane.add(passwordField_1);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(131, 103, 98, 16);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(134, 155, 98, 16);
		contentPane.add(lblPassword);
		
		JButton login = new JButton("Login");
		login.setBounds(204, 258, 90, 29);
		contentPane.add(login);
		
		JButton register = new JButton("Register");
		register.setBounds(319, 258, 90, 29);
		contentPane.add(register);
		
		wrongInfo = new JLabel("Wrong username or password");
		wrongInfo.setForeground(Color.RED);
		wrongInfo.setBounds(236, 233, 215, 14);
		contentPane.add(wrongInfo);
		wrongInfo.setVisible(false);
		
		promptRegister = new JLabel("Would you like to register");
		promptRegister.setForeground(Color.RED);
		promptRegister.setBounds(259, 233, 192, 14);
		contentPane.add(promptRegister);
		promptRegister.setVisible(false);
		
		saveInfo = new JRadioButton("Save Username/Password");
		saveInfo.setBackground(Color.WHITE);
		saveInfo.setBounds(239, 203, 212, 23);
		contentPane.add(saveInfo);
		saveInfo.setOpaque(false);

		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	            	
            	try {
					if(login()){

						dispose();
						TaskBar.putIconInSystemTray();
						if(messageHost==null){   
				   	   		messageHost = new MessageHost();
				   	   		messageHost.start();
				        }
						//Conversation convo = new Conversation();
		            	//convo.getFrmBluetext().setVisible(true);						
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
            	dispose();
            	Register reg = new Register();
            	reg.setVisible(true);
            }
        });	
		
		//save password and username button
				saveInfo.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	  
		            	saveInfo();
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
	
	
	//returns row number of users database row
	public boolean login() throws ClassNotFoundException, SQLException, UnknownHostException{
		
		
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db30901", "adm309", "EXbDqudt4");
		Statement stmt = con.createStatement();
		String iP =InetAddress.getLocalHost().getHostAddress();		
		
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

		
		stmt.executeUpdate("UPDATE testTable SET IP_Computer='" + iP + "' WHERE phoneNumber='" + getUsername() + "';");
		
		rs.close();		
		con.close();
		return true;
		
	}
	
	public void saveInfo(){
		
		Properties prop = new Properties();
		
		prop.setProperty("username", getUsername());
		prop.setProperty("password", getPassword(passwordField_1));
		prop.setProperty("save", "1");
		
		
		try {
			prop.store(new FileOutputStream("userPreferences.properties"),null);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
}
