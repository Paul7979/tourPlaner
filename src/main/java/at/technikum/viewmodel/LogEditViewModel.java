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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class LogEditViewModel implements IViewModel {

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
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    public LogEditViewModel(ModelFactory modelFactory) {
        this.logsModel = modelFactory.getLogsModel();
    }

    public void fillViewModel() {
        report.setValue(selectedLog.get().getReport());
        distance.setValue(selectedLog.get().getDistance());
        totalTime.setValue(selectedLog.get().getTotalTime());
        rating.setValue(String.valueOf(selectedLog.get().getRating()));
        averageSpeed.setValue(selectedLog.get().getAverageSpeed());
        typeOfTransport.setValue(selectedLog.get().getTypeOfTransport());
        difficulty.setValue(selectedLog.get().getDifficulty());
        recommendedPeopleCount.setValue(String.valueOf(selectedLog.get().getRecommendedPeopleCount()));
        date.setValue(selectedLog.get().getDate());
    }

    public void saveUpdatedViewModel() {
        logsModel.updateLog(
                Log.builder()
                .id(selectedLog.get().getId())
                .report(report.getValue())
                .distance(distance.getValue())
                .totalTime(totalTime.getValue())
                .rating(Integer.valueOf(rating.getValue()))
                .averageSpeed(averageSpeed.getValue())
                .typeOfTransport(typeOfTransport.getValue())
                .difficulty(difficulty.getValue())
                .recommendedPeopleCount(Integer.parseInt(recommendedPeopleCount.getValue()))
                .date(date.get())
                .build()
        );
    }


}
