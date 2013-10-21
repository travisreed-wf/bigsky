package bigsky;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import javax.swing.*;

import bigsky.gui.Conversation;
import bigsky.gui.SmallChat;
 


public class TaskBar {



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
        final TrayIcon trayIcon =createTrayIconImage();
               
        
       // new TrayIcon(createImage("BlueText.gif", "tray icon"));
        
        
        final SystemTray tray = SystemTray.getSystemTray();
       
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
         
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }
         
        
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
            	
            	SmallChat smallChat = new SmallChat(null, null);
            	smallChat.getFrmBluetext().setVisible(true);
           
            }
        });
                
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
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
}