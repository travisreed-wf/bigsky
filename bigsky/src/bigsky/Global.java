package bigsky;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

/**
 * Global Variables used throughout the program
 * @author Travis Reed, Jonathan Mielke, Andrew Hartman
 *
 */
public class Global {
	//These are used by the contactList or contact search is one way or another
	public static ArrayList<Contact> contactAList = new ArrayList<Contact>();
	public static DefaultListModel listModel = new DefaultListModel();
	public static JTabbedPane conversationPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
	public static JList list = new JList(listModel);
	public static JMenuItem batteryIndicator = new JMenuItem();
	public static Integer battery_remaining = 100;
	public final static String ON = "ON";
	public final static String OFF = "OFF";
	public final static String MESSAGEPREVIEW = "MESSAGEPREVIEW";
	public final static String NOTIFICATION = "NOTIFICATION";
	public static String username;
	public final static String save = "save";
	public static ArrayList<TextMessage> phoneTextHistory = new ArrayList<TextMessage>();
	public static ArrayList<TextMessage> historyGatherText = new ArrayList<TextMessage>();
	public final static String smallChatFontSize = "SMALLCHATFONTSIZE";
	public final static String conversationFontSize = "CONVERSATIONFONSIZE";
}
