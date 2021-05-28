package at.technikum.model;

import at.technikum.client.mapsearch.MapSearchService;
import at.technikum.model.logs.LogsModel;
import at.technikum.model.tours.ToursModel;

import java.util.ArrayList;
import java.util.List;

public class ModelFactory {
    private ILocationRepository locationModel;
    private MapSearchService mapSearchService;
    private ToursModel toursModel;
    private LogsModel logsModel;

    List<Runnable> selectedTourChangedCallback = new ArrayList<>();

    public ILocationRepository getLocationModel() {
        if (locationModel == null) {
            //locationModel = new MockLocationRepository();
            locationModel = new MapQuestLocationRepository();
        }
        return locationModel;
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



}
