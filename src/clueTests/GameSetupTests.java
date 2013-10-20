package clueTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import clue.ClueGame;
import clue.ComputerPlayer;

public class GameSetupTests {
	private ClueGame clueGame;

	@Before
	public void setUp() throws Exception {
		clueGame = new ClueGame();
		clueGame.loadConfigFiles();
	}

	@Test
	public void testLoadPeople() {
		// Valid players
		ComputerPlayer mrgreen = new ComputerPlayer("Mr. Green");
		ComputerPlayer mustard = new ComputerPlayer("Colonel Mustard");
		ComputerPlayer scarlet = new ComputerPlayer("Miss Scarlet");
		// Not a valid player
		ComputerPlayer player0 = new ComputerPlayer("Player Zero");

		assertTrue(clueGame.getPlayers().contains(mrgreen));
		assertTrue(clueGame.getPlayers().contains(mustard));
		assertTrue(clueGame.getPlayers().contains(scarlet));
		assertFalse(clueGame.getPlayers().contains(player0));
		assertTrue(clueGame.getPlayers().size() == 6);
	}

	@Test
	public void testLoadCards() {
		fail("Not yet implemented");
	}

	@Test public void testEqualCards() {
		fail("Not yet implemented");
	}

	@Test
	public void testDealCards() {
		fail("Not yet implemented");
	}

}
