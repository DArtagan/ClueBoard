package clueTests;

//import static org.junit.Assert.*;
//import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Test;

import clue.BadConfigFormatException;
import clue.Board;
import clue.BoardCell;
import clue.RoomCell;

public class tests {
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	
	@BeforeClass
	public static void setUp()  {
		board = new Board();
		try {
			board.loadConfigFiles("BoardConfiguration.csv", "Legend.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
		// Test retrieving a few from the hash, including the first
		// and last in the file and a few others
		assertEquals("Hallway", rooms.get('H'));
		assertEquals("Closet", rooms.get('X'));
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Dining Room", rooms.get('D'));
		assertEquals("Ball Room", rooms.get('B'));
		assertEquals("Billiards Room", rooms.get('R'));
		assertEquals("Library", rooms.get('L'));
		assertEquals("Study", rooms.get('S'));
		assertEquals("Lounge", rooms.get('O'));
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Television Room", rooms.get('T'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	@Test
	public void FourDoorDirections() {
		// Test one each RIGHT/LEFT/UP/DOWN
		RoomCell room = (RoomCell) board.getCellAt(2, 4);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		
		room = (RoomCell) board.getCellAt(7, 4);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		
		room = (RoomCell) board.getCellAt(1, 18);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = (RoomCell) board.getCellAt(18, 6);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = (RoomCell) board.getCellAt(17, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell  =  board.getCellAt(5, 5);
		assertFalse(cell.isDoorway());		

	}

	@Test
	public void testRoomInitials() {
		assertEquals('C', board.getCellAt(0, 0).getInitial());
		assertEquals('R', board.getCellAt(20, 20).getInitial());
		assertEquals('B', board.getCellAt(10, 20).getInitial());
		assertEquals('O', board.getCellAt(9, 3).getInitial());
		assertEquals('K', board.getCellAt(1, 11).getInitial());
		assertEquals('D', board.getCellAt(3, 19).getInitial());
		assertEquals('T', board.getCellAt(18, 2).getInitial());
		assertEquals('S', board.getCellAt(17, 9).getInitial());
		assertEquals('L', board.getCellAt(17, 14).getInitial());
		assertEquals('X', board.getCellAt(7, 9).getInitial());
		assertEquals('H', board.getCellAt(0, 6).getInitial());
	}
	
	@Test
	public void calcIndexTest(){
		int list = board.calcIndex(0, 0);
		assertEquals(list, 0);
		list = board.calcIndex(5, 5);
		assertEquals(120, list);
		list = board.calcIndex(2, 4);
		assertEquals(list, 50);
		list = board.calcIndex(10, 8);
		assertEquals(list, 238);
		list = board.calcIndex(20, 20);
		assertEquals(list, 480);
		list = board.calcIndex(2, 2);
		assertEquals(list, 48);
	}

	// Test that an exception is thrown for a bad boardConfig file
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, IOException {
		Board b = new Board();
		b.loadConfigFiles("BadFile.csv", "Legend.txt");
	}

	// Test that an exception is thrown for a bad legend file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws IOException, BadConfigFormatException {
		Board b = new Board();
		b.loadConfigFiles("BoardConfiguration.csv", "BadLegend.txt");
	}


}
