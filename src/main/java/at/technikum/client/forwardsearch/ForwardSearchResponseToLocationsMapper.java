package at.technikum.client.forwardsearch;

import at.technikum.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ForwardSearchResponseToLocationsMapper {


    public List<Location> map(ForwardSearchResponse body) {
        var results = body.getResults();
        return results
                .stream()
                .map(result -> Location.builder()
                        .displayName(result.getDisplayString())
                        .build())
                .collect(Collectors.toList());
    }
}
