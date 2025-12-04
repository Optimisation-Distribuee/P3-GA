package be.brw.config;

import be.brw.domain.strategy.*;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ConfigLoader {
    public static GAConfig fromYaml(Path path) throws IOException {
        Yaml yaml = new Yaml();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Map<String, Object> obj = yaml.load(reader);

            int seed = Integer.parseInt(obj.get("seed").toString());
            String solutionRaw = obj.get("solution").toString();

            byte[] solution = new byte[solutionRaw.length()];
            for(int i = 0; i < solutionRaw.length(); i++){
                if(solutionRaw.charAt(i) == '0'){
                    solution[i] = (byte) 0;
                }
                if(solutionRaw.charAt(i) == '1'){
                    solution[i] = (byte) 1;
                }
            }

            int minGenomeLength = Integer.parseInt(obj.get("minGenomeLength").toString());
            int maxGenomeLength = Integer.parseInt(obj.get("maxGenomeLength").toString());
            int maxGeneration = Integer.parseInt(obj.get("maxGeneration").toString());
            int populationSize = Integer.parseInt(obj.get("populationSize").toString());

            SelectionStrategy selectionStrategy = SelectionStrategy.valueOf(obj.get("selectionStrategy").toString());
            int tournamentSize = -1;
            if (selectionStrategy == SelectionStrategy.TOURNAMENT) {
                tournamentSize = Integer.parseInt(obj.get("tournamentSize").toString());
            }
            MutationTargetStrategy mutationTargetStrategy = MutationTargetStrategy.valueOf(obj.get("mutationTargetStrategy").toString());

            double mutationRate = Double.parseDouble(obj.get("mutationRate").toString());
            double bitFlipRate = Double.parseDouble(obj.get("bitFlipRate").toString());
            double bitAddRate = Double.parseDouble(obj.get("bitAddRate").toString());
            double bitRemoveRate = Double.parseDouble(obj.get("bitRemoveRate").toString());

            double sum = bitFlipRate + bitAddRate + bitRemoveRate;
            double epsilon = 1e-9;

            if (Math.abs(sum - 1.0) > epsilon) {
                throw new IllegalArgumentException(
                        String.format("Sum of bit mutation rates does not equal 1 (%.12f)", sum)
                );
            }

            CrossoverStrategy crossoverStrategy = CrossoverStrategy.valueOf(obj.get("crossoverStrategy").toString());
            double crossoverRate = Double.parseDouble(obj.get("crossoverRate").toString());
            CrossoverLeftoverStrategy crossoverLeftoverStrategy = CrossoverLeftoverStrategy.valueOf(obj.get("crossoverLeftoverStrategy").toString());

            LengthPunishingStrategy lengthPunishingStrategy = LengthPunishingStrategy.valueOf(obj.get("lengthPunishingStrategy").toString());
            double lengthPunishingFactor = Double.parseDouble(obj.get("lengthPunishingFactor").toString());

            int maxSolutions = Integer.parseInt(obj.get("maxSolutions").toString());

            return new GAConfig(
                    seed,
                    solution,
                    minGenomeLength,
                    maxGenomeLength,
                    maxGeneration,
                    populationSize,
                    selectionStrategy,
                    tournamentSize,
                    mutationTargetStrategy,
                    mutationRate,
                    bitFlipRate,
                    bitAddRate,
                    bitRemoveRate,
                    crossoverStrategy,
                    crossoverRate,
                    crossoverLeftoverStrategy,
                    lengthPunishingStrategy,
                    lengthPunishingFactor,
                    maxSolutions
            );
        }
    }
}
