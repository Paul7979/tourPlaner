package at.technikum.model.tours;

import at.technikum.model.Tour;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class ToursModel {

    private static ToursModel instance;

    private final TourDAO tourDAO;

    private ObjectProperty<Tour> selectedTour;

    private ToursModel() {
        tourDAO = TourDAO.getInstance();
    }

    public static ToursModel getInstance() {
        if (instance == null) {
            instance = new ToursModel();
        }
        return instance;
    }

    List<Runnable> toursChangedCallbacks = new ArrayList<>();
    List<Runnable> selectedTourChangedCallback = new ArrayList<>();


    public List<Tour> getAll() {
        return tourDAO.getAll();
    }

    public void addTour(Tour tour) {
        tourDAO.add(tour);
        selectedTourChangedCallback.forEach(Runnable::run);
    }

    public void removeTour(Tour tour) {
        tourDAO.remove(tour);
    }


    public void addToursChangedCallback(Runnable callback) {
        selectedTourChangedCallback.add(callback);
    }

    public void addSelectedToursChangedCallback(Runnable callback) {
        selectedTourChangedCallback.add(callback);
    }

    public void removeToursChangedCallback(Runnable callback) {
        selectedTourChangedCallback.remove(callback);
    }

    public List<Tour> getAllFullTextSearch(StringProperty fullTextSearch) {
        log.info("Searching for {}", fullTextSearch);
        return tourDAO.searchFor(fullTextSearch.get());
    }
}
