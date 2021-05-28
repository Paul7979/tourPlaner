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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class TourLogsViewController implements Initializable {

    public ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();

    public ObjectProperty<Log> selectedLog = new SimpleObjectProperty<>();

    public StringProperty searchString = new SimpleStringProperty();

    @Setter
    private CreateLogViewModel createLogViewModel;

    public ListView<Log> logsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("Initialized!");

    }

    public void addNewLog(ActionEvent actionEvent) throws IOException {
        log.info("adding log");
        var createTourLogController = (CreateTourLogController) ViewHandler.openView("createTourLog", new Stage(), "Create Log");
        createTourLogController.selectedTour.bindBidirectional(selectedTour);
    }

    public void showLog(ActionEvent actionEvent) {
        log.info("showing log");
    }

    public void removeLog(ActionEvent actionEvent) {
        log.info("removing log");
        createLogViewModel.removeLog(selectedLog.get());

    }

    public void editLog(ActionEvent actionEvent) {
        log.info("editing log");
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
                    setText(item.getReport() + " | " + item.getReport());
                }
            }
        });
    }
}
