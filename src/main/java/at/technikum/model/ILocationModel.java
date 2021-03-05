package at.technikum.model;

import java.util.List;

public interface ILocationModel {

    List<Location> queryForLocation(String query);
}
