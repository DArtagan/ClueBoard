package clue;

public class WalkwayCell extends BoardCell {
	protected static final char WALKWAY = 'W';

	public WalkwayCell() {
		super(WALKWAY);
	}

	@Override
    public boolean isWalkway() {
        return true;
    }
}
