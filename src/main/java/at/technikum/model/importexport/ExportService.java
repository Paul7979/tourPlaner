package at.technikum.model.importexport;

import at.technikum.dal.logs.LogDAO;
import at.technikum.dal.tours.TourDAO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ExportService {

    private final TourDAO persistentTourDAO;
    private final LogDAO logDAO;

    private ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);


    public ExportService(LogDAO persistentLogDAO, TourDAO persistentTourDAO) {
        this.persistentTourDAO = persistentTourDAO;
        this.logDAO = persistentLogDAO;
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
    }

    public void exportToFile(File file) throws IOException {
        TourExportWrapper tourExportWrapper = new TourExportWrapper();
        var allTours = persistentTourDAO.getAll();
        allTours.forEach(tour -> {
            var logsFor = logDAO.getLogsFor(tour);
            var tourExportContainer = TourExportContainer.builder()
                    .tour(tour)
                    .logs(logsFor.orElse(new ArrayList<>()))
                    .build();
            tourExportWrapper.addContainer(tourExportContainer);
        });
        objectMapper.writeValue(file, tourExportWrapper);
    }
}
