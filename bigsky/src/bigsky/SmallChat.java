package bigsky;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.Canvas;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.JToolBar;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Point;
import java.awt.Insets;
import java.util.Scanner;

public class SmallChat  {

	private JFrame frmBluetext;
	private final JTextField textField = new JTextField();
	private JButton btnName;
	private JButton btnNewButton;
	private final JTextArea textArea = new JTextArea();
	private static final String myName = "Jon:\t";
	private final JButton send = new JButton("Send");
	private JScrollPane scrollPane;
	//private JScrollPane scroll;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SmallChat window = new SmallChat();
					window.frmBluetext.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SmallChat() {
		initialize();
		
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String yourMsg = textField.getText();
				updateConv(yourMsg);
				textField.setText("");
			}
		});
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBluetext = new JFrame();
		frmBluetext.getRootPane().setDefaultButton(send);
		frmBluetext.setResizable(false);
		frmBluetext.getContentPane().setBackground(Color.DARK_GRAY);
		frmBluetext.setTitle("BlueText");
		frmBluetext.setBounds(100, 100, 236, 340);
		frmBluetext.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBluetext.getContentPane().setLayout(null);
		
		frmBluetext.getContentPane().add(send);
		
		
		
		textField.setBounds(0, 289, 230, 23);
		frmBluetext.getContentPane().add(textField);
		textField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 24, 230, 264);
		frmBluetext.getContentPane().add(scrollPane);
		scrollPane.setViewportView(textArea);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setForeground(Color.BLUE);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 10));
		textArea.setLineWrap(true);
		textArea.setTabSize(2);
		
		
		
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		
		
		btnName = new JButton("Jonathan");
		btnName.setOpaque(false);
		btnName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnName.setVerticalAlignment(SwingConstants.TOP);
		btnName.setBounds(0, 1, 139, 23);
		frmBluetext.getContentPane().add(btnName);
		
		btnNewButton = new JButton("Settings");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(137, 1, 93, 23);
		frmBluetext.getContentPane().add(btnNewButton);
		

	}
	
	
	protected void updateConv(String msg){
		
		//Scanner scanner = new Scanner(System.in);
		textArea.append("" + myName + msg + "\n\n");
		
		
	}


	public JFrame getFrmBluetext() {
		// TODO Auto-generated method stub
		return frmBluetext;
	}

}
