package clue;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUINotes extends JDialog {
	private JButton button1, button2;
	public GUINotes(ClueGame clueGame) {
		setTitle("Notes");
		//setSize(200, 100);
		setLayout(new GridLayout(0, 2));
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		JPanel people = new JPanel();
		JPanel rooms = new JPanel();
		JPanel weapons = new JPanel();
		JPanel peopleGuess = new JPanel();
		JPanel roomsGuess = new JPanel();
		JPanel weaponsGuess = new JPanel();

		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		people.setLayout(new GridLayout(0, 2));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		rooms.setLayout(new GridLayout(0, 2));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weapons.setLayout(new GridLayout(0, 2));
		peopleGuess.setBorder(new TitledBorder(new EtchedBorder(), "People Guess"));
		roomsGuess.setBorder(new TitledBorder(new EtchedBorder(), "Rooms Guess"));
		weaponsGuess.setBorder(new TitledBorder(new EtchedBorder(), "Weapons Guess"));

		for(Player player : clueGame.getPlayers()) {
			people.add(new JCheckBox(player.getName()));
		}

		for (Character key : clueGame.getBoard().getRooms().keySet()) {
			if (key != Board.CLOSET && key != Board.WALKWAY) {
				rooms.add(new JCheckBox(clueGame.getBoard().getRooms().get(key)));
			}
		}

		for(Card weapon : clueGame.getWeapons()) {
			weapons.add(new JCheckBox(weapon.toString()));
		}

		JComboBox<String> peopleCombo = new JComboBox<String>();
		peopleCombo.addItem("Undecided");
		for(Player player : clueGame.getPlayers()) {
			peopleCombo.addItem(player.getName());
		}
		peopleGuess.add(peopleCombo);


		JComboBox<String> roomsCombo = new JComboBox<String>();
		roomsCombo.addItem("Undecided");
		for (Character key : clueGame.getBoard().getRooms().keySet()) {
			if (key != Board.CLOSET && key != Board.WALKWAY) {
				roomsCombo.addItem(clueGame.getBoard().getRooms().get(key));
			}
		}
		roomsGuess.add(roomsCombo);

		JComboBox<String> weaponsCombo = new JComboBox<String>();
		weaponsCombo.addItem("Undecided");
		for(Card weapon : clueGame.getWeapons()) {
			weaponsCombo.addItem(weapon.toString());
		}
		weaponsGuess.add(weaponsCombo);

		add(people);
		add(peopleGuess);
		add(rooms);
		add(roomsGuess);
		add(weapons);
		add(weaponsGuess);
	}
}
