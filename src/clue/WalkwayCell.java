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
		g.setColor(Color.BLUE);
		g.drawRect(col*20, row*20, 20, 20);
	}
}
