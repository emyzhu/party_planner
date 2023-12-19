
/**
 * framework for a user in the system
 * @author Emily Zhu
 * ITP 265, Fall 2019, Coffee Section
 * Final Project
 * Email: emilyzhu@usc.edu
 */
public class User {
	private String name;
	private String email;
	private String password;
	private Boolean hasPremium;


	public User(String name, String email, String password, Boolean hasPremium) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.hasPremium = hasPremium;
	}




	public boolean verifyPassword(String pword) {
		return password.equals(pword);
			
	}
	@Override
	public String toString() {
		String s=  "User " + name + ", email=" + email + "/" + hasPremium;
		return s;
	}
	
	public String toFileString() {
		String fileLine = getEmail() + "/" + getName() + "/" + password + "/" + hasPremium;
		return fileLine;
	}
	
	public boolean changePassword(String oldPassWord, String newPassWord) {
		boolean changed = false;
		if(verifyPassword(oldPassWord)) {
			this.password = newPassWord;
			changed = true;
		}
		return changed;	
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	
	/**
	 * @return the subscription
	 */
	public Boolean getHasPremium() {
		return hasPremium;
	}


	/**
	 * @param subscription the subscription to set
	 */
	public void setSubscription(Boolean hasPremium) {
		this.hasPremium = hasPremium;
	}

}
