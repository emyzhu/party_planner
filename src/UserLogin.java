import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * import file containing information on guests attending a party
 * @author Emily Zhu
 * ITP 265, Fall 2019, Coffee Section
 * Final Project
 * Email: emilyzhu@usc.edu
 */

public class UserLogin {
	private Map<String, User> users; // map of unique email address to user objects
	private static final String USER_FILE = "src/users.txt";
	private Scanner sc;

	public UserLogin() {
		users = new HashMap<>();
		readExistingUsersFromFile();
		sc = new Scanner(System.in);
	}

	/**
	 * Given an email, finds and returns the User associated with the email. IF user does not exist, this 
	 * method will return NULL
	 * @param email
	 * @return
	 */
	public User login(String email) {
		User u  = users.get(email);
		return u;
	}

	/**
	 * gets email from the user only; this was done in class
	 * @return
	 */
	public User createNewUser() {
		//TODO: a real system would verify email address has @ and something.something
		String email = InputHelper.readString(sc, "Please enter your email address: "); 
		return createNewUser(email);
	}



	/**
	 * creates a User object from teh information given by the User
	 * @param email obtained form createNewUser()
	 * @return a User object that represents person who just made an account
	 */
	public User createNewUser(String email) {

		// if the user already exists, don't want to create one
		if(users.containsKey(email)) {
			System.err.println("Can't create a user with this email, because they already exist in system");
			return null;
		}

		//GET THE DATA NEEDED TO MAKE THE USER
		String name = InputHelper.readString(sc, "Please enter your full Name : "); 
		String password = InputHelper.readString(sc, "Please enter your password: "); 
		//String password2 = InputHelper.readString(sc, "Please verify your password: "); 
		Boolean hasPremium = InputHelper.readYesNoBoolean(sc, "Would you like a premium subscription?");

		User u =  new User(name, email, password, hasPremium);

		/**
		String question = "";
		String answer = "";
		if(InputHelper.readYesNoBoolean(sc, "Would you like to enter a security question? (y/n)" )) {
			question = InputHelper.readString(sc, "Please enter your question: "); 
			answer = InputHelper.readString(sc, "Please enter the answer to: " + question);
		}

		User u =  new User(name, email, password, question, answer);
		 **/
		users.put(email, u);// add the user to the map
		writeUsersToFile();
		return u;
	}

	/**
	 * reads in from the users file and parses each line to obtain relevant information used when the user logs in and afterwards
	 */
	private void readExistingUsersFromFile() {
		try(FileInputStream fis = new FileInputStream(USER_FILE);
				Scanner scan = new Scanner(fis))	{  
			while(scan.hasNextLine()) {
				String line = scan.nextLine(); // each line is a user in the system
				User user = parseLineToUser(line);
				//once we have a user, put it in map
				users.put(user.getEmail(), user); //unique email maps to ONE user in the system
			}

		} catch (FileNotFoundException e) {
			System.err.println("File not found exception in readExistingUsersFromFile");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IOException in readExistingUsersFromFile");
			e.printStackTrace();
		}

	}
	/**
	 * Takes a line from our file and turns it into a user object
	 * @param line (where everything is split by the "/" symbol)
	 * @return a User object
	 */
	private User parseLineToUser(String line) {

		User u;

		// line needs to be broken apart into tokens. (Use Scanner)
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter("/"); // instead of white space, use the / as the thing that breaks the line apart
		// good lines will either have 3 tokens or 5 tokens.

		String email = lineReader.next(); // email is always first in the line from the file
		String name = lineReader.next(); // name is always second in the line from the file
		String password = lineReader.next(); // Password is always third in the line from the file
		Boolean hasPremium = lineReader.nextBoolean(); //subscription type is always fourth in the line from the file

		u = new User(name, email, password, hasPremium);
		/**
		// is there additional data to be read in?
		if(lineReader.hasNext()) {
			// then I need 2 more pieces of information
			String securityQuestion = lineReader.next();
			String answer = lineReader.next();
			u = new User(name, email, password, securityQuestion, answer);
		}
		else {
			u = new User(name, email, password);
		}
		 **/
		return u;
	}

