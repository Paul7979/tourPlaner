package at.technikum.client.forwardsearch;

import at.technikum.model.Location;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
public class ForwardSearchClient {

    private static final String BASE_URL = "http://www.mapquestapi.com/search/v3/prediction?key="
            + System.getenv("apiKey") +
            "&limit=15&collection=adminArea,poi,address,category" +
            "&location=16.372845,48.208339";

    private static final ForwardSearchResponseToLocationsMapper mapper = new ForwardSearchResponseToLocationsMapper();

    private static final ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

    public List<Location> searchFor(String queryParam) throws IOException, InterruptedException {
        var uri = BASE_URL + "&q=" + queryParam;
        var request = createRequest(uri);
        HttpResponse<String> response = getResponse(request);
        log.info("Got {} Response {}", response.statusCode(), response.body());
        if (response != null && response.statusCode() == 200) {
            ForwardSearchResponse searchResult = objectMapper.readValue(response.body(), ForwardSearchResponse.class);
            log.info("Mapping {}", searchResult);
            return mapper.map(searchResult);
        }
        return null;
    }

    private HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        try {
            log.info("Sending HTTP Request");
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.error("IO Exception when calling {}", request.uri());
            throw e;
        } catch (InterruptedException e) {
            log.error("Interrupted Exception when calling {}", request.uri());
            throw e;
        }
    }

    private HttpRequest createRequest(String uri) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            log.error("Syntax Exception in {}", uri);
            throw new IllegalStateException("Invalid Syntax in uri");
        }
    }

}
