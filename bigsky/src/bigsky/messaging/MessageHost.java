package bigsky.messaging;

import java.net.*;
import java.io.*;

import bigsky.Contact;
import bigsky.TaskBar;
import bigsky.TextMessage;
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
					System.out.println("Client: " + txtMessage.getContent());
					
					if(txtMessage.getSender() == null){
						txtMessage.setSender(TaskBar.you);
						txtMessage.setReceiver(TaskBar.me);
					}
					
					TaskBar.myTextArray.add(txtMessage);
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
	public ObjectOutputStream ps2;
	
	public void run(){
		
		
		ServerSocket socket = null;
		try{
			socket = new ServerSocket(1300);
			System.out.print("Waiting for request from peer.....");
			Socket client = socket.accept();
			conn = new ClientConn(client);
			System.out.println("request accepted!\nBeginning of chat:");
			ps2 = new ObjectOutputStream(client.getOutputStream());

			while (true) {
				
//				String textFromSmallChat = new String();
//				if(textFromSmallChat == null ||  textFromSmallChat.equalsIgnoreCase("quit")){
//					return;
//				}
//				TextMessage textMsg = new TextMessage(TaskBar.me, TaskBar.you, textFromSmallChat);
//				ps2.writeObject(textMsg);
//				ps2.flush();
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