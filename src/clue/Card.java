package clue;

public class Card {
	public static enum CardType {PERSON, WEAPON, ROOM};

	public String name;
	private CardType type;

	public Card(String setName, CardType setType) {
		name = setName;
		type = setType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType setType) {
		type = setType;
	}

}