package at.technikum.views;

import at.technikum.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class ViewHandler {

    private final Stage stage;
    private final ViewModelFactory viewModelFactory;

    public void start() throws IOException {
        openView("init");
    }

    public void openView(String viewToOpen) throws IOException {
        log.info("Opening view: {}", viewToOpen);
        Scene scene = null;
        FXMLLoader loader = new FXMLLoader();
        Parent parent = null;

        loader.setLocation(getClass().getResource("/" + viewToOpen + "View.fxml"));
        parent = loader.load();

        if ("init".equals(viewToOpen)) {
            InitViewController controller = loader.getController();
            controller.init(viewModelFactory.getInitViewModel());
        }
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("RoutePlaner");
        stage.show();
    }
}
