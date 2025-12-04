package be.brw.domain;

import java.util.List;

public interface FitnessEvaluator {
    /**
     * Calculates and returns the fitness scores for a list of genomes.
     *
     * @param genomes A list of genomes to be evaluated.
     * @return A list of fitness scores, one for each genome in the input list.
     */
    List<Double> evaluate(List<Individual> genomes);
}
