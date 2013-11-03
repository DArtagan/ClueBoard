package clue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

public abstract class BoardCell extends JPanel {

	private char initial;
	protected int row;
	protected int col;

	public BoardCell(int theRow, int theCol, char initial) {
		this.initial = initial;
		this.row = theRow;
		this.col = theCol;
	}

	public char getInitial() {
		return initial;
	}

	public boolean isWalkway() {
		return false;
	}

	public boolean isRoom() {
		return false;
	}

	public boolean isDoorway() {
		return false;
	}

	public abstract void draw(Graphics g, Board board);

	public void draw(Graphics g, Board board, Color color) {
		super.paintComponent(g);
		int size = GUIBoard.CELL_SIZE;
		g.setColor(color);
		g.fill3DRect(col*size, row*size, size, size, false);
	}

	public boolean containsClick(int mouseX, int mouseY) {
		int size = GUIBoard.CELL_SIZE;
		Rectangle rect = new Rectangle(this.col*size, this.row*size, size, size);
		if (rect.contains(new Point(mouseX, mouseY))) {
			return true;
		}
		return false;
	}

}