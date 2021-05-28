package at.technikum.client.mapsearch;


import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.util.Optional;

public class MapSearchService {

    private MapSearchClient client;

    private static MapSearchService instance;

    private MapSearchService() {
        client = new MapSearchClient();
    }

    public static MapSearchService getInstance() {
        if (instance == null) {
            instance = new MapSearchService();
        }
        return instance;
    }

    public Task<Image> searchMapPreview(String start, String destination) {
        return new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                var mapPictureFor = client.getMapPictureFor(start, destination, 300, 200);
                return mapPictureFor.orElseThrow(() -> new IllegalStateException("No pic"));
            }
        };
    }

    public Optional<String> searchMapFullSizeBlocking(String start, String destination) {
        return client.storeToFile(start, destination, 1280, 720);
    }

}
