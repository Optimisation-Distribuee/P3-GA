package be.brw.infrastructure;

import be.brw.domain.Individual;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RemoteFitnessEvaluatorTest {

    private MockWebServer mockWebServer;
    private RemoteFitnessEvaluator evaluator;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url("").toString();
        evaluator = new RemoteFitnessEvaluator(baseUrl);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void evaluate_shouldReturnFitnessScores_whenServerResponds200() throws InterruptedException {
        // Arrange: Prepare the mock server to send a successful response.
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"fitness_scores\": [42.0, 55.5]}"));

        // Prepare a list of genomes for batch evaluation
        List<Individual> genomes = List.of(
                new Individual(toByteList("LRLRLR")),
                new Individual(toByteList("RRLRRL"))
        );

        // Act: Call the method we want to test.
        List<Double> fitnessScores = evaluator.evaluate(genomes);

        // Assert: Verify the outcome.
        assertThat(fitnessScores).containsExactly(42.0, 55.5);

        // Assert: Verify that our client sent the correct request.
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/evaluate");
        assertThat(recordedRequest.getBody().readUtf8()).isEqualTo("{\"solutions\":[\"LRLRLR\",\"RRLRRL\"]}");
    }

    @Test
    void evaluate_shouldReturnZero_whenServerRespondsWithNon200Status() {
        // Arrange
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        List<Individual> genomes = List.of(
                new Individual(toByteList("LRLRLR")),
                new Individual(toByteList("ABC"))
        );

        // Act
        List<Double> fitnessScores = evaluator.evaluate(genomes);

        // Assert
        assertThat(fitnessScores).containsExactly(0.0, 0.0);
    }

    @Test
    void evaluate_shouldReturnZero_whenResponseBodyIsMalformed() {
        // Arrange
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"invalid_key\": [123.0]}"));

        List<Individual> genomes = List.of(
                new Individual(toByteList("a"))
        );

        // Act
        List<Double> fitnessScores = evaluator.evaluate(genomes);

        // Assert
        assertThat(fitnessScores).containsExactly(0.0);
    }

    @Test
    void evaluate_shouldReturnEmptyList_whenGivenEmptyList() {
        // Act
        List<Double> fitnessScores = evaluator.evaluate(Collections.emptyList());

        // Assert
        assertThat(fitnessScores).isEmpty();
    }

    // Helper method: Convert a String to a List<Byte>
    private List<Byte> toByteList(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        List<Byte> byteList = new ArrayList<>(bytes.length);
        for (byte b : bytes) {
            byteList.add(b);
        }
        return byteList;
    }
}