package clue;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUIControl extends JFrame {
	private JTextArea turnDisplay, dieDisplay, suggestionDisplay, resultDisplay;

	public GUIControl() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Controls");
		setSize(500, 150);
		setLayout(new GridLayout(0, 3));
		createLayout();
	}

	private void createLayout() {
		// Turn Panel
		JPanel turn = new JPanel();
		turnDisplay = new JTextArea(1,10);
		turnDisplay.setEditable(false);
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
		die.add(dieDisplay);
		die.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		add(die);
		// Suggestion Panel
		JPanel suggestion = new JPanel();
		suggestionDisplay = new JTextArea(1,10);
		suggestionDisplay.setEditable(false);
		suggestion.add(suggestionDisplay);
		suggestion.setBorder(new TitledBorder (new EtchedBorder(), "Suggestion"));
		add(suggestion);
		// Result Panel
		JPanel result = new JPanel();
		resultDisplay = new JTextArea(1,10);
		resultDisplay.setEditable(false);
		result.add(resultDisplay);
		result.setBorder(new TitledBorder (new EtchedBorder(), "Result"));
		add(result);
	}


	public static void main(String[] args) {
		GUIControl gui = new GUIControl();
		gui.setVisible(true);
	}
}