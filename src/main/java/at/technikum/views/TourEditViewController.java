package at.technikum.views;

import at.technikum.model.tours.Tour;
import at.technikum.viewmodel.CreateTourViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TourEditViewController implements ViewController {

    private CreateTourViewModel createTourViewModel;

    public TextField name;
    public TextField description;
    public TextField distance;
    public TextField start;
    public TextField destination;
    public ImageView preview;
    public ProgressIndicator progressStart;

    @Setter
    private Tour selectedTourWhenOpened;

    private BooleanProperty inProgress = new SimpleBooleanProperty(false);


    public void init(CreateTourViewModel createTourViewModel) {
        this.createTourViewModel = createTourViewModel;
        progressStart.setProgress(-1);
        progressStart.visibleProperty().bindBidirectional(createTourViewModel.getInProgress());
        destination.textProperty().bindBidirectional(createTourViewModel.getDestination());
        start.textProperty().bindBidirectional(createTourViewModel.getStart());
        preview.imageProperty().bindBidirectional(createTourViewModel.getPreview());
        description.textProperty().bindBidirectional(createTourViewModel.getDescription());
        distance.textProperty().bindBidirectional(createTourViewModel.getDistance());
        preview.visibleProperty().bindBidirectional(createTourViewModel.getImageVisible());
        name.textProperty().bindBidirectional(createTourViewModel.getName());
    }

    public void fillViewModelToEdit(Tour tour) {
        log.info("Editing tour {}", tour);
        selectedTourWhenOpened = tour;
        name.textProperty().setValue(tour.getName());
        description.textProperty().setValue(tour.getDescription());
        distance.textProperty().setValue(String.valueOf(tour.getDistance()));
        start.textProperty().setValue(tour.getStart());
        destination.textProperty().setValue(tour.getDestination());
    }

    public void cancelCreation(ActionEvent actionEvent) {
        var stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    public void updatePreview(ActionEvent actionEvent) {
        createTourViewModel.updatePreview();
    }

    public void saveTour(ActionEvent actionEvent) {
        var errors = createTourViewModel.updateTour(selectedTourWhenOpened);
        if (errors.isEmpty()) {
            var stage = (Stage) name.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Errors with inputs: %s".formatted(String.join(", ", errors)));
            alert.showAndWait();
        }
    }
}
