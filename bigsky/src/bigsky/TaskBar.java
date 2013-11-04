package bigsky;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import bigsky.gui.*;
import bigsky.messaging.MessageHost;
import bigsky.TextMessageManager;




public class TaskBar{


public static Queue<TextMessage> myTextQueue = new Queue<TextMessage>();
public static ArrayList<TextMessage> myTextArray = new ArrayList<TextMessage>();
public static ArrayList<TextMessage> sendingTextArray = new ArrayList<TextMessage>();
public static TrayIcon notification = new TrayIcon(new ImageIcon(TaskBar.class.getResource("BlueText.gif"), "tray icon").getImage());
public static ArrayList<SmallChat> smallChatWindows = new ArrayList<SmallChat>();
public static Contact me = new Contact("me", "me","me","");
public static Contact you = new Contact("Andy", "G",    "+1 5072542815", null);
public static final TrayIcon trayIcon = createTrayIconImage();
private static final SystemTray tray = SystemTray.getSystemTray();
public static MessageHost messageHost = null;
public static TextMessageManager textManager = null;	


    public static void main(String[] args) {
        try {
        	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        // Checks to see if the user setting is to save username and password
        if(savedInfo()){
        	Conversation convo = new Conversation();
        	convo.getFrmBluetext().setVisible(true);

        	TaskBar.putIconInSystemTray();
			if(messageHost==null){
	   	   		messageHost = new MessageHost();
	   	   		messageHost.start();
	   	   		textManager = new TextMessageManager();
	   	   		textManager.start();
	        }
        }

        else{
        	Login login = new Login();
           	login.setVisible(true);
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	initialize();
            	//TaskBar.trayIcon.displayMessage("SUP","sup",MessageType.INFO);
            }
        });
    }

//I dont think this method is ever used
//    public void startTaskBar(){
//    	initialize();
//    }

    private static void initialize() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }


        final PopupMenu menu = new PopupMenu();



       // new TrayIcon(createImage("BlueText.gif", "tray icon"));




        //shows full image in taskbar
        trayIcon.setImageAutoSize(true);

        //  menu items
        MenuItem conversation = new MenuItem("Open BlueText");
        MenuItem smallChat = new MenuItem("Side Chat");
        MenuItem exitItem = new MenuItem("Exit");

        //Adding  menu items
        menu.add(conversation);
        menu.add(smallChat);
        menu.add(exitItem);
        trayIcon.setPopupMenu(menu);




//        trayIcon.addMouseListener(new MouseAdapter() {
//            public void mouseReleased(MouseEvent e) {
//                if (e.isPopupTrigger()) {
//
//                }
//            }
//        });

        conversation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {




            	Conversation convo = new Conversation();
            	convo.getFrmBluetext().setVisible(true);


//                JOptionPane.showMessageDialog(null,
//                        "This dialog box is run from the About menu item Something different");
//
//                trayIcon.displayMessage("Sun TrayIcon Demo",
//                        "This is an error message", TrayIcon.MessageType.ERROR);
            }
        });

        smallChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for(int i = 0; i < smallChatWindows.size(); i++){
            		smallChatWindows.get(i).getFrmBluetext().setVisible(true);
            	}
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }

    public static void putIconInSystemTray(){
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }

    }

    //Obtain tray icon image
    protected static TrayIcon createTrayIconImage() {
       TrayIcon tray;
   	   URL imageURL = TaskBar.class.getResource("BlueText.gif");

       Image icon = new ImageIcon(imageURL, "tray icon").getImage();

        if (imageURL == null) {
            System.err.println("Resource not found: " + "BlueText.gif");
            return null;
        } else {
        	tray = new TrayIcon(icon);
            return tray;
        }
    }

    protected static SmallChat createSmallChat(Contact me, Contact you){
    	SmallChat smallChat = new SmallChat(me,you);
    	smallChat.getFrmBluetext().setVisible(false);
    	return smallChat;
    }

    public static boolean savedInfo(){

		Properties prop = new Properties();
		String compare = "1";

		try {
			prop.load(new FileInputStream("userPreferences.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(compare.equals(prop.getProperty("save"))){
			return true;
		}

		return false;
	}



}

class Queue<T>{
	protected LinkedList<T> list;

	public Queue(){
		list = new LinkedList<T>();
	}

	public void add(T element){
		list.add(element);
	}

	public T removeFirst(){
		return list.removeFirst();
	}

	public boolean isEmpty(){
		if(list.isEmpty()){
			return true;
		}
		else
			return false;
	}


}


