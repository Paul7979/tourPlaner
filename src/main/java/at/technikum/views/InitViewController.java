package at.technikum.views;

import at.technikum.viewmodel.InitViewModel;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class InitViewController implements ViewController{


    public LongProperty selectedTourId = new SimpleLongProperty();

    public StringProperty searchString = new SimpleStringProperty("");

    @FXML
    public TextField fullTextSearch;

    @FXML
    public ToursSidebarViewController toursSidebarViewController;

    @FXML
    public TourDescriptionViewController tourDescriptionViewController;

    @FXML
    public TourLogsViewController tourLogsViewController;

    @Setter
    private InitViewModel initViewModel;
/*
    @FXML
    public ProgressIndicator progressStart;

    @FXML
    public ProgressIndicator progressDestination;

    @FXML
    public ListView<String> listView;

    @FXML
    public TextField startLocation;

    @FXML
    public TextField destinationLocation;

    public void init(InitViewModel initViewModel) {
        this.initViewModel = initViewModel;
        listView.itemsProperty().bind(initViewModel.getListProperty());
        startLocation.textProperty().bindBidirectional(initViewModel.getQueryParamStart());
        destinationLocation.textProperty().bindBidirectional(initViewModel.getQueryParamDestination());
        progressStart.visibleProperty().bindBidirectional(initViewModel.getInProgressStart());
        progressDestination.visibleProperty().bindBidirectional(initViewModel.getInProgressDestination());
    }*/

    public void init(InitViewModel initViewModel) {
        this.initViewModel = initViewModel;
        selectedTourId.bindBidirectional(toursSidebarViewController.selectedTourId);
        selectedTourId.bindBidirectional(tourDescriptionViewController.selectedTourId);
        selectedTourId.bindBidirectional(tourLogsViewController.selectedTourId);
        initViewModel.setTours(toursSidebarViewController.toursListView.getItems());

        //fullTextSearch.textProperty().bindBidirectional(searchString);

        //searchString.bindBidirectional(toursSidebarViewController.getSearchString());
        initViewModel.getFullTextSearch().bindBidirectional(fullTextSearch.textProperty());
        fullTextSearch.setOnKeyReleased(event -> {
            initViewModel.modelOrSearchChanged();
        });
        searchString.bindBidirectional(tourLogsViewController.searchString);
    }


    public void onButtonClick(ActionEvent actionEvent) {
        //initViewModel.queryLocation(inProgressStart);
    }

    public void onKeyReleasedStart(KeyEvent keyEvent) {
        //initViewModel.queryStartLocation();
    }

    public void elementClicked(MouseEvent mouseEvent) {
        //log.info("Picked {}", listView.getSelectionModel().getSelectedItem());
    }

    public void onKeyReleasedDestination(KeyEvent keyEvent) {
        //initViewModel.queryDestinationLocation();
    }

    public void importData(ActionEvent actionEvent) {
        log.info("Importing Data");
    }

    public void exportData(ActionEvent actionEvent) {
        log.info("Exporting Data");
    }

    public void generateReport(ActionEvent actionEvent) {
        log.info("Generating Report");
    }
}
