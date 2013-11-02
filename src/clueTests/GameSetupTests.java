package clueTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import clue.Card;
import clue.Card.CardType;
import clue.ClueGame;
import clue.ComputerPlayer;
import clue.HumanPlayer;
import clue.Player;

public class GameSetupTests {
	// Constants
	private final static int cardTotal = 21;

	// Valid cards
	Card dagger, rope, conservatory, library, mrgreen, scarlett;

	// Variables
	private ClueGame clueGame;

	@Before
	public void setUp() throws Exception {
		clueGame = new ClueGame("ClueBoard.csv", "legend.txt");
		clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
		clueGame.deal();

		// Check valid cards.
		dagger = new Card("Dagger", CardType.WEAPON);
		rope = new Card("Rope", CardType.WEAPON);
		conservatory = new Card("Conservatory", CardType.ROOM);
		library = new Card("Library", CardType.ROOM);
		mrgreen = new Card("Mr. Green", CardType.PERSON);
		scarlett = new Card("Miss Scarlett", CardType.PERSON);
	}

	@Test
	public void testLoadPeople() {
		// Test some valid players, including first and last in the file.
		ComputerPlayer mrgreen = new ComputerPlayer("Mr. Green", "0x859900", 160);
		ComputerPlayer mustard = new ComputerPlayer("Colonel Mustard", "0xb58900", 10);
		ComputerPlayer prfplum = new ComputerPlayer("Professor Plum", "0x6c71c4", 368);
		HumanPlayer scarlet = new HumanPlayer("Miss Scarlett", "0xdc322f", 161);

		// Test to make sure the number of players is correct.
		assertEquals(6, clueGame.getPlayers().size());
		// Test that specific players still exist.
		assertTrue(clueGame.getPlayers().contains(mrgreen));
		assertTrue(clueGame.getPlayers().contains(mustard));
		assertTrue(clueGame.getPlayers().contains(scarlet));
		assertTrue(clueGame.getPlayers().contains(prfplum));
	}

	@Test
	public void testLoadCards() {
		int weaponTotal = 6;
		int roomTotal = 9;
		int personTotal = 6;

		assertTrue(clueGame.getCards().contains(dagger));
		assertTrue(clueGame.getCards().contains(rope));
		assertTrue(clueGame.getCards().contains(conservatory));
		assertTrue(clueGame.getCards().contains(library));
		assertTrue(clueGame.getCards().contains(mrgreen));
		assertTrue(clueGame.getCards().contains(scarlett));
		// Check number of cards is correct.
		assertEquals(cardTotal, clueGame.getCards().size());

		// Count numbers of each type of card.
		int weaponCount = 0;
		int roomCount = 0;
		int personCount = 0;
		for (Card card : clueGame.getCards()) {
			if (card.getType() == Card.CardType.WEAPON) {
				++weaponCount;
			}
			if (card.getType() == Card.CardType.ROOM) {
				++roomCount;
			}
			if (card.getType() == Card.CardType.PERSON) {
				++personCount;
			}
		}
		assertEquals(weaponTotal, weaponCount);
		assertEquals(roomTotal, roomCount);
		assertEquals(personTotal, personCount);
	}

	@Test
	public void testDealCards() {
		LinkedList<Player> players = clueGame.getPlayers();

		// Solution generated
		assertTrue(clueGame.solution.weapon != null && clueGame.solution.room != null && clueGame.solution.person != null);

		// Card Count
		int cardCount;
		int cardCountTotal = 0;
		int cardCountLast = 0;
		for (Player player : players) {
			cardCount = player.getCards().size();
			if(cardCountLast == 0) {
				// For the first player,
				// can't check against the last player
				cardCountLast = cardCount;
			}
			// assert that this player has the same number of cards as the last player
			assertTrue(cardCount == cardCountLast);
			cardCountLast = cardCount;  // set the record of the last count before checking next player
			cardCountTotal += cardCount;  // add to total for each player
		}
		assertTrue(cardCountTotal + 3 == cardTotal);
		// the total number of cards that all players have, plus three, equal the total deck
		// we can use plus 3, because we checked earlier that a solution was generated
		// solutions eat three cards

		// Duplicate Card Check
		for (Player player : players) {
			for (Player opponent : players) {
				if(opponent.equals(player)) {
					continue;
				}
				for (Card card : player.getCards()) {
					assertFalse(opponent.getCards().contains(card));
				}
			}
		}
	}

}