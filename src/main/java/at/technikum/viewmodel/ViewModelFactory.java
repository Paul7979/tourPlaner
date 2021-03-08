package at.technikum.viewmodel;

import at.technikum.model.ModelFactory;


public class ViewModelFactory {

    private final InitViewModel initViewModel;

    public ViewModelFactory (ModelFactory modelFactory) {
        initViewModel = new InitViewModel(modelFactory.getLocationModel());
    }

    public InitViewModel getInitViewModel() {
        return initViewModel;
    }
}
