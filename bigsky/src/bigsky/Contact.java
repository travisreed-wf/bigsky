package bigsky;

import java.io.Serializable;

/**
 * A contact. Has first name, last name, and phone number(s)
 * @author Travis Reed
 *
 */
public class Contact implements Serializable {

	private static final long serialVersionUID = 1394646218153182251L;
	private String first_name;
	private String last_name;
	private String phone_number;
	private String second_phone;
	private String contactImageName;

	/**
	 * Constructor for Contact
	 * @param start_first_name - Initial First Name
	 * @param start_last_name - Initial Last Name
	 * @param start_phone_number - Initial Phone Number
	 * @param start_second_phone - Initial Secondary Phone Number
	 */
	public Contact(String start_first_name, String start_last_name, String start_phone_number, String start_second_phone) {
		if (start_first_name == null || start_first_name.equals("")){
			throw new IllegalArgumentException();
		}
		if (start_last_name == null){
			start_last_name = "";
		}
		if (start_phone_number == null){
			throw new IllegalArgumentException();
		}
		if (start_second_phone == null){
			start_second_phone = "";
		}
		first_name = start_first_name;
		last_name = start_last_name;
		phone_number = start_phone_number;
		second_phone = start_second_phone;
		contactImageName = Global.blankContactImage;
	}
	
	/**
	 * Set the first name
	 * @param name - new first name
	 */
	public void setFirstName(String name) {
		if (name.equals("") || name == null){
			throw new IllegalArgumentException();
		}
		first_name = name;
	}

	/**
	 * Set Last Name of Contact
	 * @param name - new last name
	 */
	public void setLastName(String name) {
		if (name == null){
			name = "";
		}
		last_name = name;
	}

	/**
	 * Set the primary phone number
	 * @param number - new Phone Number
	 */
	public void setPhoneNumber(String number){
		if (number.equals("") || number == null){
			throw new IllegalArgumentException();
		}
		phone_number = number;
	}

	/**
	 * Set the Seconary Phone number
	 * @param number - secondary phone number
	 */
	public void setSecondPhone(String number){
		second_phone = number;
	}
	
	/**
	 * Set contact image name
	 * @param name - the contact image name
	 */
	public void setContactImageName(String name){
		contactImageName = name;
	}

	/**
	 * Get the first name
	 * @return - Contact first name
	 */
	public String getFirstName(){
		return first_name;
	}

	/**
	 * Get the last name
	 * @return Contact last name
	 */
	public String getLastName(){
		return last_name;
	}

	/**
	 * Get the primary phone number
	 * @return - primary phone number
	 */
	public String getPhoneNumber(){
		return phone_number;
	}

	/**
	 * Get the secondary phone number
	 * @return - secondary phone number
	 */
	public String getSecondPhone(){
		return second_phone;
	}
	
	/**
	 * Get the contact image name
	 * @return - Contact image Name
	 */
	public String getContactImageName(){
		return contactImageName;
	}

	@Override
	public String toString(){
		return String.format("%-15s",this.getFirstName()) + String.format("%-15s", this.getLastName()) + this.getPhoneNumber(); 
	}
}
