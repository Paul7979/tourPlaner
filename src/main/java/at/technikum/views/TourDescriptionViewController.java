package at.technikum.views;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
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


    //private Tour selectedTour= null;
    public LongProperty selectedTourId= new SimpleLongProperty();


    @FXML
    public Label selectedTourNameField = new Label();
    SimpleStringProperty selectedTourName = new SimpleStringProperty();

    @FXML
    public TextArea selectedTourDescField= new TextArea();

    SimpleStringProperty selectedTourDesc = new SimpleStringProperty();

    @FXML
    public ImageView selectedTourRouteInfoField = new ImageView();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
