package clue;

public class RoomCell extends BoardCell {
	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	public DoorDirection doorDirection = DoorDirection.NONE;

	RoomCell(char initial, char direction) {
		super(initial);
		switch (direction) {
			case 'U': doorDirection = DoorDirection.UP; break;
			case 'D': doorDirection = DoorDirection.DOWN; break;
			case 'L': doorDirection = DoorDirection.LEFT; break;
			case 'R': doorDirection = DoorDirection.RIGHT; break;
			default: doorDirection = DoorDirection.NONE; break;
		}
	}

	RoomCell(char initial) {
		super(initial);
		doorDirection = DoorDirection.NONE;
	}

    @Override
    public boolean isRoom() {
        return true;
    }

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	@Override
	public boolean isDoorway() {
        if (doorDirection == DoorDirection.NONE) {
        	return false;
        }
        return true;
    }
}