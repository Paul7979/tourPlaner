package at.technikum.views;

import at.technikum.viewmodel.InitViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class InitViewController{


    @Setter
    private InitViewModel initViewModel;

    @FXML
    public ProgressIndicator progressStart;

    @FXML
    public ProgressIndicator progressDestination;

    @FXML
    public ListView<String> listView;

    @FXML
    public TextField startLocation;

    @FXML
    public TextField destinationLocation;

    public void init(InitViewModel initViewModel) {
        this.initViewModel = initViewModel;
        listView.itemsProperty().bind(initViewModel.getListProperty());
        startLocation.textProperty().bindBidirectional(initViewModel.getQueryParamStart());
        destinationLocation.textProperty().bindBidirectional(initViewModel.getQueryParamDestination());
        progressStart.visibleProperty().bindBidirectional(initViewModel.getInProgressStart());
        progressDestination.visibleProperty().bindBidirectional(initViewModel.getInProgressDestination());
    }


    public void onButtonClick(ActionEvent actionEvent) {
        //initViewModel.queryLocation(inProgressStart);
    }

    public void onKeyReleasedStart(KeyEvent keyEvent) {
        initViewModel.queryStartLocation();
    }

    public void elementClicked(MouseEvent mouseEvent) {
        log.info("Picked {}", listView.getSelectionModel().getSelectedItem());
    }

    public void onKeyReleasedDestination(KeyEvent keyEvent) {
        initViewModel.queryDestinationLocation();
    }
}
