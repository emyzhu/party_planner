
/**
 * represents a free user. cannot use methods that are in the Premium class
 * @author Emily Zhu
 * ITP 265, Fall 2019, Coffee Section
 * Final Project
 * Email: emilyzhu@usc.edu
 */
public class Free extends User {


	public Free(String name, String email, String password, Boolean hasPremium) {
		super(name, email, password, hasPremium);
		// TODO Auto-generated constructor stub
		hasPremium = false;
	}

}
