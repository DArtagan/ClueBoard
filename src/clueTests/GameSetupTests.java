package clueTests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import clue.Card;
import clue.Card.CardType;
import clue.ClueGame;
import clue.ComputerPlayer;

public class GameSetupTests {
	private ClueGame clueGame;

	@Before
	public void setUp() throws Exception {
		clueGame = new ClueGame();
		clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
	}

	@Test
	public void testLoadPeople() {
		// Valid players
		ComputerPlayer mrgreen = new ComputerPlayer("Mr. Green");
		ComputerPlayer mustard = new ComputerPlayer("Colonel Mustard");
		ComputerPlayer scarlet = new ComputerPlayer("Miss Scarlet");

		assertTrue(clueGame.getPlayers().contains(mrgreen));
		assertTrue(clueGame.getPlayers().contains(mustard));
		assertTrue(clueGame.getPlayers().contains(scarlet));
		assertTrue(clueGame.getPlayers().size() == 6);
	}

	@Test
	public void testLoadCards() {
		int cardTotal = 21;

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
			if (card.getCardType() == Card.CardType.WEAPON) {
				++weaponCount;
			}
			if (card.getCardType() == Card.CardType.ROOM) {
				++roomCount;
			}
			if (card.getCardType() == Card.CardType.PERSON) {
				++personCount;
			}
		}
		assertTrue(weaponCount == weaponTotal);
		assertTrue(roomCount == roomTotal);
		assertTrue(personCount == personTotal);
	}

	@Test
	public void testDealCards() {
		fail("Not yet implemented");
	}

}
