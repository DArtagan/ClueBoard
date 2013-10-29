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
		g.setColor(Color.BLACK);
		g.drawRect(col*CELL_SIZE, row*CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}
}
