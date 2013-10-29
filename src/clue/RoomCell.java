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
		g.setColor(Color.BLACK);
		g.fillRect(CELL_SIZE*col, CELL_SIZE*row, CELL_SIZE, CELL_SIZE);
		g.setColor(Color.RED);
		if(doorDirection == DoorDirection.UP) {
			g.drawLine(col*CELL_SIZE, row*CELL_SIZE, col*CELL_SIZE+CELL_SIZE, row*CELL_SIZE);
		} else if (doorDirection == DoorDirection.RIGHT) {
			g.drawLine(col*CELL_SIZE+CELL_SIZE, row*CELL_SIZE, col*CELL_SIZE+CELL_SIZE, row*CELL_SIZE+CELL_SIZE);
		} else if (doorDirection == DoorDirection.DOWN) {
			g.drawLine(col*CELL_SIZE, row*CELL_SIZE+CELL_SIZE, col*CELL_SIZE+CELL_SIZE, row*CELL_SIZE+CELL_SIZE);
		} else if (doorDirection == DoorDirection.LEFT) {
			g.drawLine(col*CELL_SIZE, row*CELL_SIZE, col*CELL_SIZE, row*CELL_SIZE+CELL_SIZE);
		}
	}
}