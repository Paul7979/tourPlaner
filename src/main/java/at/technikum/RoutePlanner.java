package at.technikum;

import at.technikum.model.ModelFactory;
import at.technikum.viewmodel.ViewModelFactory;
import at.technikum.views.ViewHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class RoutePlanner extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var modelFactory = new ModelFactory();
        var viewModelFactory = new ViewModelFactory(modelFactory);
        var viewHandler = new ViewHandler(stage, viewModelFactory);
        viewHandler.start();
    }

    public static void main(String[] args) {
        launch();
    }

}