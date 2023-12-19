
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * main menu that shows up after User logs in 
 * @author Emily Zhu
 * ITP 265, Fall 2019, Coffee Section
 * Final Project
 * Email: emilyzhu@usc.edu
 */
public class MainMenu {
	private Scanner sc;
	private User user;
	private Map<Party, RSVPStatus> partyStatuses;
	private ArrayList<Party> listOfParties;

	private static final String USER_FILE = "src/parties.txt";


	public MainMenu(User user) {
		sc = new Scanner(System.in);
		this.user = user;
		listOfParties = new ArrayList<Party>();
		partyStatuses = new HashMap<>();
	}

	/**
	 * main menu set up that allows user to pick a number that represents an option that they can do in the system
	 * opens the Parties file upon login and puts into ArrayList
	 * upon closure, a blank file with the same name is created. then the contents from the ArrayList that may have been edited during login
	 * are written back into the file
	 */
	public void run() {
		//assuming login from constructor

		boolean loggedOn = true;
		readExistingPartiesFromFile();
		
		while(loggedOn) {
			int input = getMenuChoice();
			switch(input) {
			case 1:
				viewHosting();
				break;
			case 2:
				partiesInvitedTo();
				break;
			case 3:
				createParty();
				break;
			case 4:
				accountType();
				break;	
			case 5:
				RSVPtoParty();
				break;
			case 6:
				premiumFeatures();
				break;
			case 7: 
				PrintWriter pw = null;
				try {
					pw = new PrintWriter("file.txt");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pw.close();
				for(Party p : listOfParties) {
					writePartyToFile();
				}
				System.out.println("You are now logged out of the Evite System.");
				loggedOn = false;
				break;
			}
		}
	}

	/**
	 * features only premium users can access. otherwise, an error message shows 
	 * currently, the user can only change invite details. many things could be done with this method in the future.
	 */
	public void premiumFeatures() {
		boolean accessPremium = false;
		if(user.getHasPremium()) {
			accessPremium = true;
			Premium premiumUser = new Premium(user.getName(), user.getEmail(), null, user.getHasPremium());
			ArrayList<Party> hosting = viewHosting();
			int choice = InputHelper.readIntBetween(sc, "\nEnter the party number that you would like to modify: ", 1, hosting.size());
			Party partyToBeChanged = listOfParties.get(choice - 1);
			while(accessPremium) {
				System.out.println("1. Change invite details \n2. Cancel Party \n3. Quit ");
				int premiumOptions = InputHelper.readIntBetween(sc, "\nEnter the number for how you would like to modify your party: ", 
						1, 3);
				switch(premiumOptions) {
				case 1:
					premiumUser.changeInviteDetails(partyToBeChanged);
					break;
				case 2:
					premiumUser.cancelParty(partyToBeChanged);
					break;
				case 3: 
					accessPremium = false;
					break;
				}
			}
		}
		else {
			System.out.println("Must have premium.");
		}
		
	}

	/**
	 * maybe have like some kind of payment option
	 * credit card (?) i think there was something like that in an earlier project like student store 
	 * or like a code that they have to put in (open a file and search for code maybe)
	 */
	private void accountType() {

		if (user.getHasPremium()) {
			System.out.println("You currently have premium!");
			user.setSubscription(InputHelper.readYesNoBoolean(sc, "Would you like to stay as a Premium member?"));	
		}
		else {
			System.out.println("You currently have the free version.");
			user.setSubscription(InputHelper.readYesNoBoolean(sc, "Would you like to upgraade to Premium?"));	
		}		
	}

	private ArrayList parseArrayListInFile(String listToParse) {
		ArrayList g = new ArrayList<>();
		Scanner sc = new Scanner(listToParse);
		sc.useDelimiter(",");
		while (sc.hasNext()) {
			g.add(sc.next());
		}
		return g;
	}

	/**
	 * Takes a line from our file and turns it into a party object
	 * @param line (where everything is split by the "/" symbol)
	 * @return a Party object
	 */
	private Party parseLineToParty(String line) {

		Party p;

		// line needs to be broken apart into tokens. (Use Scanner)
		Scanner s = new Scanner(line);
		s.useDelimiter("/"); // instead of white space, use the / as the thing that breaks the line apart

		String stringEventType = s.next();
		String eventName = s.next(); 
		String hostEmail = s.next(); 
		String stringGuestList = s.next();
		String dateString = s.next();
		String location = s.next();

		ArrayList<String> guestList = parseArrayListInFile(stringGuestList);

		//TODO: read in additional details according to party type
		if (stringEventType.equals("POTLUCK")) {
			//String stringFoodList = s.next();
			//ArrayList<String> foodList = parseArrayListInFile(stringFoodList);
			p = new Potluck(PartyType.POTLUCK, eventName, hostEmail, guestList, dateString, location);
		}
		else if (stringEventType.equals("BIRTHDAY")) {
			p = new Birthday(PartyType.BIRTHDAY, eventName, hostEmail, guestList, dateString, location);
		}
		else {
			p = new Kickback(PartyType.KICKBACK, eventName, hostEmail, guestList, dateString, location);
		}

		return p;
	}

	private void readExistingPartiesFromFile() {
		try(FileInputStream fis = new FileInputStream(USER_FILE);
				Scanner scan = new Scanner(fis))	{  
			while(scan.hasNextLine()) {
				String line = scan.nextLine(); // each line is a party in the system
				Party party = parseLineToParty(line);
				//once we have a party, put it in arraylist
				listOfParties.add(party); //add Party to the list of parties
			}

		} catch (FileNotFoundException e) {
			System.err.println("File not found exception in readExistingPartiesFromFile");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException in readExistingPartiesFromFile");
			e.printStackTrace();
		}

	}

	private void writePartyToFile() {
		PrintWriter pw = null;
		try (FileOutputStream fos = new FileOutputStream(USER_FILE)){
			pw = new PrintWriter(fos);
			// take each party in the map and write it to the file.
			for(Party p: listOfParties) {
				pw.println(p.toFileString());

			}
			pw.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // automatically close at end, which is good.


	}


	/**
	 * refer to final submission details. need to create a file for parties and this method would write to it in the same way that
	 * createNewUser did in the UserLogin class. use scanner to ask for required information to create the Party object.
	 */
	public void createParty() {
		//GET THE DATA NEEDED TO MAKE THE PARTY
		PartyType eventType = null;
		String eventTypeString = InputHelper.readString(sc, "Please enter the event type (POTLUCK, BIRTHDAY, KICKBACK): "); 
		for(PartyType p : PartyType.values()) {
			if(eventTypeString.toUpperCase().equals(p.name())) {
				eventType = p;
			}
		}

		String eventName = InputHelper.readString(sc, "Please enter the name of the event: "); 
		String hostEmail = user.getName();

		ArrayList<String> guestList = new ArrayList<String>();
		Boolean notDone = true;

		while (notDone) {
			guestList.add(InputHelper.readString(sc, "What is the name of the guest you would like to add?"));
			notDone = InputHelper.readYesNoBoolean(sc, "Would you like to add more guests?");	
		}

		String year = Integer.toString(InputHelper.readIntBetween(sc, "Enter the year: ", 2019, 2099));
		String month = Integer.toString(InputHelper.readIntBetween(sc, "Enter the month: ", 1, 12));
		String day = Integer.toString(InputHelper.readIntBetween(sc, "Enter the day: ", 1, 31));
		String dateString = year + "-" + month + "-" + day;
		String location =  InputHelper.readString(sc, "Please enter the location: ");

		//PUTTING ALL THE OBTAINED INFORMATION TOGETHER BUT ALSO ADDING MORE INFORMATION DEPENDING ON WHAT KIND OF PARTY IT IS.
		if (eventType.equals(PartyType.POTLUCK)) {
			Potluck p = new Potluck(eventType, eventName, hostEmail, guestList, dateString, location);
			listOfParties.add(p);
			/** what should have happened was that the user could create a food list upon creating the party, and then the items on the food list would be assigned to each guest, but it didn't quite work out... " :(
			ArrayList<String> foodList = new ArrayList<>();
			p = new Potluck(eventType, eventName, hostEmail, guestList, dateString, location, foodList);
			foodList = ((Potluck) p).makeFoodList();
			p = new Potluck(eventType, eventName, hostEmail, guestList, dateString, location, foodList);
			 **/
		}
		else if (eventType.equals(PartyType.BIRTHDAY)) {
			Birthday p = new Birthday(eventType, eventName, hostEmail, guestList, dateString, location);
			listOfParties.add(p);
		}
		else { //defaulting to kickback
			Kickback p = new Kickback(eventType, eventName, hostEmail, guestList, dateString, location);
			listOfParties.add(p);
		}
		;
		writePartyToFile();
	}


	/**
	 * print out a list of parties that the User is invited to
	 */
	public ArrayList<Party> partiesInvitedTo() {
		int counter = 1;
		ArrayList<Party> partyInvited = new ArrayList<>();
		System.out.println("\nParties invited to: ");
		for(Party p : listOfParties) {
			for (String guest : p.getGuestList()) {
				if(guest.trim().equals(user.getName())){
					partyInvited.add(p);
					System.out.println(counter + ". "+ p);
					counter += 1;
				}
			}

		}		
		return partyInvited;
	}

	/**
	 * allows user to pick which party they'd like to RSVP to, and once a status is set, it gets put into a map with
	 * the key being the party and the value an rsvp enum status.
	 * if the user already picked a status beforehand, it is overwritten with the new value.
	 */
	public void RSVPtoParty() {
		System.out.println("Parties RSVP'd to: ");
		for (Map.Entry entry : partyStatuses.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		ArrayList<Party> invitedTo = partiesInvitedTo();
		RSVPStatus attendance = null;
		int choice = InputHelper.readIntBetween(sc, "\nEnter the number that you would like to RSVP to", 1, invitedTo.size());
		Party partyToBeChanged = invitedTo.get(choice - 1);
		System.out.println("\n" + partyToBeChanged);
		int setStatusNum = 0;
		while(1 > setStatusNum || setStatusNum > 3 ) {
			setStatusNum = InputHelper.readIntBetween(sc, "\nEnter the number that corresponds with the status you wish to set: "
					+ "\n1. ATTENDING\n2. NOT_ATTENDING\n3. UNSURE", 1, 3);
			if(setStatusNum == 1) {
				attendance = RSVPStatus.ATTENDING;
			}
			else if(setStatusNum == 2) {
				attendance = RSVPStatus.NOT_ATTENDING;
			}
			else if(setStatusNum == 3) { 
				attendance = RSVPStatus.UNSURE;
			}
			else {
				System.out.println("Invalid input. Please try again.");
			}
		}

		if(partyStatuses.containsKey(partyToBeChanged)) {
			partyStatuses.replace(partyToBeChanged, attendance);
		}
		else {
			partyStatuses.put(partyToBeChanged, attendance);
		}
		for (Map.Entry entry : partyStatuses.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	}

	/**
	 * thinking of structure very similar to parties invited to. print out info about parties that the User is hosting
	 * print out: guestlist, who's bringing what, time, place, RSVP status for each guest
	 * return: parties that the User is hosting
	 */
	public ArrayList<Party> viewHosting() {
		int counter = 1;
		System.out.println("Parties hosting: \n");
		ArrayList<Party> partiesHosting = new ArrayList<>();
		for (Party p : listOfParties) {
			if(p.getHostName().equals(user.getName())){
				partiesHosting.add(p);
				System.out.println(counter +". " + p);
				counter += 1;
			}
		}
		return partiesHosting;
	}

	/**
	 * user enters a numerical choice that corresponds to the printed options. 
	 * @return the number that corresponds to the printed options
	 */
	public int getMenuChoice() {
		int numberChoiceChosen = 0;
		System.out.println("\nEvite Main Menu for " + user.getName());
		System.out.println("1 : Parties Hosting");
		System.out.println("2 : Parties Invited To");
		System.out.println("3 : Create Party");
		System.out.println("4 : Account Type Details");
		System.out.println("5 : RSVP to a party.");
		System.out.println("6 : Premium Features");
		System.out.println("7 : Quit");
		numberChoiceChosen = InputHelper.readIntBetween(sc, ">", 1, 7);
		return  numberChoiceChosen;

	}

	public static void main(String[] args) {
		UserLogin system = new UserLogin();
		User mem = system.signOnMenu();
		MainMenu menu = new MainMenu(mem);
		menu.run();

	}

}
