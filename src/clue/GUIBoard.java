package clue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUIBoard extends JFrame {
	public final static int CELL_SIZE = 20;

	private Board board;
	private ClueGame clueGame;
	private GUINotes notes;

	public GUIBoard() {
		// Setup
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Board");
		try {
			clueGame = new ClueGame("ClueBoard.csv", "legend.txt");
		} catch (IOException | BadConfigFormatException e) {
			e.printStackTrace();
		}
		try {
			clueGame.loadConfigFiles("playerConfig.txt", "weaponConfig.txt");
		} catch (BadConfigFormatException | IOException e) {
			e.printStackTrace();
		}

		// Draw board
		add(clueGame);

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

		notes = new GUINotes(clueGame);
		notes.pack();
	}

	// File menu in menu bar
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}

	// Menu bar listener
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	// GUINotes listener
	private JMenuItem createFileNotesItem() {
		JMenuItem item = new JMenuItem("Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	public static void main(String[] args) {
		GUIBoard gui = new GUIBoard();
		gui.pack();
		gui.setVisible(true);
	}
}
