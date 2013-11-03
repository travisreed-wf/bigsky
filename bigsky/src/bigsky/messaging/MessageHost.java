package bigsky.messaging;

import java.net.*;
import java.awt.TrayIcon.MessageType;
import java.io.*;

import bigsky.Contact;
import bigsky.Global;
import bigsky.TaskBar;
import bigsky.TextMessage;
import bigsky.gui.Conversation;
import bigsky.gui.LoadScreen;
import bigsky.gui.SmallChat;

class ClientConn implements Runnable {
	
	public boolean newMessage = false;
	
	Thread t;
	Socket client = null;

	ClientConn(Socket client) {
		this.client = client;
		t = new Thread(this);
		t.start();
	}

	public void run() {
		ObjectInputStream br = null;
		newMessage = false;
		try {
			br = new ObjectInputStream(client.getInputStream());
			while (br != null) {
				Object streamObject = br.readObject();
				if(streamObject instanceof Contact)
				{
					Contact ct = (Contact) streamObject;
					System.out.println("Got contact first=" + ct.getFirstName() + " last=" + ct.getLastName() + " number=" + ct.getPhoneNumber());				
				}
				else if(streamObject instanceof TextMessage)
				{
					TextMessage txtMessage = (TextMessage) streamObject;
					System.out.println("Client: " + txtMessage.getContent());
					newMessage = true;
					
					if(txtMessage.getSender() == null){
						txtMessage.setSender(TaskBar.you);
						txtMessage.setReceiver(TaskBar.me);
					}
					TaskBar.textHistory.add(txtMessage);
					TaskBar.trayIcon.displayMessage("New Message", "message from:\t" + txtMessage.getSender(), MessageType.INFO);
					TaskBar.smallChatWindow.recievedText(txtMessage);
				}
				else{
					System.out.println("Unknown object sent through stream");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " inside run()");
		}
	}
}

public class MessageHost extends Thread{
	
	public ClientConn conn = null;
	public static ObjectOutputStream ps2;
	
	public void run(){
		
		LoadScreen load = new LoadScreen();
		ServerSocket socket = null;
		try{
			
			socket = new ServerSocket(1300);
			System.out.print("Waiting for request from peer.....");
			load.setVisible(true);
			Socket client = socket.accept();
			conn = new ClientConn(client);
			System.out.println("request accepted!\nBeginning of chat:");
			load.dispose();
			Conversation convo = new Conversation();
			convo.getFrmBluetext().setVisible(true);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			ps2 = new ObjectOutputStream(client.getOutputStream());

			//Contact tempContact = new Contact("Andy", "G",    "+1 5072542815", null);
			//Contact tempContact = new Contact("Travis", "Reed", "+1 5633817739", null);

			while (true) {
				String servMsg = br2.readLine();
				String textFromSmallChat = new String(TaskBar.myTextHistory.get(SmallChat.getMyTextCount()).getContent());
				if(textFromSmallChat == null ||  textFromSmallChat.equalsIgnoreCase("quit")){
					return;
				}
				TextMessage textMsg = new TextMessage(TaskBar.me, TaskBar.you, textFromSmallChat);
				ps2.writeObject(textMsg);
				ps2.flush();
			}
		} catch(Exception e){
			
		}
		finally{
			System.out.println("Closing server socket.");
			if(socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					
				}

		}
	}
}