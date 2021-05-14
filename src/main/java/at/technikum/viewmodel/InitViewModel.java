package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import at.technikum.model.Tour;
import at.technikum.model.tours.ToursModel;
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

    public InitViewModel(ModelFactory modelFactory) {
        //this.modelFactory = modelFactory;
        toursModel = modelFactory.getToursModel();
        toursModel.addCallback(this::modelChanged);
    }

    private void modelChanged() {
        log.info("Callback got called!");
        tours.retainAll();
        tours.addAll(toursModel.getAll());
    }
}
