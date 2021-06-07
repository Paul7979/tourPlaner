package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import at.technikum.model.importexport.ExportService;
import at.technikum.model.importexport.ImportService;
import at.technikum.model.logs.Log;
import at.technikum.model.logs.LogsModel;
import at.technikum.model.report.ReportService;
import at.technikum.model.tours.Tour;
import at.technikum.model.tours.ToursModel;
import com.itextpdf.text.DocumentException;
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

import java.io.FileNotFoundException;
import java.io.IOException;

@Getter
@Setter
@Slf4j
public class InitViewModel {

    private final ToursModel toursModel;

    private final LogsModel logsModel;

    private final ExportService exportService;
    private final ImportService importService;

    private final ReportService reportService;

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
        reportService = modelFactory.getReportService();

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

    public void generateFullReport() {
        FileChooser fileChooser = new FileChooser();
        var file = fileChooser.showOpenDialog(new Stage());
        try {
            reportService.generateFullReport(file.getAbsolutePath());
        } catch (FileNotFoundException | DocumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "PDF generation failed, try again!");
            alert.showAndWait();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "PDF generation successful!");
        alert.showAndWait();
    }

    public void generateSingleReport() {
        var tour = selectedTour.get();
        if (tour != null) {
            FileChooser fileChooser = new FileChooser();
            var file = fileChooser.showOpenDialog(new Stage());
            try {
                reportService.generateSingleReport(tour, file.getAbsolutePath());
            } catch (FileNotFoundException | DocumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "PDF generation failed, try again!");
                alert.showAndWait();
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "PDF generation successful!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No tour selected!");
            alert.showAndWait();
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
            try {
                exportService.exportToFile(file);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Export successful");
                alert.showAndWait();
            } catch (IOException e) {
                log.error("Failed to export", e);
                Alert alert = new Alert(Alert.AlertType.WARNING, "Export failed unexpectedly, try again");
                alert.showAndWait();
            }
        }
    }
}
