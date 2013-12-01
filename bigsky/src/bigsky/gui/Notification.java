package bigsky.gui;

import java.awt.Color;
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






import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class Notification {

	private JDialog frame;
	private TextMessage messager;
	private int winNum;
	private float fade = 1.0F;
	private final Timer timer = new Timer(50, null);

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
		winNum = 0;
		
		try {
			initialize();
		} catch (BadLocationException e) {
			System.out.println("ERROR in Notification Initialize()");
			e.printStackTrace();
		}
		
		frame.setVisible(true);
		
		
		 float i = 0.0F;
		 timer.addActionListener(new ActionListener() {
			 @Override
		     public void actionPerformed(ActionEvent e) {
		        	
				 if (fade < 0.0126F){
					 timer.stop();
					 frame.dispose();
		         }
		         fade = fade - 0.0125F;
		         frame.setOpacity(fade);
		     }
		 });
		 timer.setInitialDelay(3000);
		 timer.start();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws BadLocationException 
	 */
	private void initialize() throws BadLocationException {
		for(int i=0; i < TaskBar.smallChatWindows.size(); i++){
			if(TaskBar.myTextArray.get(0).getSender().getPhoneNumber().equals(TaskBar.smallChatWindows.get(i).getFromContact().getPhoneNumber())){
				winNum = i;
			}
		}
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		frame = new JDialog();
		frame.setName("New Message");
		frame.setResizable(false);
		frame.setBounds(gd.getDisplayMode().getWidth() - 260, gd.getDisplayMode().getHeight() - 220, 257, 178);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
		frame.setTitle("New Message");
		
		JButton btnQuickChat = new JButton("Quick Chat");
		btnQuickChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("quickChatbtn");
				TaskBar.smallChatWindows.get(winNum).getFrmBluetext().setVisible(true);
				frame.dispose();
			}
		});
		btnQuickChat.setBounds(0, 114, 125, 36);
		frame.getContentPane().add(btnQuickChat);
		
		JButton btnMainWindow = new JButton("BlueText");
		btnMainWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("mainWindowbtn");
				TaskBar.convo.getFrmBluetext().setVisible(true);
				Global.conversationPane.setSelectedIndex(winNum);
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
				 frame.setOpacity(fade);
				 timer.stop();
				 timer.setInitialDelay(3000);
				 timer.start();
			 }

			@Override
			public void mouseDragged(MouseEvent arg0) {
				
			}
		});
	}
}
