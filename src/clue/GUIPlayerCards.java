package clue;

import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import clue.Card.CardType;

public class GUIPlayerCards extends JPanel {
	JTextArea myPeopleCards;
	JTextArea myRoomCards;
	JTextArea myWeaponCards;

	public GUIPlayerCards(HashSet<Card> cards) {
		myPeopleCards = new JTextArea(1,10);
		myRoomCards = new JTextArea(1,10);
		myWeaponCards = new JTextArea(1,10);
		myPeopleCards.setEditable(false);
		myRoomCards.setEditable(false);
		myWeaponCards.setEditable(false);
		add(myPeopleCards);
		add(myRoomCards);
		add(myWeaponCards);

		// Setup and read cards
		String peopleCards;
		String roomCards;
		String
		for (Card card : cards) {
			if(card.getType() == CardType.PERSON) {

			} else if (card.getType() == CardType.ROOM) {

			} else if (card.getType() == CardType.WEAPON) {

			}
			myPeopleCards.setText("Hi");
			myRoomCards.setText("Hi");
			myWeaponCards.setText("Hi");
		}
	}

	// Update myCards
	public void updateCards() {
		myPeopleCards.setText("Hi");
		myRoomCards.setText("Hi");
		myWeaponCards.setText("Hi");
	}
}
