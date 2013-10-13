package bigsky;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

public class Conversation {

	private JFrame frmBluetext;
	private JTextField txtSearch;
	private JList list = new JList(Global.listModel);

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
		for (int i=0;i<Global.contactList.length;i++){
			Global.contactList[i] = new Contact("", "", "", "");
		}
		Contact firstContact = new Contact("Create Contact", null, null, null);
		Global.contactList[499] = firstContact;

		Global.contactList[0] = new Contact("Travis", "Reed", "5633817739", "");
		Global.contactList[1] = new Contact("Andrew", "Hartman", "523234", "");
		Global.contactList[2] = new Contact("Jon", "Mielke", "52342", "");

		for (int i=0;i<Global.contactList.length;i++){
			if (!Global.contactList[i].getFirstName().equals("")){
				Global.listModel.addElement(Global.contactList[i].getFirstName());
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
				String selectedContact = (String)list.getSelectedValue();
				
			}
		});
		btn_select_contact.setBounds(16, 388, 186, 29);
		panel.add(btn_select_contact);
		
		JButton btnAddContact = new JButton("AddContact");
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openNewContactWindow();
			}
		});
		btnAddContact.setBounds(26, 538, 117, 29);
		panel.add(btnAddContact);
		
		JButton editContact = new JButton("EditContact");
		editContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedContact = (String)list.getSelectedValue();
				Contact selectedContactCon;
				for (int i=0;i<Global.contactList.length;i++){
					if (Global.contactList[i].getFirstName().equals(selectedContact)){
						selectedContactCon = Global.contactList[i];
						EditContact editCon = new EditContact(selectedContactCon, i);
						editCon.getFrmEditContact().setVisible(true);
						break;
					}
				}
			}
		});
		editContact.setBounds(150, 538, 117, 29);
		panel.add(editContact);
	}

	private void openNewContactWindow(){
		NewContact newCon = new NewContact();
		newCon.getFrmNewContact().setVisible(true);
	}
	private void searchContact(String searchTerm){
		Global.listModel.removeAllElements();
		if (!searchTerm.equals("")){
			for (int i = 0; i < Global.contactList.length-1; i++){
				if (Global.contactList[i].getFirstName().toLowerCase().contains(searchTerm.toLowerCase())) {
					Global.listModel.addElement(Global.contactList[i].getFirstName());
				}
				else Global.listModel.removeElement(Global.contactList[i].getFirstName());
			}
		}
		else {
			for (int i = 0; i < Global.contactList.length-1; i++){
				if (!Global.contactList[i].getFirstName().equals("")) {
					Global.listModel.addElement(Global.contactList[i].getFirstName());
				}
			}
		}
		if (Global.listModel.size() < 12){
			Global.listModel.addElement(Global.contactList[499].getFirstName());
		}
	}

	public JFrame getFrmBluetext() {
		// TODO Auto-generated method stub
		return frmBluetext;
	}
}
