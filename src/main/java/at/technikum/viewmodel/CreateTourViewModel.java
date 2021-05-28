package at.technikum.viewmodel;

import at.technikum.client.mapsearch.MapSearchService;
import at.technikum.model.ModelFactory;
import at.technikum.model.tours.Tour;
import at.technikum.model.tours.ToursModel;
import at.technikum.util.TaskExecutorService;
import javafx.beans.property.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateTourViewModel {

    private final MapSearchService searchService;

    private final BooleanProperty inProgress;

    private final StringProperty start;

    private final StringProperty destination;

    private final StringProperty name;
    private final StringProperty description;
    private final StringProperty distance;
    private final ObjectProperty<Image> preview;
    private final BooleanProperty imageVisible;
    private final ToursModel toursModel;

    public CreateTourViewModel(ModelFactory modelFactory) {
        searchService = modelFactory.getMapSearchService();
        toursModel = modelFactory.getToursModel();
        inProgress = new SimpleBooleanProperty(false);
        start = new SimpleStringProperty();
        destination = new SimpleStringProperty();
        preview = new SimpleObjectProperty<>();
        description = new SimpleStringProperty();
        distance = new SimpleStringProperty();
        name = new SimpleStringProperty();
        imageVisible = new SimpleBooleanProperty();
    }

    public void updatePreview() {
        if (validateSearchParams()) {
            var imageTask = searchService.searchMapPreview(start.get(), destination.get());
            inProgress.bind(imageTask.runningProperty());
            imageVisible.bind(imageTask.runningProperty().not());
            TaskExecutorService.execute(imageTask);
            imageTask.setOnSucceeded(workerStateEvent -> {
                preview.setValue(imageTask.getValue());
            });
            imageTask.setOnFailed(workerStateEvent -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No Route Found :(");
                alert.showAndWait();
            });
        }
    }

    private boolean validateSearchParams() {
        return (start.get().length() > 2 && destination.get().length() > 2);
    }

    public List<String> createTour() {
        var errors = validateInputForTour();
        if (errors.isEmpty()) {
            var path = searchService.searchMapFullSizeBlocking(start.get(), destination.get());
            if (path.isEmpty()) {
                errors.add("Start, Destination");
                return errors;
            }
            var tour = Tour.builder()
                    .mapPath(path.get())
                    .destination(destination.get())
                    .start(start.get())
                    .name(name.get())
                    .distance(Integer.parseInt(distance.get()))
                    .description(description.get())
                    .build();
            toursModel.addTour(tour);
            clearViewModel();
            return errors;
        }
        return errors;
    }

    public void removeTour(Tour tour) {
        toursModel.removeTour(tour);
    }

    private List<String> validateInputForTour() {
        List<String> errors = new ArrayList<>();
        if (!validateSearchParams()) {
            errors.add("Search Params");
        }
        if (isEmpty(destination.get())) {
            errors.add("Destination");
        }
        if(isEmpty(description.get())) {
            errors.add("Description");
        }
        if (isEmpty(name.get())) {
            errors.add("Destination");
        }
        if (isEmpty(start.get())) {
            errors.add("Start");
        }
        if (isEmpty(distance.get())) {
            errors.add("Distance");
        }
        if (distance.get() == null || !distance.get().matches("\\d+")) {
            errors.add("Distance no Integer");
        }
        return errors;
    }

    public void clearViewModel() {
        distance.setValue("");
        start.setValue("");
        destination.setValue("");
        description.setValue("");
        name.setValue("");
        preview.setValue(null);
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
