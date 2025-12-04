package be.brw.infrastructure;

import be.brw.domain.FitnessEvaluator;
import be.brw.domain.Individual;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RemoteFitnessEvaluator  implements FitnessEvaluator {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final URI serviceUri;

    public RemoteFitnessEvaluator(String baseUrl) {
        this.serviceUri = URI.create(baseUrl)
                             .resolve(baseUrl.endsWith("/") ? "" : "/")
                             .resolve("evaluate");
    }

    @Override
    public List<Double> evaluate(List<Individual> genomes) {
        if (genomes == null || genomes.isEmpty()) {
            return Collections.emptyList();
        }
        // Convert to a List<String>
        List<String> genomeStrings = genomes.stream()
                .map(genome -> {
                    byte[] genomeBytes = new byte[genome.getGenomeLength()];
                    for (int i = 0; i < genome.getGenomeLength(); i++) {
                        genomeBytes[i] = genome.getGene(i);
                    }
                    return new String(genomeBytes, StandardCharsets.UTF_8);
                })
                .toList();

        try {
            // Prepare the request JSON & send it
            Map<String, List<String>> requestPayload = Map.of("solutions", genomeStrings);
            String requestBody = objectMapper.writeValueAsString(requestPayload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(serviceUri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the response
                Map<String, List<Double>> responseBody = objectMapper.readValue(response.body(), new TypeReference<>() {});
                List<Double> fitnessScores = responseBody.get("fitness_scores");
                if (fitnessScores != null) {
                    return fitnessScores;
                }
            }
            // Handle non-200 responses or unexpected response format
            System.err.println("Error evaluating fitness. Status: " + response.statusCode() + ", Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Exception during remote fitness evaluation: " + e.getMessage());
        }
        // In case of any error, return a list of zeros with the same size as the input.
        return Collections.nCopies(genomes.size(), 0.0);
    }
}
