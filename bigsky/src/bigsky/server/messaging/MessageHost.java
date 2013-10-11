package bigsky.server.messaging;

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
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while (br != null) {
				String st1 = br.readLine();
				System.out.println("Client: " + st1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " inside run()");
		}
	}
}

/**
 * Deprecating MessageHost because we are going to try and make a direct
 * connection between phone and PC instead
 * @author Andrew
 */
@Deprecated
class MessageHost {
	public static void main(String args[]) throws IOException {
		
		ServerSocket socket = null;
		try{
			socket = new ServerSocket(1300);
			System.out.print("Waiting for request from peer.....");
			Socket client = socket.accept();
			@SuppressWarnings("unused")
			ClientConn conn = new ClientConn(client);
			System.out.println("request accepted!\n");
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			PrintStream ps2 = new PrintStream(client.getOutputStream());
			
			while (true) {
				String servMsg = br2.readLine();
				ps2.println(servMsg);
			}
		} 
		finally{
			System.out.println("Closing server socket.");
			if(socket != null) socket.close();
			
		}
	}
}
