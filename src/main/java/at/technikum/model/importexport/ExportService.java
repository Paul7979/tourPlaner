package at.technikum.model.importexport;

import at.technikum.model.logs.PersistentLogDAO;
import at.technikum.model.tours.PersistentTourDAO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.concurrent.Task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ExportService {

    private final PersistentTourDAO persistentTourDAO;
    private final PersistentLogDAO logDAO;

    private final ObjectMapper objectMapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);


    public ExportService(PersistentLogDAO persistentLogDAO, PersistentTourDAO persistentTourDAO) {
        this.persistentTourDAO = persistentTourDAO;
        this.logDAO = persistentLogDAO;
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
    }

    public Task<String> exportToFile(File file) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
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
                return "";
            }
        };
    }
}
