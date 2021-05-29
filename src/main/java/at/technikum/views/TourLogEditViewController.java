package at.technikum.views;

import at.technikum.model.logs.Log;
import at.technikum.viewmodel.LogEditViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TourLogEditViewController implements ViewController {

    private static final String NUMBER_ZERO_TO_TEN = "[0-9]|10";
    private static final String INTEGER = "\\d+";

    public ObjectProperty<Log> selectedLog;

    public TextField report;
    public TextField distance;
    public TextField totalTime;
    public TextField rating;
    public TextField averageSpeed;
    public TextField typeOfTransport;
    public TextField difficulty;
    public TextField recommendedPeopleCount;
    public DatePicker date;


    private LogEditViewModel logEditViewModel;

    public void init(LogEditViewModel viewModel) {
        selectedLog = new SimpleObjectProperty<>();
        viewModel.getSelectedLog().bindBidirectional(selectedLog);
        report.textProperty().bindBidirectional(viewModel.getReport());
        distance.textProperty().bindBidirectional(viewModel.getDistance());
        totalTime.textProperty().bindBidirectional(viewModel.getTotalTime());
        rating.textProperty().bindBidirectional(viewModel.getRating());
        averageSpeed.textProperty().bindBidirectional(viewModel.getAverageSpeed());
        typeOfTransport.textProperty().bindBidirectional(viewModel.getTypeOfTransport());
        difficulty.textProperty().bindBidirectional(viewModel.getDifficulty());
        recommendedPeopleCount.textProperty().bindBidirectional(viewModel.getRecommendedPeopleCount());
        logEditViewModel = viewModel;
    }

    public void refreshViewModel() {
        logEditViewModel.fillViewModel();
        date.setValue(logEditViewModel.getDate().getValue());
    }

    public void refreshDate(ActionEvent actionEvent) {
        logEditViewModel.getDate().setValue(date.getValue());
    }

    public void cancelCreation(ActionEvent actionEvent) {
        var stage = (Stage) report.getScene().getWindow();
        stage.close();
    }

    public void saveLog(ActionEvent actionEvent) {
        var errors = validateInput();
        if (errors.isEmpty()) {
            logEditViewModel.saveUpdatedViewModel();
            selectedLog.setValue(null);
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
        if (date.getValue() == null) {
            errors.add("Date not picked");
        }
        return errors;
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
