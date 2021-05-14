package at.technikum.model;

import at.technikum.client.mapsearch.MapSearchService;
import at.technikum.model.tours.ToursModel;

public class ModelFactory {
    private ILocationRepository locationModel;
    private MapSearchService mapSearchService;
    private ToursModel toursModel;

    public ILocationRepository getLocationModel() {
        if (locationModel == null) {
            //locationModel = new MockLocationRepository();
            locationModel = new MapQuestLocationRepository();
        }
        return locationModel;
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
