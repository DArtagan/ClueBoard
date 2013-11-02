package clue;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clue.Card.CardType;

public class GUIPlayerCards extends JPanel {
	JTextArea myPeopleCards;
	JTextArea myRoomCards;
	JTextArea myWeaponCards;

	public GUIPlayerCards(HashSet<Card> cards) {
		setLayout(new GridLayout(3,1));

		JPanel people = new JPanel();
		JPanel rooms = new JPanel();
		JPanel weapons = new JPanel();

		people.setLayout(new GridLayout(0,1));
		rooms.setLayout(new GridLayout(0,1));
		weapons.setLayout(new GridLayout(0,1));

		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));

		Color jframeColor = getBackground();

		// Setup and read cards
		for (Card card : cards) {
			JTextArea text = new JTextArea(1, 10);
			text.setText(card.toString());
			text.setBackground(jframeColor);
			text.setEditable(false);
			if(card.getType() == CardType.PERSON) {
				people.add(text);
			} else if (card.getType() == CardType.ROOM) {
				rooms.add(text);
			} else if (card.getType() == CardType.WEAPON) {
				weapons.add(text);
			}
		}

		add(people);
		add(rooms);
		add(weapons);
	}
}