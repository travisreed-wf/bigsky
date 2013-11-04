package bigsky;

import java.awt.TrayIcon.MessageType;

import javax.swing.text.BadLocationException;

import bigsky.TaskBar;
import bigsky.gui.SmallChat;

public class TextMessageManager extends Thread{

	public void run(){
		boolean matchR = false;
		
		while(true){

			if(!TaskBar.myTextArray.isEmpty()){
				TaskBar.trayIcon.displayMessage("New Message", TaskBar.myTextArray.get(0).getSender().getFirstName() + " " + TaskBar.myTextArray.get(0).getSender().getLastName(), MessageType.INFO);
				matchR = false;
				for(int i=0; i < TaskBar.smallChatWindows.size(); i++){
					if(TaskBar.myTextArray.get(0).getReceiver().getPhoneNumber().equals(TaskBar.smallChatWindows.get(i).getFromContact().getPhoneNumber())){
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
					TaskBar.smallChatWindows.add(new SmallChat(TaskBar.myTextArray.get(0).getReceiver(),TaskBar.myTextArray.get(0).getSender()));
					TaskBar.myTextArray.remove(0);
				}
			}
			
		}
		
	}
	
}