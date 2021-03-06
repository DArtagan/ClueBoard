package clue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUIControl extends JPanel {
	private JTextArea turnDisplay, dieDisplay;
	protected static JTextArea suggestionDisplay, resultDisplay;
	private ClueGame clueGame;

	public GUIControl(ClueGame game) {
		clueGame = game;
		setSize(500, 150);
		setLayout(new GridLayout(0, 3));
		createLayout();
	}

	private void createLayout() {
		Color jframeColor = getBackground();

		// Turn Panel
		JPanel turn = new JPanel();
		turnDisplay = new JTextArea(1,10);
		turnDisplay.setEditable(false);
		turnDisplay.setBackground(jframeColor);
		turn.add(turnDisplay);
		turn.setBorder(new TitledBorder (new EtchedBorder(), "Turn"));
		add(turn);

		// Buttons
		JButton nextButton = new JButton("Next Turn");
		nextButton.addActionListener(new NextListener());
		add(nextButton);
		JButton accuseButton = new JButton("Make Accusation");
		accuseButton.addActionListener(new AccusationListener());
		add(accuseButton);

		// Die Panel
		JPanel die = new JPanel();
		dieDisplay = new JTextArea(1,10);
		dieDisplay.setEditable(false);
		dieDisplay.setBackground(jframeColor);
		die.add(dieDisplay);
		die.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		add(die);

		// Suggestion Panel
		JPanel suggestion = new JPanel();
		suggestionDisplay = new JTextArea(2,20);
		suggestionDisplay.setLineWrap(true);
		suggestionDisplay.setWrapStyleWord(true);
		suggestionDisplay.setEditable(false);
		suggestionDisplay.setBackground(jframeColor);
		suggestion.add(suggestionDisplay);
		suggestion.setBorder(new TitledBorder (new EtchedBorder(), "Suggestion"));
		add(suggestion);

		// Result Panel
		JPanel result = new JPanel();
		resultDisplay = new JTextArea(1,10);
		resultDisplay.setEditable(false);
		resultDisplay.setBackground(jframeColor);
		result.add(resultDisplay);
		result.setBorder(new TitledBorder (new EtchedBorder(), "Result"));
		add(result);
	}

	class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			suggestionDisplay.setText(null);
			resultDisplay.setText(null);
			if (clueGame.getHumanMoved() || clueGame.getTurn() == 0) {
				int die = new Random().nextInt(GUIBoard.DIE) + 1;
				Player player = clueGame.getPlayers().get(clueGame.getTurn() % clueGame.getPlayers().size());

				// Turn display
				turnDisplay.setText(player.getName());
				dieDisplay.setText(new Integer(die).toString());

				clueGame.getBoard().calcTargets(player.getRow(), player.getCol(), die);

				if (player.isComputerPlayer()) {
					// Accusation
					// Cause there's three cards in the deck that can't be seen
					HashSet<Card> seen = ((ComputerPlayer) player).getSeen();
					HashSet<Card> deck = clueGame.getCards();
					if (seen.size() == deck.size() - 3) {
						HashSet<Card> accusation = new HashSet<Card>(deck);
						for (Card card : seen) {
							if (accusation.contains(card)) {
								accusation.remove(card);
							}
						}
						String room = "";
						String person = "";
						String weapon = "";
						for (Card card : accusation) {
							if (card.getType() == Card.CardType.ROOM) {
								room = card.toString();
							}
							if (card.getType() == Card.CardType.PERSON) {
								person = card.toString();
							}
							if (card.getType() == Card.CardType.WEAPON) {
								weapon = card.toString();
							}
						}
						if (clueGame.checkAccusation(new Solution(person, weapon, room))) {
							JOptionPane.showMessageDialog(clueGame, person + " in the " + room + " with the " + weapon + ". " + player.getName() + " just won the game!", "Winning", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						} else {
							JOptionPane.showMessageDialog(clueGame, person + " in the " + room + " with the " + weapon + ". " + player.getName() + " just guessed wrong.  If this was a real clue game, they'd be disqualified.", "Nope", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					// Move and suggest
					BoardCell location = ((ComputerPlayer) player).pickLocation(clueGame.getBoard().getTargets());
					player.move(location);
					if (clueGame.getBoard().getCellAt(player.getRow(), player.getCol()).isRoom()) {
						HashSet<Card> suggestion = ((ComputerPlayer) player).createSuggestion(clueGame.getCards(), clueGame.getBoard().getRooms());
						for (Card card : suggestion) {
							if(card != null && card.getType() == Card.CardType.PERSON) {
								for(Player accused : clueGame.getPlayers()) {
									if (accused.getName() == card.toString()) {
										accused.move(location);
									}
								}
							}
						}
						suggestionDisplay.setText(suggestion.toString());
						Card disprove = clueGame.handleSuggestion(suggestion, player);
						if (disprove == null) {
							resultDisplay.setText("No new clue.");
						} else {
							resultDisplay.setText(disprove.toString());
						}
					}
					clueGame.getBoard().setTargets(null);
				} else {
					clueGame.getBoard().getTargets();
					clueGame.setHumanMoved(false);
					//System.out.println(clueGame.getHumanMoved());
					if (clueGame.getSuggestionWindow() != null) {
						suggestionDisplay.setText(clueGame.getSuggestionWindow().getSuggestion().toString());
						if (clueGame.getSuggestionWindow().getDisprove().toString() != null) {
							resultDisplay.setText(clueGame.getSuggestionWindow().getDisprove().toString());
						}
					}
				}

				repaint();
				clueGame.repaint();
				clueGame.incrementTurn();
			} else if (clueGame.getHumanMoved() == false) {
				JOptionPane.showMessageDialog(clueGame, "Finish your turn!", "No", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Player player;
			GUIAccusation accusationWindow = new GUIAccusation(clueGame);
			if (clueGame.getTurn() != 0) {
				player = clueGame.getPlayers().get(clueGame.getTurn() % clueGame.getPlayers().size() - 1);
			} else {
				player = clueGame.getPlayers().get(clueGame.getTurn() % clueGame.getPlayers().size());
			}
			System.out.println(clueGame.getHumanMoved());
			System.out.println(player.getName());
			if (!(player.isComputerPlayer()) && !(clueGame.getHumanMoved()) && clueGame.getTurn() != 0) {
				accusationWindow.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(clueGame, "Wait your turn!", "Wrong turn.", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}