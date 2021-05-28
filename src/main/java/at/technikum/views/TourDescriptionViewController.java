package at.technikum.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class TourDescriptionViewController implements Initializable {


    @FXML
    public Label selectedTourNameField = new Label();

    @FXML
    public TextArea selectedTourDescField= new TextArea();

    @FXML
    public ImageView selectedTourRouteInfoField = new ImageView();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
