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
	
	public void close(){
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void run() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			while (true) {
				String st1 = br.readLine();
				System.out.println("Client: " + st1);
				if(st1 == null || st1.trim().length() == 0 || st1.equalsIgnoreCase("quit")){
					br.close();
					client.getInputStream().close();
					return;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage() + "inside run()");
		} finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(client != null){
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
			ClientConn conn = new ClientConn(client);
			System.out.println("request accepted\n");
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			PrintStream ps2 = new PrintStream(client.getOutputStream());
			
			while (true) {
				String servMsg = br2.readLine();
				ps2.println(servMsg);
				if(servMsg == null || servMsg.trim().length() == 0 || servMsg.equalsIgnoreCase("quit")){
					br2.close();
					ps2.close();
					conn.close();
					return;
				}
			}
		} 
		finally{
			System.out.println("Closing server socket.");
			if(socket != null) socket.close();
			
		}
	}
}
