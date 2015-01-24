package cardgame;

import java.util.List;
import java.util.Vector;

/**
 * This represents a user within our game.
 * 
 * @author gordonjahn
 *
 */
public class User {
	/**
	 * The cards this user currently holds
	 */
	List<Aircraft> userCards;
	
	/**
	 * The name of this users
	 */
	String userName;
	
	/**
	 * Constructor requires only the users name - cards are populated through a dealing sequence
	 *  
	 * @param userName
	 */
	public User(String userName) {
		super();
		this.userName = userName;
		this.userCards = new Vector<Aircraft>();
	}
	
	/**
	 * Print out how many cards the user has left
	 * 
	 * @return
	 */
	public int getCardCount() {
		return userCards.size();
	}
	
	/**
	 * Get the user's name
	 * 
	 * @return
	 */
	public String getUsername() {
		return userName;
	}
	
	/**
	 * Return if user still in game - no cards means they are out
	 * 
	 * @return
	 */
	public boolean stillInGame() {
		return !userCards.isEmpty();
	}
	
	/**
	 * Play card returns the next card in the user's deck - the user will be set as the last owner
	 * to allow tracking of the winner
	 * @return
	 */
	public Aircraft playCard() {
		Aircraft response = userCards.remove(0);
		response.setLastOwner(this);
		return response;
	}

	/**
	 * Give another card to this user
	 * 
	 * @param aircraft
	 */
	public void dealCard(Aircraft aircraft) {
		userCards.add(aircraft);
	}
	
	/**
	 * Win cards - this is called when the user wins a hand and cards are added to the user's deck
	 * @param winnings
	 */
	public void winCards(List<Aircraft> winnings) {
		userCards.addAll(winnings);
	}
}
