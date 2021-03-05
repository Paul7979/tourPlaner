package at.technikum.model;

public class ModelFactory {
    private ILocationModel locationModel;

    public ILocationModel getLocationModel() {
        if (locationModel == null) {
            locationModel = new MockLocationModel();
        }
        return locationModel;
    }



}
