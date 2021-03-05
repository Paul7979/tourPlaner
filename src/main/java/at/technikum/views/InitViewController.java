package at.technikum.views;

import at.technikum.viewmodel.InitViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@NoArgsConstructor
public class InitViewController{


    @Setter
    private InitViewModel initViewModel;

    @FXML
    public ListView<String> listView;

    @FXML
    public TextField startLocation;

    public void init(InitViewModel initViewModel) {
        this.initViewModel = initViewModel;
        listView.itemsProperty().bind(initViewModel.getListProperty());
        startLocation.textProperty().bindBidirectional(initViewModel.getQueryParam());
    }


    public void onButtonClick(ActionEvent actionEvent) {
        initViewModel.queryLocation();
    }
}
