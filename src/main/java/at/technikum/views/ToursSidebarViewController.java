package at.technikum.views;

import at.technikum.model.tours.Tour;
import at.technikum.viewmodel.CreateTourViewModel;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
@Getter
public class ToursSidebarViewController implements Initializable {

    @Setter
    private CreateTourViewModel createTourViewModel;

    public LongProperty selectedTourId= new SimpleLongProperty();

    private StringProperty searchString = new SimpleStringProperty();

    public ListView<Tour> toursListView;

    public ObjectProperty<Tour> selectedTour;

    public void addNewTour(ActionEvent actionEvent) throws IOException {
        log.info("Adding new tour");
        ViewHandler.openView(ViewHandler.CREATE_TOUR, new Stage(), "Create Tour");
    }

    public void removeTour(ActionEvent actionEvent) {
        log.info("removing tour");
        createTourViewModel.removeTour(selectedTour.get());
    }

    public void editTour(ActionEvent actionEvent) {
        log.info("edit tour");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedTour = new SimpleObjectProperty<>();
        setUpListView();
    }

    public void selectedTour(MouseEvent mouseEvent) {
        selectedTour.setValue(toursListView.getSelectionModel().getSelectedItem());
    }

    private void setUpListView() {
        toursListView.setCellFactory(in -> new ListCell<>(){
                @Override
                protected void updateItem(Tour item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null || item.getId() == 0)
                        setText(null);
                    else {
                        setText(item.getName() + " " + item.getStart() + " to " + item.getDestination());
                    }
                }
        });
    }
}
