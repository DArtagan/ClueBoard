package clueTests;

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
		clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
	}

	@Test
	public void testLoadPeople() {
		// Valid players
		ComputerPlayer mrgreen = new ComputerPlayer("Reverend Green", "green", 160);
		ComputerPlayer mustard = new ComputerPlayer("Colonel Mustard", "yellow", 10);
		ComputerPlayer scarlet = new ComputerPlayer("Miss Scarlett", "red", 161);
		ComputerPlayer prfplum = new ComputerPlayer("Professor Plum", "purple", 368);

		assertTrue(clueGame.getPlayers().contains(mrgreen));
		assertTrue(clueGame.getPlayers().contains(mustard));
		assertTrue(clueGame.getPlayers().contains(scarlet));
		assertTrue(clueGame.getPlayers().contains(prfplum));
		assertTrue(clueGame.getPlayers().size() == 6);
	}

	@Test
	public void testLoadCards() {
		fail("Not yet implemented");

	}

	@Test
	public void testDealCards() {
		fail("Not yet implemented");
	}

}
