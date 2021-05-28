package at.technikum.model.tours;

import java.util.List;
import java.util.Optional;

public interface TourDAO {
    Tour add(Tour tour);

    Optional<Tour> getTour(long id);

    List<Tour> getAll();

    List<Tour> searchFor(String term);

    void remove(Tour tour);
}
