package at.technikum.viewmodel;

import at.technikum.model.ILocationRepository;
import at.technikum.model.Location;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Slf4j
public class InitViewModel {

    private final ILocationRepository locationModel;

    private final ListProperty<String> listProperty;

    private final StringProperty queryParamStart;

    private final StringProperty queryParamDestination;

    private final BooleanProperty inProgressStart;

    private final BooleanProperty inProgressDestination;

    private final ExecutorService executorService= Executors
            .newFixedThreadPool(4, r -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
            });


    public InitViewModel(ILocationRepository locationModel) {
        this.locationModel = locationModel;
        queryParamStart = new SimpleStringProperty();
        queryParamDestination = new SimpleStringProperty();
        listProperty = new SimpleListProperty<>();
        inProgressStart = new SimpleBooleanProperty(false);
        inProgressDestination = new SimpleBooleanProperty(false);
    }

    public void queryStartLocation() {
        queryLocation(inProgressStart, queryParamStart.get());
    }

    public void queryDestinationLocation() {
        queryLocation(inProgressDestination, queryParamDestination.get());
    }

    private void queryLocation(BooleanProperty inProgress, String queryParam) {
        log.info("Starting Query for {}", queryParam);
        if (queryParamStart.get() != null && queryParam.trim().length() > 2) {
            locationModel.setQuery(queryParam);
            var query = locationModel.createTask();
            inProgress.bind(query.runningProperty());
            executorService.submit(query);
            query.setOnSucceeded(workerStateEvent -> {
                var queryResultString = query.getValue()
                        .stream()
                        .map(Location::getDisplayName)
                        .toList();
                listProperty.setValue(FXCollections
                        .observableArrayList(queryResultString));
            });
            query.setOnFailed(workerStateEvent -> {
                log.info("Query Task Failed {}", workerStateEvent);
            });
        }
    }

}
