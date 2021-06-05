package at.technikum.model.importexport;

import at.technikum.client.mapsearch.MapSearchService;
import at.technikum.dal.logs.LogDAO;
import at.technikum.dal.tours.TourDAO;
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

    private final TourDAO persistentTourDAO;
    private final LogDAO logDAO;
    private final MapSearchService mapSearchService;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);


    public ImportService(TourDAO persistentTourDAO, LogDAO logDAO, MapSearchService mapSearchService) {
        this.persistentTourDAO = persistentTourDAO;
        this.logDAO = logDAO;
        this.mapSearchService = mapSearchService;
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
            if (!validMapPath(tour.getMapPath())) {
                log.info("Invalid path for tour {}, fetching new!", tour);
                tour.setMapPath(mapSearchService.searchMapFullSizeBlocking(tour.getStart(), tour.getDestination()).orElse("NOT_FOUND"));
            }
            Tour tour_from_db = persistentTourDAO.add(tour);
            container.getLogs().forEach(log_in -> logDAO.addLogForTour(tour_from_db, log_in));
        });
        return true;
    }

    private boolean validMapPath(String mapPath) {
        File file = new File(mapPath);
        return file.exists();
    }
}
