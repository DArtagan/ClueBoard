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
		int size = GUIBoard.CELL_SIZE;
		g.setColor(Color.decode("0x073642"));
		if (isDoorway()) {
			g.fill3DRect(size*col, size*row, size, size, true);
		} else {
			g.fill3DRect(size*col, size*row, size, size, true);
		}
		g.setColor(Color.RED);
		if (doorDirection == DoorDirection.UP) {
			g.drawLine(col*size, row*size, col*size+size, row*size);
		} else if (doorDirection == DoorDirection.RIGHT) {
			g.drawLine(col*size+size-1, row*size, col*size+size-1, row*size+size);
		} else if (doorDirection == DoorDirection.DOWN) {
			g.drawLine(col*size, row*size+size-1, col*size+size, row*size+size-1);
		} else if (doorDirection == DoorDirection.LEFT) {
			g.drawLine(col*size, row*size, col*size, row*size+size);
		}
	}
}