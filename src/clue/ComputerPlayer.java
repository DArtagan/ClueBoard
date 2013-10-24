package clue;

import java.util.HashSet;

public class ComputerPlayer extends Player {
	private int lastRoomVisited;

	public ComputerPlayer(String player, String color, int index) {
		super(player, color, index);
	}

	public BoardCell pickLocation(HashSet<BoardCell> targets) {
		return null;
	}

	public HashSet<Card> createSuggestion(HashSet<Card> deck) {
		return deck;
	}

	public void updateSeen(Card seen) {

	}

	// For tests
	public void setVisited(int index) {
		lastRoomVisited = index;
	}
}
