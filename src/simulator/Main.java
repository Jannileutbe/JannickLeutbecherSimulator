package simulator;

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.stage.Stage;
import simulator.viewmodel.ViewModel;

//Zur Hilfe wurden die Folien aus dem StudIP genommen für das geasmte Projekt
public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage) throws Exception {
    ViewModel viewModel = new ViewModel(primaryStage);
  }
}
