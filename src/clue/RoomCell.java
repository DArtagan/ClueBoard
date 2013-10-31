package clue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

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
		// Walls
		BoardCell cell;
		LinkedList<Point> myCorners = new LinkedList<Point>();
		LinkedList<Point> otherCorners = new LinkedList<Point>();
		System.out.println(board.calcIndex(row, col));
		for (Integer cellIndex : board.getAdjList(board.calcIndex(row, col))) {
			if ((cell = board.getCellAt(cellIndex)).isRoom()) {
				if (cell.getInitial() != getInitial()) {
					for (int i = 0; i < 1; ++i) {
						for (int j = 0; j < 1; ++j) {
							myCorners.add(new Point(col*i + col, row*j + row));
							otherCorners.add(new Point(cell.col*i + cell.col, cell.row*j + cell.row));
						}
					}
					LinkedList<Point> matchingCorners = new LinkedList<Point>(myCorners);
					for (Point point : myCorners) {
						if (!otherCorners.contains(point)) {
							matchingCorners.remove(point);
						}
					}
					g.drawLine(matchingCorners.getFirst().x*size, matchingCorners.getFirst().y*size, matchingCorners.getLast().x*size, matchingCorners.getLast().y*size+size);
				}
			}
		}
	}
}