package at.technikum.views;

import at.technikum.viewmodel.CreateLogViewModel;
import at.technikum.viewmodel.CreateTourViewModel;
import at.technikum.viewmodel.InitViewModel;
import at.technikum.viewmodel.LogDetailsViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    public void init(InitViewModel initViewModel, CreateLogViewModel createLogViewModel, CreateTourViewModel createTourViewModel, LogDetailsViewModel logDetailsViewModel) {
        this.initViewModel = initViewModel;

        initViewModel.setTours(toursSidebarViewController.toursListView.getItems());
        initViewModel.setLogs(tourLogsViewController.logsListView.getItems());

        initViewModel.getSelectedTour().bindBidirectional(toursSidebarViewController.selectedTour);
        tourLogsViewController.selectedTour.bindBidirectional(toursSidebarViewController.selectedTour);
        createLogViewModel.selectedLog.bindBidirectional(tourLogsViewController.selectedLog);
        tourLogsViewController.selectedLog.bindBidirectional(logDetailsViewModel.getSelectedLog());

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

    public void importData(ActionEvent actionEvent) {
        log.info("Importing Data");
        initViewModel.importData();
        initViewModel.modelOrSearchChanged();
    }

    public void exportData(ActionEvent actionEvent) {
        log.info("Exporting Data");
        initViewModel.exportData();
    }

    public void generateReport(ActionEvent actionEvent) {
        log.info("Generating Report");
        initViewModel.generateFullReport();
    }

    public void generateSingleReport(ActionEvent actionEvent) {
        log.info("Generation single Report");
        initViewModel.generateSingleReport();
    }
}
