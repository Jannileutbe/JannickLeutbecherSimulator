package simulator;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;
import simulator.viewmodel.ViewModel;

public class Main extends Application {

  public static void main(String[] args) throws FileNotFoundException {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage) throws Exception {
    ViewModel viewModel = new ViewModel(primaryStage);
  }
}
