package clue;

import java.util.HashSet;
import java.util.Random;

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
		seenCards.add(card);
	}

	public BoardCell pickLocation(HashSet<BoardCell> targets) {
		HashSet<BoardCell> tempTargets = new HashSet<BoardCell>(targets);
		for (BoardCell target : tempTargets) {
			if (target.isDoorway()) {
				if (target != lastRoomVisited) {  // If it hasn't been to that door before
					lastRoomVisited = target;
					return target;
				}
			}
		}
		BoardCell target = randomTarget(targets);
		if (target.isDoorway()){
			lastRoomVisited = target;
		}
		return target;
	}

	public HashSet<Card> createSuggestion(HashSet<Card> deck) {
		return null;
	}

	public void updateSeen(Card seen) {

	}

	private BoardCell randomTarget(HashSet<BoardCell> targets) {
		int size = targets.size();
		if (size <= 0) {
			return null;
		}
		int choice = new Random().nextInt(size);
		int i = 0;
		for(BoardCell target : targets) {
			if (i == choice) {
				return target;
			}
			++i;
		}
		return null;
	}

	// For tests
	public void setVisited(BoardCell cell) {
		lastRoomVisited = cell;
	}

	public void setSeen(HashSet<Card> seen) {
		seenCards.addAll(seen);
	}
}
