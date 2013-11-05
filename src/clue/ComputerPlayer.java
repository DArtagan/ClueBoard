package clue;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer extends Player {
	private BoardCell lastRoomVisited;
	private HashSet<Card> seenCards;


	public ComputerPlayer(String player, String color, int row, int col) {
		super(player, color, row, col);
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

	public boolean isComputerPlayer() {
		return true;
	}

	public HashSet<Card> createSuggestion(HashSet<Card> deck, Map<Character, String> rooms) {
		HashSet<Card> suggestion = new HashSet<Card>();
		HashSet<Card> person = new HashSet<Card>();
		HashSet<Card> weapon = new HashSet<Card>();

		// Create and split not-seen list into players and weapons
		for (Card card : deck) {
			if (!(seenCards.contains(card))) {
				if (card.getType() == Card.CardType.PERSON) {
					person.add(card);
				} else if (card.getType() == Card.CardType.WEAPON) {
					weapon.add(card);
				}
			}
		}

		// Note: By convention the suggestion is given in order of person room, weapon
		suggestion.add(ClueGame.randomCard(person));
		suggestion.add(new Card(rooms.get(lastRoomVisited.getInitial()), Card.CardType.ROOM));
		suggestion.add(ClueGame.randomCard(weapon));
		return suggestion;
	}

	public void updateSeen(Card seen) {
		seenCards.add(seen);
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
		seenCards = seen;
	}
}
