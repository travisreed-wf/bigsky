package bigsky;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewContact extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txt_second_phone;
	private JTextField txt_phone;
	private JTextField txt_last_name;
	private JTextField txt_first_name;
	private String first_name = "";
	private String last_name = "";
	private String phone_number = "";
	private String second_phone = "";


	/**
	 * Create the dialog.
	 */
	public NewContact() {
		initUI();
	}
	
	public final void initUI(){
		setBounds(100, 100, 321, 203);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("First Name:");
			lblNewLabel.setBounds(25, 29, 82, 16);
			contentPanel.add(lblNewLabel);
		}
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(25, 57, 82, 16);
		contentPanel.add(lblLastName);
		
		JLabel lblPrimaryPhone = new JLabel("Primary Phone:");
		lblPrimaryPhone.setBounds(25, 89, 99, 16);
		contentPanel.add(lblPrimaryPhone);
		
		JLabel lblSecondaryPhone = new JLabel("Secondary Phone:");
		lblSecondaryPhone.setBounds(25, 117, 110, 16);
		contentPanel.add(lblSecondaryPhone);
		
		txt_second_phone = new JTextField();
		txt_second_phone.setBounds(147, 111, 134, 28);
		contentPanel.add(txt_second_phone);
		txt_second_phone.setColumns(10);
		
		txt_phone = new JTextField();
		txt_phone.setBounds(147, 83, 134, 28);
		contentPanel.add(txt_phone);
		txt_phone.setColumns(10);
		
		txt_last_name = new JTextField();
		txt_last_name.setBounds(147, 51, 134, 28);
		contentPanel.add(txt_last_name);
		txt_last_name.setColumns(10);
		
		txt_first_name = new JTextField();
		txt_first_name.setBounds(147, 23, 134, 28);
		contentPanel.add(txt_first_name);
		txt_first_name.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						first_name = txt_first_name.getText();
						last_name = txt_last_name.getText();
						phone_number = txt_phone.getText();
						second_phone = txt_second_phone.getText();
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("New Contact");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public String getPhoneNumber() {
		return phone_number;
	}
	
	public String getSecondPhone() {
		return second_phone;
	}
}
