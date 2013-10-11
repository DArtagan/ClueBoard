package boardClasses;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import boardClasses.RoomCell.DoorDirection;

public class Board {
    private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
    private Map<Character, String> rooms = new TreeMap<Character, String>();
    private int numRows = 0;
    private int numColumns = 0;
    private Set<BoardCell> targets;
	private Map<Integer, LinkedList<Integer>> adjMtx;//map for what squares are adjacent to each
	private LinkedList<Integer> adjList;//list for adjacencies
	private boolean[] visited ;
    
    
    public Board() {
		
	}

	public void loadConfigFiles(String layoutName, String legendName) throws BadConfigFormatException{
		
        loadLayout(layoutName);
        loadLegend(legendName);
		
    }
	/*
	public void loadLayout(String fileName){

        cells.clear();

        FileReader reader;

        try {

            reader = new FileReader(fileName);

            Scanner board = new Scanner(reader);

            while (board.hasNextLine()){

                String row = board.nextLine();

                numRows ++;

                int cols = 0;

                for(String c: row.split(",")){

                    cells.add(new RoomCell(c));

                    cols ++;

                } if (numRows == 1){

                    numColumns = cols;

                } else if(numColumns != cols){

                    throw new BadConfigFormatException();

                }

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        */
	public void loadLayout(String layoutName) throws BadConfigFormatException{
		int colCount1=0, colCount2=0;
		Scanner scan = new Scanner(System.in);
		FileReader reader = null;
		try{
		reader = new FileReader(layoutName);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		scan = new Scanner(reader);
		
		
		while (scan.hasNextLine()){
			numRows++;
			String line = scan.nextLine();
			colCount1=0;
			for (int i = 0; i < line.length(); i++){
			    char c = line.charAt(i);        
			    if (c!=',' && (i==0 || line.charAt(i-1) ==',' )){
			    	colCount1++;
			    	if (numRows==1)
			    		colCount2++;
			    	if(c != 'H')
			    		if(i != line.length()-1){
			    			cells.add(new RoomCell(c,line.charAt(i+1)));
			    		}
			    		else
			    			cells.add(new RoomCell(c,' '));
			    	else
			    		cells.add(new WalkwayCell());
			    	
			    }
			}
			if(colCount1 != colCount2)
				throw new BadConfigFormatException();
			colCount2 = colCount1;
		}
		numColumns = colCount2;
		scan.close();
	}
	
	public void loadLegend(String legendName)throws BadConfigFormatException{
		FileReader reader = null;
		try{
		reader = new FileReader(legendName);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		Scanner scan = new Scanner(reader);
		while (scan.hasNextLine()){
			String line = scan.nextLine();
			if(!(line.charAt(0) == 'H'||line.charAt(0) == 'R'||line.charAt(0) == 'X'||line.charAt(0) == 'K'||line.charAt(0) == 'D'||line.charAt(0) == 'B'||line.charAt(0) == 'L'||line.charAt(0) == 'S'||line.charAt(0) == 'O'||line.charAt(0) == 'C'||line.charAt(0) == 'T'))
				throw new BadConfigFormatException();
			rooms.put(line.charAt(0), line.substring(3,line.length()));
		}
		scan.close();
		
	}
    
    public int calcIndex(int row, int col){
    	
        return row*numColumns+col;
    }
    
    public BoardCell GetRoomCellAt(int row, int col){
        int x = this.calcIndex(row, col);
       // if(cells.get(x).isRoom())
            return cells.get(x);
       // return null;

    }

    public Map<Character, String> getRooms(){
    	return rooms;
    }

	public int getNumRows() {
		
		return numRows;
	}

	public int getNumColumns() {

		return numColumns;
	}


	public void calcAdjacencies() {
adjMtx = new HashMap<Integer, LinkedList<Integer>>();//initializes the adjacency matrix
		
		LinkedList <Integer> adjs;
		for (int i = 0; i < numRows*numColumns; i++){//for every square in grid
			adjs = new LinkedList<Integer>();//start a new list
			if (GetRoomCellAt(0,i).isDoorway()){
				if (((RoomCell) GetRoomCellAt(0,i)).getDoorDirection() == DoorDirection.DOWN)
					adjs.add(i+numColumns);
				if (((RoomCell) GetRoomCellAt(0,i)).getDoorDirection() == DoorDirection.LEFT)
					adjs.add(i-1);
				if (((RoomCell) GetRoomCellAt(0,i)).getDoorDirection() == DoorDirection.RIGHT)
					adjs.add(i+1);
				if (((RoomCell) GetRoomCellAt(0,i)).getDoorDirection() == DoorDirection.UP)
					adjs.add(i-numColumns);
				
			}
			else{
				if(i%numColumns != 0)//if the square isn't on the left
					if(!cells.get(i-1).isRoom() || (cells.get(i-1).isDoorway() && ((RoomCell) cells.get(i-1)).getDoorDirection() == DoorDirection.RIGHT))
						adjs.add(i-1);//add the square to the left to the adjacency list
				if(i/numColumns != 0)//if the square isn't on the top
					if(!cells.get(i-numColumns).isRoom() || (cells.get(i-numColumns).isDoorway() && ((RoomCell) cells.get(i-numColumns)).getDoorDirection() == DoorDirection.DOWN))
						adjs.add(i-numColumns);//add the square to the top to the adjacency list
				if(i%numColumns != numColumns-1)//if the square isn't on the right
					if(!cells.get(i+1).isRoom() || (cells.get(i+1).isDoorway() && ((RoomCell) cells.get(i+1)).getDoorDirection() == DoorDirection.LEFT))
						adjs.add(i+1);//add the square to the right to the adjacency listif(i/numColumns != numRows-1)//if the square isn't on the right
				if(i/numColumns != numRows-1)//if the square isn't on the bottom
					if(!cells.get(i+numColumns).isRoom() || (cells.get(i+numColumns).isDoorway() && ((RoomCell) cells.get(i+numColumns)).getDoorDirection() == DoorDirection.UP))
						adjs.add(i+numColumns);//add the square to the top to the adjacency listif(i/numColumns != numRows-1)//if the square isn't on the top
				
			}
			adjMtx.put(i, adjs);
		}
		
	}

	public LinkedList<Integer> getAdjList(int calcIndex) {
		adjList = adjMtx.get(calcIndex);
		return adjList;
	}
	public void calcTargets(int row, int col, int step){
		//int index = row * col;
		targets = new HashSet<BoardCell>();
		visited = new boolean[numRows*numColumns];
		for (int i = 0; i < visited.length; i++)
			visited[i] = false;

		
		calc2Targets(row, col, step);	
	}
	public void calc2Targets(int row, int col, int step) {
		
		int index = calcIndex(row,col);
		visited[index] = true;
		LinkedList<Integer> adjs = getAdjList(index);
		
		
		for (int i :adjs){
			if(visited[i] == false){
	
				if(GetRoomCellAt(0,i).isDoorway())
					targets.add(GetRoomCellAt(0,i));
				if (step == 1){
					if (!GetRoomCellAt(0,i).isDoorway())
						targets.add(GetRoomCellAt(0,i));
				}
				else
					calc2Targets(0,i,step-1);
			}
		}
		visited[index]=false;
		
	}
		


	public Set<BoardCell> getTargets() {
		
		return targets;
	}
	
   }