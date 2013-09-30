package bigsky;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import javax.swing.*;
 


public class TaskBar {



    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
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
        //Turn off metal's bold fonts
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
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu menu = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(createImage("BlueText.gif", "tray icon"));
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
            	convo.startConversation();
            	//convo.getFrmBluetext().setVisible(true);
            	
            	
//                JOptionPane.showMessageDialog(null,
//                        "This dialog box is run from the About menu item Something different");
//                
//                trayIcon.displayMessage("Sun TrayIcon Demo",
//                        "This is an error message", TrayIcon.MessageType.ERROR);
            }
        });
         
        smallChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	SmallChat smallChat = new SmallChat();
            	smallChat.startSmallChat();
            	//smallChat.getFrmBluetext().setVisible(true);
           
            }
        });
                
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
     
    //Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = TaskBar.class.getResource(path);
         
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}