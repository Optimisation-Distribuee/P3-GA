package be.brw.config;

import be.brw.domain.strategy.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GAConfig {

    private final int seed;
    private final byte[] solution;
    private final int minGenomeLength;
    private final int maxGenomeLength;
    private final int maxGeneration;

    private final int populationSize;

    // Selection
    private final SelectionStrategy selectionStrategy;
    private final int tournamentSize;

    // Mutation
    private final MutationTargetStrategy mutationTargetStrategy;
    private final double mutationRate;
    private final double bitFlipRate;
    private final double bitAddRate;
    private final double bitRemoveRate;

    // Crossover
    private final CrossoverStrategy crossoverStrategy;
    private final double crossoverRate;

    // Leftover
    private final CrossoverLeftoverStrategy crossoverLeftoverStrategy;

    // Punisher
    private final LengthPunishingStrategy lengthPunishingStrategy;
    private final double lengthPunishingFactor;
    private final int maxSolutions;

    public GAConfig(int seed, byte[] solution, int minGenomeLength, int maxGenomeLength, int maxGeneration, int populationSize, SelectionStrategy selectionStrategy, int tournamentSize, MutationTargetStrategy mutationTargetStrategy, double mutationRate, double bitFlipRate, double bitAddRate, double bitRemoveRate, CrossoverStrategy crossoverStrategy, double crossoverRate, CrossoverLeftoverStrategy crossoverLeftoverStrategy, LengthPunishingStrategy lengthPunishingStrategy, double lengthPunishingFactor, int maxSolutions) {
        this.seed = seed;
        this.solution = solution;
        this.minGenomeLength = minGenomeLength;
        this.maxGenomeLength = maxGenomeLength;
        this.maxGeneration = maxGeneration;
        this.populationSize = populationSize;
        this.selectionStrategy = selectionStrategy;
        this.tournamentSize =  tournamentSize;
        this.mutationTargetStrategy = mutationTargetStrategy;
        this.mutationRate = mutationRate;
        this.bitFlipRate = bitFlipRate;
        this.bitAddRate = bitAddRate;
        this.bitRemoveRate = bitRemoveRate;
        this.crossoverStrategy = crossoverStrategy;
        this.crossoverRate = crossoverRate;
        this.crossoverLeftoverStrategy = crossoverLeftoverStrategy;
        this.lengthPunishingStrategy = lengthPunishingStrategy;
        this.lengthPunishingFactor = lengthPunishingFactor;
        this.maxSolutions = maxSolutions;
    }

    public int getSeed(){
        return seed;
    }

    public List<Byte> getSolution() {
        List<Byte> solution = new ArrayList<>();
        for(byte b : this.solution){
            solution.add(b);
        }
        return solution;
    }

    public int getMinGenomeLength() {
        return minGenomeLength;
    }

    public int getMaxGenomeLength() {
        return maxGenomeLength;
    }

    public int getMaxGeneration() {
        return maxGeneration;
    }

    public int getPopulationSize(){
        return populationSize;
    }

    public SelectionStrategy getSelectionStrategy() {
        return selectionStrategy;
    }

    public int getTournamentSize() { return tournamentSize; }

    public MutationTargetStrategy getMutationTargetStrategy() {
        return mutationTargetStrategy;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public double getBitFlipRate() {
        return bitFlipRate;
    }

    public double getBitAddRate() {
        return bitAddRate;
    }

    public double getBitRemoveRate() {
        return bitRemoveRate;
    }

    public CrossoverStrategy getCrossoverStrategy() {
        return crossoverStrategy;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public CrossoverLeftoverStrategy getCrossoverLeftoverStrategy() {
        return crossoverLeftoverStrategy;
    }

    public LengthPunishingStrategy getLengthPunishingStrategy(){
        return lengthPunishingStrategy;
    }

    public double getLengthPunishingFactor() {
        return lengthPunishingFactor;
    }

    public int getMaxSolutions() { return maxSolutions; }

    @Override
    public String toString() {
        return "GAConfig{" +
                "seed=" + seed +
                ", solution=" + Arrays.toString(solution) +
                ", minGenomeLength=" + minGenomeLength +
                ", maxGenomeLength=" + maxGenomeLength +
                ", maxGeneration=" + maxGeneration +
                ", populationSize=" + populationSize +
                ", selectionStrategy=" + selectionStrategy +
                ", tournamentSize=" + tournamentSize +
                ", mutationTargetStrategy=" + mutationTargetStrategy +
                ", mutationRate=" + mutationRate +
                ", bitFlipRate=" + bitFlipRate +
                ", bitAddRate=" + bitAddRate +
                ", bitRemoveRate=" + bitRemoveRate +
                ", crossoverStrategy=" + crossoverStrategy +
                ", crossoverRate=" + crossoverRate +
                ", crossoverLeftoverStrategy=" + crossoverLeftoverStrategy +
                ", lengthPunishingStrategy=" + lengthPunishingStrategy +
                ", lengthPunishingFactor=" + lengthPunishingFactor +
                ", maxSolutions=" + maxSolutions +
                '}';
    }
}
