package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;
import lombok.Getter;

@Getter
public class ViewModelFactory {

    private final InitViewModel initViewModel;
    private final CreateTourViewModel createTourViewModel;

    public ViewModelFactory (ModelFactory modelFactory) {
        initViewModel = new InitViewModel(modelFactory);
        createTourViewModel = new CreateTourViewModel(modelFactory);
    }
}
