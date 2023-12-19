import java.util.ArrayList;
import java.util.Scanner;

/**
 * represents the things that a Premium user can do that a regular user cannot do - only those with a paid plan can access these features
 * only implementing for one type of paid subscription!
 * do a payment option in the future like asking for credit card info
 * @author Emily Zhu
 * ITP 265, Fall 2019, Coffee Section
 * Final Project
 * Email: emilyzhu@usc.edu
 */
public class Premium extends User implements PaidMembership{
	
	private static final String USER_FILE = "src/parties.txt";
	private boolean hasPremium;

	/**
	 * @param name
	 * @param email
	 * @param password
	 * @param hasPremium
	 */
	public Premium(String name, String email, String password, Boolean hasPremium) {
		super(name, email, password, hasPremium);
		this.hasPremium = true;
	}

	/* (non-Javadoc)
	 * @see itp265_FinalProject_emilyzhu.PaidMembership#changeInviteDetails()
	 */
	@Override
	public void changeInviteDetails(Party party) {
		Scanner sc = new Scanner(System.in);
		int input = InputHelper.readIntBetween(sc, "Select party detail you wish to change: \n"
				+ "1. Party Type\n2. Event Name\n3. Guest List\n4. Date\n5. Location ", 0, 7);
		switch(input) {
		case 1:
			PartyType typeOfParty = party.getPartyType();
			int typeOfPartyChoice = InputHelper.readIntBetween(sc, "Select the Party Type you would like to change to: \n"
					+ "1. POTLUCK\n2. BIRTHDAY\n 3.KICKBACK", 0, 4);
			if(typeOfPartyChoice == 1) {
				typeOfParty = PartyType.POTLUCK;
			}
			if(typeOfPartyChoice == 2) {
				typeOfParty = PartyType.BIRTHDAY;
			}
			if(typeOfPartyChoice == 3) {
				typeOfParty = PartyType.KICKBACK;
			}
			party.setPartyType(typeOfParty);
			System.out.println("Party type changed to: " + typeOfParty);
			break;
		case 2:
			String partyName = InputHelper.readString(sc, "Type in the name  you wish to change the name of the party to: ");
			party.setEventName(partyName);
			System.out.println("The name of the party is now: " + party.getEventName());
			break;
		case 3: // pro version may allow for the user to select which guest they wish to delete and add onto the list, rather than just rewrite
			ArrayList<String> listOfGuests = new ArrayList<>();
			System.out.println("Please note that you have to rewrite the name of every guest at this time.");
			Boolean notDone = true;
			while (notDone) {
				listOfGuests.add(InputHelper.readString(sc, "What is the name of the guest you would like to invite?"));
				notDone = InputHelper.readYesNoBoolean(sc, "Would you like to add more guests?");	
			}
			party.setGuestList(listOfGuests);
			System.out.println("Guest list is now: \n" + party.getGuestList());
			break;
		case 4:
			String year = Integer.toString(InputHelper.readIntBetween(sc, "Enter the year: ", 2018, 3000));
			String month = Integer.toString(InputHelper.readIntBetween(sc, "Enter the month: ", 0, 13));
			String day = Integer.toString(InputHelper.readIntBetween(sc, "Enter the day: ", 0, 32));
			String dateString = year + "-" + month + "-" + day;
			party.setDateString(dateString);
			System.out.println("The party's date is now: \n" + dateString);
			break;	
		case 5:
			String loc = InputHelper.readString(sc, "Type in the new place you wish to hold the party: ");
			party.setLocation(loc);
			System.out.println("The party's location is now: \n" + party.getLocation());
			break;
		}
	}

	/* (non-Javadoc)
	 * @see itp265_FinalProject_emilyzhu.PaidMembership#cancelParty()
	 */
	public void cancelParty(Party party) {
		System.out.println("Not yet done. for future");
		
	}


}
