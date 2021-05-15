package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import at.technikum.model.Tour;
import at.technikum.model.tours.ToursModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class InitViewModel {

    private final ToursModel toursModel;

    private ObservableList<Tour> tours;

    private StringProperty fullTextSearch = new SimpleStringProperty();

    public InitViewModel(ModelFactory modelFactory) {
        //this.modelFactory = modelFactory;
        toursModel = modelFactory.getToursModel();
        toursModel.addToursChangedCallback(this::modelOrSearchChanged);
    }

    public void modelOrSearchChanged() {
        log.info("Callback got called!");
        tours.retainAll();

        if (fullTextSearch.get() == null || fullTextSearch.get().isBlank() || fullTextSearch.get().length() < 2) {
            tours.addAll(toursModel.getAll());
            return;
        }
        tours.addAll(toursModel.getAllFullTextSearch(fullTextSearch));


    }
}
