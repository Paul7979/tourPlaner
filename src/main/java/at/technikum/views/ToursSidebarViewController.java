package at.technikum.views;

import at.technikum.model.Tour;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Getter
public class ToursSidebarViewController {

    public LongProperty selectedTourId= new SimpleLongProperty();

    //public TourCreateViewController tourCreateViewController;

    //public TourEditViewController tourEditViewController;

    private StringProperty searchString = new SimpleStringProperty();

    public ListView<Tour> toursListView;

    public void addNewTour(ActionEvent actionEvent) throws IOException {
        log.info("Adding new tour");
        ViewHandler.openView("CreateTour", new Stage(), "Create Tour");
    }

    public void removeTour(ActionEvent actionEvent) {
        log.info("removing tour");
    }

    public void editTour(ActionEvent actionEvent) {
        log.info("edit tour");
    }
}
