package at.technikum.viewmodel;

import at.technikum.client.mapsearch.MapSearchService;
import at.technikum.model.ModelFactory;
import at.technikum.model.Tour;
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

    private final MapSearchService mapSearchService;

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
        mapSearchService = modelFactory.getMapSearchService();
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
            var searchService = MapSearchService.getInstance();
            var imageTask = searchService.searchMap(start.get(), destination.get());
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
            var tour = Tour.builder()
                    .destination(destination.get())
                    .start(start.get())
                    .name(description.get())
                    .distance(Integer.parseInt(distance.get()))
                    .build();
            toursModel.addTour(tour);
            clearViewModel();
            return errors;
        }
        return errors;
    }

    private List<String> validateInputForTour() {
        List<String> errors = new ArrayList<>();
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
        if (!distance.get().matches("\\d+")) {
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
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
