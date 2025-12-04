package be.brw.domain;

import java.util.List;
/**
 * Represents a single individual in a genetic algorithm population.
 * <p>
 * An individual is defined by its {@code genome}, which is a list of bytes (genes),
 * and its {@code fitness}, an integer score indicating how well it solves the problem.
 * This class is comparable, allowing individuals to be sorted based on their fitness
 * in descending order (higher fitness is better).
 * </p>
 */
public class Individual implements Comparable<Individual>{

    /**
     * The genetic makeup of the individual, represented as a list of bytes.
     */
    private final List<Byte> genome;
    /**
     * The fitness score of the individual. A higher value indicates a better solution.
     */
    private double fitness;

    /**
     * Constructs a new Individual with a specified genome and fitness.
     *
     * @param genome The list of bytes representing the individual's genome.
     * @param fitness The initial fitness score of the individual.
     */
    public Individual(List<Byte> genome, double fitness) {
        this.genome = genome;
        this.fitness = fitness;
    }

    /**
     * Constructs a new Individual with a specified genome and a default fitness of 0.
     *
     * @param genome The list of bytes representing the individual's genome.
     */
    public Individual(List<Byte> genome){
        this.genome = genome;
        this.fitness = 0;
    }

    /**
     * Sets the fitness score for this individual.
     *
     * @param fitness The new fitness score.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * Returns the fitness score of this individual.
     *
     * @return The current fitness score.
     */
    public double getFitness() {
        return this.fitness;
    }

    /**
     * Returns the genome of this individual.
     * <p>
     * Note: This method returns a direct reference to the internal list.
     * Modifications to the returned list will affect the individual's state.
     * </p>
     *
     * @return The list of bytes representing the genome.
     */
    public List<Byte> getGenome() {
        return this.genome;
    }

    /**
     * Returns the number of genes in the individual's genome.
     *
     * @return The length of the genome.
     */
    public int getGenomeLength() {
        return this.genome.size();
    }

    /**
     * Get a gene at a specific position in the genome.
     *
     * @param index The position of the gene to set.
     * @return the gene at index {index}
     */
    public Byte getGene(int index){
        return genome.get(index);
    }

    /**
     * Updates a gene at a specific position in the genome.
     *
     * @param index The position of the gene to set.
     * @param gene The new gene value.
     */
    public void setGene(int index, Byte gene){
        genome.set(index, gene);
    }

    /**
     * Appends a gene to the end of the individual's genome.
     *
     * @param gene The gene to add.
     */
    public void addGene(Byte gene){
        genome.add(gene);
    }

    /**
     * Removes a gene from a specific position in the genome.
     *
     * @param index The index of the gene to remove.
     */
    public void removeGene(int index){
        genome.remove(index);
    }

    /**
     * Compares this individual with another based on fitness.
     * The comparison is done in descending order of fitness, so an individual with a higher
     * fitness score is considered "less than" an individual with a lower score.
     * This is useful for sorting populations to find the best individuals easily.
     *
     * @param other The other individual to compare against.
     * @return A negative integer, zero, or a positive integer as this individual's fitness
     *         is greater than, equal to, or less than the other individual's fitness.
     */
    @Override
    public int compareTo(Individual other) {
        double diff = other.fitness - this.fitness;
        if (diff > 0) return 1;
        else if (diff < 0) return -1;
        else return 0;
    }

    /**
     * Returns a string representation of the individual, including its genome and fitness.
     *
     * @return A string detailing the individual's properties.
     */
    @Override
    public String toString() {
        // Convert the list of bytes/integers to a readable string
        byte[] bytes = new byte[genome.size()];
        for(int i = 0; i < genome.size(); i++) {
            bytes[i] = genome.get(i);
        }
        String genomeString = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);

        return "Individual{" +
                "genome='" + genomeString + '\'' +
                ", fitness=" + fitness +
                '}';
    }
}
