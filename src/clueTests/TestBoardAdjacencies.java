package clueTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clue.BadConfigFormatException;
import clue.Board;
import clue.BoardCell;

public class TestBoardAdjacencies {
	private static Board board;
	LinkedList<Integer> list;
	Set<BoardCell> targets;

	@Before
	public void setUp() {
		board = new Board();
		try {
			board.loadConfigFiles("ClueBoard.csv", "legend.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		board.calcAdjacencies();
		list = null;
		targets = null;
	}

	// Red: adjacency list tests, inside room
	@Test
	public void testAdjacenciesInsideRooms1() {
		list = board.getAdjList(board.calcIndex(1, 1));
		Assert.assertEquals(0, list.size());
	}
	
	@Test
	public void testAdjacenciesInsideRooms2() {
		list = board.getAdjList(board.calcIndex(4, 18));
		Assert.assertEquals(0, list.size());
	}
	
	@Test
	public void testAdjacenciesInsideRooms3() {
		list = board.getAdjList(board.calcIndex(11, 6));
		Assert.assertEquals(0, list.size());
	}
	
	@Test
	public void testAdjacenciesInsideRooms4() {
		list = board.getAdjList(board.calcIndex(22, 11));
		Assert.assertEquals(0, list.size());
	}

	// Purple: adjacency list tests, room exits
	@Test
	public void testAdjacenciesRoomExits1() {
		list = board.getAdjList(board.calcIndex(5, 12));
		Assert.assertTrue(list.contains(board.calcIndex(6, 12)));
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testAdjacenciesRoomExits2() {
		list = board.getAdjList(board.calcIndex(10, 18));
		Assert.assertTrue(list.contains(board.calcIndex(10, 17)));
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testAdjacenciesRoomExits3() {
		list = board.getAdjList(board.calcIndex(11, 7));
		Assert.assertTrue(list.contains(board.calcIndex(11, 8)));
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testAdjacenciesRoomExits4() {
		list = board.getAdjList(board.calcIndex(16, 16));
		Assert.assertTrue(list.contains(board.calcIndex(15, 16)));
		Assert.assertEquals(1, list.size());
	}

	// Green: adjacency list tests beside room entrance
	@Test
	public void testAdjacenciesRoomEntrances1() {
		list = board.getAdjList(board.calcIndex(6, 13));
		assertTrue(list.contains(board.calcIndex(5, 13)));
		assertTrue(list.contains(board.calcIndex(6, 14)));
		assertTrue(list.contains(board.calcIndex(7, 13)));
		assertTrue(list.contains(board.calcIndex(6, 12)));
		assertEquals(4, list.size());
	}
	
	@Test
	public void testAdjacenciesRoomEntrances2() {
		list = board.getAdjList(board.calcIndex(10, 17));
		assertTrue(list.contains(board.calcIndex(9, 17)));
		assertTrue(list.contains(board.calcIndex(10, 18)));
		assertTrue(list.contains(board.calcIndex(11, 17)));
		assertTrue(list.contains(board.calcIndex(10, 16)));
		assertEquals(4, list.size());
	}
		
	@Test
	public void testAdjacenciesRoomEntrances3() {
		list = board.getAdjList(board.calcIndex(15, 14));
		assertTrue(list.contains(board.calcIndex(14, 14)));
		assertTrue(list.contains(board.calcIndex(15, 15)));
		assertTrue(list.contains(board.calcIndex(15, 13)));
		assertEquals(3, list.size());
	}
		
	@Test
	public void testAdjacenciesRoomEntrances4() {
		list = board.getAdjList(board.calcIndex(14, 8));
		assertTrue(list.contains(board.calcIndex(13, 8)));
		assertTrue(list.contains(board.calcIndex(14, 9)));
		assertTrue(list.contains(board.calcIndex(15, 8)));
		assertTrue(list.contains(board.calcIndex(14, 7)));
		assertEquals(4, list.size());
	}
		
	@Test
	public void testAdjacenciesRoomEntrances5() {
		list = board.getAdjList(board.calcIndex(4, 15));
		assertTrue(list.contains(board.calcIndex(3, 15)));
		assertTrue(list.contains(board.calcIndex(5, 15)));
		assertEquals(2, list.size());
	}

	// Light purple: walkway scenarios
	@Test
	public void testAdjacenciesWalkways1() {
		list = board.getAdjList(board.calcIndex(0, 15));
		assertTrue(list.contains(board.calcIndex(1, 15)));
		assertEquals(1, list.size());
	}
	@Test
	public void testAdjacenciesWalkways2() {
		list = board.getAdjList(board.calcIndex(5, 18));
		assertTrue(list.contains(board.calcIndex(5, 19)));
		assertTrue(list.contains(board.calcIndex(6, 18)));
		assertTrue(list.contains(board.calcIndex(5, 17)));
		assertEquals(3, list.size());
	}
	@Test
	public void testAdjacenciesWalkways3() {
		list = board.getAdjList(board.calcIndex(7, 22));
		assertTrue(list.contains(board.calcIndex(6, 22)));
		assertTrue(list.contains(board.calcIndex(7, 21)));
		assertEquals(2, list.size());
	}
	@Test
	public void testAdjacenciesWalkways4() {
		list = board.getAdjList(board.calcIndex(10, 16));
		assertTrue(list.contains(board.calcIndex(9, 16)));
		assertTrue(list.contains(board.calcIndex(10, 17)));
		assertTrue(list.contains(board.calcIndex(11, 16)));
		assertTrue(list.contains(board.calcIndex(10, 15)));
		assertEquals(4, list.size());
	}
		
	@Test
	public void testAdjacenciesWalkways5() {
		list = board.getAdjList(board.calcIndex(16, 0));
		assertTrue(list.contains(board.calcIndex(15, 0)));
		assertTrue(list.contains(board.calcIndex(16, 1)));
		assertEquals(2, list.size());
	}

	// Light blue: test targets
	@Test
	public void testTargetsOneStep() {
		Set<BoardCell> targets = board.getTargets(board.calcIndex(4, 10), 1);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 10))));
		assertEquals(2, targets.size());
		
		targets = board.getTargets(board.calcIndex(14, 13), 1);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 12))));
		assertEquals(3, targets.size());
	}

	// Light blue: test targets
	@Test
	public void testTargetsTwoSteps() {
		Set<BoardCell> targets = board.getTargets(board.calcIndex(4, 10), 2);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 10))));
		assertEquals(3, targets.size());
		
		targets = board.getTargets(board.calcIndex(14, 13), 2);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 15))));
		assertEquals(5, targets.size());

		targets = board.getTargets(board.calcIndex(8, 9), 2);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 8))));
		assertEquals(6, targets.size());
		
		targets = board.getTargets(board.calcIndex(15, 4), 2);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 2))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 5))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 3))));
		assertEquals(4, targets.size());
	}

	// Light blue: test targets
	@Test
	public void testTargetsThreeSteps() {
		targets = board.getTargets(board.calcIndex(4, 10), 3);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 10))));
		assertEquals(4, targets.size());
		
		targets = board.getTargets(board.calcIndex(14, 13), 3);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 16))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 13))));
		assertEquals(9, targets.size());

		targets = board.getTargets(board.calcIndex(8, 9), 3);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 6))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 8))));
		assertEquals(10, targets.size());
	
		targets = board.getTargets(board.calcIndex(15, 4), 3);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 1))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 3))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 5))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 2))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 6))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 5))));
		assertEquals(8, targets.size());
	}

	// Light blue: test targets
	@Test
	public void testTargetsFourSteps() {
		targets = board.getTargets(board.calcIndex(4, 10), 4);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 11))));
		assertEquals(7, targets.size());
	
		targets = board.getTargets(board.calcIndex(14, 13), 4);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 16))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 17))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 13)))); // Door
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 16))));
		assertEquals(12, targets.size());

		targets = board.getTargets(board.calcIndex(8, 9), 4);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 6))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 5))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 9))));
		assertEquals(14, targets.size());
	
		targets = board.getTargets(board.calcIndex(15, 4), 4);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 6))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 5))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 0))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 2))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 1))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 3))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 1)))); // Door
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 2)))); // Door
		assertEquals(10, targets.size());
	}

	// Black: enter room targets
	@Test
	public void testTargetsIntoRoom1() {
		targets = board.getTargets(board.calcIndex(7, 3), 3);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 1))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 0))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 1))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 2))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 3))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 6))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 5))));
		assertEquals(8, targets.size());
	}

	// Black: enter room targets
	@Test
	public void testTargetsIntoRoom2() {
		targets = board.getTargets(board.calcIndex(11, 9), 3);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 8))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 9))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 9))));
		assertEquals(9,targets.size());
	}

	// Light green: exit room targets
	@Test
	public void testTargetsRoomExits1() {
		targets = board.getTargets(board.calcIndex(6, 1), 1);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 1))));
		assertEquals(1, targets.size());

		targets = board.getTargets(board.calcIndex(6, 1), 2);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 1))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 0))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 2))));
		assertEquals(3, targets.size());
	}
	
	// Light green: exit room targets
	@Test
	public void testTargetsRoomExits2() {
		targets = board.getTargets(board.calcIndex(0, 11), 1);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 10))));
		assertEquals(1, targets.size());
		
		targets = board.getTargets(board.calcIndex(0, 11), 3);
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 10))));
		assertEquals(1, targets.size());
	}
}
