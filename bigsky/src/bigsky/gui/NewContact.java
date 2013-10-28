package bigsky.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import bigsky.Contact;
import bigsky.Global;

public class NewContact {

	private JFrame frmNewContact;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtPhone;
	private JTextField txtSecondPhone;
	private JLabel lblPhone;
	private JLabel lblSecondPhone;
	private JButton btnSubmit;
	private JButton btnCancel;

	/**
	 * Create the application.
	 */
	public NewContact() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNewContact = new JFrame();
		frmNewContact.setTitle("New Contact");
		frmNewContact.setIconImage(Toolkit.getDefaultToolkit().getImage(NewContact.class.getResource("/bigsky/BlueText.gif")));
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		frmNewContact.setBounds((width/2)-177, (height/2)-150, 355, 301);
		frmNewContact.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNewContact.getContentPane().setLayout(null);
		frmNewContact.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(33, 35, 86, 16);
		frmNewContact.getContentPane().add(lblFirstName);
		
		lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(33, 80, 86, 16);
		frmNewContact.getContentPane().add(lblLastName);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(136, 36, 134, 28);
		frmNewContact.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(136, 81, 134, 28);
		frmNewContact.getContentPane().add(txtLastName);
		
		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(136, 122, 134, 28);
		frmNewContact.getContentPane().add(txtPhone);
		
		txtSecondPhone = new JTextField();
		txtSecondPhone.setColumns(10);
		txtSecondPhone.setBounds(136, 171, 134, 28);
		frmNewContact.getContentPane().add(txtSecondPhone);
		
		lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(33, 128, 86, 16);
		frmNewContact.getContentPane().add(lblPhone);
		
		lblSecondPhone = new JLabel("Second Phone:");
		lblSecondPhone.setBounds(33, 177, 106, 16);
		frmNewContact.getContentPane().add(lblSecondPhone);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String first = txtFirstName.getText();
				String last = txtLastName.getText();
				String phone = txtPhone.getText();
				String secondPhone = txtSecondPhone.getText();
				if (secondPhone == ""){
					secondPhone = "0";
				}
				Contact contactToAdd = validateContact(first, last, phone, secondPhone);
				if (contactToAdd != null){
					if (Global.nextContactNumber < Global.totalAllowableContacts){
						//TODO remove previous listElement
						Global.contactList[Global.nextContactNumber] = contactToAdd;
						if (addContactToListModel(Global.nextContactNumber)){
							String newLine = System.getProperty("line.separator");
							String data = first + "," + last + "," + phone + "," + secondPhone + newLine;
							 
				    		File file =new File("contact.txt");
				 
				    		try {
				    			//if file doesnt exists, then create it
					    		if(!file.exists()){
									file.createNewFile();
					    		}
					 
					    		//true = append file
					    		FileWriter fileWritter = new FileWriter(file.getName(),true);
					    		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					    	    bufferWritter.write(data);
					    	    bufferWritter.close();
				    		}
				    		catch (IOException ie){
				    			
				    		}
				    		
							Global.nextContactNumber++;
							frmNewContact.setVisible(false);
						}	
					}
					else {
						//TODO
					}
					
				}
					
			}
		});
		btnSubmit.setBounds(189, 230, 117, 29);
		frmNewContact.getContentPane().add(btnSubmit);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmNewContact.setVisible(false);
			}
		});
		btnCancel.setBounds(33, 230, 117, 29);
		frmNewContact.getContentPane().add(btnCancel);
		
	}
	public JFrame getFrmNewContact(){
		return frmNewContact;
	}
	
	private Contact validateContact(String firstName, String lastName, String phone, String secondPhone){
		if (firstName.equals("")) {
			if (lastName.equals("")) {
				if (phone.equals("")) {
					JOptionPane.showMessageDialog(null, "Please Enter a First Name, Last Name, or Phone Number");
					return null;
				}
				else {
					firstName = phone;
				}
			}
		}
		if (phone.equals("")){
			if (secondPhone.equals("")){
				JOptionPane.showMessageDialog(null, "Please Enter a Phone Number");
				return null;
			}
			else {
				phone = secondPhone;
				secondPhone = "";
			}
		}
		else {
			phone = phone.replaceAll("\\D+","");
			if (phone.length() != 10){
				JOptionPane.showMessageDialog(null, "Please Enter all Phone Numbers with 10 digits");
				return null;
			}
		}
		if (!secondPhone.equals("")){
			secondPhone = secondPhone.replaceAll("\\D+","");
			if (secondPhone.length() != 10){
				JOptionPane.showMessageDialog(null, "Please Enter all Phone Numbers with 10 digits");
				return null;
			}
		}
		return new Contact(firstName, lastName, phone, secondPhone);
	}
	
	private boolean addContactToListModel(int i){
		String newEntry;
		Contact[] test = Global.contactList;
		if (!Global.contactList[i].getFirstName().equals("")){
			if (!Global.contactList[i].getLastName().equals("")){
				newEntry = Global.contactList[i].getFirstName() + " " + Global.contactList[i].getLastName();
			}
			else {
				newEntry = Global.contactList[i].getFirstName();
			}
			int j = Global.listModel.size()/2;
			j = getNewPositionBasedOnStringComparision(j, newEntry);
			DefaultListModel test2 = Global.listModel;
			if (Global.listModel.get(j).equals(newEntry)){
				JOptionPane.showMessageDialog(null, "This Name already exists. Please Alter Name");
				Global.contactList[i] = new Contact("", "", "", "");
				return false;
			}
			else {
				Global.listModel.add(j, newEntry);
				return true;
			}
		}
		else if (!Global.contactList[i].getLastName().equals("")){
			newEntry = Global.contactList[i].getLastName();
			int j = Global.listModel.size()/2;
			j = getNewPositionBasedOnStringComparision(j, newEntry);
			Global.listModel.add(j, newEntry);
			return true;
		}
		return false;
	}
	private int getNewPositionBasedOnStringComparision(int j , String newEntry){
		String testEntry = newEntry.toLowerCase();
		String listEntry = (String)Global.listModel.get(j);
		while (testEntry.compareTo(listEntry.toLowerCase()) < 0 & j!=0){
			j = j /2;
			listEntry = (String)Global.listModel.get(j);
		}
		while (testEntry.compareTo(listEntry.toLowerCase()) > 0 & j < Global.listModel.size()-1){
			j++;
			listEntry = (String)Global.listModel.get(j);
		}
		return j;
	}
}
