package org.usfirst.frc4904.standard.commands.healthchecks;

public class BodyPartsSongImpl extends BodyPartsSong {
	private final String string;

	BodyPartsSongImpl(String generatedString) {
		this.string = generatedString;
	}

	@Override
	public String toString() {
		return string;
	}

}
