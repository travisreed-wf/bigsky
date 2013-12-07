package bigsky.gui;

import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;

import bigsky.Global;
import bigsky.TaskBar;
import bigsky.TextMessage;

import java.awt.Font;






import java.util.ArrayList;

import javax.swing.JLabel;


public class Notification {

	private JDialog frame;
	private TextMessage messager;
	private int chatWinNum;
	private float fade = 1.0F;
	private final Timer timer1 = new Timer(50, null);
	private final Timer timer2 = new Timer(1, null);
	private float positionY;
	private static int totalWindows;
	private int windowNum = 0;
	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static ArrayList<Notification> openNotifications = new ArrayList<Notification>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
        	UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notification window = new Notification(new TextMessage(TaskBar.me, TaskBar.you, "HEY STUPID"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Notification(TextMessage messager) {
		this.messager = messager;
		chatWinNum = 0;
		
		totalWindows++;
		windowNum = totalWindows;
		positionY = gd.getDisplayMode().getHeight();
		
		try {
			initialize();
		} catch (BadLocationException e) {
			System.out.println("ERROR in Notification Initialize()");
			e.printStackTrace();
		}
		
		frame.setVisible(true);
		
		
		 
		 timer1.addActionListener(new ActionListener() {
			 @Override
		     public void actionPerformed(ActionEvent e) {
		        	
				 if (fade < 0.0126F){
					 timer1.stop();
					 frame.dispose();
					 totalWindows--;
					 for(int i = windowNum - 1; i < openNotifications.size();i++){
						 //System.out.println("Array Num: " + openNotifications.get(i).windowNum);
						 openNotifications.get(i).windowNum--;
					 }
					// System.out.println("Removed Window: " + (windowNum));
					 openNotifications.remove(windowNum);
		         }
		         fade = fade - 0.0125F;
		         //frame.setOpacity(fade);
		     }
		 });
		 timer1.setInitialDelay(3000);
		 timer1.start();
		 this.animate();
		 openNotifications.add(this);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws BadLocationException 
	 */
	private void initialize() throws BadLocationException {
		for(int i=0; i < TaskBar.smallChatWindows.size(); i++){
			if(TaskBar.myTextArray.get(0).getSender().getPhoneNumber().equals(TaskBar.smallChatWindows.get(i).getFromContact().getPhoneNumber())){
				chatWinNum = i;
			}
		}
		
		
		
		frame = new JDialog();
		frame.setName("New Message");
		frame.setResizable(false);
		frame.setBounds(gd.getDisplayMode().getWidth() - 260, gd.getDisplayMode().getHeight(), 257, 178);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
		frame.setTitle("New Message");
		
		JButton btnQuickChat = new JButton("Quick Chat");
		btnQuickChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("quickChatbtn");
				TaskBar.smallChatWindows.get(chatWinNum).getFrmBluetext().setVisible(true);
				frame.dispose();
			}
		});
		btnQuickChat.setBounds(0, 114, 125, 36);
		frame.getContentPane().add(btnQuickChat);
		
		JButton btnMainWindow = new JButton("BlueText");
		btnMainWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				Notification not = new Notification(new TextMessage(TaskBar.me,TaskBar.you,""));
//				System.out.println("window Number: " + windowNum);
				
//				System.out.println("mainWindowbtn");
				TaskBar.convo.getFrmBluetext().setVisible(true);
				Global.conversationPane.setSelectedIndex(chatWinNum);
				frame.dispose();
			}
		});
		btnMainWindow.setBounds(125, 114, 125, 36);
		frame.getContentPane().add(btnMainWindow);
		
		JTextPane messagerContent = new JTextPane();
		messagerContent.setEditable(false);
		messagerContent.setBounds(0, 34, 250, 82);
		frame.getContentPane().add(messagerContent);
		messagerContent.getDocument().insertString(0,"- " + messager.getContent(), null);
		
		JTextPane messagerName = new JTextPane();
		messagerName.setFont(new Font("Tahoma", Font.BOLD, 16));
		messagerName.setEditable(false);
		messagerName.setBounds(0, 0, 250, 49);
		frame.getContentPane().add(messagerName);
		messagerName.getDocument().insertString(0, messager.getSender().getFirstName() + " " + messager.getSender().getLastName() + ":", null);
		
		frame.addMouseMotionListener(new MouseMotionListener() {
			 public void mouseMoved(MouseEvent e) {
				 fade = 1.0f;
				 //frame.setOpacity(fade);
				 timer1.stop();
				 timer1.setInitialDelay(3000);
				 timer1.start();
			 }

			@Override
			public void mouseDragged(MouseEvent arg0) {
				
			}
		});
	}
	
	private void animate(){
		 timer2.addActionListener(new ActionListener() {
			 @Override
		     public void actionPerformed(ActionEvent e) {
				 
				 if ((positionY > ((gd.getDisplayMode().getHeight() - 50) - (180 * windowNum)))){
					 positionY = positionY - 5;
					 openNotifications.get(windowNum - 1).frame.setLocation(gd.getDisplayMode().getWidth() - 260, (int)positionY);
		         }
				 else if(windowNum != 0 && ((positionY + 5) < ((gd.getDisplayMode().getHeight() - 50) - (180 * windowNum)))){
					 positionY = positionY + 5;
					 openNotifications.get(windowNum - 1).frame.setLocation(gd.getDisplayMode().getWidth() - 260, (int)positionY);
				 }
				 else{
					 
				 }
		     }
		 });
		 timer2.start();
	}
}
