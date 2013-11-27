package bigsky.messaging;

import java.awt.TrayIcon.MessageType;
import java.util.Arrays;
import javax.swing.text.BadLocationException;
import bigsky.Contact;
import bigsky.Global;
import bigsky.TaskBar;
import bigsky.gui.Conversation;
import bigsky.gui.SmallChat;

public class TextMessageManager extends Thread
{
	public void run()
	{
		boolean matchR = false;

		try {
			synchronized(this){
				while(true){
					this.wait();					
					
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
						if (ct.getLastName() == null){
							ct.setLastName("");
						}
						String first_name = ct.getFirstName();
						String perm_last = ct.getLastName();
						Boolean isUnique = true;
						//This loop is designed to remove duplicated names
						int j = 1;
						for (int i = 0; i<Global.contactAList.size();i++){
							Contact con = Global.contactAList.get(i);
							if (con.getFirstName().equals(first_name)){
								if (con.getLastName().equals(ct.getLastName())){
									//If the name already exists and the phone number is identical don't add it
									if (con.getPhoneNumber().equals(ct.getPhoneNumber())){
										isUnique = false;
										break;
									}
									//If the name already exists and there is room to add the second phone to the contact do it.
									else if (con.getSecondPhone().equals("")){
										con.setSecondPhone(ct.getPhoneNumber());
										isUnique = false;
										break;
									}
									String last_name = perm_last + " (" + Integer.toString(j) + ")";
									j++;
									ct.setLastName(last_name);
									i = 0; //set i back to 0 in case there is someone who already uses (1) we may need to use (2)
								}
							}
						}
						if (isUnique){
							addContactToListModel(ct.getFirstName(), ct.getLastName());
							Global.contactAList.add(ct);
						}
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
