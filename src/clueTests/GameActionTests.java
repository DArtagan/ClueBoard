package clueTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import clue.ClueGame;
import clue.Solution;

public class GameActionTests {
	ClueGame clueGame;

	@Before
	public void setUp() throws Exception {
		clueGame = new ClueGame();
		clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
	}

	@Test
	public void testCheckAccusation() {
		// Set the solution.
		Solution accusation = new Solution("Miss Scarlett", "Revolver", "Aquarium");
		clueGame.setSolution(accusation);

		// Test that correct accusations
		assertTrue(clueGame.checkAccusation(accusation));
		assertFalse(clueGame.checkAccusation(accusation));
	}

	@Test
	public void testSelectTarget() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisproveSelection() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeSuggestion() {
		fail("Not yet implemented");
	}
}
