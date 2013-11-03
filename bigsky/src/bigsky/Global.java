package bigsky;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTabbedPane;

public class Global {
	//These are used by the contactList or contact search is one way or another
	public static int totalAllowableContacts = 500;
	public static ArrayList<Contact> contactAList = new ArrayList<Contact>();
	public static DefaultListModel listModel = new DefaultListModel();
	public static JTabbedPane conversationPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
	public static JList list = new JList(listModel);
}
