package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import at.technikum.model.logs.Log;
import at.technikum.model.logs.LogsModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class LogDetailsViewModel implements ViewModel {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");

    private final ObjectProperty<Log> selectedLog = new SimpleObjectProperty<>();
    @Getter(value = AccessLevel.PRIVATE)
    private final LogsModel logsModel;
    private final StringProperty report = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty totalTime = new SimpleStringProperty();
    private final StringProperty rating = new SimpleStringProperty();
    private final StringProperty averageSpeed = new SimpleStringProperty();
    private final StringProperty typeOfTransport = new SimpleStringProperty();
    private final StringProperty difficulty = new SimpleStringProperty();
    private final StringProperty recommendedPeopleCount = new SimpleStringProperty();
    private final StringProperty Date = new SimpleStringProperty();

    public LogDetailsViewModel(ModelFactory modelFactory) {
        logsModel = modelFactory.getLogsModel();
    }

    public void fillViewModel() {
        assert (selectedLog.get() != null);
        report.setValue(selectedLog.get().getReport());
        distance.setValue(selectedLog.get().getDistance());
        totalTime.setValue(selectedLog.get().getTotalTime());
        rating.setValue(String.valueOf(selectedLog.get().getRating()));
        averageSpeed.setValue(selectedLog.get().getAverageSpeed());
        typeOfTransport.setValue(selectedLog.get().getTypeOfTransport());
        difficulty.setValue(selectedLog.get().getDifficulty());
        recommendedPeopleCount.setValue(String.valueOf(selectedLog.get().getRecommendedPeopleCount()));
        Date.setValue(selectedLog.get().getDate().format(dateTimeFormatter));
    }



}
