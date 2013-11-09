package clue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import clue.Card.CardType;

public class GUIAccusation extends JDialog {
	private JTextArea roomText, personText, weaponText;
	JComboBox<String> roomGuess;
	private JComboBox<String> personGuess, weaponGuess;
	private JButton submit, cancel;
	private ClueGame clueGame;
	private HashSet<Card> suggestion;
	private Card disprove;
	private Card room;

	public GUIAccusation(ClueGame game) {
		clueGame = game;
		suggestion = new HashSet<Card>();
		setSize(500, 150);
		setLayout(new GridLayout(0, 2));
		createLayout();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
	}

	private void createLayout() {
		Color jframeColor = getBackground();

		// Room
		roomText = new JTextArea(1,10);
		roomText.setEditable(false);
		roomText.setBackground(jframeColor);
		roomText.setText("Room");
		add(new JPanel().add(roomText));

		roomGuess = new JComboBox<String>();
		for(Card card : clueGame.getCards()) {
			if(card.getType() == CardType.ROOM) {
				roomGuess.addItem(card.toString());
			}
		}
		add(new JPanel().add(roomGuess));

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
			disprove = null;
			if (e.getSource() == submit) {
				String person = personGuess.getSelectedItem().toString();
				String weapon = weaponGuess.getSelectedItem().toString();
				String room = roomGuess.getSelectedItem().toString();
				if (clueGame.checkAccusation(new Solution(person, weapon, room))) {
					JOptionPane.showMessageDialog(clueGame, person + " in the " + room + " with the " + weapon + ". " + person + " just won the game!", "Winning", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(clueGame, person + " in the " + room + " with the " + weapon + ". " + person + " just guessed wrong.  If this was a real clue game, you'd be disqualified.", "Nope", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			setVisible(false);
		}
	}
}
