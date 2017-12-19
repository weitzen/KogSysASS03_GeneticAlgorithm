package de.uniba.cogsys.genetic.main;

import java.util.Random;

/**
 * A class for representing a single hypothesis. It implements the Comparable
 * interface, so hypothesis can be sorted by their fitness.
 */
public class Hypothesis implements Comparable<Hypothesis> {

	private boolean[] hypothesis = new boolean[13];
	private float fitness = 0.0f;

	/**
	 * Initializes the hypothesis to a random boolean array (this will be our
	 * bitstrings).
	 */
	public void initRandomHypothesis() {

		Random random = new Random();
		for (int i = 0; i < 13; i++) {
			hypothesis[i] = random.nextBoolean();
		}
	}

	public void setHypothesis(boolean[] hypothesis) {
		this.hypothesis = hypothesis;
	}

	public boolean[] getHypothesis() {
		return hypothesis;
	}

	public void setFitness(float fitness) {
		this.fitness = fitness;
	}

	public float getFitness() {
		return fitness;
	}

	/**
	 * Classifies an instance according to the hypothesis.
	 * 
	 * @param instance
	 *            The instance to be classified.
	 * 
	 * @return The classification result. true for positive, false for negative.
	 */
	public boolean classify(Instance instance) {
		boolean output = true;

		boolean[] instanceBitstring = instance.getInstance();

		for (int i = 0; i < instanceBitstring.length; i++) {
			if (instanceBitstring[i] && !hypothesis[i]) {
				output = false;
				break;
			}
		}

		return output;
	}

	@Override
	public String toString() {

		String output = "(sky = ";

		int soFar = 0;

		if (hypothesis[0]) {
			output += "sunny";
			soFar++;
		}
		if (hypothesis[1]) {
			if (soFar > 0) {
				output += " OR cloudy";
			} else {
				output += " cloudy";
			}
			soFar++;
		}
		if (hypothesis[2]) {
			if (soFar > 0) {
				output += " OR rainy";
			} else {
				output += " rainy";
			}
		}
		output += ") AND (airtemp = ";
		soFar = 0;
		if (hypothesis[3]) {
			output += "warm";
			soFar++;
		}
		if (hypothesis[4]) {
			if (soFar > 0) {
				output += " OR cold";
			} else {
				output += " cold";
			}
		}
		output += ") AND (humidity = ";
		soFar = 0;
		if (hypothesis[5]) {
			output += "normal";
			soFar++;
		}
		if (hypothesis[6]) {
			if (soFar > 0) {
				output += " OR high";
			} else {
				output += " high";
			}
		}
		output += ") AND (wind = ";
		soFar = 0;
		if (hypothesis[7]) {
			output += "strong";
			soFar++;
		}
		if (hypothesis[8]) {
			if (soFar > 0) {
				output += " OR weak";
			} else {
				output += " weak";
			}
		}
		output += ") AND (water = ";
		soFar = 0;
		if (hypothesis[9]) {
			output += "warm";
			soFar++;
		}
		if (hypothesis[10]) {
			if (soFar > 0) {
				output += " OR cool";
			} else {
				output += " cool";
			}
		}
		output += ") AND (forecast = ";
		soFar = 0;
		if (hypothesis[11]) {
			output += "same";
			soFar++;
		}
		if (hypothesis[12]) {
			if (soFar > 0) {
				output += " OR change";
			} else {
				output += " change";
			}
		}

		output += ")";

		return output;
	}

	public int compareTo(Hypothesis other) {
		float compareResult = this.fitness - other.fitness;

		if (compareResult > 0) {
			return 1;
		} else if (compareResult < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}