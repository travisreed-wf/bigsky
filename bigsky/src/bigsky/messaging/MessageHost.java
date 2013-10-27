package bigsky.messaging;

import java.net.*;
import java.awt.TrayIcon.MessageType;
import java.io.*;

import bigsky.TaskBar;
import bigsky.TextMessage;
import bigsky.gui.SmallChat;

class ClientConn implements Runnable {
	
	public TextMessage st1;
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
				st1 = ((TextMessage) br.readObject());
				System.out.println("Client: " + st1.getContent());
				newMessage = true;
				
				if(st1.getSender() == null){
					st1.setSender(TaskBar.you);
					st1.setReceiver(TaskBar.me);
				}
				TaskBar.textHistory.add(st1);
				TaskBar.trayIcon.displayMessage("New Message", "message from:\t" + st1.getSender(), MessageType.INFO);
				TaskBar.smallChatWindow.recievedText(st1);
         	   //TaskBar.notification.displayMessage("New Message", "NEW MESSAGE", null);
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
		
		
		ServerSocket socket = null;
		try{
			socket = new ServerSocket(1300);
			System.out.print("Waiting for request from peer.....");
			Socket client = socket.accept();
			conn = new ClientConn(client);
			System.out.println("request accepted!\nBeginning of chat:");
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