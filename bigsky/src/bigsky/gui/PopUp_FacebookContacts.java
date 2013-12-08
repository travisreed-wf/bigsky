package bigsky.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;

import bigsky.Global;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class PopUp_FacebookContacts extends JFrame{
	private static final long serialVersionUID = -5289641047430720306L;
	private JTextField textField;
	private JFrame thisFrame;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PopUp_FacebookContacts();
	}
	
	public PopUp_FacebookContacts(){
		thisFrame = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/bigsky/BlueText.gif")));
		setResizable(false);
		setSize(new Dimension(481, 346));
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextPane txtStep1 = new JTextPane();
		txtStep1.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 16));
		txtStep1.setText(" 1 :   Click button to open web browser");
		txtStep1.setBounds(62, 22, 301, 38);
		getContentPane().add(txtStep1);
		
		JButton btnOpenBrowser = new JButton("Open Browser");
		btnOpenBrowser.setBounds(154, 71, 121, 23);
		getContentPane().add(btnOpenBrowser);
		btnOpenBrowser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://developers.facebook.com/tools/explorer?method=GET&path=me%2Ffriends"));
				} catch (Exception e1) {
					System.out.println("Unable to open web page, please go to this website: https://developers.facebook.com/tools/explorer?method=GET&path=me%2Ffriends");
				}
			}
		});
				
		JTextPane txtpnClick = new JTextPane();
		txtpnClick.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 16));
		txtpnClick.setText(" 2 :   Click \"Get Access Token\" \r\n\r\n 3 :   Select \"user_friends\".\r\n\r\n 4 :   Paste Access Token below:");
		txtpnClick.setBounds(62, 118, 301, 106);
		getContentPane().add(txtpnClick);
		
		textField = new JTextField();
		textField.setBounds(62, 235, 301, 29);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 286, 89, 23);
		getContentPane().add(btnCancel);
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.setVisible(false);
				thisFrame.dispose();
			}
		});
		
		JButton btnDone = new JButton("Done");
		btnDone.setBounds(369, 286, 89, 23);
		getContentPane().add(btnDone);
		btnDone.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String accessToken = textField.getText();
				if(accessToken == null || accessToken.length() < 10){
					System.out.println("Please paste access token above.");
				}
				Global.ACCESS_TOKEN = accessToken;
				thisFrame.setVisible(false);
				setFacebookContacts(Global.ACCESS_TOKEN);
			}			
		});		
		
		setVisible(true);
	}
	
	private void setFacebookContacts(String accessString)
	{
		String urlString = "https://graph.facebook.com/me/friends?access_token=" + accessString;
		
		try{
			URL url = new URL(urlString);
	        URLConnection cnt = url.openConnection();
	        BufferedReader br = new BufferedReader(new InputStreamReader(cnt.getInputStream()));
	        String content = br.readLine();
	        br.close();
	        content = content.replace("{", "")
	        		.replace("\"name\"", "")
		            .replace("\"id\"", "")
		            .replace("},", "")
		            .replace("}", "")
		            .replace("]", "")
	        		.replace(".", " ")
	        		.replace(" ",".")
		            .replace("\"", "")
		            .replace(",:", ":");
	        
	        String[] tokens = content.split("[:\\,]");        
	        Properties prop = new Properties();
	        prop.load(new FileInputStream(Global.username + ".properties"));
	        
	        int i;
	        for(i = 2; i < tokens.length-4; i +=2){
	        	if(prop.getProperty(tokens[i]) == null){
	        		//System.out.println("name is: " + String.format("%-30s", tokens[i]) + " id is: " + tokens[i+1]);
	        		prop.setProperty(tokens[i].toLowerCase(), tokens[i+1]);
	        	}
	        }            	       
	        
	        prop.store(new FileOutputStream(Global.username + ".properties"), null);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
