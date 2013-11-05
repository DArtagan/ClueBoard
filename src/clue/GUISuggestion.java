package clue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUISuggestion extends JFrame {
	private JTextArea roomText, roomGuess, personText, weaponText;
	private JComboBox<String> personGuess, weaponGuess;
	private JButton submit, cancel;
	private ClueGame clueGame;
	private HashSet<Card> suggestion;
	private Card disprove;
	private Card room;

	public GUISuggestion(ClueGame game, Card card) {
		clueGame = game;
		room = card;
		suggestion = new HashSet<Card>();
		setSize(500, 150);
		setLayout(new GridLayout(0, 2));
		createLayout(room);
	}

	private void createLayout(Card room) {
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

		suggestion.add(room);

		// Person
		personText = new JTextArea(1,10);
		personText.setEditable(false);
		personText.setBackground(jframeColor);
		personText.setText("Person");
		add(new JPanel().add(personText));

		personGuess = new JComboBox<String>();
		for(Player player : clueGame.getPlayers()) {
			personGuess.addItem(player.getName());
		}
		personGuess.addActionListener(new SuggestionListener());
		add(new JPanel().add(personGuess));

		// Weapon
		weaponText = new JTextArea(1,10);
		weaponText.setEditable(false);
		weaponText.setBackground(jframeColor);
		weaponText.setText("Weapon");
		add(new JPanel().add(weaponText));

		weaponGuess = new JComboBox<String>();
		for(Card player : clueGame.getWeapons()) {
			weaponGuess.addItem(player.toString());
		}
		personGuess.addActionListener(new SuggestionListener());
		add(new JPanel().add(weaponGuess));

		// Buttons
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		submit.addActionListener(new ButtonListener());
		cancel.addActionListener(new ButtonListener());
		add(submit);
		add(cancel);
	}

	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submit) {
				suggestion.add(new Card(personGuess.getSelectedItem().toString(), Card.CardType.PERSON));
				suggestion.add(new Card(weaponGuess.getSelectedItem().toString(), Card.CardType.WEAPON));
				disprove = clueGame.handleSuggestion(suggestion, clueGame.getPlayers().get(clueGame.getTurn() % clueGame.getPlayers().size()));
			}
			setVisible(false);
		}
	}

	class SuggestionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == personGuess) {
				suggestion.add(new Card(personGuess.getSelectedItem().toString(), Card.CardType.PERSON));
			} else if (e.getSource() == weaponGuess) {
				suggestion.add(new Card(weaponGuess.getSelectedItem().toString(), Card.CardType.WEAPON));
			}
		}
	}

	public HashSet<Card> getSuggestion() {
		return suggestion;
	}

	public Card getDisprove() {
		return disprove;
	}
}
