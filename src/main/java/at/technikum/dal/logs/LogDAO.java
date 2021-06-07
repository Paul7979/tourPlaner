package at.technikum.dal.logs;

import at.technikum.model.logs.Log;
import at.technikum.model.tours.Tour;

import java.util.List;
import java.util.Optional;

public interface LogDAO {

    void addLogForTour(Tour tour, Log log);

    Optional<List<Log>> getLogsFor(Tour tour);

    void removeLog(Log log);

    void updateLog(Log log);

    int getAvgRatingFor(Tour tour);

    int getAvgDifficultyFor(Tour tour);

    int getTotalDistanceFor(Tour tour);
}
