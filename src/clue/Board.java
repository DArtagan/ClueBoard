package clue;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import clue.RoomCell.DoorDirection;

public class Board {
	private ArrayList<BoardCell> cells;
	private TreeMap<Character, String> rooms;
	private int numRows;
	private int numColumns;
	private Set<BoardCell> targets;
	private Map<Integer, LinkedList<Integer>> adjMap;
	private boolean[] visited;

	public Board() {
		cells = new ArrayList<BoardCell>();
		rooms = new TreeMap<Character, String>();
	}

	public Board(String layoutName, String legendName) throws IOException, BadConfigFormatException {
		cells = new ArrayList<BoardCell>();
		rooms = new TreeMap<Character, String>();
		loadConfigFiles(layoutName, legendName);
	}

	public void loadConfigFiles(String layoutName, String legendName) throws IOException, BadConfigFormatException {
		loadLegend(legendName);
		loadLayout(layoutName);
	}

	private void loadLegend(String legendName) throws IOException, BadConfigFormatException {
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

	private void loadLayout(String layoutName) throws BadConfigFormatException, IOException {
		int colCount1 = 0;
		int colCount2 = 0;
		char c;
		FileReader reader = new FileReader(layoutName);
		Scanner scan = new Scanner(reader);

		while (scan.hasNextLine()) {
			++numRows;
			String line = scan.nextLine();
			colCount1 = 0;
			for (int i = 0; i < line.length(); ++i) {
				c = line.charAt(i);
				if (!(c != ',' && (i == 0 || line.charAt(i-1) == ','))) {
					continue;
				}
				if (!rooms.containsKey(c)) {
					scan.close();
					reader.close();
					throw new BadConfigFormatException("Bad room initial in layout.");
				} else {
					++colCount1;
					if (numRows == 1) {
						++colCount2;
					}
					if (c != WalkwayCell.WALKWAY) {
						if (i != line.length()-1) {
							cells.add(new RoomCell(c, line.charAt(i+1)));
						} else {
							cells.add(new RoomCell(c));
						}
					} else {
						cells.add(new WalkwayCell());
					}
				}
			}
			if (colCount1 != colCount2) {
				scan.close();
				reader.close();
				throw new BadConfigFormatException();
			}
			colCount2 = colCount1;
		}
		numColumns = colCount2;
		scan.close();
		reader.close();
	}

	public int calcIndex(int row, int col) {
		return row*numColumns+col;
	}

	public BoardCell getCellAt(int row, int col) {
		return getCellAt(calcIndex(row, col));
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
		adjMap = new HashMap<Integer, LinkedList<Integer>>();

		LinkedList <Integer> adjs;
		for (int i = 0; i < numRows*numColumns; i++) {
			adjs = new LinkedList<Integer>();
			if (getCellAt(i).isDoorway()) {
				if (((RoomCell) cells.get(i)).getDoorDirection()  == DoorDirection.DOWN) {
					adjs.add(i+numColumns);
				} else if (((RoomCell) cells.get(i)).getDoorDirection()  == DoorDirection.LEFT) {
					adjs.add(i-1);
				} else if (((RoomCell) cells.get(i)).getDoorDirection()  == DoorDirection.RIGHT) {
					adjs.add(i+1);
				} else if (((RoomCell) cells.get(i)).getDoorDirection() == DoorDirection.UP) {
					adjs.add(i-numColumns);
				}
			} else if (!getCellAt(i).isRoom()) {
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
			adjMap.put(i, adjs);
		}
	}

	public LinkedList<Integer> getAdjList(int calcIndex) {
		return adjMap.get(calcIndex);
	}

	public void calcTargets(int row, int col, int steps) {
		targets = new HashSet<BoardCell>();
		visited = new boolean[numRows*numColumns];
		Arrays.fill(visited, false);
		int index = calcIndex(row,col);
		calcTargetsHelper(index, steps);
	}

	private void calcTargetsHelper(int index, int steps) {
		visited[index] = true;
		LinkedList<Integer> adjacencies = getAdjList(index);

		for (int i : adjacencies) {
			if(visited[i] == false) {
				if(getCellAt(i).isDoorway()) {
					targets.add(getCellAt(i));
				}
				if (steps == 1) {
					if (!getCellAt(i).isDoorway()) {
						targets.add(getCellAt(i));
					}
				} else {
					calcTargetsHelper(i, steps-1);
				}
			}
		}
		visited[index] = false;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Set<BoardCell> getTargets(int index, int steps) {
		targets = new HashSet<BoardCell>();
		visited = new boolean[numRows*numColumns];
		Arrays.fill(visited, false);
		calcTargetsHelper(index, steps);
		return targets;
	}
}
