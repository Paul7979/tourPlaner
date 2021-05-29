package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import lombok.Getter;

@Getter
public class ViewModelFactory {

    private final InitViewModel initViewModel;
    private final CreateTourViewModel createTourViewModel;
    private final CreateLogViewModel createLogViewModel;
    private final LogDetailsViewModel logDetailsViewModel;
    private final LogEditViewModel logEditViewModel;

    public ViewModelFactory (ModelFactory modelFactory) {
        initViewModel = new InitViewModel(modelFactory);
        createTourViewModel = new CreateTourViewModel(modelFactory);
        createLogViewModel = new CreateLogViewModel(modelFactory);
        logDetailsViewModel = new LogDetailsViewModel(modelFactory);
        logEditViewModel = new LogEditViewModel(modelFactory);
    }
}
