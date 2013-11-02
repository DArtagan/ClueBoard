package clue;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUIControl extends JPanel {
	private JTextArea turnDisplay, dieDisplay, suggestionDisplay, resultDisplay;

	public GUIControl() {
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
		add(nextButton);
		JButton accuseButton = new JButton("Make Accusation");
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
}