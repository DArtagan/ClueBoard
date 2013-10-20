package clue;

import java.util.HashSet;

public class ComputerPlayer extends Player {
	private int lastRoomVisited;

	public ComputerPlayer(String player, String color, int index) {
		super(player, color, index);
	}

	public void pickLocation(HashSet<BoardCell> targets) {

	}

	public void createSuggestion() {

	}

	public void updateSeen(Card seen) {

	}
}
