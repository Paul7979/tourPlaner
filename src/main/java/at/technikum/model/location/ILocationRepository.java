package at.technikum.model.location;

import javafx.concurrent.Task;

import java.util.List;

public interface ILocationRepository {

    void setQuery(String query);

    Task<List<Location>> createTask();
}
