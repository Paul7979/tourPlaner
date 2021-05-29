package at.technikum.views;

import at.technikum.viewmodel.LogDetailsViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TourLogDetailsViewController implements ViewController{
    public TextField report;
    public TextField distance;
    public TextField totalTime;
    public TextField rating;
    public TextField averageSpeed;
    public TextField typeOfTransport;
    public TextField difficulty;
    public TextField recommendedPeopleCount;
    public TextField date;

    public void init(LogDetailsViewModel viewModel) {
        report.textProperty().bindBidirectional(viewModel.getReport());
        distance.textProperty().bindBidirectional(viewModel.getDistance());
        totalTime.textProperty().bindBidirectional(viewModel.getTotalTime());
        rating.textProperty().bindBidirectional(viewModel.getRating());
        averageSpeed.textProperty().bindBidirectional(viewModel.getAverageSpeed());
        typeOfTransport.textProperty().bindBidirectional(viewModel.getTypeOfTransport());
        difficulty.textProperty().bindBidirectional(viewModel.getDifficulty());
        recommendedPeopleCount.textProperty().bindBidirectional(viewModel.getRecommendedPeopleCount());
        date.textProperty().bindBidirectional(viewModel.getDate());
        viewModel.fillViewModel();
    }

    public void close(ActionEvent actionEvent) {
        var stage = (Stage) report.getScene().getWindow();
        stage.close();
    }
}
