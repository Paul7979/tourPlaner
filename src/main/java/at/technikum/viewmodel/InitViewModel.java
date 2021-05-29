package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import at.technikum.model.importexport.ExportService;
import at.technikum.model.importexport.ImportService;
import at.technikum.model.logs.Log;
import at.technikum.model.logs.LogsModel;
import at.technikum.model.tours.Tour;
import at.technikum.model.tours.ToursModel;
import at.technikum.util.TaskExecutorService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class InitViewModel {

    private final ToursModel toursModel;

    private final LogsModel logsModel;

    private final ExportService exportService;
    private final ImportService importService;

    private ObservableList<Tour> tours;

    private ObservableList<Log> logs;

    private ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();

    private ObjectProperty<Image> selectedTourImage = new SimpleObjectProperty<>();

    private StringProperty selectedTourName = new SimpleStringProperty();

    private StringProperty selectedTourDescription = new SimpleStringProperty();

    private StringProperty fullTextSearch = new SimpleStringProperty();

    public InitViewModel(ModelFactory modelFactory) {
        exportService = modelFactory.getExportService();
        importService = modelFactory.getImportService();
        toursModel = modelFactory.getToursModel();
        logsModel = modelFactory.getLogsModel();

        selectedTour.addListener((observableValue, tour, t1) -> selectedTourOrLogsChanged());

        logsModel.addSelectedToursChangedCallback(this::selectedTourOrLogsChanged);
        toursModel.addToursChangedCallback(this::modelOrSearchChanged);
        toursModel.addToursChangedCallback(this::selectedTourOrLogsChanged);
    }

    public void selectedTourOrLogsChanged() {
        log.info("fetching logs");
        logs.retainAll();
        if (selectedTour.get() != null) {
            logs.addAll(logsModel.getLogsFor(selectedTour.get()));
            selectedTourImage.setValue(toursModel.getImageFor(selectedTour.get()));
            selectedTourDescription.setValue(selectedTour.get().getDescription());
            selectedTourName.setValue(selectedTour.get().getName());
        }
    }

    public void modelOrSearchChanged() {
        log.info("fetching tours");
        tours.retainAll();

        if (fullTextSearch.get() == null || fullTextSearch.get().isBlank() || fullTextSearch.get().length() < 2) {
            tours.addAll(toursModel.getAll());
            return;
        }
        tours.addAll(toursModel.getAllFullTextSearch(fullTextSearch));
    }

    public void importData() {
        FileChooser fileChooser = new FileChooser();
        var file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            var start = System.currentTimeMillis();
            var importFromFile = importService.importFromFile(file);
            log.info("Imported tours in {}ms", System.currentTimeMillis() - start);
            Alert alert;
            if (!importFromFile) {
                alert = new Alert(Alert.AlertType.WARNING, "Import failed, try again!");
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Import successful!");
            }
            alert.showAndWait();
        }
    }

    public void exportData() {
        FileChooser fileChooser = new FileChooser();
        var file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            var exportToFile = exportService.exportToFile(file);
            TaskExecutorService.execute(exportToFile);
            exportToFile.setOnFailed(workerStateEvent -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Export failed unexpectedly, try again");
                alert.showAndWait();
            });
            exportToFile.setOnSucceeded(workerStateEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Export successful");
                alert.showAndWait();
            });
        }
    }
}
