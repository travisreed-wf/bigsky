package bigsky;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Login extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
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
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.GRAY);
		passwordField.setBounds(111, 120, 130, 28);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(111, 160, 130, 28);
		contentPane.add(passwordField_1);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(17, 126, 98, 16);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(17, 166, 98, 16);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(139, 219, 70, 29);
		contentPane.add(btnNewButton);
	}
}
