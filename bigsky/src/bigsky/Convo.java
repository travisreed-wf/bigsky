package bigsky;

public class Convo {
	private Contact target;
	private String phone_number;
	private TextMessage[] text_messages;
	
	public Convo(Contact start_target, String start_phone_number, TextMessage[] start_text_messages){
		target = start_target;
		phone_number = start_phone_number;
		text_messages = start_text_messages;
	}
	
	public void setTarget(Contact tar){
		target = tar;
	}
	
	public void setPhoneNumber(String num){
		phone_number = num;
	}
	
	public TextMessage[] getTextMessages(){
		return text_messages;
	}
	
	public Contact getTarget(){
		return target;
	}
	
	public String getPhoneNumber(){
		return phone_number;
	}
}
