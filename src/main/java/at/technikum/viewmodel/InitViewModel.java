package at.technikum.viewmodel;

import at.technikum.model.ILocationModel;
import at.technikum.model.Location;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InitViewModel {

    private ILocationModel locationModel;

    private ListProperty<String> listProperty;

    private StringProperty queryParam;

    public InitViewModel(ILocationModel locationModel) {
        this.locationModel = locationModel;
        queryParam = new SimpleStringProperty();
        listProperty = new SimpleListProperty<>();
    }

    public void queryLocation() {
        if (queryParam.get() != null && queryParam.get().trim().length() > 2) {
            var queryResultString = locationModel.queryForLocation(queryParam.get())
                    .stream()
                    .map(Location::getDisplayName)
                    .collect(Collectors.toList());
            listProperty.setValue(FXCollections
                    .observableArrayList(queryResultString));
        }
    }

}
