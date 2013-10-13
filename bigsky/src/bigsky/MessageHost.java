package bigsky;

import java.net.*;
import java.io.*;

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
				String st1 = ((TextMessage) br.readObject()).getContent();
				System.out.println("Client: " + st1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " inside run()");
		}
	}
}

class MessageHost {
	public static void main(String args[]) throws IOException {

		ServerSocket socket = null;
		try{
			socket = new ServerSocket(1300);
			System.out.print("Waiting for request from peer.....");
			Socket client = socket.accept();
			@SuppressWarnings("unused")
			ClientConn conn = new ClientConn(client);
			System.out.println("request accepted!\nBeginning of chat:");
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			ObjectOutputStream ps2 = new ObjectOutputStream(client.getOutputStream());

			Contact tempContact = new Contact("Andy", "G",    "+1 5072542815", null);
			//Contact tempContact = new Contact("Travis", "Reed", "+1 5633817739", null);

			while (true) {
				String servMsg = br2.readLine();
				if(servMsg == null ||  servMsg.equalsIgnoreCase("quit")){
					return;
				}
				ps2.writeObject(new TextMessage(null, tempContact, servMsg));
				ps2.flush();
			}
		} 
		finally{
			System.out.println("Closing server socket.");
			if(socket != null) socket.close();

		}
	}
}