package at.technikum.model;

public class ModelFactory {
    private ILocationRepository locationModel;

    public ILocationRepository getLocationModel() {
        if (locationModel == null) {
            //locationModel = new MockLocationRepository();
            locationModel = new MapQuestLocationRepository();
        }
        return locationModel;
    }



}