	public String usersToString() {
		// just a way to visualize the map of users....
		// go through the map, get all the keys, and print all the User objects
		String s = "Map of All Users --> Email: User Object\n";
		for(String key: users.keySet()) { // for each key in the user map of keys....
			User value = users.get(key); // get the object associated with the key
			s += "\t" + key + ": "+ value + "\n";
		}

		return s;
	}

	/**
	 * checks to see if username is in the system, then checks to see if the password assoicated with the username matches
	 * if there is no match, the the User must start over again, in the case that they realize that they don't have an account
	 * or accidentally typed in someone else's email or something of that sort.
	 * @return User
	 */
	public User loginWithNameAndPass() {
		Boolean loginStatus = false;
		Scanner sc = new Scanner(System.in);
		User isValidUser = null;

		while (loginStatus == false) {
			String username = InputHelper.readString(sc, "\nEnter username: ");
			isValidUser = login(username);

			readExistingUsersFromFile();

			while( isValidUser == null ) {
				System.out.println("User with this email not found.");
				username = InputHelper.readString(sc, "\nEnter username: ");
				isValidUser = login(username);
			} 

			String password = InputHelper.readString(sc, "Enter password: ");

			for (Map.Entry<String, User> entry : users.entrySet()) {
				if (entry.getKey().equals(username)) {
					isValidUser = entry.getValue();
					if (isValidUser.verifyPassword(password) == false) {
						System.out.println("Password incorrect.\n");
					}
					else{
						loginStatus = true;
					}

				}
			}
		}
		System.out.println("Logged in to system!");
		return isValidUser;

	}

	/**
	 * this method is where the bulk of the logic for the login lies. first, it checks to see if the username (aka the email) exists
	 * within the file. then, the password is checked for by looking in the Map of users (key - email, value - User) that was written to
	 * from the text file. if the User's password matches the email, then the User is then logged onto the system. 
	 * @return User
	 */
	public User signOnMenu() {
		System.out.println("\nWelcome to the Evite System!");
		InputHelper.printStars(25);
		String prompt = "\n\nPress 0 to create an account.\nPress 1 to log in if you are an existing member";
		User member = null;

		Scanner sc = new Scanner(System.in);
		String decide = InputHelper.readString(sc, prompt);

		if (decide.equals("0")) {
			String email = InputHelper.readString(sc, "Please enter your email.");
			createNewUser(email);
			member = loginWithNameAndPass();
		}
		else if (decide.equals("1")) {
			member = loginWithNameAndPass();
		}
		else {
			System.out.println("Invalid input. Please try again.");
			signOnMenu();
		}
		return member;
	}

	public static void main(String[] args) {
		UserLogin system = new UserLogin();
		System.out.println(system.usersToString());
		// test login capability

		User mem = system.signOnMenu();
		System.out.println(mem);


		/**
		User tester = system.login("kendra@usc.edu");
		System.out.println(tester + " logged into the system");


		User tester2 = system.login("philyang@usc.edu");
		if(tester2 == null) {
			System.out.println("No user found with that email. Please register.");
			User u = system.createNewUser("philyang@usc.edu");
			if(u != null ) {
				System.out.println("User created in system! " + u);
			}
		}
		else {
			System.out.println(tester2 + " logged into the system");
		}


		User u = system.createNewUser("philyang@usc.edu");
		if(u == null) {
			System.out.println("oops, alreadys exists, try logging in instead....");
		}


		System.out.println("New Map\n" + system.usersToString());


		// before logging out, write to file.
		system.writeUsersToFile();
		 **/

	}

	/**
	 * users in the map get written to the file.
	 */
	private void writeUsersToFile() {
		PrintWriter pw = null;
		try (FileOutputStream fos = new FileOutputStream(USER_FILE)){
			pw = new PrintWriter(fos);
			// take each user in the map and write it to the file.
			for(String key: users.keySet()) {
				User u = users.get(key);
				// print users email/name/password/q/a
				pw.println(u.toFileString());

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
}
