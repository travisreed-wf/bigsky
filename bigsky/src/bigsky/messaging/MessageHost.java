package bigsky.messaging;

import java.net.*;
import java.io.*;

import bigsky.Contact;
import bigsky.TaskBar;
import bigsky.TextMessage;
import bigsky.TextMessageManager;
import bigsky.gui.Conversation;
import bigsky.gui.LoadScreen;
import bigsky.gui.SmallChat;

class ClientConn implements Runnable {
	
	
	Thread t;
	Socket client = null;

	ClientConn(Socket client) {
		this.client = client;
		t = new Thread(this);
		t.start();
	}

	public void run() {
		ObjectInputStream br = null;
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
					txtMessage.setReceiver(new Contact("Jonathan", "Mielke", "6185204620", ""));
					System.out.println("Client: " + txtMessage.getContent());

					System.out.println("TEXT ADDED TO ARRAY");
					
					TaskBar.myTextArray.add(txtMessage);
					
					synchronized(TaskBar.textManager){
						TaskBar.textManager.notify();
					}
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
	public ObjectOutputStream ps2 = null;
	
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
			ps2 = new ObjectOutputStream(client.getOutputStream());

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
	
	public synchronized void sendObject(Object o)
	{
		try{
			ps2.writeObject(o);
			ps2.flush();
		} catch(Exception e){
			System.out.println("Got exception in MessageHost.sendObject(): " + e.getMessage());
		}
	}
}