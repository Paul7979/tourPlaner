package at.technikum.dal.logs;

import at.technikum.model.logs.Log;
import at.technikum.model.tours.Tour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class LogDAOInMemory implements LogDAO {

    private static LogDAOInMemory instance;

    private LogDAOInMemory() {
        logsForTour = new HashMap<>();
    }

    public static LogDAOInMemory getInstance() {
        if (instance == null) {
            instance = new LogDAOInMemory();
        }
        return instance;
    }

    private final HashMap<Tour, List<Log>> logsForTour;

    @Override
    public void addLogForTour(Tour tour, Log log) {
        if (logsForTour.containsKey(tour)) {
            logsForTour.get(tour).add(log);
            return;
        }
        var logs = new ArrayList<Log>();
        logs.add(log);
        logsForTour.put(tour, logs);
    }

    @Override
    public void removeLog(Log log) {
        System.out.println("Removing log + " + log.getReport());
    }

    @Override
    public void updateLog(Log log) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Optional<List<Log>> getLogsFor(Tour tour) {
        return Optional.ofNullable(logsForTour.get(tour));
    }

}
