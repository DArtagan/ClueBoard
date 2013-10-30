package clue;

import java.awt.BorderLayout;
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
		setSize(clueGame.getBoard().getNumColumns()*CELL_SIZE+50, clueGame.getBoard().getNumRows()*CELL_SIZE+100);

		// Draw board
		add(clueGame, BorderLayout.CENTER);

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());

		// Custom Dialogue

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
				GUINotes dialogue = new GUINotes();
				dialogue.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}

	public static void main(String[] args) {
		GUIBoard gui = new GUIBoard();
		gui.setVisible(true);
	}
}
