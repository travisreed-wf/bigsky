package bigsky;

import java.net.*;
import java.io.*;

class ServerConn implements Runnable {
	Thread t;
	Socket client;

	ServerConn(Socket client) {
		this.client = client;
		t = new Thread(this);
		t.start();

	}

	public void run() {
		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while (true) {
				String st1 = br.readLine();
				if(st1 == null || st1.trim().length() == 0 ||  st1.equalsIgnoreCase("quit")){
					br.close();
					client.getInputStream().close();
					client.close();
					return;
				}
				System.out.println(" " + st1);
			}

		} catch (IOException e) {
			System.out.println(e);

		}
	}
}

class MessageClient {
	public static void main(String args[]) throws IOException {
		try {
			System.out.println("sending request to peer....");
			//TODO for this to work you need to hardcode the IP of the server
			// Currently set to hit Andy's PC
			Socket client = new Socket("206.127.186.9", 1300);
			System.out.println("successfully conneted");
			@SuppressWarnings("unused")
			ServerConn conn = new ServerConn(client);

			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
			PrintStream ps = new PrintStream(client.getOutputStream());
			while (true) {
				String s = br1.readLine();
				ps.println(s);
				if(s == null || s.trim().length() == 0 ||  s.equalsIgnoreCase("quit")){
					br1.close();
					ps.close();
					client.close();
					return;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
