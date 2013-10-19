package clue;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import clue.RoomCell.DoorDirection;

public class Board {
	// Constants
	protected static final char WALKWAY = 'W';
	
	private ArrayList<BoardCell> cells;
	private TreeMap<Character, String> rooms;
	private int numRows = 0;
	private int numColumns = 0;
	private Set<BoardCell> targets;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private boolean[] visited ;

	public Board() {
		cells = new ArrayList<BoardCell>();
		rooms = new TreeMap<Character, String>();
	}
	
	public Board(String legendName, String layoutName) throws IOException, BadConfigFormatException {
		cells = new ArrayList<BoardCell>();
		rooms = new TreeMap<Character, String>();
		loadConfigFiles(legendName, layoutName);
	}

	public void loadConfigFiles(String legendName, String layoutName) throws IOException, BadConfigFormatException {
		loadLegend(legendName);
		loadLayout(layoutName);
	}

	public void loadLegend(String legendName) throws IOException, BadConfigFormatException {
		FileReader legendReader = new FileReader(legendName);
		Scanner legendIn = new Scanner(legendReader);
		String line, parts[];
		while (legendIn.hasNextLine()) {
			line = legendIn.nextLine();
			parts = line.split(", ");
			if(parts.length != 2 || parts[0] == "" || parts[1] == "") {
				legendIn.close();
				legendReader.close();
				throw new BadConfigFormatException("Legend is malformed.");
			}
			rooms.put(parts[0].charAt(0), parts[1]);
		}
		legendIn.close();
		legendReader.close();
	}
	
	public void loadLayout(String layoutName) throws BadConfigFormatException {
		int colCount1=0, colCount2=0;
		Scanner scan = new Scanner(System.in);
		FileReader reader = null;
		try {
			reader = new FileReader(layoutName);
		} catch(FileNotFoundException e) {
			scan.close();
			System.out.println(e.getMessage());
		}
		scan = new Scanner(reader);

		while (scan.hasNextLine()) {
			numRows++;
			String line = scan.nextLine();
			colCount1=0;
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);        
				if (c!=',' && (i==0 || line.charAt(i-1) ==',' )) {
					colCount1++;
					if (numRows==1) {
						colCount2++;
					}
					if (c != WALKWAY) {
						if (i != line.length()-1) {
							cells.add(new RoomCell(c,line.charAt(i+1)));
						} else {
							cells.add(new RoomCell(c,' '));
						}
					} else {
						cells.add(new WalkwayCell());
					}
				}
			}
			if (colCount1 != colCount2) {
				scan.close();
				throw new BadConfigFormatException();
			}
			colCount2 = colCount1;
		}
		numColumns = colCount2;
		scan.close();
	}

	public int calcIndex(int row, int col) {
		return row*numColumns+col;
	}

	public BoardCell getCellAt(int row, int col) {
		return cells.get(this.calcIndex(row, col));
	}
	
	public BoardCell getCellAt(int index) {
		return cells.get(index);
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void calcAdjacencies() {
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();

		LinkedList <Integer> adjs;
		for (int i = 0; i < numRows*numColumns; i++) {
			adjs = new LinkedList<Integer>();
			if (getCellAt(0,i).isDoorway()) {
				if (((RoomCell) cells.get(i)).getDoorDirection()  == DoorDirection.DOWN) {
					adjs.add(i+numColumns);
				} else if (((RoomCell) cells.get(i)).getDoorDirection()  == DoorDirection.LEFT) {
					adjs.add(i-1);
				} else if (((RoomCell) cells.get(i)).getDoorDirection()  == DoorDirection.RIGHT) {
					adjs.add(i+1);
				} else if (((RoomCell) cells.get(i)).getDoorDirection() == DoorDirection.UP) {
					adjs.add(i-numColumns);
				}
			} else {
				if (i%numColumns != 0) {
					// If the square isn't on the left.
					if(!cells.get(i-1).isRoom() || (cells.get(i-1).isDoorway() && ((RoomCell) cells.get(i-1)).getDoorDirection() == DoorDirection.RIGHT)) {
						// If it's a walkway or a proper facing door, add the
						// square to the left to the adjacency list.
						adjs.add(i-1);  
					}
				}
				if(i/numColumns != 0) {
					// If the square isn't on the top.
					if(!cells.get(i-numColumns).isRoom() || (cells.get(i-numColumns).isDoorway() && ((RoomCell) cells.get(i-numColumns)).getDoorDirection() == DoorDirection.DOWN)) {
						// If it's a walkway or a proper facing door, add
						// the square to the top to the adjacency list.
						adjs.add(i-numColumns);
					}
				}
				if(i%numColumns != numColumns-1) {
					// If the square isn't on the right.
					if(!cells.get(i+1).isRoom() || (cells.get(i+1).isDoorway() && ((RoomCell) cells.get(i+1)).getDoorDirection() == DoorDirection.LEFT)) {
						// If it's a walkway or a proper facing door,
						// add the square to the right to the adjacency list. 
						adjs.add(i+1);
					}
				}
				if(i/numColumns != numRows-1) {
					//if the square isn't on the bottom.
					if(!cells.get(i+numColumns).isRoom() || (cells.get(i+numColumns).isDoorway() && ((RoomCell) cells.get(i+numColumns)).getDoorDirection() == DoorDirection.UP)) {
						// If it's a walkway or a proper facing door,
						// add the square to the top to the adjacency list
						adjs.add(i+numColumns);
					}
				}
			}
			adjMtx.put(i, adjs);
		}
	}

	public LinkedList<Integer> getAdjList(int calcIndex) {
		return adjMtx.get(calcIndex);
	}
	
	public void calcTargets(int row, int col, int step) {
		targets = new HashSet<BoardCell>();
		visited = new boolean[numRows*numColumns];
		for (int i = 0; i < visited.length; i++) {
			visited[i] = false;
		}
		calcTargetsHelper(row, col, step);	
	}
	
	private void calcTargetsHelper(int row, int col, int step) {
		int index = calcIndex(row,col);
		visited[index] = true;
		LinkedList<Integer> adjs = getAdjList(index);

		for (int i :adjs) {
			if(visited[i] == false) {
				if(getCellAt(0,i).isDoorway()) {
					targets.add(getCellAt(0,i));
				}
				if (step == 1) {
					if (!getCellAt(0,i).isDoorway()) {
						targets.add(getCellAt(0,i));
					}
				} else {
					calc2Targets(0,i,step-1);
				}
			}
		}
		visited[index]=false;
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
}
