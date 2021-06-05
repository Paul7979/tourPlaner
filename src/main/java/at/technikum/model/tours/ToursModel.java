package at.technikum.model.tours;

import at.technikum.dal.tours.PersistentTourDAO;
import at.technikum.dal.tours.TourDAO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
public class ToursModel {

    private static ToursModel instance;

    private final TourDAO tourDAO;

    private ObjectProperty<Tour> selectedTour;

    private ToursModel() {
        //tourDAO = TourDAOInMemory.getInstance();
        tourDAO = new PersistentTourDAO();
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
        selectedTourChangedCallback.forEach(Runnable::run);
    }


    public void addToursChangedCallback(Runnable callback) {
        selectedTourChangedCallback.add(callback);
    }

    public void removeToursChangedCallback(Runnable callback) {
        selectedTourChangedCallback.remove(callback);
    }

    public List<Tour> getAllFullTextSearch(StringProperty fullTextSearch) {
        log.info("Searching for {}", fullTextSearch);
        return tourDAO.searchFor(fullTextSearch.get());
    }

    public Image getImageFor(Tour tour) {
        var mapPath = tour.getMapPath();
        try {
            log.info("loading map from {}", mapPath);
            return new Image(new FileInputStream(mapPath));
        } catch (FileNotFoundException e) {
            log.error("Fallback to not_found");
            return new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/not_found.jpg")));
        }
    }

    public void updateTour(Tour tour) {
        tourDAO.update(tour);
        toursChangedCallbacks.forEach(Runnable::run);
        selectedTourChangedCallback.forEach(Runnable::run);
    }
}
