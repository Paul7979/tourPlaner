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

    public static final String DETAILS_TOUR_LOG = "detailsTourLog";
    public static final String CREATE_TOUR_LOG = "createTourLog";
    public static final String CREATE_TOUR = "createTour";
    public static final String INIT = "init";
    public static final String EDIT_TOUR_LOG = "editTourLog";
    public static final String EDIT_TOUR = "editTour";
    @Setter
    private static ViewModelFactory viewModelFactory;

    public static void start(Stage stage) throws IOException {
        openView(INIT, stage, "RoutePlaner");
    }

    public static ViewController openView(String viewToOpen, Stage stage, String title) throws IOException {
        log.info("Opening view: {}", viewToOpen);
        Scene scene = null;
        FXMLLoader loader = new FXMLLoader();
        Parent parent = null;

        loader.setLocation(ViewHandler.class.getResource("/" + viewToOpen + "View.fxml"));
        parent = loader.load();

        if (INIT.equals(viewToOpen)) {
            InitViewController controller = loader.getController();
            controller.init(viewModelFactory.getInitViewModel(), viewModelFactory.getCreateLogViewModel(), viewModelFactory.getCreateTourViewModel(), viewModelFactory.getLogDetailsViewModel());
        }
        if (CREATE_TOUR.equals(viewToOpen)) {
            CreateTourViewController controller = loader.getController();
            controller.init(viewModelFactory.getCreateTourViewModel());
        }
        if (CREATE_TOUR_LOG.equals(viewToOpen)) {
            CreateTourLogController controller = loader.getController();
            controller.init(viewModelFactory.getCreateLogViewModel());
        }
        if (DETAILS_TOUR_LOG.equals(viewToOpen)) {
            TourLogDetailsViewController controller = loader.getController();
            controller.init(viewModelFactory.getLogDetailsViewModel());
        }
        if (EDIT_TOUR_LOG.equals(viewToOpen)) {
            TourLogEditViewController controller = loader.getController();
            controller.init(viewModelFactory.getLogEditViewModel());
        }
        if (EDIT_TOUR.equals(viewToOpen)) {
            TourEditViewController controller = loader.getController();
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
