
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * framework for all the different types of parties
 * @author Emily Zhu
 * ITP 265, Fall 2019, Coffee Section
 * Final Project
 * Email: emilyzhu@usc.edu
 */
public abstract class Party {
	

	private PartyType partyType;
	private String eventName;
	private String hostName;
	private ArrayList<String> guestList;
	private String dateString;
	private String location;
	
	private static final String USER_FILE = "src/parties.txt";


	public Party(PartyType partyType, String eventName, String hostName, ArrayList<String> guestList,
			String dateString, String location) {
		this.partyType = partyType;
		this.eventName = eventName;
		this.hostName = hostName;
		this.guestList = guestList;
		this.dateString = dateString;
		this.location = location;
	}

	@Override
	public String toString() {
		return "Party [eventName=" + eventName + ", hostName=" + hostName + ", guestList=" + guestList
				+ ", dateString=" + dateString + ", location=" + location + ", partyType=" + partyType + "]";
	}


	/**
	 * @return the partyType
	 */
	public PartyType getPartyType() {
		return partyType;
	}


	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(PartyType partyType) {
		this.partyType = partyType;
	}


	/**
	 * @return the eventName
	 */
	public String getEventName() {
		return eventName;
	}


	/**
	 * @param eventName the eventName to set
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	/**
	 * @return the hostEmail
	 */
	public String getHostName() {
		return hostName;
	}


	/**
	 * @param hostEmail the hostEmail to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	/**
	 * @return the guestList
	 */
	public ArrayList<String> getGuestList() {
		return guestList;
	}


	/**
	 * @param guestList the guestList to set
	 */
	public void setGuestList(ArrayList<String> guestList) {
		this.guestList = guestList;
	}


	/**
	 * @return the dateString
	 */
	public String getDateString() {
		return dateString;
	}


	/**
	 * @param dateString the dateString to set
	 */
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	


	/**
	 * @return string representation of a party to be stored in parties file
	 */
	public String toFileString() {
		String fileLine = getPartyType() + "/" + getEventName() + "/" + getHostName() + "/" + guestListSting() + "/" + getDateString()
		+ "/" + getLocation();
		return fileLine;
	}

	/**
	 * @return string represnetation of guestList, so no brackets will appear when writing the guestList to file
	 */
	private String guestListSting() {
		String guestListString = String.join(", ", guestList);
		return guestListString;
	}

	
}
