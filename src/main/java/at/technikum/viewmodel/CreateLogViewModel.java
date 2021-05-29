package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import at.technikum.model.logs.Log;
import at.technikum.model.logs.LogsModel;
import at.technikum.model.tours.Tour;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class CreateLogViewModel {

    public StringProperty report;
    public StringProperty distance;
    public StringProperty totalTime;
    public StringProperty rating;
    public StringProperty averageSpeed;
    public StringProperty typeOfTransport;
    public StringProperty difficulty;
    public StringProperty recommendedPeopleCount;
    //public BooleanProperty toiletOnThePath;
    public ObjectProperty<LocalDate> date;

    public ObjectProperty<Tour> selectedTour = new SimpleObjectProperty<>();
    public ObjectProperty<Log> selectedLog = new SimpleObjectProperty<>();
    private LogsModel logsModel;

    public CreateLogViewModel(ModelFactory modelFactory) {
        logsModel = modelFactory.getLogsModel();
        report = new SimpleStringProperty();
        distance = new SimpleStringProperty();
        totalTime = new SimpleStringProperty();
        rating = new SimpleStringProperty();
        averageSpeed = new SimpleStringProperty();
        typeOfTransport = new SimpleStringProperty();
        difficulty = new SimpleStringProperty();
        recommendedPeopleCount = new SimpleStringProperty();
        //toiletOnThePath = new SimpleBooleanProperty();
        date = new SimpleObjectProperty<>();
    }

    public void clearViewModel() {
        report.setValue("");
        distance.setValue("");
        totalTime.setValue("");
        rating.setValue("");
        averageSpeed.setValue("");
        typeOfTransport.setValue("");
        difficulty.setValue("");
        recommendedPeopleCount.setValue("");
        date.setValue(LocalDate.now());
    }

    public void createLog() {
        var newLog = Log.builder()
                .report(report.get())
                .distance(distance.get())
                .totalTime(totalTime.get())
                .rating(Integer.valueOf(rating.get()))
                .averageSpeed(averageSpeed.get())
                .typeOfTransport(typeOfTransport.get())
                .difficulty(difficulty.get())
                .recommendedPeopleCount(Integer.parseInt(recommendedPeopleCount.get()))
                .date(date.get())
                .build();
        var tour = selectedTour.get();

        log.info("Adding log {} for {}", newLog, tour);
        logsModel.addLogFor(newLog, tour);
    }

    public void removeLog(Log log) {
        logsModel.removeLog(log);
    }
}
