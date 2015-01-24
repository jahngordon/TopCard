package cardgame;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class GameEngine {
	 
	/**
	 * Create the deck of cards for the game
	 * 
	 * @return
	 */
	static List<Aircraft> getSourceCardList() {
		List<Aircraft> cards = new Vector<Aircraft>();
		cards.add(new Aircraft("Airbus", "A380", 15700,79.75,72.72,24.09,560,1020,2005));
		cards.add(new Aircraft("Boeing", "747-800", 14800,68.5,76.3,19.4,448,917,2010));
		cards.add(new Aircraft("Aerospatiale-BAC", "Concorde", 7223,25.6,61.66,12.2,187,2179,1969));
		cards.add(new Aircraft("Lockheed", "L1011 Tristar", 7410,47.35,54.17,16.87,200,1164,1970));
		cards.add(new Aircraft("McDonnell Douglas", "DC10", 6116,47.34,51.97,17.7,195,982,1970));
		cards.add(new Aircraft("British Aerospace", "146-200", 2909,26.21,28.6,8.59,42,894,1981));
		cards.add(new Aircraft("Boeing", "737-400", 4204,28.9,36.5,11.1,68,943,1988));
		cards.add(new Aircraft("Airbus", "A320", 6100,35.8,37.57,11.76,78,871,1987));
		cards.add(new Aircraft("Bombardier", "Dash8 Q400", 2522,28.4,32.81,8.3,29,667,1998));
		cards.add(new Aircraft("Antonov", "An-225", 15400,88.4,84,18.1,640,850,1988));
		cards.add(new Aircraft("Antonov", "An-124", 5200,73.3,68.96,20.78,405,865,1982));
		cards.add(new Aircraft("Ilushin", "Il-96-300", 7500,60.1,55.3,17.6,216,910,1988));
		cards.add(new Aircraft("Tupolev", "Tu-144", 2500,28.8,65.5,12.5,207,2120,1968));
		cards.add(new Aircraft("Cessna", "172", 1289,11,8.28,2.72,1,302,1955));
		cards.add(new Aircraft("Embraer", "ERJ145", 2873,20.04,29.87,6.76,24,851,1995));
		cards.add(new Aircraft("ATR", "42-320", 1950,24.6,22.7,7.6,17,500,1984));
		cards.add(new Aircraft("Fokker", "100", 3100,28.1,35.5,8.5,46,856,1986));
		cards.add(new Aircraft("Airbus", "A340-500", 16400,63.5,67.9,17.1,372,913,2002));
		cards.add(new Aircraft("Boeing", "777-300ER", 14600,64.8,73.9,18.6,352,945,1997));
		cards.add(new Aircraft("Dornier", "328Jet", 1700,20.1,21.3,7,16,750,1998));
		Collections.shuffle(cards);
		return cards;
	}
	
	public static void main(String[] args) {
		// This is used for all user input/output
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome to the Best Card Game!\n\n");
		
		// Get the number of players in the game - 20 cards means we can have up to 20 players (it'll be a short game though!)
		int players = 0;
		while (players < 2 || players > 20) {
			System.out.print("Enter the number of players (max: 20): ");
			players = scanner.nextInt();
			scanner.nextLine();
		}

		// Now get names for each user in the game
		List<User> users = new Vector<User>();
		
		for (int i = 0; i < players; i++) {
			System.out.print("Enter player " + (i + 1) + " name: ");
			users.add(new User(scanner.nextLine()));
		}
		
		// Shuffle and deal - get the shuffled card list and go round the users handing them out
		System.out.println("\n\nShuffling and dealing...");
		
		List<Aircraft> cards = getSourceCardList();
		
		while (cards.size() > 0) {
			for (int i = 0; i < players && cards.size() > 0; i++) {
				Aircraft card = cards.remove(0);
				users.get(i).dealCard(card);
			}
		}
		
		// Start the game!
		System.out.println("\nGame starts!");
		
		// Default start with the first user
		User u = users.get(0);
		
		// Keep a list of aircraft available in case there's a draw...
		List<Aircraft> rolloverPool = new Vector<Aircraft>();
		
		// So long as there are more than one users with cards, the game continues
		while (users.size() > 1) {
			// Create a list for the cards played in this round
			List<Aircraft> currentPool = new Vector<Aircraft>();

			// Print out the next player details
			System.out.println("Next player is " + u.getUsername() + " - you have " + 
					u.getCardCount() + " cards");

			// Get the next card for all users
			for (User currentUser : users) {
				currentPool.add(currentUser.playCard());
			}
			
			// Print the card for the current user - we always put the current user as the first user
			System.out.println("Your card is:\n");
			currentPool.get(0).printCard();
			
			// Prompt to select category
			System.out.println("Which category would you like to play?");
			System.out.print("(r)ange, (w)ingspan, (l)ength, (h)eight, (m)tow, (s)peed, (f)irst flight: ");
			
			// This selects the comparator instance (see below) used to compare cards
			Comparator<Aircraft> c = null;
			do {
				char option = scanner.nextLine().charAt(0);
				switch(option) {
				case 'r':
					c = new RangeComparator();
					break;
				case 'w':
					c = new WingspanComparator();
					break;
				case 'l':
					c = new LengthComparator();
					break;
				case 'h':
					c = new HeightComparator();
					break;
				case 'm':
					c = new MtowComparator();
					break;
				case 's':
					c = new SpeedComparator();
					break;
				case 'f':
					c = new FirstFlightComparator();
					break;
				}
			} while (c == null);

			// Print out who played what
			System.out.println("\nGame in progress...\n");
			for (Aircraft ac : currentPool) {
				System.out.println(ac.getLastOwner().getUsername() + " played " + ac.getManufacturer() + " " + ac.getModel());
			}
			
			// Sort the cards using the comparator picked above
			Collections.sort(currentPool, c);
			
			// If cards 0 and 1 are the same, we have a draw - hold back the cards and just play again
			if (c.compare(currentPool.get(0), currentPool.get(1)) == 0) {
				System.out.println("\nThis round was a draw - cards have been held for the winner of the next round!");
				rolloverPool.addAll(currentPool);
				currentPool.clear();
			} else {
				// ... otherwise the winner gets all cards...
				u = currentPool.get(0).getLastOwner();
				System.out.println("\nThis round was won by " + u.getUsername() + "\n");
				u.winCards(currentPool);
				
				// ... and any in the rollover pool
				if (rolloverPool.size() > 0) {
					u.winCards(rolloverPool);
					rolloverPool.clear();
				}
				
				// Move this user to the top of the user list so the current user is always the first one
				users.remove(u);
				users.add(0, u);
			}
			
			// Filter the users to remove any who are out of the game
			List<User> filterUserList = new Vector<User>();
			for (User checkUser : users) {
				if (checkUser.stillInGame()) {
					filterUserList.add(checkUser);
				} else {
					System.out.println(checkUser.getUsername() + " has no cards and is out of the game");
				}
			}
			users = filterUserList;
			
			System.out.println("\nRemaining users:");
			for (User ru : users) {
				System.out.println(ru.getUsername() + " has " + ru.getCardCount() + " cards");
			}
			System.out.println("");
		}
		
		// If we've fallen out, only one user should be left...
		if (users.size() == 1) {
			System.out.println("\n" + users.get(0).getUsername() + " has won the game!");
		} else {
			// This, in theory, should never happen unless the first round draws
			System.out.println("\nIt was a draw - that's rubbish, have another go!");
		}
		
		// Close down!
		scanner.close();
	}
}

