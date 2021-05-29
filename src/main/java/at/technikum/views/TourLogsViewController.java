package at.technikum.views;

import at.technikum.model.logs.Log;
import at.technikum.model.tours.Tour;
import at.technikum.viewmodel.CreateLogViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Slf4j
public class TourLogsViewController implements Initializable {

    public ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();

    public ObjectProperty<Log> selectedLog = new SimpleObjectProperty<>();

    public StringProperty searchString = new SimpleStringProperty();

    @Setter
    private CreateLogViewModel createLogViewModel;

    public ListView<Log> logsListView;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("Initialized!");
        setUpListView();
    }

    public void addNewLog(ActionEvent actionEvent) throws IOException {
        log.info("adding log");
        var createTourLogController = (CreateTourLogController) ViewHandler.openView(ViewHandler.CREATE_TOUR_LOG, new Stage(), "Create Log");
        createTourLogController.selectedTour.bindBidirectional(selectedTour);
    }

    public void showLog(ActionEvent actionEvent) throws IOException {
        log.info("showing log details");
        if (selectedLog.get() != null) {
            ViewHandler.openView(ViewHandler.DETAILS_TOUR_LOG, new Stage(), "Log Detail");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a log to show");
            alert.showAndWait();
        }
    }

    public void removeLog(ActionEvent actionEvent) {
        log.info("removing log");
        createLogViewModel.removeLog(selectedLog.get());

    }

    public void editLog(ActionEvent actionEvent) throws IOException {
        log.info("editing log");
        if (selectedLog.get() != null) {
            var editViewController = (TourLogEditViewController) ViewHandler.openView(ViewHandler.EDIT_TOUR_LOG, new Stage(), "Edit Log");
            editViewController.selectedLog.setValue(selectedLog.getValue());
            editViewController.refreshViewModel();
            selectedLog.setValue(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a log to edit");
            alert.showAndWait();
        }
    }

    public void selectedLog(MouseEvent mouseEvent) {
        selectedLog.setValue(logsListView.getSelectionModel().getSelectedItem());
    }

    private void setUpListView() {
        logsListView.setCellFactory(in -> new ListCell<>(){
            @Override
            protected void updateItem(Log item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getId() == 0)
                    setText(null);
                else {
                    setText(item.getDate().format(dateTimeFormatter) + " | " + item.getReport());
                }
            }
        });
    }
}
