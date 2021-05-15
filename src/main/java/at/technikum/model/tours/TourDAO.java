package at.technikum.model.tours;

import at.technikum.model.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TourDAO {

    private static TourDAO tourDAO;
    private static long id = 0;
    private final List<Tour> tours;


    public static TourDAO getInstance() {
        if (tourDAO == null) {
            tourDAO = new TourDAO();
        }
        return tourDAO;
    }

    private TourDAO() {
        tours = new ArrayList<>();
    }

    public void add(Tour tour) {
        tour.setId(id);
        id++;
        tours.add(tour);
    }

    public Optional<Tour> getTour(long id) {
        return tours.parallelStream().filter(tour -> tour.getId() == id).findFirst();
    }

    public List<Tour> getAll() {
        return tours;
    }

    public List<Tour> searchFor(String term) {
        String searchTerm = term.toLowerCase();
        return tours.parallelStream()
                .filter(hay -> hay.getFullTextSearchString().contains(searchTerm))
                .collect(Collectors.toList());
    }

    public void remove(Tour tour) {
        tours.remove(tour);
    }
}
