package at.technikum.model.tours;

import at.technikum.model.Tour;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ToursModel {

    private static ToursModel instance;

    private final TourDAO tourDAO;

    private ToursModel() {
        tourDAO = TourDAO.getInstance();
    }

    public static ToursModel getInstance() {
        if (instance == null) {
            instance = new ToursModel();
        }
        return instance;
    }

    List<Runnable> callbacks = new ArrayList<>();


    public List<Tour> getAll() {
        return tourDAO.getAll();
    }

    public void addTour(Tour tour) {
        tourDAO.add(tour);
        callbacks.forEach(Runnable::run);
    }

    public void removeTour(Tour tour) {
        tourDAO.remove(tour);
    }


    public void addCallback(Runnable callback) {
        callbacks.add(callback);
    }

    public void removeListener(Runnable callback) {
        callbacks.remove(callback);
    }
}
