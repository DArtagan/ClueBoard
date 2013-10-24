package clue;

import java.util.HashSet;

public class ComputerPlayer extends Player {
	private BoardCell lastRoomVisited;
	private HashSet<Card> seenCards;


	public ComputerPlayer(String player, String color, int index) {
		super(player, color, index);
		seenCards = new HashSet<Card>();
		lastRoomVisited = null;
	}

	public void addCard(Card card) {
		super.addCard(card);
		updateSeen(card);
	}

	public BoardCell pickLocation(HashSet<BoardCell> targets) {
		HashSet<BoardCell> tempTargets = new HashSet<BoardCell>(targets);
		for (BoardCell target : tempTargets) {
			if (target.isDoorway()) {
				if (target == lastRoomVisited) {  // If it hasn't been to that door before
					return target;
				}
			}
		}
		lastRoomVisited = null;
		return lastRoomVisited;
	}

	public boolean isComputerPlayer() {
		return true;
	}

	public HashSet<Card> createSuggestion(HashSet<Card> deck) {
		return null;
	}

	public void updateSeen(Card seen) {
		seenCards.add(seen);
	}

	private BoardCell randomTarget(HashSet<BoardCell> targets) {
		return null;
	}

	// For tests
	public void setVisited(BoardCell cell) {
		lastRoomVisited = cell;
	}

	public void setSeen(HashSet<Card> seen) {
		seenCards = seen;
	}
}
