package at.technikum.model;

import javafx.concurrent.Task;

import java.util.List;

public interface ILocationRepository {

    Task<List<Location>> queryForLocation(String query);
}
