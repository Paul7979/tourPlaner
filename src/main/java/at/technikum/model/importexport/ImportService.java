package at.technikum.model.importexport;

import at.technikum.model.logs.PersistentLogDAO;
import at.technikum.model.tours.PersistentTourDAO;
import at.technikum.model.tours.Tour;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
public class ImportService {

    private final PersistentTourDAO persistentTourDAO;
    private final PersistentLogDAO logDAO;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);


    public ImportService(PersistentTourDAO persistentTourDAO, PersistentLogDAO logDAO) {
        this.persistentTourDAO = persistentTourDAO;
        this.logDAO = logDAO;
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
    }

    public boolean importFromFile(File file) {
        TourExportWrapper tourExportWrapper = null;
        try {
            tourExportWrapper = objectMapper.readValue(file, TourExportWrapper.class);
        } catch (IOException e) {
            log.error("Error deserializing json", e);
            return false;
        }
        var containers = tourExportWrapper.getContainers();
        containers.forEach(container -> {
            var tour = container.getTour();
            Tour tour_from_db = persistentTourDAO.add(tour);
            container.getLogs().forEach(log_in -> logDAO.addLogForTour(tour_from_db, log_in));
        });
        return true;
    }
}
