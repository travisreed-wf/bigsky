package bigsky;

import javax.swing.DefaultListModel;

public class Global {
	//These are used by the contactList or contact search is one way or another
	public static int totalAllowableContacts = 500;
	public static Contact[] contactList = new Contact[totalAllowableContacts];
	public static int nextContactNumber = 0;
	public static DefaultListModel listModel = new DefaultListModel();
}
