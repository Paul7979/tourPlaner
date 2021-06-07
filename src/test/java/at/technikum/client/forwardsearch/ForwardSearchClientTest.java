package at.technikum.client.forwardsearch;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class ForwardSearchClientTest {

    @Test
    public void givenForwardSearchClientWhenSerializingSearchDeserializingEqual() throws IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        List<String> lines = Files.readAllLines(Paths.get(getClass().getResource("/ForwardSearchResponse.json").toURI()));
        var searchResult = objectMapper.readValue(String.join("\n", lines), ForwardSearchResponse.class);
        System.out.println(searchResult);
    }

}