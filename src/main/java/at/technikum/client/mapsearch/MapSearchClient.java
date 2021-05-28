package at.technikum.client.mapsearch;

import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Slf4j
public class MapSearchClient {

    private static final String BASE_URL = "https://www.mapquestapi.com/staticmap/v5/map?start=%s&end=%s&size=%d,%d&key=" + System.getenv("apiKey");

    public Optional<Image> getMapPictureFor(String start, String destination, int width, int height) {
        log.info("Searching for journey from {} to {}", start, destination);

        start = start.replace(" ", "+");
        destination = destination.replace(" ", "+");
        var formatted = BASE_URL.formatted(start, destination, width, height);
        var image = new Image(formatted);
        if (image.isError()) {
            return Optional.empty();
        }
        return Optional.of(image);
    }


    public Optional<String> storeToFile(String start, String destination, int width, int height) {
        log.info("Searching for journey from {} to {}", start, destination);

        start = start.replace(" ", "+");
        destination = destination.replace(" ", "+");
        var formatted = BASE_URL.formatted(start, destination, width, height);
        try {
            URL url = new URL(formatted);
            var read = ImageIO.read(url);
            var pathname = "./data/" + read.hashCode() + ".jpeg";
            File file = new File(pathname);
            //file.createNewFile();
            ImageIO.write(read, "jpeg", file);
            return Optional.of(pathname);
        } catch (IOException e) {
            log.error("Error when storing picture", e);
            return Optional.empty();
        }
    }
}
