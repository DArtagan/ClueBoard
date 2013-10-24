package clueTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import clue.BoardCell;
import clue.ClueGame;
import clue.ComputerPlayer;
import clue.Solution;

public class GameActionTests {
	ClueGame clueGame;
	ComputerPlayer grimm;

	@Before
	public void setUp() throws Exception {
		clueGame = new ClueGame("ClueBoard.csv", "legend.txt");
		clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
		grimm = new ComputerPlayer("Grimm", "black", 291);
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
	public void testTargetRoomPreference() {
		int trials = 10;
		// Pick a location with no rooms in target, just three targets
		clueGame.getBoard().calcTargets(1, 10, 2);
		int loc0x11Tot = 0;
		for (int i=0; i<trials; i++) {
			BoardCell selected = grimm.pickLocation(clueGame.getBoard().getTargets());
			if (selected == clueGame.getBoard().getCellAt(8, 15)) {
				loc0x11Tot++;
			} else {
				fail("Invalid target selected");
			}
		}
		assertTrue(loc0x11Tot == 10);
	}

	@Test
	public void testTargetVisitedRoomPreference() {
		int trials = 30;
		clueGame.getBoard().calcTargets(15, 0, 2);
		grimm.setVisited(323);
		int loc14x1Tot = 0;
		int loc15x2Tot = 0;
		int loc16x1Tot = 0;
		for (int i=0; i<trials; i++) {
			BoardCell selected = grimm.pickLocation(clueGame.getBoard().getTargets());
			if (selected == clueGame.getBoard().getCellAt(14, 1)) {
				loc14x1Tot++;
			} else if (selected == clueGame.getBoard().getCellAt(15, 2)) {
				loc15x2Tot++;
			} else if (selected == clueGame.getBoard().getCellAt(16, 1)) {
				loc16x1Tot++;
			} else {
				fail("Invalid target selected");
			}
		}
		// Ensure we have 30 total selections (fail should also ensure)
		assertEquals(trials, loc14x1Tot + loc15x2Tot + loc16x1Tot);
		// Ensure each target was selected more than once
		assertTrue(loc14x1Tot > 2);
		assertTrue(loc15x2Tot > 2);
		assertTrue(loc16x1Tot > 2);
	}

	@Test
	public void testTargetRandomSelection() {
		int trials = 30;
		// Pick a location with no rooms in target, just three targets
		clueGame.getBoard().calcTargets(15, 22, 2);
		int loc13x22Tot = 0;
		int loc14x21Tot = 0;
		int loc15x20Tot = 0;
		for (int i=0; i<trials; i++) {
			BoardCell selected = grimm.pickLocation(clueGame.getBoard().getTargets());
			if (selected == clueGame.getBoard().getCellAt(8, 15)) {
				loc13x22Tot++;
			} else if (selected == clueGame.getBoard().getCellAt(10, 17)) {
				loc14x21Tot++;
			} else if (selected == clueGame.getBoard().getCellAt(14, 15)) {
				loc15x20Tot++;
			} else {
				fail("Invalid target selected");
			}
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(trials, loc13x22Tot + loc14x21Tot + loc15x20Tot);
		// Ensure each target was selected more than once
		assertTrue(loc13x22Tot > 2);
		assertTrue(loc14x21Tot > 2);
		assertTrue(loc15x20Tot > 2);
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

