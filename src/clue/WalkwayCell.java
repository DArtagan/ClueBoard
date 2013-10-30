package clue;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	protected static final char WALKWAY = 'W';

	public WalkwayCell(int row, int col) {
		super(row, col, WALKWAY);
	}

	@Override
	public boolean isWalkway() {
		return true;
	}

	public void draw(Graphics g, Board board) {
		super.paintComponent(g);
		int size = GUIBoard.CELL_SIZE;
		g.setColor(Color.lightGray);
		g.fill3DRect(col*size, row*size, size, size, false);
	}
}
