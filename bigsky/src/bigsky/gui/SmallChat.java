package bigsky.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileOutputStream;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

import bigsky.Contact;
import bigsky.Global;
import bigsky.TaskBar;
import bigsky.TextMessage;
import bigsky.messaging.TextMessageManager;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SmallChat  {

	private JFrame frmBluetext;
	public final JTextField textField = new JTextField();
	private JButton btnName;
	private JButton btnNewButton;
	private ArrayList<TextMessage> myTextHistory = new ArrayList<TextMessage>();
	
	
	private JButton send;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private DefaultCaret caret;
	private int winLocationY;
	
	private int offset = 0;
	private int textCount = -1;
	private static int windowNum = 0;
	private int winNum = 0;
	
	private Contact me;
	private Contact you;
	private TextMessage sent;
	private JTextField textField_1;
	private JMenu settings;
	private JMenu messagePreview;
	private JMenu notification;
	private JMenu fontSize;
	private JRadioButtonMenuItem notificationON;
	private JRadioButtonMenuItem notificationOFF;
	private JRadioButtonMenuItem messagePreviewON;
	private JRadioButtonMenuItem messagePreviewOFF;
	private ButtonGroup notiGroup;
	private JMenuBar menuBar;
	private ButtonGroup previewMessageGroup;
    private JMenuItem defaultSettings;

	//private TrayIcon notification = new TrayIcon(new ImageIcon(TaskBar.class.getResource("BlueText.gif"), "tray icon").getImage());
	

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
		this.me = TaskBar.me;
		this.you = you;
		winNum = windowNum;
		windowNum++;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//final SystemTray tray = SystemTray.getSystemTray();
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		send = new JButton("Send");
		
		frmBluetext = new JFrame();
		frmBluetext.setIconImage(Toolkit.getDefaultToolkit().getImage(SmallChat.class.getResource("/bigsky/BlueText.gif")));
		frmBluetext.getRootPane().setDefaultButton(send);
		frmBluetext.setResizable(false);
		frmBluetext.getContentPane().setBackground(Color.DARK_GRAY);
		frmBluetext.setTitle("BlueText");
		if((gd.getDisplayMode().getHeight() - 385 * (winNum + 1)) > gd.getDisplayMode().getHeight()){
			winLocationY = gd.getDisplayMode().getHeight();
		}
		else{
			winLocationY = gd.getDisplayMode().getHeight() - 385 * (winNum + 1);
		}
		frmBluetext.setBounds(gd.getDisplayMode().getWidth() - 243, winLocationY, 236, 340);
		frmBluetext.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBluetext.getContentPane().setLayout(null);
		
		frmBluetext.getContentPane().add(send);
		
		
		
		textField.setBounds(0, 289, 230, 23);
		frmBluetext.getContentPane().add(textField);
		textField.setColumns(10);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 12));
		textPane.setEditable(false);
		//textPane.setAutoscrolls(true);

		
		scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(0, 24, 230, 264);
		frmBluetext.getContentPane().add(scrollPane);
		scrollPane.setViewportView(textPane);
		caret = (DefaultCaret)textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		//Beginning of Settings Menu Bar
		menuBar = new JMenuBar();
		menuBar.setBounds(169, 0, 60, 23);
		frmBluetext.getContentPane().add(menuBar);
		
		settings = new JMenu("Settings");
		menuBar.add(settings);
		
		//Notifications menu
		notification = new JMenu("Notifications");
		settings.add(notification);
				
		notificationON = new JRadioButtonMenuItem("On");
		notificationOFF = new JRadioButtonMenuItem("Off");
		
		notification.add(notificationON);
		notification.add(notificationOFF);
		// Adding buttons to group so only 1 radio button can be selected at any times
		notiGroup = new ButtonGroup();
		notiGroup.add(notificationON);
		notiGroup.add(notificationOFF);
		
		//preview message Menu
		messagePreview = new JMenu("Preview Message");
		settings.add(messagePreview);
			
		messagePreviewON = new JRadioButtonMenuItem("On");
		messagePreviewOFF = new JRadioButtonMenuItem("Off");
		
		messagePreview.add(messagePreviewON);
		messagePreview.add(messagePreviewOFF);
				
		//Adding buttons to group so that only 1 radio button can be selected at any time.
		previewMessageGroup = new ButtonGroup();
		previewMessageGroup.add(messagePreviewON);
		previewMessageGroup.add(messagePreviewOFF);
		
		//Choose font size of small chat				
		fontSize = new JMenu("Font Size");
		settings.add(fontSize);
		
		textField_1 = new JTextField();
		fontSize.add(textField_1);
		textField_1.setColumns(10);
		
		//default settings
		defaultSettings = new JMenuItem("Default Settings");
		settings.add(defaultSettings);
		
		
		checkButtons();
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				sent = new TextMessage(me, you, textField.getText());

				try {			
					updateConv(sent);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
					System.out.println("updateConv in SmallChat - FAILED");
				} 
				textField.setText("");
			}
		});
		
		//Notification setting
		notificationON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(notificationON.isSelected()){
					Login.saveInProp(Global.username,Global.NOTIFICATION, Global.ON);
					notificationOFF.setSelected(false);
				}				
			}
		});
		//Notification setting
		notificationOFF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(notificationOFF.isSelected()){
					Login.saveInProp(Global.username,Global.NOTIFICATION, Global.OFF);
					notificationON.setSelected(false);
				}						
			}
		});
		//MessagePreview setting
		messagePreviewON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(messagePreviewON.isSelected()){
					Login.saveInProp(Global.username,Global.MESSAGEPREVIEW, Global.ON);
					messagePreviewOFF.setSelected(false);
				}				
			}
		});
		//MessagePreview setting
			messagePreviewOFF.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(messagePreviewOFF.isSelected()){
							Login.saveInProp(Global.username,Global.MESSAGEPREVIEW, Global.OFF);
							messagePreviewON.setSelected(false);
						}						
					}
				});
		
		//Font Size setting
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_1.getText().matches("[0-9]+")){
					textPane.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, Integer.valueOf(textField_1.getText())));
					Login.saveInProp(Global.username,Global.smallChatFontSize,textField_1.getText().toString());
				}
			}
		});
		
		//Default Setting listener
		defaultSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultSettings();
				}
		});
		

	}
	
	/**
	 * Checks(selects) the buttons that are set in the preference file
	 */
	private void checkButtons(){
		Properties prop = new Properties();
	
		try {
			prop.load(new FileInputStream(Global.username +".properties"));
		} catch (Exception e) {
			System.out.println("file load problem.");
		}
		
			
			if(prop.getProperty(Global.NOTIFICATION).equals(Global.ON)){
				notificationON.setSelected(true);
			}
			else if(prop.getProperty(Global.NOTIFICATION).equals(Global.OFF)){
				notificationOFF.setSelected(true);
			}
			
			if(prop.getProperty(Global.MESSAGEPREVIEW).equals(Global.ON)){
				messagePreviewON.setSelected(true);
			}
			else if(prop.getProperty(Global.MESSAGEPREVIEW).equals(Global.OFF)){
				messagePreviewOFF.setSelected(true);
			}
		}
	
	/**
	 * Allows user to reset the settings of the specified window to that of manufacturers
	 */
	private void defaultSettings(){
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(Global.username +".properties"));
		} catch (Exception e) {
			System.out.println("file load problem.");
		}
		
		prop.setProperty("save", Global.OFF);
		prop.setProperty(Global.MESSAGEPREVIEW,Global.ON);
		prop.setProperty(Global.NOTIFICATION,Global.ON);
		prop.setProperty(Global.smallChatFontSize, "12");	
		
		try{
			prop.store(new FileOutputStream(Global.username + ".properties"), null);
		}
		catch(Exception e1){
			System.out.println("Problem saving default settings in small chat");
			
		}
		textPane.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, Integer.valueOf(TaskBar.savedInfo(Global.conversationFontSize))));

	}
	
	
	protected void updateConv(TextMessage text) throws BadLocationException{
		boolean check = false;
		int temp = 0;
		
		
		//checks if user is sender
		if(!text.getContent().trim().isEmpty() && text.getSender().getPhoneNumber().equalsIgnoreCase(me.getPhoneNumber())){
			textPane.getDocument().insertString(offset, text.getSender().getFirstName() + ":\t" + text.getContent() + "\n\n", null);
			offset += (text.getSender().getFirstName() + ":\t" + text.getContent() + "\n\n").length();
			textCount++;
			myTextHistory.add(text);
			you.setSecondPhone("");
			text.setReceiver(you);
			
			if(!TaskBar.doNotSend && TextMessageManager.sendTexts){
				TaskBar.outGoingInSmall.add(text);
			}
			
			if(TaskBar.outGoingInSmall.size() != 0 && TextMessageManager.sendTexts){
				for(int i = 0; i < Conversation.currentConvs.size();i++){
					if(TaskBar.outGoingInSmall.get(0).getReceiver().getPhoneNumber().equalsIgnoreCase(Conversation.currentConvs.get(i).getPhoneNumber())){
						TaskBar.doNotSend = true;
						Conversation.updateConv(text);
						temp = Conversation.offset.get(i);
						Conversation.textPanes.get(i).getDocument().insertString(Conversation.offset.get(i), text.getSender().getFirstName() + ":\t" + text.getContent() + "\n\n", null);
						temp += (text.getSender().getFirstName() + ":\t" + text.getContent() + "\n\n").length();
						Conversation.offset.set(i, temp);
						TaskBar.outGoingInSmall.remove(0);
						TaskBar.doNotSend = false;
						check = true;
						break;
					}
				}
				if(check == false && TextMessageManager.sendTexts){
					TaskBar.doNotSend = true;
					
					JTextPane textPane = new JTextPane();
					DefaultCaret caretC = (DefaultCaret)textPane.getCaret();
					caretC.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
					textPane.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 12));
					textPane.setEditable(false);
					Conversation.textPanes.add(textPane);
					JScrollPane scroll = new JScrollPane(textPane);
					Global.conversationPane.addTab(text.getReceiver().getFirstName() + " " + text.getReceiver().getLastName(), null, scroll, null);
					Global.conversationPane.setSelectedIndex(Global.conversationPane.getTabCount()-1);
					Conversation.offset.add(new Integer(0));
					Conversation.currentConvs.add(text.getReceiver());
					
					Conversation.updateConv(text);
					TaskBar.outGoingInSmall.remove(0);
					TaskBar.doNotSend = false;
				}
			}

			if(!TaskBar.doNotSend && TextMessageManager.sendTexts){
				TaskBar.messageHost.sendObject(text);
			}

		}
		
			
		if(!text.getContent().trim().isEmpty() && text.getSender().getPhoneNumber().equals(you.getPhoneNumber())){
			textPane.getDocument().insertString(offset, text.getSender().getFirstName() + ":\t" + text.getContent() + "\n\n", null);
			offset += (text.getSender().getFirstName() + ":\t" + text.getContent() + "\n\n").length();
		}
		//attempt to scroll on creation
		textPane.setAutoscrolls(true);
		scrollPane.scrollRectToVisible(new Rectangle(0,textPane.getBounds(null).height,1,1));
		
	}

	public Contact getLocalContact()
	{
		return me;
	}
	
	public Contact getFromContact(){
		return you;
	}
	
	public void receivedText(TextMessage text) throws BadLocationException{
		updateConv(text);
	}

	public int getMyTextCount(){
		return textCount;
	}
	
	public ArrayList<TextMessage> getMyHistory(){
		return myTextHistory;
	}
	
	public int getChatNumber(){
		return winNum;
	}
	
	public JFrame getFrmBluetext() {
		return frmBluetext;
	}
	
	
	
	
}
