package at.technikum.views;

import at.technikum.viewmodel.CreateTourViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateTourViewController implements Initializable, ViewController {

    @FXML
    public TextField name;

    @FXML
    public TextField description;

    @FXML
    public TextField distance ;

    @FXML
    public TextField start;

    @FXML
    public TextField destination;

    @FXML
    public ImageView preview;

    @FXML
    public ProgressIndicator progressStart;

    private CreateTourViewModel createTourViewModel;

    private BooleanProperty inProgress = new SimpleBooleanProperty(false);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing create tour view controller");
        //progressStart.setProgress(-1);
        //progressStart.visibleProperty().bindBidirectional(inProgress);
    }

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

    public void cancelCreation(ActionEvent actionEvent) {
        createTourViewModel.clearViewModel();
        var stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    public void updatePreview(ActionEvent actionEvent) {
        System.out.println("Updating preview");
        createTourViewModel.updatePreview();
    }

    private boolean validStartAndDestination(TextField start, TextField destination) {
        return start.textProperty().get().length() > 2 && destination.textProperty().get().length() > 2;
    }

    public void createTour(ActionEvent actionEvent) {
        var errors = createTourViewModel.createTour();
        if (errors.isEmpty()) {
            var stage = (Stage) name.getScene().getWindow();
            stage.close();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING, "Errors with inputs: %s".formatted(String.join(", ", errors)));
        alert.showAndWait();
    }
}
