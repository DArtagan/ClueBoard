package boardClasses;
public class RoomCell extends BoardCell {
    public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
    public DoorDirection doorDirection;
    private char roomInitial;
    
    RoomCell( char initial, char direction){
    	switch (direction){
    	case 'U':
    		doorDirection = DoorDirection.UP;
    		break;
    	case 'D':
    		doorDirection = DoorDirection.DOWN;
    		break;
    	case 'L':
    		doorDirection = DoorDirection.LEFT;
    		break;
    	case 'R':
    		doorDirection = DoorDirection.RIGHT;
    		break;
    	default:
    		doorDirection = DoorDirection.NONE;
    		break;	
    	}
    	roomInitial = initial;
    }
    @Override
    public boolean isRoom(){
        return true;
    }
    
    public void draw(){
        
    }

	public DoorDirection getDoorDirection() {
		
		return doorDirection;
	}

	public char getInitial() {
		
		return roomInitial;
	}
	@Override
	public boolean isDoorway(){
        if (doorDirection == DoorDirection.NONE)
        	return false;
        return true;
    }
}