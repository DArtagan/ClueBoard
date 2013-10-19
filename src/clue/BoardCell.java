package clue;

abstract public class BoardCell {
    private int pieceRow;
    private int pieceCol;
    
    public boolean isWalkway() {
        return false;
    }    
    
    public boolean isRoom() {
        return false;
    }

    public boolean isDoorway() {
        return false;
    }  

    public void draw() {
    }
    
    public char getInitial() {
    	return 'W';
    }

}