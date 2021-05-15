package at.technikum.views;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class TourLogsViewController implements Initializable {

    private Long selectedLogId = 0L;

    //private Tour selectedTour;
    public LongProperty selectedTourId = new SimpleLongProperty();

    public StringProperty searchString = new SimpleStringProperty();

    //public TourLogCreateViewController logCreateViewController;

    //public TourLogEditViewController tourLogEditViewController;

    //public TourLogDetailsViewController tourLogDetailsViewController;

    public ListView logsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addNewLog(ActionEvent actionEvent) {
        log.info("adding log");
    }

    public void showLog(ActionEvent actionEvent) {
        log.info("showing log");
    }

    public void removeLog(ActionEvent actionEvent) {
        log.info("removing log");
    }

    public void editLog(ActionEvent actionEvent) {
        log.info("editing log");
    }

}
