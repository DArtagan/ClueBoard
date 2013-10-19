package clue;

public abstract class BoardCell {
	char initial;

	public BoardCell(char initial) {
		this.initial = initial;
	}

	public char getInitial() {
		return initial;
	}

    public boolean isWalkway() {
        return false;
    }

    public boolean isRoom() {
        return false;
    }

    public boolean isDoorway() {
        return false;
    }
}