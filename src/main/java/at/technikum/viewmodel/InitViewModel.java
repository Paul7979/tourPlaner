package at.technikum.viewmodel;

import at.technikum.model.ILocationRepository;
import at.technikum.model.Location;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class InitViewModel {

    private ILocationRepository locationModel;

    private ListProperty<String> listProperty;

    private StringProperty queryParam;

    //private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ExecutorService executorService= Executors
            .newFixedThreadPool(4, r -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
            });


    public InitViewModel(ILocationRepository locationModel) {
        this.locationModel = locationModel;
        queryParam = new SimpleStringProperty();
        listProperty = new SimpleListProperty<>();
    }

    public void queryLocation() {
        if (queryParam.get() != null && queryParam.get().trim().length() > 2) {
            var query = locationModel.queryForLocation(queryParam.get());
            executorService.submit(query);
            query.setOnSucceeded(workerStateEvent -> {
                var queryResultString = query.getValue()
                        .stream()
                        .map(Location::getDisplayName)
                        .collect(Collectors.toList());
                listProperty.setValue(FXCollections
                        .observableArrayList(queryResultString));
            });
            query.setOnFailed(workerStateEvent -> {
                log.info("Query Task Failed {}", workerStateEvent);
            });
        }
    }

}
