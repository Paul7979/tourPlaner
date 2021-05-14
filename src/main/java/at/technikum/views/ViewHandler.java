package at.technikum.views;

import at.technikum.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class ViewHandler {

    @Setter
    private static ViewModelFactory viewModelFactory;

    public static void start(Stage stage) throws IOException {
        openView("init", stage, "RoutePlaner");
    }

    public static ViewController openView(String viewToOpen, Stage stage, String title) throws IOException {
        log.info("Opening view: {}", viewToOpen);
        Scene scene = null;
        FXMLLoader loader = new FXMLLoader();
        Parent parent = null;

        loader.setLocation(ViewHandler.class.getResource("/" + viewToOpen + "View.fxml"));
        parent = loader.load();

        if ("init".equals(viewToOpen)) {
            InitViewController controller = loader.getController();
            controller.init(viewModelFactory.getInitViewModel());
        }
        if ("CreateTour".equals(viewToOpen)) {
            CreateTourViewController controller = loader.getController();
            controller.init(viewModelFactory.getCreateTourViewModel());
        }
        //InitViewController controller = loader.getController();
        //controller.init(viewModelFactory.getInitViewModel());
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        return loader.getController();
    }
}
