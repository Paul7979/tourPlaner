package at.technikum.model.location;

import at.technikum.client.forwardsearch.ForwardSearchClient;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MapQuestLocationRepository extends Service<List<Location>> implements ILocationRepository {

    private final ForwardSearchClient forwardSearchClient = new ForwardSearchClient();

    @Setter
    private String query = "";

    public Task<List<Location>> queryForLocation(String query) {
        log.info("Starting Forward Search For {}", query);
        return new Task<List<Location>>() {
            @Override
            protected List<Location> call() throws Exception {
                return forwardSearchClient.searchFor(query);
            }
        };
        //return forwardSearchClient.searchFor(query);
    }

    @Override
    public Task<List<Location>> createTask() {
        return new Task<>() {
            @Override
            protected List<Location> call() throws Exception {
                return forwardSearchClient.searchFor(query);
            }
        };
    }
}
