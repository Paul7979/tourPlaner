package at.technikum.client.mapsearch;

import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class MapSearchClient {

    private static final String URL = "https://www.mapquestapi.com/staticmap/v5/map?start=%s&end=%s&size=300,200@2x&key=" + System.getenv("apiKey");

    public Optional<Image> getMapPictureFor(String start, String destination) {
        log.info("Searching for journey from {} to {}", start, destination);

        start = start.replace(" ", "+");
        destination.replace(" ", "+");
        //URL url = new URL));
        var image = new Image((URL.formatted(start, destination)));
        if (image.isError()) {
            return Optional.empty();
        }
        return Optional.of(image);
    }
}
