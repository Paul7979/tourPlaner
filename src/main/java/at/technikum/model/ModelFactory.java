package at.technikum.model;

import at.technikum.client.mapsearch.MapSearchService;
import at.technikum.client.route.RouteSearchService;
import at.technikum.model.importexport.ExportService;
import at.technikum.model.importexport.ImportService;
import at.technikum.model.location.ILocationRepository;
import at.technikum.model.location.MapQuestLocationRepository;
import at.technikum.model.logs.LogsModel;
import at.technikum.dal.logs.PersistentLogDAO;
import at.technikum.dal.tours.PersistentTourDAO;
import at.technikum.model.report.ReportService;
import at.technikum.model.tours.ToursModel;

import java.util.ArrayList;
import java.util.List;

public class ModelFactory {
    private ILocationRepository locationModel;
    private MapSearchService mapSearchService;
    private ToursModel toursModel;
    private LogsModel logsModel;
    private ExportService exportService;
    private ImportService importService;
    private ReportService reportService;

    List<Runnable> selectedTourChangedCallback = new ArrayList<>();


    public ILocationRepository getLocationModel() {
        if (locationModel == null) {
            //locationModel = new MockLocationRepository();
            locationModel = new MapQuestLocationRepository();
        }
        return locationModel;
    }

    public RouteSearchService getRouteSearchService() {
        return RouteSearchService.getInstance();
    }

    public ExportService getExportService() {
        if (exportService == null) {
            exportService = new ExportService(new PersistentLogDAO(), new PersistentTourDAO());
        }
        return exportService;
    }

    public ImportService getImportService() {
        if (importService == null) {
            importService = new ImportService(new PersistentTourDAO(), new PersistentLogDAO(), MapSearchService.getInstance());
        }
        return importService;
    }

    public LogsModel getLogsModel() {
        if (logsModel == null) {
            logsModel = LogsModel.getInstance();
        }
        return logsModel;
    }

    public MapSearchService getMapSearchService() {
        if (mapSearchService == null) {
            mapSearchService = MapSearchService.getInstance();
        }
        return mapSearchService;
    }

    public ToursModel getToursModel() {
        if (toursModel == null) {
            toursModel = ToursModel.getInstance();
        }
        return toursModel;
    }


    public ReportService getReportService() {
        if (reportService == null) {
            reportService = new ReportService(new PersistentTourDAO(), new PersistentLogDAO());
        }
        return reportService;
    }
}
