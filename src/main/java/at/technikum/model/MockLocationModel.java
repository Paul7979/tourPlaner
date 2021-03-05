package at.technikum.model;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MockLocationModel implements ILocationModel{

    //Use search ahead api: https://developer.mapquest.com/documentation/searchahead-api/get/
    @Override
    public List<Location> queryForLocation(String query) {
        log.info("Looking for {} in Database", query);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = LocalDateTime.now().format(formatter);
        List<Location> locations = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            locations.add(
                    Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(i + query + " " + now).build()
            );
        }

        /*var locations = List.of(
                Location.builder().geoLocation(new GeoLocation(1.0, 2.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(3.0, 4.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build(),
                Location.builder().geoLocation(new GeoLocation(5.0, 6.0)).displayName(query + " " + now).build());*/
        return locations;
    }
}
