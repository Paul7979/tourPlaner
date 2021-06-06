package at.technikum.client.route;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteSearchResponse {

    private Route route;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Route {
        private int distance;
    }
}
