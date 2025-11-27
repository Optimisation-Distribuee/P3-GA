package be.brw.domain;

import java.util.List;

public interface FitnessEvaluator {
    /**
     * Calculates and returns the fitness score for a given genome.
     *
     * @param genome The list of bytes representing the individual's genome.
     * @return The fitness score as an integer.
     */
    int evaluate(List<Byte> genome);
}
