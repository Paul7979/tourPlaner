package at.technikum.views;

import at.technikum.model.tours.Tour;
import at.technikum.viewmodel.CreateLogViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CreateTourLogController implements ViewController {
    public static final String NUMBER_ZERO_TO_TEN = "[0-9]|10";
    public static final String INTEGER = "\\d+";
    public TextField report;
    public TextField distance;
    public TextField totalTime;
    public TextField rating;
    public TextField averageSpeed;
    public TextField typeOfTransport;
    public TextField difficulty;
    public TextField recommendedPeopleCount;
    public CheckBox toiletOnThePath;
    private CreateLogViewModel createLogViewModel;
    public ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();

    public void init(CreateLogViewModel createLogViewModel) {
        this.createLogViewModel = createLogViewModel;
        selectedTour.bindBidirectional(createLogViewModel.selectedTour);
        report.textProperty().bindBidirectional(createLogViewModel.report);
        distance.textProperty().bindBidirectional(createLogViewModel.distance);
        totalTime.textProperty().bindBidirectional(createLogViewModel.totalTime);
        rating.textProperty().bindBidirectional(createLogViewModel.rating);
        averageSpeed.textProperty().bindBidirectional(createLogViewModel.averageSpeed);
        typeOfTransport.textProperty().bindBidirectional(createLogViewModel.typeOfTransport);
        difficulty.textProperty().bindBidirectional(createLogViewModel.difficulty);
        recommendedPeopleCount.textProperty().bindBidirectional(createLogViewModel.recommendedPeopleCount);
        toiletOnThePath.selectedProperty().bindBidirectional(createLogViewModel.toiletOnThePath);
    }

    public void cancelCreation(ActionEvent actionEvent) {
        createLogViewModel.clearViewModel();
        var stage = (Stage) report.getScene().getWindow();
        stage.close();
    }

    public void createLog(ActionEvent actionEvent) {
        log.info("Creating log");
        var errors = validateInput();
        if (errors.isEmpty()) {
            log.info("Input Validation successful!");
            createLogViewModel.createLog();
            createLogViewModel.clearViewModel();
            var stage = (Stage) report.getScene().getWindow();
            stage.close();
        } else {
            log.error("Encountered {} errors when validating input", errors.size());
            Alert alert = new Alert(Alert.AlertType.WARNING, "Errors with inputs: %s".formatted(String.join(", ", errors)));
            alert.showAndWait();
        }

    }

    private List<String> validateInput() {
        List<String> errors = new ArrayList<>();
        if (isEmpty(report.textProperty().get())) {
            errors.add("Report");
        }
        if (isEmpty(distance.textProperty().get()) || !distance.textProperty().get().matches(INTEGER)) {
            errors.add("Distance no Integer");
        }
        if (isEmpty(totalTime.textProperty().get()) || !totalTime.textProperty().get().matches(INTEGER)) {
            errors.add("Total time no Integer");
        }
        if (isEmpty(rating.textProperty().get()) || !rating.textProperty().get().matches(NUMBER_ZERO_TO_TEN)) {
            errors.add("Rating between 0 and 10");
        }

        if (isEmpty(averageSpeed.textProperty().get()) || !averageSpeed.textProperty().get().matches(INTEGER)) {
            errors.add("Total time no Integer");
        }
        if (isEmpty(typeOfTransport.textProperty().get())) {
            errors.add("Type of Transport");
        }

        if (isEmpty(difficulty.textProperty().get()) || !difficulty.textProperty().get().matches(NUMBER_ZERO_TO_TEN)) {
            errors.add("Difficulty between 0 and 10");
        }
        if (isEmpty(recommendedPeopleCount.textProperty().get()) || !recommendedPeopleCount.textProperty().get().matches(INTEGER)) {
            errors.add("Recommended People Count no Integer");
        }
        return errors;
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
