package clue;

import java.util.HashSet;

public class ClueGame {
	Solution solution;

	public ClueGame() {

	}

	public void deal() {

	}

	public void loadConfigFiles(String playerConfig, String weaponConfig) {

	}

	public void selectAnswer() {

	}

	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {

	}

	public boolean checkAccusation(Solution solution) {
		return false;
	}


	// These methods to be used by unit tests only.
	public HashSet<Player> getPlayers() {
		return null;
	}

	public void setSolution(Solution accusation) {
		solution = accusation;
	}

	public HashSet<Card> getCards() {
		return null;
	}
}
