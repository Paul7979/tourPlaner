package at.technikum.model.tours;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TourDAOInMemory implements TourDAO {

    private static TourDAOInMemory tourDAO;
    private static long id = 0;
    private final List<Tour> tours;


    public static TourDAOInMemory getInstance() {
        if (tourDAO == null) {
            tourDAO = new TourDAOInMemory();
        }
        return tourDAO;
    }

    private TourDAOInMemory() {
        tours = new ArrayList<>();
    }

    @Override
    public Tour add(Tour tour) {
        tour.setId(id);
        id++;
        tours.add(tour);
        return tour;
    }

    @Override
    public Optional<Tour> getTour(long id) {
        return tours.parallelStream().filter(tour -> tour.getId() == id).findFirst();
    }

    @Override
    public List<Tour> getAll() {
        return tours;
    }

    @Override
    public List<Tour> searchFor(String term) {
        String searchTerm = term.toLowerCase();
        return tours.parallelStream()
                //.filter(hay -> hay.getFullTextSearchString().contains(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Tour tour) {
        tours.remove(tour);
    }
}
