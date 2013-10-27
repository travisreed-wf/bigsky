package bigsky;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import bigsky.gui.*;
import bigsky.*;

 


public class TaskBar{


public static Queue<TextMessage> myTextQueue = new Queue<TextMessage>();
public static ArrayList<TextMessage> textHistory = new ArrayList<TextMessage>();
public static ArrayList<TextMessage> myTextHistory = new ArrayList<TextMessage>();
public static TrayIcon notification = new TrayIcon(new ImageIcon(TaskBar.class.getResource("BlueText.gif"), "tray icon").getImage());
public static SmallChat smallChatWindow = null;
public static Contact me = new Contact("me", "me","me","");
public static Contact you = new Contact("Andy", "G",    "+1 5072542815", null);
public static final TrayIcon trayIcon = createTrayIconImage();
private static final SystemTray tray = SystemTray.getSystemTray();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
        
        smallChatWindow = createSmallChat(me,you);
        
       	Login login = new Login();
    	login.setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	initialize();
          
                	
               	
            }
        });
    }
     
    public void startTaskBar(){
    	initialize();
    }
    
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
            	
            	smallChatWindow.getFrmBluetext().setVisible(true);
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


