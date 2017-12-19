package de.uniba.cogsys.genetic.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	public static List<Instance> trainingExamples = new ArrayList<>();

	/**
	 * This method executes a genetic algorithm to learn the best hypothesis for the
	 * concept "play tennis".
	 * 
	 * @param fitnessThreshold
	 *            The threshold for the fitness that must be met in order for the
	 *            algorithm to terminate.
	 * @param p
	 *            The population size.
	 * @param r
	 *            The fraction of the population to be replaced by crossover.
	 * @param m
	 *            The mutation rate.
	 * @return The best hypothesis according to the fitness function.
	 */
	public static Hypothesis ga(float fitnessThreshold, int p, float r, float m) {

		Hypothesis bestHypothesis = new Hypothesis();

		List<Hypothesis> population = new ArrayList<>();

		// generate population

		for (int i = 0; i < p; i++) {
			Hypothesis hyp = new Hypothesis();
			hyp.initRandomHypothesis();
			population.add(hyp);
		}

		// check fitness

		for (Hypothesis hyp : population) {
			float fitness = FitnessCalculator.calculateFitness(hyp, trainingExamples);
			hyp.setFitness(fitness);
		}

		Collections.sort(population, Collections.reverseOrder());

		bestHypothesis = population.get(0);
		float maxFitness = population.get(0).getFitness();

		if (maxFitness >= fitnessThreshold) {
			return bestHypothesis;
		}

		do {

			// TODO select
			// TODO crossover
			// TODO mutate
			// TODO update
			// TODO evaluate

		} while (maxFitness < fitnessThreshold);

		return bestHypothesis;
	}

	public static void main(String[] args) {

		// fill positive training examples
		trainingExamples.add(new Instance("sunny,warm,normal,strong,warm,same", true));
		trainingExamples.add(new Instance("sunny,warm,high,strong,warm,same", true));
		trainingExamples.add(new Instance("sunny,warm,normal,strong,warm,change", true));
		trainingExamples.add(new Instance("sunny,warm,high,strong,warm,change", true));
		trainingExamples.add(new Instance("sunny,warm,high,weak,warm,same", true));

		// fill negative training examples
		trainingExamples.add(new Instance("rainy,cold,high,strong,cool,change", false));
		trainingExamples.add(new Instance("cloudy,cold,high,strong,cool,change", false));
		trainingExamples.add(new Instance("cloudy,cold,high,strong,cool,same", false));

		System.out.println(ga(0.99f, 10, 0.5f, 0.1f));
	}
}