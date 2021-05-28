package at.technikum.views;

import at.technikum.viewmodel.CreateLogViewModel;
import at.technikum.viewmodel.CreateTourViewModel;
import at.technikum.viewmodel.InitViewModel;
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

    public void init(InitViewModel initViewModel, CreateLogViewModel createLogViewModel, CreateTourViewModel createTourViewModel) {
        this.initViewModel = initViewModel;

        initViewModel.setTours(toursSidebarViewController.toursListView.getItems());
        initViewModel.setLogs(tourLogsViewController.logsListView.getItems());


        initViewModel.getSelectedTour().bindBidirectional(toursSidebarViewController.selectedTour);
        tourLogsViewController.selectedTour.bindBidirectional(toursSidebarViewController.selectedTour);
        createLogViewModel.selectedLog.bindBidirectional(tourLogsViewController.selectedLog);


        tourLogsViewController.setCreateLogViewModel(createLogViewModel);
        toursSidebarViewController.setCreateTourViewModel(createTourViewModel);

        initViewModel.getFullTextSearch().bindBidirectional(fullTextSearch.textProperty());
        fullTextSearch.setOnKeyReleased(event -> {
            initViewModel.modelOrSearchChanged();
        });

        //Refresh on startup
        initViewModel.modelOrSearchChanged();

        tourDescriptionViewController.selectedTourRouteInfoField.imageProperty().bindBidirectional(initViewModel.getSelectedTourImage());
        tourDescriptionViewController.selectedTourDescField.textProperty().bindBidirectional(initViewModel.getSelectedTourDescription());
        tourDescriptionViewController.selectedTourNameField.textProperty().bindBidirectional(initViewModel.getSelectedTourName());
        searchString.bindBidirectional(tourLogsViewController.searchString);
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
