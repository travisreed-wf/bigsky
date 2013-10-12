package bigsky;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewContact {

	private JFrame frame;
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
	private NewContact self = this;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 355, 301);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(33, 35, 86, 16);
		frame.getContentPane().add(lblFirstName);
		
		lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(33, 80, 86, 16);
		frame.getContentPane().add(lblLastName);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(136, 36, 134, 28);
		frame.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(136, 81, 134, 28);
		frame.getContentPane().add(txtLastName);
		
		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(136, 122, 134, 28);
		frame.getContentPane().add(txtPhone);
		
		txtSecondPhone = new JTextField();
		txtSecondPhone.setColumns(10);
		txtSecondPhone.setBounds(136, 171, 134, 28);
		frame.getContentPane().add(txtSecondPhone);
		
		lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(33, 128, 86, 16);
		frame.getContentPane().add(lblPhone);
		
		lblSecondPhone = new JLabel("Second Phone:");
		lblSecondPhone.setBounds(33, 177, 106, 16);
		frame.getContentPane().add(lblSecondPhone);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contact contactToAdd = new Contact(txtFirstName.getText(), txtLastName.getText(), txtPhone.getText(), txtSecondPhone.getText());
				if (Global.nextContactNumber < Global.totalAllowableContacts){
					//TODO remove previous listElement
					Global.contactList[Global.nextContactNumber] = contactToAdd;
					Global.listModel.addElement(contactToAdd.getFirstName());
					Global.nextContactNumber++;
					frame.setVisible(false);
				}
				else {
					//TODO
				}
				
			}
		});
		btnSubmit.setBounds(189, 230, 117, 29);
		frame.getContentPane().add(btnSubmit);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(33, 230, 117, 29);
		frame.getContentPane().add(btnCancel);
		
	}
	public JFrame getFrmNewContact(){
		return frame;
	}
}
