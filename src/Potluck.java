import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * party specifically pertaining to potlucks - so has a lot to deal with who brings what food
 * @author Emily Zhu
 * ITP 265, Fall 2019, Coffee Section
 * Final Project
 * Email: emilyzhu@usc.edu
 */
public class Potluck extends Party {

	Scanner sc;
	ArrayList<String> food = new ArrayList<>();;
	Map<String, String> bringFood; 


	public Potluck(PartyType eventType, String eventName, String hostEmail, ArrayList<String> guestList,
			String dateString, String location, ArrayList<String> food) {
		super(eventType, eventName, hostEmail, guestList,
				dateString, location);
		bringFood = new HashMap<>();
		sc = new Scanner(System.in);

	}

	public Potluck(PartyType eventType, String eventName, String hostEmail, ArrayList<String> guestList,
			String dateString, String location) {
		super(eventType, eventName, hostEmail, guestList,
				dateString, location);
		sc = new Scanner(System.in);

	}

	/**
	 * get user input of what foods to bring to party and puts each food as a String in an ArrayList and returns it so it can be sued for assignFood
	 * @return a list of foods that need to be assigned to each user for the potluck
	 */
	public ArrayList<String> makeFoodList() {
		Boolean notDone = true;

		while (notDone) {
			food.add(InputHelper.readString(sc, "What is the name of the food you would like to add?"));
			notDone = InputHelper.readYesNoBoolean(sc, "Would you like to add more food items to the list?");	
		}
		return food;
	}

	//TODO: find a way to get this to work in the future, would assign a food (the value) from the list of foods to each guest (the key). very incomplete and incorrect
	public void assignFood(ArrayList<String> food) {
		int counter = 0;
		for(String guest : getGuestList()) {
			bringFood.put(guest, null);
		}
		for(String key : bringFood.keySet()) {
			String value = bringFood.get(key);
			value = food.get(counter);
			counter += 1;
		}


	}






}
