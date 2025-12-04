package be.brw.infrastructure;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
    void evaluate_shouldReturnFitnessScore_whenServerResponds200() throws InterruptedException {
        // Arrange: Prepare the mock server to send a successful response.
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"fitness_scores\": [42.0, 55.5]}"));

        // Convert byte[] to List<Byte>
        List<Byte> genomeList = toByteList("LRLRLR");

        // Act: Call the method we want to test.
        int fitness = evaluator.evaluate(genomeList);

        // Assert: Verify the outcome.
        assertThat(fitness).isEqualTo(42);

        // Assert: Verify that our client sent the correct request.
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/evaluate");
        assertThat(recordedRequest.getBody().readUtf8()).isEqualTo("{\"solutions\":[\"LRLRLR\"]}");
    }

    @Test
    void evaluate_shouldReturnZero_whenServerRespondsWithNon200Status() {
        // Arrange
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        List<Byte> genomeList = toByteList("LRLRLR");

        // Act
        int fitness = evaluator.evaluate(genomeList);

        // Assert
        assertThat(fitness).isZero();
    }

    @Test
    void evaluate_shouldReturnZero_whenResponseBodyIsMalformed() {
        // Arrange
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody("{\"invalid_key\": [123.0]}"));

        List<Byte> genome = toByteList("a");

        // Act
        int fitness = evaluator.evaluate(genome);

        // Assert
        assertThat(fitness).isZero();
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