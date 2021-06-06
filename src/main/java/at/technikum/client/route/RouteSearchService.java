package at.technikum.client.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class RouteSearchService {

    private static RouteSearchService instance;

    private static final String BASEURL = "http://www.mapquestapi.com/directions/v2/route?key="
            + System.getenv("apiKey") + "&from=%s&to=%s&unit=k&locale=de_DE";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private RouteSearchService() {
    }

    public static RouteSearchService getInstance(){
        if (instance == null) {
            instance = new RouteSearchService();
        }
        return instance;
    }


    public int getDistanceFor(String start, String destination) throws IOException, InterruptedException {
        start = start.replace(" ", "+");
        destination = destination.replace(" ", "+");
        var request = createRequest(BASEURL.formatted(start, destination));

        var begin= System.currentTimeMillis();
        var httpResponse = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        var routeSearchResponse = objectMapper.readValue(httpResponse.body(), RouteSearchResponse.class);
        log.info("Fetched and deserialized Route in {}ms", System.currentTimeMillis() - begin);
        return routeSearchResponse.getRoute().getDistance();

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
