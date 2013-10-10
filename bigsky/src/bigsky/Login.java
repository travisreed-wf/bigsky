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

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField_1;

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
		contentPane.setBackground(Color.BLUE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setBackground(Color.GRAY);
		textField.setBounds(111, 120, 130, 28);
		contentPane.add(textField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(111, 160, 130, 28);
		contentPane.add(passwordField_1);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(17, 126, 98, 16);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(17, 166, 98, 16);
		contentPane.add(lblPassword);
		
		JButton login = new JButton("Login");
		login.setBounds(139, 219, 70, 29);
		contentPane.add(login);
		
		
		login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	            	
            	try {
					if(login()){
						
						System.out.println("Works");
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
				}
            	      
            }
        });

	}
	
	public String getPassword(){
	
		String pass = "";
		char [] password = passwordField_1.getPassword();
		for(int i = 0; i < password.length; i++){
			pass = pass + password[i];
		}
		return pass;	
		
	}
	
	public String getUsername(){
		return  textField.getText();	
	}
	
	//returns row number of users database row
	public boolean login() throws ClassNotFoundException, SQLException{
		
		
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db30901", "adm309", "EXbDqudt4");
		Statement stmt = con.createStatement();
		
		//stmt.executeUpdate("insert into testTable (phoneNumber,lastName,firstName, password) values ('1111111111','Johnson','Missy','mypassword')");
		ResultSet rs = con.createStatement().executeQuery("select * from testTable where phoneNumber='" + getUsername() + "'");
	
		if(rs.next() == false){
			System.out.println("Username not found");
			return false;
			
		}
		
		if(rs.getString("password") == null){
			System.out.println("Password not found");
			return false;			
		}
		
		
		System.out.println(getPassword());
		System.out.println(getUsername());
		System.out.println("-----------------");
		System.out.println("-----------------");
		System.out.println("-----------------");
		System.out.println("-----------------");
		System.out.println("-----------------");
		System.out.println("-----------------");
		System.out.println(rs.getString("password"));
		System.out.println(rs.getString("phoneNumber"));
		
		
		
		
		
		
		
//		int count = 0;;
//		rs.last();
//		int numRows = rs.getRow();
//		rs.first();
//		for(int i =0; i <numRows; i++){
//			count = 0;	
//			if(rs.getString("phoneNumber") != null){
//				System.out.println("Username Input is: " + getUsername());
//				System.out.println("--------------------------------------------------");
//				System.out.println("--------------------------------------------------");
//				System.out.println("Username Database is: " + rs.getString("phonenumber"));
//				System.out.println("--------------------------------------------------");
//				System.out.println("--------------------------------------------------");
//				if(getUsername().equals(rs.getString("phonenumber"))){
//					count++;
//				}
//			}
//			
//			if(rs.getString("password") != null){
//				System.out.println("Password Input is: " + getPassword());
//				System.out.println("--------------------------------------------------");
//				System.out.println("--------------------------------------------------");
//				System.out.println("Password Database is: " + rs.getString("password"));
//				System.out.println("--------------------------------------------------");
//				System.out.println("--------------------------------------------------");
//				if(getPassword().equals(rs.getString("password"))){
//					count++;
//				}
//			}
//				
//			
//			if(count == 2){
//				return rs.getRow();
//			}
//			
//			rs.next();
//		}
		
		
		rs.close();		
		con.close();

		return true;
		
	}
	
	
	
	
	
	
	
}