class RangeComparator implements Comparator<Aircraft> {
	@Override
	public int compare(Aircraft o1, Aircraft o2) {
		return o2.range - o1.range;
	}
}

class WingspanComparator implements Comparator<Aircraft> {
	@Override
	public int compare(Aircraft o1, Aircraft o2) {
		return (int) ((o2.wingspan - o1.wingspan) * 100);
	}
}

class LengthComparator implements Comparator<Aircraft> {
	@Override
	public int compare(Aircraft o1, Aircraft o2) {
		return (int) ((o2.length - o1.length) * 100);
	}
}

class HeightComparator implements Comparator<Aircraft> {
	@Override
	public int compare(Aircraft o1, Aircraft o2) {
		return (int) ((o2.height - o1.height) * 100);
	}
}

class MtowComparator implements Comparator<Aircraft> {
	@Override
	public int compare(Aircraft o1, Aircraft o2) {
		return o2.mtow - o1.mtow;
	}
}

class SpeedComparator implements Comparator<Aircraft> {
	@Override
	public int compare(Aircraft o1, Aircraft o2) {
		return o2.maxspeed - o1.maxspeed;
	}
}

class FirstFlightComparator implements Comparator<Aircraft> {
	@Override
	public int compare(Aircraft o1, Aircraft o2) {
		return o2.year - o1.year;
	}
}