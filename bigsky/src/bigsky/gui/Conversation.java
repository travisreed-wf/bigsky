package bigsky.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import bigsky.Contact;
import bigsky.Global;

public class Conversation {

	private JFrame frmBluetext;
	private JTextField txtSearch;
	
	private final int returnsNull = 99999;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Conversation window = new Conversation();
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
	public Conversation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frmBluetext = new JFrame();
		frmBluetext.setIconImage(Toolkit.getDefaultToolkit().getImage(Conversation.class.getResource("/bigsky/BlueText.gif")));
		frmBluetext.setTitle("BlueText");
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		frmBluetext.setBounds((width/2)-400, (height/2)-325, 800, 650);
		frmBluetext.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmBluetext.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mnu_new_contact = new JMenuItem("New Contact");
		mnu_new_contact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openNewContactWindow();
			}
		});
		mnFile.add(mnu_new_contact);

		JMenuItem mnu_new_conversation = new JMenuItem("New Conversation");
		mnu_new_conversation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panel_2 = new JPanel();
				Global.conversationPane.addTab((String)Global.list.getSelectedValue(), null, panel_2, null);
			}
		});
		mnu_new_conversation.addMouseListener(new MouseAdapter() {
		});
		mnFile.add(mnu_new_conversation);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmEditContact = new JMenuItem("Edit Contact");
		mntmEditContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editContactAction();
				
			}
		});
		mnEdit.add(mntmEditContact);

		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAboutBluetext = new JMenuItem("About BlueText");
		mnHelp.add(mntmAboutBluetext);

		JPanel panel = new JPanel();
		frmBluetext.getContentPane().add(panel);
		panel.setLayout(null);

		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String searchTerm = txtSearch.getText();
				searchContact(searchTerm);
			}
		});
		txtSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				txtSearch.setText("");
			}
		});

		txtSearch.setBounds(16, 6, 190, 29);
		panel.add(txtSearch);
		txtSearch.setText("Search");
		txtSearch.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 41, 188, 346);
		panel.add(scrollPane);
		Global.list.addMouseListener(new PopClickListener());
		Global.list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String selectedContact = (String)Global.list.getSelectedValue();
				if (selectedContact.contains("Create Contact")) {
					openNewContactWindow();
				}
			}
		});

		Global.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(Global.list);


		Global.conversationPane.setBounds(226, 0, 490, 35);
		panel.add(Global.conversationPane);

		JPanel panel_1 = new JPanel();
		Global.conversationPane.addTab("New Conversation", null, panel_1, null);

		JPanel conversationPanel = new JPanel();
		conversationPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		conversationPanel.setBounds(226, 25, 490, 385);
		panel.add(conversationPanel);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(16, 422, 190, 100);
		panel.add(panel_3);

		JButton btnSend = new JButton("Send");
		btnSend.setBounds(599, 493, 117, 29);
		panel.add(btnSend);

		JTextArea txtrEnterMessageHere = new JTextArea();
		txtrEnterMessageHere.setText("New Message...");
		txtrEnterMessageHere.setBounds(226, 429, 490, 93);
		panel.add(txtrEnterMessageHere);

		JButton btn_select_contact = new JButton("Start New Convo");
		btn_select_contact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel_2 = new JPanel();
				Global.conversationPane.addTab((String)Global.list.getSelectedValue(), null, panel_2, null);
			}
		});
		btn_select_contact.setBounds(16, 388, 186, 29);
		panel.add(btn_select_contact);
		
		importContactsFromFile();
	}
	private void editContactAction(){
		String selectedValue = (String)Global.list.getSelectedValue();
		if (selectedValue != null){
			int i = findContactInListModel(selectedValue);
			if (i != returnsNull){
				Contact selectedContactCon = Global.contactAList.get(i);
				EditContact editCon = new EditContact(selectedContactCon, i, selectedValue);
				editCon.getFrmEditContact().setVisible(true);
			}
			else System.out.println("Error in Edit Contact");
		}
		else{
			JOptionPane.showMessageDialog(null, "Please select a contact to edit.");
		}
	}

	private void openNewContactWindow(){
		NewContact newCon = new NewContact();
		newCon.getFrmNewContact().setVisible(true);
	}
	private void searchContact(String searchTerm){
		Global.listModel.removeAllElements();
		if (!searchTerm.equals("")){
			for (int i = 0; i < Global.contactAList.size()-1; i++){
				Contact con = Global.contactAList.get(i);
				String first = con.getFirstName();
				String last = con.getLastName();
				if (first.toLowerCase().contains(searchTerm.toLowerCase())) {
						addContactToListModel(first, last);
				}
				else if (last.toLowerCase().contains(searchTerm.toLowerCase())) {
					addContactToListModel(first, last);
				}
				else if ((first.toLowerCase() + " " + last.toLowerCase()).contains(searchTerm.toLowerCase())) {
					addContactToListModel(first, last);
				}
			}
		}
		else {
			for (int i = 0; i < Global.contactAList.size()-1; i++){
				Contact con = Global.contactAList.get(i);
				String firstName = con.getFirstName();
				String lastName = con.getLastName();
				addContactToListModel(firstName, lastName);
				sortListModel();
			}
		}
		if (Global.listModel.size() < 12){
			Global.listModel.addElement("Create Contact");
		}
	}
	
	private void addContactToListModel(String firstName, String lastName){
		if (!firstName.equals("")){
			String newEntry = firstName + " " + lastName;
			Global.listModel.addElement(newEntry);
		}
		else if (lastName.equals("")){
			String newEntry = lastName;
			Global.listModel.addElement(newEntry);
		}
	}
	
	private int findContactInListModel(String selectedValue){
		for (int i=0;i<Global.contactAList.size();i++){
			Contact con = Global.contactAList.get(i);
			if (con.getFirstName().equals(selectedValue)){
				return i;
			}
			else if (con.getLastName().equals(selectedValue)){
				return i;
			}
			else if ((con.getFirstName() + " " + con.getLastName()).equals(selectedValue)){
				return i;
			}
		}
		return returnsNull;
	}

	public JFrame getFrmBluetext() {
		return frmBluetext;
	}
	
	private void sortListModel(){
		String[] tempList = new String[Global.listModel.size()];
		for (int i=0; i<Global.listModel.size(); i++) {
			tempList[i] = (String)Global.listModel.get(i);
		}
		Global.listModel.removeAllElements();
		Arrays.sort(tempList);
		for (int i=0; i<tempList.length;i++){
			Global.listModel.addElement(tempList[i]);
		}
	}
	
	private void importContactsFromFile(){
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("contact.txt"));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] splitline = sCurrentLine.split(",");
				String first = splitline[0];
				String last = splitline[1];
				String phone = splitline[2];
				String secondPhone = "";
				try {
					secondPhone = splitline[3];
				}
				catch (ArrayIndexOutOfBoundsException a){
					//This just means the contact doesn't have a second phone number
				}
				Global.contactAList.add(new Contact(first, last, phone, secondPhone));
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {

		}
		Contact firstContact = new Contact("Create Contact", "", "", "");
		Global.contactAList.add(firstContact);
		for (int i=0;i<Global.contactAList.size();i++){
			Contact con = Global.contactAList.get(i);
			String first = con.getFirstName();
			String last = con.getLastName();
			addContactToListModel(first, last);
		}
		sortListModel();
	}
}



