package clue;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import clue.Card.CardType;

public class ClueGame {
	public Solution solution;
	HashSet<Player> players;
	HashSet<Card> weapons;
	HashSet<Card> deck;
	Board board;

	public ClueGame(String layoutName, String legendName) throws IOException, BadConfigFormatException {
		players = new HashSet<Player>();
		weapons = new HashSet<Card>();
		board = new Board();
		board.loadConfigFiles(layoutName, legendName);
	}

	public Card randomCard(HashSet<Card> set) {
		int size = set.size();
		int choice = new Random().nextInt(size);
		int i = 0;
		for(Card card : set) {
			if (i == choice) {
				return card;
			}
			++i;
		}
		return null;
	}

	public void deal() {
		// Set Solution
		Card card = null;
		String[] tempSolution = new String[3];
		int i = 0;
		for (CardType type : Card.CardType.values()) {
			while (card.getType() != type) {
				card = randomCard(deck);
			}
			deck.remove(card);
			tempSolution[i] = card.name;
			i++;
		}
		solution = new Solution(tempSolution[0], tempSolution[1], tempSolution[2]);

		// Give to players
		while (!(deck.isEmpty())) {
			for (Player player : players) {
				card = randomCard(deck);
				if (card != null) {
					player.addCard(card);
					deck.remove(card);
				}
			}
		}
	}

	public void loadConfigFiles(String playerConfig, String weaponConfig) throws BadConfigFormatException, IOException {
		// Load player config.
		deck = new HashSet<Card>();
		FileReader playerReader = new FileReader(playerConfig);
		Scanner playerScanner = new Scanner(playerReader);
		String line, fields[];

		// Is there a good way to avoid this duplication?
		if (playerScanner.hasNextLine()) {
			// First player is human.
			line = playerScanner.nextLine();
			fields = line.split(", ");
			if (fields.length >= 3) {
				try {
					players.add(new HumanPlayer(fields[0], fields[1], Integer.parseInt(fields[2])));
				} catch (NumberFormatException e) {
					playerScanner.close();
					playerReader.close();
					throw new BadConfigFormatException("Invalid starting index in player config.");
				}
			}
		}
		while (playerScanner.hasNextLine()) {
			// The rest are computer.
			line = playerScanner.nextLine();
			fields = line.split(", ");
			if (fields.length >= 3) {
				try {
					players.add(new ComputerPlayer(fields[0], fields[1], Integer.parseInt(fields[2])));
				} catch (NumberFormatException e) {
					playerScanner.close();
					playerReader.close();
					throw new BadConfigFormatException("Invalid starting index in player config.");
				}
			}
		}
		playerScanner.close();
		playerReader.close();

		// Load weapon config.
		FileReader weaponReader = new FileReader(weaponConfig);
		Scanner weaponScanner = new Scanner(weaponReader);

		while (weaponScanner.hasNextLine()) {
			line = weaponScanner.nextLine();
			weapons.add(new Card(line, Card.CardType.WEAPON));
		}
		weaponScanner.close();
		weaponReader.close();

		generateDeck();
	}

	public void selectAnswer() {

	}

	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {

	}

	public boolean checkAccusation(Solution solution) {
		return this.solution.equals(solution);
	}

	private void generateDeck() {
		// Add all rooms.
		for (Character key : board.getRooms().keySet()) {
			if (key != Board.CLOSET && key != Board.WALKWAY) {
				deck.add(new Card(board.getRooms().get(key), Card.CardType.ROOM));
			}
		}

		// Add all players.
		for (Player player : players) {
			deck.add(new Card(player.getName(), Card.CardType.PERSON));
		}

		// Add all weapons.
		deck.addAll(weapons);
	}

	// These methods to be used by unit tests only.
	public HashSet<Player> getPlayers() {
		return players;
	}

	public void setSolution(Solution accusation) {
		solution = accusation;
	}

	public HashSet<Card> getCards() {
		return deck;
	}
}
