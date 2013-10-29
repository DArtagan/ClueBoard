package clue;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public static enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	public DoorDirection doorDirection = DoorDirection.NONE;

	RoomCell(int row, int col, char initial, char direction) {
		super(row, col, initial);
		switch (direction) {
		case 'U': doorDirection = DoorDirection.UP; break;
		case 'D': doorDirection = DoorDirection.DOWN; break;
		case 'L': doorDirection = DoorDirection.LEFT; break;
		case 'R': doorDirection = DoorDirection.RIGHT; break;
		default: doorDirection = DoorDirection.NONE; break;
		}
	}

	RoomCell(int row, int col, char initial) {
		super(row, col, initial);
		doorDirection = DoorDirection.NONE;
	}

	@Override
	public boolean isRoom() {
		return true;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	@Override
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) {
			return false;
		}
		return true;
	}

	public void draw(Graphics g, Board board) {
		super.paintComponent(g);
		if (isDoorway()) {
			g.setColor(Color.GREEN);
			g.fill3DRect(20*col, 20*row, 20, 20, false);
		} else {
			g.setColor(Color.BLUE);
			g.fill3DRect(20*col, 20*row, 20, 20, false);
		}
	}
}