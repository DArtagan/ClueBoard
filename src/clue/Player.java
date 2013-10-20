package clue;

import java.awt.Color;
import java.lang.reflect.Field;

public abstract class Player {
	private String name;
	private Color color;
	private int index;

	public Player(String name, String color, int index) {
		this.name = name;
		this.index = index;

		try {
		    Field field = Class.forName("java.awt.Color").getField(color);
		    this.color = (Color)field.get(null);
		} catch (Exception e) {
		    this.color = null;  // Not defined
		}
	}

	public Card disproveSuggestion(String person, String room, String weapon) {
		return null;
	}
}
