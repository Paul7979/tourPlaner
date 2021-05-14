package at.technikum.model.tours;

import at.technikum.model.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourDAO {

    private static TourDAO tourDAO;
    private List<Tour> tours;


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
        tours.add(tour);
    }

    public List<Tour> getAll() {
        return tours;
    }


    public void remove(Tour tour) {
        tours.remove(tour);
    }
}
