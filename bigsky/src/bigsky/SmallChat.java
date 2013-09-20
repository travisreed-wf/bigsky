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
import javax.swing.JTextArea;

public class SmallChat {

	private JFrame frmBluetext;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBluetext = new JFrame();
		frmBluetext.setTitle("BlueText");
		frmBluetext.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frmBluetext.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		JTextArea messageArea = new JTextArea();
		messageArea.setText("message...");
		messageArea.setBounds(50,50, 50, 50);
		frmBluetext.getContentPane();
		
		
		frmBluetext.setBounds(100, 100, 224, 315);
		frmBluetext.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
	}

}
