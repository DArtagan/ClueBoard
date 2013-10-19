package clueTests;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clue.BadConfigFormatException;
import clue.Board;
import clue.BoardCell;

public class CR_BoardAdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board();
		try{
		board.loadConfigFiles("BoardConfiguration.csv", "Legend.txt");
		}catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
		board.calcAdjacencies();

	}

	

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(4, 11));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(4, 12)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(board.calcIndex(18, 18));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(18, 17)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(7, 4));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(8, 4)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(18, 6));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(17, 6)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(17, 12));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 11)));
		Assert.assertTrue(testList.contains(board.calcIndex(17, 13)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 12)));
		Assert.assertTrue(testList.contains(board.calcIndex(18, 12)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(8, 4));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 5)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(10, 17));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 18)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 16)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(10, 4));
		Assert.assertTrue(testList.contains(board.calcIndex(11, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 5)));
		
		Assert.assertEquals(3, testList.size());
		// Test beside a door that's not the right direction
		testList = board.getAdjList(board.calcIndex(12, 4));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 4)));
		// This ensures we haven't included cell (11, 4) which is a doorway
		Assert.assertEquals(3, testList.size());		
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 16));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(0, 15)));
		Assert.assertTrue(testList.contains(board.calcIndex(1, 16)));
		Assert.assertEquals(3, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(board.calcIndex(13, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 0)));
		Assert.assertEquals(3, testList.size());

		// Test between two rooms, walkways up and down
		testList = board.getAdjList(board.calcIndex(20, 17));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 17)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(14,17));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 18)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(21, 13));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 12)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 13)));
		Assert.assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(15, 22));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 22)));
		Assert.assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(board.calcIndex(5, 11));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 12)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 11)));
		Assert.assertEquals(3, testList.size());
	}
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(21, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(20, 7)));
		
		board.calcTargets(14, 0, 1);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(14, 1)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(13, 0)));			
	}
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(14, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(12, 7)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(13, 6)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(13, 8)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(14, 5)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(14, 9)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(15, 6)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(16, 7)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(15, 8)));
		
		board.calcTargets(21, 17, 2);
		targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(19, 17)));
			
	}
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(6, 15, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(18, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(2, 15)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(3, 14)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(3, 16)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(4, 13)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(4, 15)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(4, 17)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(5, 12)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(5, 14)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(5, 16)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(5, 18)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(6, 11)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(6, 13)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(6, 17)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(6, 19)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(7, 16)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(7, 18)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(8, 17)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(9, 16)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(19, 17, 4);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(15, 17)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(16, 16)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(18, 16)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(18, 18)));	
	}	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(0, 7, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(13, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(6, 7)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(5, 6)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(5, 8)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(4, 7)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(4, 5)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(3, 6)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(3, 8)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(2, 5)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(2, 7)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(1, 6)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(1, 8)));	
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(0, 5)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(2, 4)));
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(17, 13, 2);
		Set<BoardCell> targets= board.getTargets();
		
		Assert.assertEquals(6, targets.size());
		// directly left (can't go right 2 steps)
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(17, 11)));
		
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(15, 13)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(19, 13)));
		// one up/down, one left/right
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(18, 12)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(16, 12)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(16, 14)));
		
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(21, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(19, 8)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(17, 7)));
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(18, 18, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(18, 17)));
		// Take two steps
		board.calcTargets(19, 8, 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(18, 7)));
		Assert.assertTrue(targets.contains(board.GetRoomCellAt(20, 7)));
	}

}

