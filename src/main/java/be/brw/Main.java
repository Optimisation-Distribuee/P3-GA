package be.brw;

import be.brw.config.ConfigLoader;
import be.brw.config.GAConfig;
import be.brw.domain.GeneticAlgorithm;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            GAConfig config = ConfigLoader.fromYaml(Path.of("src/main/resources/config.yaml"));
            // System.out.println(config);

            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(config);
            List<String> winners = geneticAlgorithm.runAlgorithm();
            winners.sort(Comparator.comparingInt(String::length));
            StringBuilder stringBuilder = new StringBuilder();
            for (String winner : winners) {
                stringBuilder.append("\"");
                stringBuilder.append(winner);
                stringBuilder.append("\"");
                stringBuilder.append(" ");
            }
            System.out.println(stringBuilder);
            if (!winners.isEmpty()) {
                System.out.println("Found " + winners.size() + " solutions, shortest one is "  + winners.getFirst());
            }
            else {
                System.out.println("No solutions found");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
