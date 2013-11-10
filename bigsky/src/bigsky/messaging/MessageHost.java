package bigsky.messaging;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import bigsky.BlueTextRequest;
import bigsky.BlueTextResponse;
import bigsky.Contact;
import bigsky.TaskBar;
import bigsky.TextMessage;
import bigsky.gui.Conversation;
import bigsky.gui.LoadScreen;

class ClientConn implements Runnable {
	
	
	Thread t;
	Socket client = null;

	ClientConn(Socket client) {
		this.client = client;
		t = new Thread(this);
		t.start();
	}

	public void run() {
		Contact user = new Contact("Jonathan", "Mielke", "6185204620", "");
		ObjectInputStream br = null;
		try {
			br = new ObjectInputStream(client.getInputStream());
			while (br != null) {
				Object streamObject = br.readObject();
				if(streamObject instanceof Contact)
				{
					Contact ct = (Contact) streamObject;
					// TODO Travis will add code here to update listmodel
				}
				else if(streamObject instanceof TextMessage)
				{
					TextMessage txtMessage = (TextMessage) streamObject;
					txtMessage.setReceiver(user);
					System.out.println("Client: " + txtMessage.getContent());					
					TaskBar.myTextArray.add(txtMessage);					
					synchronized(TaskBar.textManager){
						TaskBar.textManager.notify();
					}
				}
				else if(streamObject instanceof BlueTextResponse)
				{
					BlueTextResponse response = (BlueTextResponse) streamObject;
					ArrayList<TextMessage> history = response.getChatHistory();
					System.out.println("Got " + history.size() + " messages from " + response.getOriginalRequest().getContact().getPhoneNumber());
				}
				else{
					System.out.println("Unknown object sent through stream");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " inside ClientConn.run()");
		}
	}
}

public class MessageHost extends Thread{
	
	public ClientConn conn = null;
	private ObjectOutputStream ps2 = null;
	
	public void run(){
		
		LoadScreen load = new LoadScreen();
		ServerSocket socket = null;
		try{
			
			socket = new ServerSocket(1300);
			load.setVisible(true);
			Socket client = socket.accept();
			conn = new ClientConn(client);
			load.dispose();
			Conversation convo = new Conversation();
			convo.getFrmBluetext().setVisible(true);
			ps2 = new ObjectOutputStream(client.getOutputStream());
			
			// This is an example usage of sending a BlueTextRequest
			// This request asks for the chat history of Andy Guibert and
			// will return a BlueTextResponse in the stream in about 500ms 
			// sendObject(new BlueTextRequest(BlueTextRequest.REQUEST.CONTACT_CHAT_HISTORY, new Contact("Andy", "Guibert", "15072542815", null)));
		} catch(Exception e){
			System.out.println("Caught exception while setting up MessageHost");
		}
		finally{
			if(socket != null)
			{
				try {
					socket.close();
				} catch (IOException e) {}
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