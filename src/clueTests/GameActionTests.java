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
		clueGame = new ClueGame("ClueBoard.csv", "legend.txt");
		clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
	}

	@Test
	public void testCheckAccusation() {
		// Set the solution.
		Solution accusation = new Solution("Miss Scarlett", "Revolver", "Aquarium");
		clueGame.setSolution(accusation);

		// Test that correct accusations return true.
		assertTrue(clueGame.checkAccusation(accusation));
		// And that accusations of various levels of almost-correctness return false.
		Solution false1 = new Solution("Miss Scarlett", "Rope", "Aquarium");
		Solution false2 = new Solution("Mr. Green", "Revolver", "Aquarium");
		Solution false3 = new Solution("Miss Scarlett", "Revolver", "Laboratory");
		Solution false4 = new Solution("Professor Plum", "Lead Pipe", "Kitchen");
		Solution false5 = new Solution("A", "B", "C");  // Nonsense solution
		Solution false6 = new Solution("Miss Scarlett", "Rope", "Kitchen");
		assertFalse(clueGame.checkAccusation(false1));
		assertFalse(clueGame.checkAccusation(false2));
		assertFalse(clueGame.checkAccusation(false3));
		assertFalse(clueGame.checkAccusation(false4));
		assertFalse(clueGame.checkAccusation(false5));
		assertFalse(clueGame.checkAccusation(false6));
	}

	@Test
	public void testSelectTarget() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisproveSuggestionOnePlayerOneMatch() {
		grimm.addCard(profplumCard);
		grimm.addCard(scarlettCard);
		grimm.addCard(revolverCard);
		grimm.addCard(leadpipeCard);
		grimm.addCard(ballroomCard);
		grimm.addCard(aquariumCard);

		assertEquals(grimm.disproveSuggestion(profplumCard, ropeCard, kitchenCard), profplumCard);
		assertEquals(grimm.disproveSuggestion(mustardCard, revolverCard, kitchenCard), revolverCard);
		assertEquals(grimm.disproveSuggestion(mustardCard, ropeCard, ballroomCard), ballroomCard);
		assertEquals(grimm.disproveSuggestion(mustardCard, ropeCard, kitchenCard), null);
	}

	@Test
	public void testDisproveSuggestionOnePlayerMultipleMatches() {
		fail("Not yet implemented");
	}

	@Test
	public void testDisproveSuggestionAllPlayers() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeSuggestion() {
		fail("Not yet implemented");
	}
}
