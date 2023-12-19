
/**
 * represents the things that a User with a paid subscription can do
 * right now only Premium will implement this interface, but
 * could have like a premium and pro level like the real evite system that would make better use of this interface
 * @author Emily Zhu
 * ITP 265, Fall 2019, Coffee Section
 * Final Project
 * Email: emilyzhu@usc.edu
 */
public interface PaidMembership {

	/**
	 * @param party
	 * a premium feature that allows for the user to change the details about a given party that they are hosting.
	 */
	public void changeInviteDetails(Party party);
	/**
	 * @param party
	 * another premium feature that allows for the user to cancel the party
	 */
	public void cancelParty(Party party);
}
