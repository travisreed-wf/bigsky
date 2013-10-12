package bigsky;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import bigsky.Contact;
import bigsky.NewContact;

public class Conversation {

	private JFrame frmBluetext;
	private JTextField txtSearch;
	private int totalAllowableContacts = 500;
	private Contact[] contactList = new Contact[totalAllowableContacts];
	private int nextContactNumber = 0;
	private DefaultListModel listModel = new DefaultListModel();
	private JList list = new JList(listModel);

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
		frmBluetext.setTitle("BlueText");
		frmBluetext.setBounds(100, 100, 800, 650);
		frmBluetext.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmBluetext.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mnu_new_contact = new JMenuItem("New Contact");
		mnu_new_contact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addContact();

			}
		});
		mnFile.add(mnu_new_contact);

		JMenuItem mnu_new_conversation = new JMenuItem("New Conversation");
		mnu_new_conversation.addMouseListener(new MouseAdapter() {
		});
		mnFile.add(mnu_new_conversation);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

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

		//TODO this will need to be removed once we actually have data
		for (int i=0;i<contactList.length;i++){
			contactList[i] = new Contact("", "", "", "");
		}
		Contact firstContact = new Contact("Create Contact", null, null, null);
		contactList[499] = firstContact;

		contactList[0] = new Contact("Travis", "Reed", "5633817739", "");
		contactList[1] = new Contact("Andrew", "Hartman", "523234", "");
		contactList[2] = new Contact("Jon", "Mielke", "52342", "");

		for (int i=0;i<contactList.length;i++){
			if (!contactList[i].getFirstName().equals("")){
				listModel.addElement(contactList[i].getFirstName());
			}
		}

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);


		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(226, 0, 150, 35);
		panel.add(tabbedPane_1);

		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("New Conversation", null, panel_1, null);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(226, 25, 490, 385);
		panel.add(panel_2);

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
		btn_select_contact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO start new conversation
				int selectedContactNumber = list.getSelectedIndex();
			}
		});
		btn_select_contact.setBounds(16, 388, 186, 29);
		panel.add(btn_select_contact);
	}

	private void addContact(){
		NewContact newCon = new NewContact();
		newCon.setVisible(true);
		//I still need to figure out how to wait until retContact is filled out
		Contact contactToAdd = newCon.getRetContact();

		if (nextContactNumber < totalAllowableContacts){
			//TODO remove previous listElement
			contactList[nextContactNumber] = contactToAdd;
			listModel.addElement(contactToAdd.getFirstName());
			nextContactNumber++;
		}
		else {
			//TODO
		}

	}

	private void searchContact(String searchTerm){
		listModel.removeAllElements();
		if (!searchTerm.equals("")){
			for (int i = 0; i < contactList.length-1; i++){
				if (contactList[i].getFirstName().toLowerCase().contains(searchTerm.toLowerCase())) {
					listModel.addElement(contactList[i].getFirstName());
				}
				else listModel.removeElement(contactList[i].getFirstName());
			}
		}
		else {
			for (int i = 0; i < contactList.length-1; i++){
				if (!contactList[i].getFirstName().equals("")) {
					listModel.addElement(contactList[i].getFirstName());
				}
			}
		}
		if (listModel.size() < 12){
			listModel.addElement(contactList[499].getFirstName());
		}
	}

	public JFrame getFrmBluetext() {
		// TODO Auto-generated method stub
		return frmBluetext;
	}
}
