package de.uniba.cogsys.genetic.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
			// TODO select hypos which will not be altered
			List<Hypothesis> ps = new ArrayList<>();
			
			double membersOf_P = population.size();	
			double selectedMembers = (1-r)*membersOf_P;

			//choose probabilistically a fraction of population to add to ps
			 for(int i=0; i<selectedMembers; i++){
				
				 Hypothesis chosenHypo = FitnessCalculator.chooseProbabilistically(population);
				 ps.add(chosenHypo);
	         }
			// TODO crossover, 
			 /*if we have an odd number for pairs to select, we round up to the next integer and 
			  * and delete the additional offspring hypothesis afterwards
			  * 
			  * Another approach would be to round the number of pairs down to the next smallest integer
			  * and later in the 'mutate'-step we could ADD the mutated hypo as an additional offspring to 
			  * our population, to again reach the specified number of 10 hypotheses in the 
			  * population pool 
			  * 
			  * */
			 
			 double numberOf_pairs = (r*membersOf_P)/2 ;

			 if(numberOf_pairs%2 != 0){
				 numberOf_pairs = (int)Math.ceil(numberOf_pairs); 
			 }
			 
			 List<Hypothesis> parentHypos = new ArrayList<>();
			 
			 //foreach pair probabilistically select two parent hypos from p (population
			 for(int i=0; i<numberOf_pairs*2; i++){
					 Hypothesis chosenHypo = FitnessCalculator.chooseProbabilistically(population);
					 parentHypos.add(chosenHypo);
		         }

			 List<Hypothesis> offspringList = new ArrayList<>();

			 /*
			  * Beispieliteration
			  * int[] meinArray = new int[] {1, 2, 3, 4, 5,6,7,8,9,10};;

 				for(int i=0; i<10; i+=2){
				 	System.out.println(meinArray[i]);
					//Ausgabe 1,3,5,7,9
	         	}
			  * */
			 
			 for(int i=0; i < numberOf_pairs*2; i+=2){
				 System.out.println("############ \n Iteration: " + i);
				 Hypothesis partnerHypo_one = parentHypos.get(i);
				 Hypothesis partnerHypo_two = parentHypos.get(i+1);
				 System.out.println("partnerHypo_one(upper): " + Arrays.toString(partnerHypo_one.getHypothesis()));
				 System.out.println("partnerHypo_two(lower): " + Arrays.toString(partnerHypo_two.getHypothesis()));
				 
				 Hypothesis offspring_one = new Hypothesis();
				 Hypothesis offspring_two = new Hypothesis();

				 Hypothesis crossover_mask = new Hypothesis();
				 crossover_mask.initRandomHypothesis();
				 System.out.println("Crossover mask:        " + Arrays.toString(crossover_mask.getHypothesis()));
				 
				 boolean[] tempOffspring_one = new boolean[13];
				 boolean[] tempOffspring_two = new boolean[13];
				
				//apply uniform crossover, produce two offspring foreach pair
				 for (int j = 0; j < 13; j++) {
						if(crossover_mask.getHypothesis()[j] == true){
							tempOffspring_one[j] = partnerHypo_one.getHypothesis()[j];
							tempOffspring_two[j] = partnerHypo_two.getHypothesis()[j];
							
						}else{
							tempOffspring_one[j] = partnerHypo_two.getHypothesis()[j];
							tempOffspring_two[j] = partnerHypo_one.getHypothesis()[j];
						}
					}
				 offspring_one.setHypothesis(tempOffspring_one);
				 System.out.println("offspring_one(upper):   " + Arrays.toString(offspring_one.getHypothesis()));
				 offspring_two.setHypothesis(tempOffspring_two);
				 System.out.println("offspring_two(lower):   " + Arrays.toString(offspring_two.getHypothesis()));
			
				 offspringList.add(offspring_one);
				 offspringList.add(offspring_two);
				 
	         }
 
			 /*
			  * Because we rounded our odd numberOf_pairs up to the next biggest integer, 
			  * we now delete the offspring with the lowest fitness
			  * */
			 

			 for (Hypothesis hyp : offspringList) {
					float fitness = FitnessCalculator.calculateFitness(hyp, trainingExamples);
					hyp.setFitness(fitness);
				}
			 
			 Collections.sort(offspringList);
			 
				Hypothesis worstOffspringHypothesis = offspringList.get(0);
				offspringList.remove(worstOffspringHypothesis);
			 
			 
			ps.addAll(offspringList);
			// TODO mutate
			
			 //since 10 percent of the hypothesis in ps is exactly one
			 //we can simply generate a randomInteger in the range from 0-9 to select the hypothesis we want to mutate
			 int randomHypo_position = new Random().nextInt(10);
			 int randomBit_position = new Random().nextInt(13);
			 
			 Hypothesis chosenHypo = ps.get(randomHypo_position);
			 
			 System.out.println("chosenHypo for mutaions, before: "+ Arrays.toString(chosenHypo.getHypothesis()));
			 chosenHypo.getHypothesis()[randomBit_position] = !chosenHypo.getHypothesis()[randomBit_position];
			 System.out.println("chosenHypo for mutaions, after: "+ Arrays.toString(chosenHypo.getHypothesis()));
			 //chosenHypo.setHypothesis(hypothesis); 
			 //= chosenHypo.getHypothesis()[randomInteger_two];

			// TODO update
			population = ps;

			// TODO evaluate
			for (Hypothesis hyp : population) {
				float fitness = FitnessCalculator.calculateFitness(hyp, trainingExamples);
				hyp.setFitness(fitness);
			}

			Collections.sort(population, Collections.reverseOrder());

			bestHypothesis = population.get(0);
			maxFitness = population.get(0).getFitness();

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