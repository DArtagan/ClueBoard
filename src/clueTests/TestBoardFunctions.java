package clueTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import clue.BadConfigFormatException;
import clue.Board;
import clue.BoardCell;
import clue.RoomCell;

public class TestBoardFunctions {
	Board board;
	BoardCell room;
	int rows;
	int columns;

	@Before
	public void setUp() throws IOException, BadConfigFormatException {
		board = new Board();
		board.loadConfigFiles("ClueBoard.csv", "legend.txt");
		rows = 23;
		columns = 23;
	}

	@Test
	public void testRoomsMapNumberOfRooms() {
		int numRooms = 11;
		assertEquals(numRooms, board.getRooms().size());
	}

	@Test
	public void testMappingWorks() {
		String roomName = "Laboratory";
		char roomInitial = 'Y';
		assertEquals(roomName, board.getRooms().get(roomInitial));
		
		roomName = "Closet";
		roomInitial = 'X';
		assertEquals(roomName, board.getRooms().get(roomInitial));
		
		roomName = "Library";
		roomInitial = 'I';
		assertEquals(roomName, board.getRooms().get(roomInitial));
	}

	@Test
	public void testCorrectNumberOfRowsAndColumns() {
		assertEquals(rows, board.getNumRows());
		assertEquals(columns, board.getNumColumns());
	}

	@Test
	public void testDoorHasCorrectDirection() {
		room = board.getCellAt(6, 1);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, ((RoomCell) room).getDoorDirection());

		room = board.getCellAt(0, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, ((RoomCell) room).getDoorDirection());

		room = board.getCellAt(15, 13);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, ((RoomCell) room).getDoorDirection());

		room = board.getCellAt(15, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, ((RoomCell) room).getDoorDirection());

		room = board.getCellAt(10, 10);
		assertFalse(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.NONE, ((RoomCell) room).getDoorDirection());
	}

	@Test
	public void testRoomAssignment() {
		char roomInitial = 'F';
		assertEquals(roomInitial, board.getCellAt(6, 5).getInitial());
		
		roomInitial = 'S';
		assertEquals(roomInitial, board.getCellAt(12, 3).getInitial());
		
		roomInitial = 'H';
		assertEquals(roomInitial, board.getCellAt(9, 20).getInitial());
	}

	@Test
	public void testCalcIndex() {
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(528, board.calcIndex(rows-1, columns-1));
		assertEquals(342, board.calcIndex(14, 20));
	}

	@Test (expected = BadConfigFormatException.class)
	public void testBadConfigFormatException() throws BadConfigFormatException, IOException {
		// board has bad config files!
		board.loadConfigFiles("CR_ClueLayoutBadColumns.csv", "legend.txt");
	}
}
