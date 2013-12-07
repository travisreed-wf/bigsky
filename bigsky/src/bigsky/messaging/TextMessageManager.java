package bigsky.messaging;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.BadLocationException;

import bigsky.BlueTextRequest;
import bigsky.BlueTextRequest.REQUEST;
import bigsky.BlueTextResponse;
import bigsky.Contact;
import bigsky.Global;
import bigsky.TaskBar;
import bigsky.gui.Conversation;
import bigsky.gui.Notification;
import bigsky.gui.SmallChat;

public class TextMessageManager extends Thread
{
	public static boolean sendTexts = true;
	private static Contact blueTextRqContact;
	public void run()
	{
		boolean matchR = false;
		String phoneHLine;
		
		try {
			synchronized(this){
				while(true){
					this.wait();	
					
					// Handle response objects
					processResponseQueue();
					
					//Adding chat history from phone
                    if(!Global.phoneTextHistory.isEmpty()){
                    	int smallChatNum = 0;
                        int size = Global.phoneTextHistory.size();
                        for(int i=0; i < TaskBar.smallChatWindows.size(); i++){
                           	if(blueTextRqContact.getPhoneNumber().equals(TaskBar.smallChatWindows.get(i).getFromContact().getPhoneNumber())){
                           		smallChatNum = i;
                           		break;
                            }
                        }
                        if(smallChatNum == 0){
                        	TaskBar.smallChatWindows.add(new SmallChat(new Contact("Jonathan", "Mielke", "6185204620", ""), blueTextRqContact));
                           	smallChatNum = TaskBar.smallChatWindows.size() - 1;
                        }
                            
                        sendTexts = false;
                                                                            
                        for(int i = 0; i < size;i++){
                        	phoneHLine = Global.phoneTextHistory.get(Global.phoneTextHistory.size()-1).getSender().getFirstName() + ":  " + Global.phoneTextHistory.get(Global.phoneTextHistory.size()-1).getContent();
                            try {
                            	Conversation.updateConv(Global.phoneTextHistory.get(Global.phoneTextHistory.size()-1));
                            	TaskBar.smallChatWindows.get(smallChatNum).receivedText(Global.phoneTextHistory.get(Global.phoneTextHistory.size()-1));
                            } catch (BadLocationException e) {
                            	e.printStackTrace();
                                System.out.println("Updating in chat history -FAILED");
                            }
                                    
                            Global.phoneTextHistory.remove(Global.phoneTextHistory.size()-1);
                        } 
                        sendTexts = true;
                    }
					
					
					// Handle incoming text messages
                    if(!TaskBar.myTextArray.isEmpty()){
                    	System.out.println("hit manager sending");
						if(TaskBar.savedInfo(Global.NOTIFICATION, Global.ON)){
							Notification notify = new Notification(TaskBar.myTextArray.get(0));
						}
							//TaskBar.trayIcon.displayMessage("New Message", TaskBar.myTextArray.get(0).getSender().getFirstName() + " " + TaskBar.myTextArray.get(0).getSender().getLastName(), MessageType.INFO);
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
                        	Global.historyGatherText.add(TaskBar.myTextArray.get(0));
                        	BlueTextRequest rq = new BlueTextRequest(BlueTextRequest.REQUEST.CONTACT_CHAT_HISTORY, TaskBar.myTextArray.get(0).getSender());
                        	TaskBar.myTextArray.remove(0);
                        	TaskBar.messageHost.sendObject(rq);
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
	
	private void processResponseQueue()
	{
		while(!TaskBar.responseQueue.isEmpty())
		{
			BlueTextResponse resp = TaskBar.responseQueue.remove();
			REQUEST req = resp.getOriginalRequest().getRequest();
			
			if(REQUEST.BATTERY_PERCENTAGE == req)
			{
				System.out.println("Updating battery percentage to: " + resp.getBatteryLevel());
				Conversation.updateBatteryIndicator(resp.getBatteryLevel());
			}
			else if(REQUEST.CONTACT_CHAT_HISTORY == req){
				Global.phoneTextHistory = resp.getChatHistory();
				if(!Global.historyGatherText.isEmpty()){
					Global.phoneTextHistory.add(Global.historyGatherText.get(0));
					Global.historyGatherText.remove(0);
				}
				 blueTextRqContact = resp.getOriginalRequest().getContact();
			}
			else if(REQUEST.CONTACT_PICTURE == req){
				
				Object imageResource = resp.getImageResource();
				ImageIcon img = null;
				if(imageResource instanceof String && ((String)imageResource).equalsIgnoreCase("NO_IMG")){
					// TRAVIS TODO set a default picture
					System.out.println("received NO_IMG from phone, will use default picture soon.");
				}
				else if(imageResource instanceof String){
					// Get facebook username from properties file
					try{
						Properties prop = new Properties();
						prop.load(new FileInputStream("config.properties"));
						
						String facebookName = (String)prop.get(resp.getOriginalRequest().getContact().getPhoneNumber());
						
						URL url = new URL("http://graph.facebook.com/" 
								+ facebookName 
								+ "/picture?type=square");
			            img = new ImageIcon(ImageIO.read(url));
					}catch(Exception e){
						// If there was any error at all trying to get the facebook picture, then use default image
		            	System.out.println("got exception getting facebook picture" + e.getMessage() + "\n Will be using default picture...");
		            	img = null;
		            }					
				}
				else if(imageResource instanceof byte[]){
					// If a byte[] was returned by the phone, then the user
					// actually has a contact picture
					img = new ImageIcon((byte[]) imageResource);					
				}				
				
				// TRAVIS TODO set picture to some element of the conversation window
		        JFrame frame = new JFrame();
		        frame.setSize(300, 300);
		        JLabel label = new JLabel(img);
		        frame.add(label);
		        frame.setVisible(true);
			}
			else{
				System.out.println("WARNING: an unknown response was received from the phone.");
			}
		}
	}	
}
