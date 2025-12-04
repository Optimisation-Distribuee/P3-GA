package be.brw;

import be.brw.config.ConfigLoader;
import be.brw.config.GAConfig;
import be.brw.domain.GeneticAlgorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

public class Check {
    public static void main(String[] args) {
        Path configDir = Path.of("src/main/resources/configs"); // folder containing config_001.yaml, etc.
        Path logFile = Path.of("results.log");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile.toFile(), false))) {
            writer.write("===== Genetic Algorithm Benchmark =====\n");
            writer.write("Started at: " + LocalDateTime.now() + "\n\n");

            // Find all YAML config files
            List<Path> configs;
            try (var stream = Files.list(configDir)) {
                configs = stream
                        .filter(p -> p.toString().endsWith(".yaml"))
                        .sorted()
                        .toList();
            }

            if (configs.isEmpty()) {
                writer.write("No config files found in " + configDir.toAbsolutePath() + "\n");
                return;
            }

            // Process each config file
            for (Path configPath : configs) {
                try {
                    GAConfig config = ConfigLoader.fromYaml(configPath);
                    GeneticAlgorithm ga = new GeneticAlgorithm(config);

                    List<String> result = ga.runAlgorithm();
                    int generationCount = ga.getGenerationCount();

                    String line = String.format(
                            "[%s] Fitness=%f  Genomes=%s  Generations=%d%n",
                            configPath.getFileName(),
                            1.0,
                            result.getFirst(),
                            generationCount
                    );

                    writer.write(line);
                    System.out.print(line);

                } catch (Exception e) {
                    writer.write(String.format("[%s] FAILED: %s%n", configPath.getFileName(), e.getMessage()));
                    System.err.printf("[%s] FAILED: %s%n", configPath.getFileName(), e.getMessage());
                }
            }

            writer.write("\n===== End of Benchmark =====\n");
            System.out.println("\nResults written to " + logFile.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error during benchmark: " + e.getMessage());
        }
    }
}
