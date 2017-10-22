package udemy.saurabh.springguru104springmvcrecipies.model;

public enum Difficulty {
	EASY("Easy"), MODERATE("Moderate"), HARD("Hard");

	private final String name;

	Difficulty(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}

/*
use the built-in .name() on an enum

String name = Modes.mode1.name();
// Returns the name of this enum constant, exactly as declared in its enum declaration.

since we din't want this - wrote a custom implementation
 */