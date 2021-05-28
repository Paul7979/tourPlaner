package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import at.technikum.model.logs.Log;
import at.technikum.model.logs.LogsModel;
import at.technikum.model.tours.Tour;
import at.technikum.model.tours.ToursModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class InitViewModel {

    private final ToursModel toursModel;

    private final LogsModel logsModel;

    private ObservableList<Tour> tours;

    private ObservableList<Log> logs;

    private ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();

    private ObjectProperty<Image> selectedTourImage = new SimpleObjectProperty<>();

    private StringProperty selectedTourName = new SimpleStringProperty();

    private StringProperty selectedTourDescription = new SimpleStringProperty();

    private StringProperty fullTextSearch = new SimpleStringProperty();

    public InitViewModel(ModelFactory modelFactory) {
        //this.modelFactory = modelFactory;
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
}
