package clue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUIControl extends JPanel {
	private JTextArea turnDisplay, dieDisplay, suggestionDisplay, resultDisplay;
	private ClueGame clueGame;
	private int turn = 0;

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
		suggestionDisplay = new JTextArea(1,10);
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

	// Menu bar listener
	class NextListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Move players
			int die = new Random().nextInt(GUIBoard.DIE) + 1;
			Player player = clueGame.getPlayers().get(turn % clueGame.getPlayers().size());
			if (player.isComputerPlayer()) {
				clueGame.getBoard().calcTargets(player.getRow(), player.getCol(), die);
				player.move(((ComputerPlayer) player).pickLocation(clueGame.getBoard().getTargets()));
			}
			clueGame.repaint();

			// Turn display
			turnDisplay.setText(player.getName());
			dieDisplay.setText(new Integer(die).toString());

			++turn;
		}
	}

	// Accusation listener
	class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}