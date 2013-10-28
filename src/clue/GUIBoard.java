package clue;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class GUIBoard extends JFrame {
	private Board board;
	private ClueGame clueGame;

	public GUIBoard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Board");
		setSize(700, 700);
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
		add(clueGame.getBoard(), BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		GUIBoard gui = new GUIBoard();
		gui.setVisible(true);
	}
}
