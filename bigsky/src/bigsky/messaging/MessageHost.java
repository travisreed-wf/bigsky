package bigsky.messaging;

import java.net.*;
import java.io.*;

import bigsky.BlueTextRequest;
import bigsky.BlueTextRequest.REQUEST;
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
					TaskBar.incomingContactQueue.add(ct);
					synchronized(TaskBar.textManager){
						TaskBar.textManager.notify();
					}
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
					TaskBar.responseQueue.add(response);
					synchronized(TaskBar.textManager){
						TaskBar.textManager.notify();
					}
				}
				else{
					System.out.println("ERROR: Unknown object sent through stream");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " inside ClientConn.run()");
			TaskBar.logout();
		}
	}
}

public class MessageHost extends Thread{
	
	public ClientConn conn = null;
	private ObjectOutputStream ps2 = null;
	private ServerSocket socket = null;

	
	public void run(){
		
		LoadScreen load = new LoadScreen();
		try{
			
			socket = new ServerSocket(1300);
			load.setVisible(true);
			Socket client = socket.accept();
			conn = new ClientConn(client);
			load.dispose();
			TaskBar.convo = new Conversation();
			TaskBar.convo.getFrmBluetext().setVisible(true);
			ps2 = new ObjectOutputStream(client.getOutputStream());
			
			// Send initial request for phone battery percentage
			sendObject(new BlueTextRequest(REQUEST.BATTERY_PERCENTAGE, null));
			
		} catch(Exception e){
			System.out.println("Caught exception while setting up MessageHost" + e.getMessage());
			TaskBar.logout();
		}
		finally{
			closeHost();
		}
	}
	
	public void closeHost(){
		
		if(conn != null){
			if(conn.client != null){
				try {
					conn.client.close();
				} catch (IOException e) {}
				conn.client = null;
			}
			conn = null;
		}
		if(ps2 != null){
			try {
				ps2.close();
			} catch (IOException e) {}
			ps2 = null;
		}
		if(socket !=null){
			try {
				socket.close();
			} catch (IOException e) {}
			socket = null;
		}
		TaskBar.messageHost = null;
		
		TextMessageManager.yield();
		TaskBar.textManager = null;
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