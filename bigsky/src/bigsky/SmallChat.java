package bigsky;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
import javax.swing.text.BadLocationException;
import javax.swing.JToolBar;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Point;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JTextPane;

import com.nitido.utils.toaster.Toaster;

public class SmallChat  {

	private JFrame frmBluetext;
	private final JTextField textField = new JTextField();
	private JButton btnName;
	private JButton btnNewButton;
	//private final JTextArea textArea = new JTextArea();
	
	
	private final JButton send = new JButton("Send");
	private JScrollPane scrollPane;
	private JTextPane textPane;
	//private JScrollPane scroll;
	
	private int offset = 0;
	
	private Contact me = new Contact("Jonathan", "Mielke", "6185204620", "");
	private Contact you = new Contact("Friendly", "Friend", "55555555555", "");
	
	private Toaster toaster = new Toaster();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SmallChat window = new SmallChat(new Contact("Jonathan", "Mielke", "6185204620", ""), new Contact("Friendly", "Friend", "55555555555", ""));
					window.frmBluetext.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * initiallizes a quick chat window
	 * @param me
	 * @param you
	 */
	public SmallChat(Contact me, Contact you) {
		initialize();
		
		
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msgOut = textField.getText();
				String msgIn = "HEY!";
				try {
					BufferedImage img = ImageIO.read(new File("C:/Users/Public/Pictures/Sample Pictures/Penguins.jpg"));
					toaster.setBackgroundImage(img);
					toaster.showToaster("NEW MESSAGE");
					updateConv(msgOut, msgIn);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2)
				{
					e2.printStackTrace();
				}
				
				textField.setText("");
			}
		});
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		
		frmBluetext = new JFrame();
		frmBluetext.getRootPane().setDefaultButton(send);
		frmBluetext.setResizable(false);
		frmBluetext.getContentPane().setBackground(Color.DARK_GRAY);
		frmBluetext.setTitle("BlueText");
		frmBluetext.setBounds(gd.getDisplayMode().getWidth() - 243, gd.getDisplayMode().getHeight() - 385, 236, 340);
		frmBluetext.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBluetext.getContentPane().setLayout(null);
		
		frmBluetext.getContentPane().add(send);
		
		
		
		textField.setBounds(0, 289, 230, 23);
		frmBluetext.getContentPane().add(textField);
		textField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 24, 230, 264);
		frmBluetext.getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 10));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

		
		
		
//		scrollPane.setViewportView(textArea);
		
		
//		textArea.setBackground(Color.LIGHT_GRAY);
//		textArea.setForeground(Color.BLUE);
//		textArea.setFont(new Font("Courier New", Font.PLAIN, 10));
//		textArea.setLineWrap(true);
//		textArea.setTabSize(2);
//		textArea.setWrapStyleWord(true);
//		textArea.setEditable(false);
		
		
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
	
	
	protected void updateConv(String msgSend, String msgRecieved) throws BadLocationException{
		TextMessage textSent = new TextMessage(me, you, msgSend);
		TextMessage textRecieved = new TextMessage(you, me, msgRecieved);
		
		
		
		
		if(!textSent.getContent().trim().isEmpty()){
			textSent.setContent(textSent.getSender().getFirstName() + ":\t" + textSent.getContent() + "\n\n");
//			textArea.append(textSent.getSender().getFirstName() + ":\t" + textSent.getContent() + "\n\n");
			textPane.getDocument().insertString(offset, textSent.getContent(), null);
			offset+=textSent.getContent().length();
		}
		
		
		
		
		if(!textRecieved.getContent().trim().isEmpty()){
			textRecieved.setContent(textRecieved.getSender().getFirstName() + ":\t" + textRecieved.getContent() + "\n\n");
//			textArea.append(textRecieved.getSender().getFirstName() + ":\t" + textRecieved.getContent() + "\n\n");
			textPane.getDocument().insertString(offset, textRecieved.getContent(), null);
			offset+=textRecieved.getContent().length();
		}
	}

	public Contact getLocalContact()
	{
		return me;
	}
	
	public Contact getFromContact(){
		return you;
	}

	public JFrame getFrmBluetext() {
		// TODO Auto-generated method stub
		return frmBluetext;
	}
}
