package clueTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import clue.BoardCell;
import clue.Card;
import clue.ClueGame;
import clue.ComputerPlayer;
import clue.HumanPlayer;
import clue.Player;
import clue.Solution;

public class GameActionTests {
	ClueGame clueGame;
	ComputerPlayer grimm, mrgreen, mustard, plum;
	Card profplumCard, scarlettCard, revolverCard, leadpipeCard,
	ballroomCard, aquariumCard, mustardCard, ropeCard, kitchenCard,
	candleCard, whiteCard, labCard, studyCard;
	HumanPlayer scarlett;
	ArrayList<Player> players;
	HashSet<Card> suggestion;

	@Before
	public void setUp() throws Exception {
		clueGame = new ClueGame("ClueBoard.csv", "legend.txt");
		clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
		grimm = new ComputerPlayer("Grimm", "black", 291);

		// Cards.
		profplumCard = new Card("Professor Plum", Card.CardType.PERSON);
		scarlettCard = new Card("Miss Scarlett", Card.CardType.PERSON);
		revolverCard = new Card("Revolver", Card.CardType.WEAPON);
		leadpipeCard = new Card("Lead Pipe", Card.CardType.WEAPON);
		ballroomCard = new Card("Ballroom", Card.CardType.ROOM);
		aquariumCard = new Card("Aquarium", Card.CardType.ROOM);
		mustardCard = new Card("Colonel Mustard", Card.CardType.PERSON);
		ropeCard = new Card("Rope", Card.CardType.WEAPON);
		kitchenCard = new Card("Kitchen", Card.CardType.ROOM);
		candleCard = new Card("Candlestick", Card.CardType.WEAPON);
		whiteCard = new Card("Mrs. White", Card.CardType.WEAPON);
		labCard = new Card("Laboratory", Card.CardType.ROOM);
		studyCard = new Card("Study", Card.CardType.ROOM);
		// Players.
		mrgreen = new ComputerPlayer("Mr. Green", "green", 160);
		mustard = new ComputerPlayer("Colonel Mustard", "yellow", 10);
		plum = new ComputerPlayer("Professor Plum", "magenta", 368);
		scarlett = new HumanPlayer("Miss Scarlett", "red", 161);
		players = new ArrayList<Player>();
		players.add(grimm);
		players.add(mrgreen);
		players.add(scarlett);
		players.add(mustard);
		players.add(plum);
		suggestion = new HashSet<Card>();
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
	public void testTargetRoomPreference() {
		int trials = 10;
		// Pick a location with one room in the targets
		clueGame.getBoard().calcTargets(1, 10, 2);
		int loc0x11Tot = 0;
		for (int i=0; i<trials; ++i) {
			grimm.setVisited(clueGame.getBoard().getCellAt(323));
			BoardCell selected = grimm.pickLocation(clueGame.getBoard().getTargets());
			System.out.println(selected);
			if (selected == clueGame.getBoard().getCellAt(0, 11)) {
				++loc0x11Tot;
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
		int loc14x1Tot = 0;
		int loc15x2Tot = 0;
		int loc16x1Tot = 0;
		for (int i=0; i<trials; ++i) {
			grimm.setVisited(clueGame.getBoard().getCellAt(323));
			BoardCell selected = grimm.pickLocation(clueGame.getBoard().getTargets());
			if (selected == clueGame.getBoard().getCellAt(14, 1)) {
				++loc14x1Tot;
			} else if (selected == clueGame.getBoard().getCellAt(15, 2)) {
				++loc15x2Tot;
			} else if (selected == clueGame.getBoard().getCellAt(16, 1)) {
				++loc16x1Tot;
			} else {
				fail("Invalid target selected");
			}
		}
		// Ensure we have 30 total selections (fail should also ensure)
		assertEquals(trials, loc14x1Tot + loc15x2Tot + loc16x1Tot);
		// Ensure each target was selected more than once
		System.out.println(loc14x1Tot);
		System.out.println(loc15x2Tot);
		System.out.println(loc16x1Tot);
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
		for (int i=0; i<trials; ++i) {
			BoardCell selected = grimm.pickLocation(clueGame.getBoard().getTargets());
			if (selected == clueGame.getBoard().getCellAt(13, 22)) {
				loc13x22Tot++;
			} else if (selected == clueGame.getBoard().getCellAt(14, 21)) {
				loc14x21Tot++;
			} else if (selected == clueGame.getBoard().getCellAt(15, 20)) {
				loc15x20Tot++;
			} else {
				fail("Invalid target selected");
			}
		}
		// Ensure we have 30 total selections (fail should also ensure)
		assertEquals(trials, loc13x22Tot + loc14x21Tot + loc15x20Tot);
		// Ensure each target was selected more than once
		assertTrue(loc13x22Tot > 2);
		assertTrue(loc14x21Tot > 2);
		assertTrue(loc15x20Tot > 2);
	}

	@Test
	public void testDisproveSuggestionOnePlayerOneMatch() {
		grimm.addCard(profplumCard);
		grimm.addCard(scarlettCard);
		grimm.addCard(revolverCard);
		grimm.addCard(leadpipeCard);
		grimm.addCard(ballroomCard);
		grimm.addCard(aquariumCard);

		// Test that the correct card (or a null value) is returned.
		suggestion.clear();
		suggestion.add(profplumCard);
		suggestion.add(kitchenCard);
		suggestion.add(ropeCard);
		assertEquals(grimm.disproveSuggestion(suggestion), profplumCard);

		suggestion.clear();
		suggestion.add(mustardCard);
		suggestion.add(kitchenCard);
		suggestion.add(revolverCard);
		assertEquals(grimm.disproveSuggestion(suggestion), revolverCard);

		suggestion.clear();
		suggestion.add(mustardCard);
		suggestion.add(ballroomCard);
		suggestion.add(ropeCard);
		assertEquals(grimm.disproveSuggestion(suggestion), ballroomCard);

		suggestion.clear();
		suggestion.add(mustardCard);
		suggestion.add(kitchenCard);
		suggestion.add(ropeCard);
		assertEquals(grimm.disproveSuggestion(suggestion), null);
	}

	@Test
	public void testDisproveSuggestionOnePlayerMultipleMatches() {
		grimm.addCard(scarlettCard);
		grimm.addCard(leadpipeCard);
		grimm.addCard(aquariumCard);

		suggestion.add(scarlettCard);
		suggestion.add(ballroomCard);
		suggestion.add(leadpipeCard);

		int scarlettTimes = 0;
		int leadpipeTimes = 0;
		int otherTimes = 0;
		for (int i = 0; i < 30; ++i) {
			switch (grimm.disproveSuggestion(suggestion).toString()) {
			case "Miss Scarlett": ++scarlettTimes; break;
			case "Lead Pipe": ++leadpipeTimes; break;
			default: ++otherTimes;
			}
		}
		assertTrue(scarlettTimes > 3);
		assertTrue(leadpipeTimes > 3);
		assertEquals(0, otherTimes);
	}

	@Test
	public void testDisproveSuggestionAllPlayers() {
		// Set up the game.
		mrgreen.addCard(profplumCard);
		mrgreen.addCard(kitchenCard);
		mustard.addCard(revolverCard);
		mustard.addCard(mustardCard);
		mustard.addCard(labCard);
		plum.addCard(aquariumCard);
		plum.addCard(candleCard);
		scarlett.addCard(leadpipeCard);
		scarlett.addCard(whiteCard);
		scarlett.addCard(studyCard);
		clueGame.setPlayers(new HashSet<Player>(players));
		/*for (Player player : clueGame.getPlayers()) {
			System.out.println(player.getCards());
		}*/

		// Make a suggestion which no players can disprove, and ensure that null is returned.
		suggestion.add(scarlettCard);
		suggestion.add(ballroomCard);
		assertEquals(null, clueGame.handleSuggestion(suggestion, grimm));

		// Ensure that if the person who makes the suggestion is the only one who can disprove it, null is returned.
		suggestion.clear();
		suggestion.add(mustardCard);
		suggestion.add(labCard);
		suggestion.add(revolverCard);
		assertEquals(null, clueGame.handleSuggestion(suggestion, mustard));
		suggestion.clear();
		suggestion.add(whiteCard);
		suggestion.add(studyCard);
		suggestion.add(leadpipeCard);
		assertEquals(null, clueGame.handleSuggestion(suggestion, scarlett));

		// Test the order that players are queried.
		// Set up a suggestion that two players can disprove.
		// Ensure that the first person does the disproving (where "first" depends on the order in the players list).
		suggestion.clear();
		suggestion.add(ballroomCard);
		suggestion.add(leadpipeCard);
		suggestion.add(mustardCard);
		assertEquals(mustardCard, clueGame.handleSuggestion(suggestion, grimm));
		// Set up a test where the furthest person from the accuser is the one who can disprove, to ensure that all players are queried.
		suggestion.clear();
		suggestion.add(ballroomCard);
		suggestion.add(aquariumCard);
		suggestion.add(ropeCard);
		assertEquals(aquariumCard, clueGame.handleSuggestion(suggestion, grimm));
	}

	@Test
	public void testComputerMakeSuggestion() {
		Card cards[] = {profplumCard,revolverCard,leadpipeCard,ballroomCard,aquariumCard,studyCard};
		HashSet<Card> seen = new HashSet<Card>();
		for (Card card : cards) {
			seen.add(card);
		}
		grimm.setSeen(seen);
		seen.add(kitchenCard);
		seen.add(ropeCard);
		seen.add(profplumCard);
		clueGame.setDeck(seen);
		grimm.setIndex(260);
		suggestion = grimm.createSuggestion(clueGame.getCards());

		assert(suggestion.contains(studyCard));
		assert(suggestion.contains(ropeCard));
		assert(suggestion.contains(profplumCard));
	}
}
