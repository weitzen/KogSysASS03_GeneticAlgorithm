package de.uniba.cogsys.genetic.main;

import java.util.List;

/**
 * Helper class for calculations related to fitness.
 *
 */
public class FitnessCalculator {

	/**
	 * Calculates the fitness for a hypothesis as a function of the training
	 * examples. The fitness is the percentage of correctly classified examples (the
	 * accuracy).
	 * 
	 * @param hypothesis
	 *            The hypothesis to calculate the fitness for.
	 * 
	 * @param trainingExamples
	 *            The training examples.
	 * 
	 * @return The fitness for the hypothesis.
	 */
	public static float calculateFitness(Hypothesis hypothesis, List<Instance> trainingExamples) {
		int classifiedCorrectly = 0;

		for (Instance instance : trainingExamples) {
			if (instance.isPositive()) {
				if (hypothesis.classify(instance)) {
					classifiedCorrectly++;
				}
			} else {
				if (!hypothesis.classify(instance)) {
					classifiedCorrectly++;
				}
			}
		}

		float fitness = (float) (classifiedCorrectly) / (float) (trainingExamples.size());

		return fitness;
	}

	/**
	 * A helper function to choose a hypothesis by a non-uniform probability
	 * distribution.
	 * 
	 * @param hypotheses
	 *            The pool of hypotheses to draw from.
	 * 
	 * @return The chosen hypothesis.
	 */
	public static Hypothesis chooseProbabilistically(List<Hypothesis> hypotheses) {
		float fitnessSum = 0.0f;
		for (Hypothesis hyp : hypotheses) {
			fitnessSum += hyp.getFitness();
		}

		float randVal = (float) (Math.random()) * fitnessSum;
		float current = 0.0f;
		for (Hypothesis hyp : hypotheses) {
			current += hyp.getFitness();
			if (current >= randVal) {
				return hyp;
			}
		}
		return null;
	}
}
