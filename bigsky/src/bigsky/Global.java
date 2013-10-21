package bigsky;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Global {
	//These are used by the contactList or contact search is one way or another
	public static int totalAllowableContacts = 500;
	public static Contact[] contactList = new Contact[totalAllowableContacts];
	public static int nextContactNumber = 0;
	public static DefaultListModel listModel = new DefaultListModel();
	public static JTabbedPane conversationPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
	
}
