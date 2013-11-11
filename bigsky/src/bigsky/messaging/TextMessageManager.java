package bigsky.messaging;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;

import javax.swing.text.BadLocationException;

import bigsky.Contact;
import bigsky.Global;
import bigsky.TaskBar;
import bigsky.gui.Conversation;
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
									Conversation.updateConv(TaskBar.myTextArray.get(0));
									TaskBar.smallChatWindows.get(i).receivedText(TaskBar.myTextArray.get(0));
								} catch (BadLocationException e) {
									e.printStackTrace();
									System.out.println("Updating a small chat conversation -FAILED");
								}
								TaskBar.myTextArray.remove(0);
								matchR = true;
								break;
							}
						}
						if(!matchR){
							TaskBar.smallChatWindows.add(new SmallChat(TaskBar.myTextArray.get(0).getReceiver(), TaskBar.myTextArray.get(0).getSender()));
							try {
								Conversation.updateConv(TaskBar.myTextArray.get(0));
								TaskBar.smallChatWindows.get(TaskBar.smallChatWindows.size()-1).receivedText(TaskBar.myTextArray.get(0));
								System.out.println("small chat window created!");
							} catch (BadLocationException e) {
								e.printStackTrace();
								System.out.println("Small chat window creation -FAILED");
							}
							TaskBar.myTextArray.remove(0);
						}
					}
					
					// Handle incoming Contacts
					while(!TaskBar.incomingContactQueue.isEmpty()){
						Contact ct = TaskBar.incomingContactQueue.remove();
						addContactToListModel(ct.getFirstName(), ct.getLastName());
						Global.contactAList.add(ct);
						ArrayList<Contact> contactList = Global.contactAList;
						System.out.println(ct.getFirstName());
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Synchronized method block -FAILED");
		}
	
	}		
	private void addContactToListModel(String firstName, String lastName){
		if (!firstName.equals("")){
			String newEntry = firstName + " " + lastName;
			Global.listModel.addElement(newEntry);
		}
		else if (lastName.equals("")){
			String newEntry = lastName;
			Global.listModel.addElement(newEntry);
		}
	}

	
}