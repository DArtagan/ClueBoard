package clueTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import clue.Card;
import clue.Card.CardType;
import clue.ClueGame;
import clue.ComputerPlayer;
import clue.Player;

public class GameSetupTests {
	// Constants
	private final static int cardTotal = 21;

	// Variables
	private ClueGame clueGame;

	@Before
	public void setUp() throws Exception {
		clueGame = new ClueGame();
		clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
		clueGame.deal();
	}

	@Test
	public void testLoadPeople() {
		// Test some valid players, including first and last in the file.
		ComputerPlayer mrgreen = new ComputerPlayer("Mr. Green", "green", 160);
		ComputerPlayer mustard = new ComputerPlayer("Colonel Mustard", "yellow", 10);
		ComputerPlayer scarlet = new ComputerPlayer("Miss Scarlett", "red", 161);
		ComputerPlayer prfplum = new ComputerPlayer("Professor Plum", "purple", 368);

		assertTrue(clueGame.getPlayers().contains(mrgreen));
		assertTrue(clueGame.getPlayers().contains(mustard));
		assertTrue(clueGame.getPlayers().contains(scarlet));
		assertTrue(clueGame.getPlayers().contains(prfplum));
		// Test to make sure the number of players is correct.
		assertTrue(clueGame.getPlayers().size() == 6);
	}

	@Test
	public void testLoadCards() {
		int weaponTotal = 6;
		int roomTotal = 9;
		int personTotal = 6;

		Card dagger = new Card("Dagger", CardType.WEAPON);
		Card rope = new Card("Rope", CardType.WEAPON);
		Card conservatory = new Card("Conservatory", CardType.ROOM);
		Card library = new Card("Library", CardType.ROOM);
		Card mrgreen = new Card("Mr. Green", CardType.PERSON);
		Card scarlett = new Card("Miss Scarlett", CardType.PERSON);

		assertTrue(clueGame.getCards().contains(dagger));
		assertTrue(clueGame.getCards().contains(rope));
		assertTrue(clueGame.getCards().contains(conservatory));
		assertTrue(clueGame.getCards().contains(library));
		assertTrue(clueGame.getCards().contains(mrgreen));
		assertTrue(clueGame.getCards().contains(scarlett));

		assertTrue(clueGame.getCards().size() == cardTotal);

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
		assertTrue(weaponCount == weaponTotal);
		assertTrue(roomCount == roomTotal);
		assertTrue(personCount == personTotal);
	}

	@Test
	public void testDealCards() {
		HashSet<Card> cards = clueGame.getCards();
		HashSet<Player> players = clueGame.getPlayers();

		// Solution generated
		assertTrue(clueGame.solution.weapon != null && clueGame.solution.room != null && clueGame.solution.person != null);

		// Card Count
		int cardCount;
		int cardCountTotal = 0;
		int cardCountLast = 0;
		for (Player player : players) {
			cardCount = 0;
			for (Card card : player.getCards()) {
				++cardCount;
			}
			if(cardCountLast == 0) {
				cardCountLast = cardCount;
			}
			assertTrue(cardCount == cardCountLast);
			cardCountLast = cardCount;
			cardCountTotal += cardCount;
		}
		assertTrue(cardCountTotal + 3 == cardTotal);

		// Duplicate Card Check
		for (Player player : players) {
			for (Player opponent : players) {
				for (Card card : player.getCards()) {
					assertFalse(opponent.getCards().contains(card));
				}
			}
		}
	}

}
