package clue;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUISuggestion extends JFrame {
	private JTextArea roomText, roomGuess;
	private ClueGame clueGame;

	public GUISuggestion(String room) {
		setSize(500, 150);
		setLayout(new GridLayout(0, 3));
		createLayout(room);
	}

	private void createLayout(String room) {
		Color jframeColor = getBackground();

		// Room
		roomText = new JTextArea(1,10);
		roomText.setEditable(false);
		roomText.setBackground(jframeColor);
		roomText.setText("Room");
		add(new JPanel().add(roomText));

		roomGuess = new JTextArea(1,10);
		roomGuess.setEditable(false);
		roomGuess.setBackground(jframeColor);
		roomGuess.setText(room.toString());
		add(new JPanel().add(roomGuess));
	}
}
