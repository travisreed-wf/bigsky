package bigsky;

import java.awt.TrayIcon.MessageType;

import javax.swing.text.BadLocationException;

import bigsky.TaskBar;
import bigsky.gui.SmallChat;

public class TextMessageManager extends Thread{

	public void run(){
		boolean matchR = false;
		


		try {
			synchronized(this){
				while(true){
					System.out.println("wait hit");
					this.wait();
					System.out.println("RESUMED");
					
					
					if(!TaskBar.myTextArray.isEmpty()){
						TaskBar.trayIcon.displayMessage("New Message", TaskBar.myTextArray.get(0).getSender().getFirstName() + " " + TaskBar.myTextArray.get(0).getSender().getLastName(), MessageType.INFO);
						matchR = false;
						for(int i=0; i < TaskBar.smallChatWindows.size(); i++){
							if(TaskBar.myTextArray.get(0).getSender().getPhoneNumber().equals(TaskBar.smallChatWindows.get(i).getFromContact().getPhoneNumber())){
								try {
									TaskBar.smallChatWindows.get(i).receivedText(TaskBar.myTextArray.get(0));
								} catch (BadLocationException e) {
									e.printStackTrace();
								}
								TaskBar.myTextArray.remove(0);
								matchR = true;
								break;
							}
						}
						if(!matchR){
							TaskBar.smallChatWindows.add(new SmallChat(TaskBar.myTextArray.get(0).getReceiver(), TaskBar.myTextArray.get(0).getSender()));
							try {
								TaskBar.smallChatWindows.get(TaskBar.smallChatWindows.size()-1).receivedText(TaskBar.myTextArray.get(0));
								System.out.println("small chat window created!");
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
							TaskBar.myTextArray.remove(0);
						}
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}		

	
}