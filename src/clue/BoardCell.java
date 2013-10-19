package clue;

abstract public class BoardCell {
    
    public boolean isWalkway() {
        return false;
    }    
    
    public boolean isRoom() {
        return false;
    }

    public boolean isDoorway() {
        return false;
    }  
    
    public char getInitial() {
    	return Board.WALKWAY;
    }

}