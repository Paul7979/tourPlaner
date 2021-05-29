package at.technikum.model.logs;

import at.technikum.model.tours.Tour;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LogsModel {

    private final LogDAO logDAO;

    private static LogsModel instance;

    List<Runnable> selectedTourChangedCallback = new ArrayList<>();

    private LogsModel() {
        //logDAO = LogDAOInMemory.getInstance();
        logDAO = new PersistentLogDAO();
    }

    public static LogsModel getInstance() {
        if (instance == null) {
            instance = new LogsModel();
        }
        return instance;
    }

    public void addLogFor(Log log, Tour tour) {
        logDAO.addLogForTour(tour, log);
        selectedTourChangedCallback.forEach(Runnable::run);
    }

    public List<Log> getLogsFor(Tour tour) {
        log.info("Getting logs for {}", tour);
        return logDAO.getLogsFor(tour).orElse(new ArrayList<>());
    }

    public void removeLog(Log log) {
        logDAO.removeLog(log);
        selectedTourChangedCallback.forEach(Runnable::run);
    }

    public void updateLog(Log log) {
        logDAO.updateLog(log);
        selectedTourChangedCallback.forEach(Runnable::run);
    }

    public void addSelectedToursChangedCallback(Runnable callback) {
        selectedTourChangedCallback.add(callback);
    }


}
