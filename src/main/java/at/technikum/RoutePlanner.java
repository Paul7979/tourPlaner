package at.technikum;

import at.technikum.model.ModelFactory;
import at.technikum.util.AppProperties;
import at.technikum.util.SQLConnectionProvider;
import at.technikum.viewmodel.ViewModelFactory;
import at.technikum.views.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JavaFX App
 */
public class RoutePlanner extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        var apiKey = System.getenv("apiKey");
        if (apiKey == null) {
            throw new IllegalStateException("No Api Key found!");
        }
        Properties properties = new Properties();
        properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties"));
        var validProps = AppProperties.initializeProperties(properties);
        if (!validProps) {
            throw new IllegalStateException("Error initializing properties");
        }
        SQLConnectionProvider.initialize();
        var modelFactory = new ModelFactory();
        var viewModelFactory = new ViewModelFactory(modelFactory);
        //var viewHandler = new ViewHandler(viewModelFactory);
        ViewHandler.setViewModelFactory(viewModelFactory);
        ViewHandler.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}