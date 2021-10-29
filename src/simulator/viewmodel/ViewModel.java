package simulator.viewmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import simulator.model.CurrentEvent;
import simulator.model.Territory;
import simulator.view.View;

public class ViewModel {


  private final Territory territory;
  private final CurrentEvent currentEvent;

  public ViewModel(Stage stage) throws FileNotFoundException {
    this.territory = new Territory();
    this.currentEvent = new CurrentEvent();
    View view = new View(territory, this, currentEvent, stage);
  }

  public void handleEvent (MouseEvent me){
    int row = (int)(me.getY() / 34) - 1;
    int column = (int)(me.getX() / 34) - 1;

    switch (currentEvent.getCurrentEvent()) {
      case LADYBUG:
        territory.getLadybug().setCoordinates(row, column);
        System.out.println(territory.getLadybug().getRow() + "" + territory.getLadybug().getColumn());
        break;
      case LOG:
        territory.getPlayingField()[row][column].setState(1);
        System.out.println("Log Updated[" + row + "][" + column + "]");
        break;
      case CHERRY:
        territory.getPlayingField()[row][column].setState(2);
        System.out.println("Cherry Updated[" + row + "][" + column + "]");
        break;
      case LEAF:
        territory.getPlayingField()[row][column].setState(3);
        System.out.println("LeafUpdated[" + row + "][" + column + "]");
        break;
      case DELETE:
        territory.getPlayingField()[row][column].setState(0);
        System.out.println("Tile cleared [" + row + "][" + column + "]");
        break;
      case TURNRIGHT:
        territory.getLadybug().rightTurn();
        break;

      default:
        System.err.println("No Button pressed!");
    }
  }

  public void safeEditor(TextArea textArea) {
    String editorContent = textArea.getText();

    String prefix = "public class JannickLeutbecherSimulator extends Ladybug { \npublic ";
    String postfix = "\n}";
    String savedContent = prefix + editorContent + postfix;
    byte[] bytes = savedContent.getBytes(StandardCharsets.UTF_16);

    String savedContentUTF16 = new String(bytes, StandardCharsets.UTF_16);

    try {
      FileOutputStream fos = new FileOutputStream(new File("./resources/programme/JannickLeutbecherSimulator.java"));
      ObjectOutputStream oos = new ObjectOutputStream(fos);

      oos.writeObject(savedContentUTF16);

    } catch (FileNotFoundException e) {
      System.err.println("File not found!");
    } catch (IOException e) {
      System.err.println("Error");
    }

    /*
    String editorContent = textArea.getText();
    String prefix = "public class JannickLeutbecherSimulator extends Ladybug { public";
    String postfix = "}";
    String savedContent = prefix + editorContent + postfix;
    File savedContentAsFile = new File("./resources/programme/JannickLeutbecherSimulator.java");
    sa
    vedContentAsFile.mkdirs();
     */
  }

  public void openNewWindow(Stage stage) throws IOException {
    ViewModel viewModel = new ViewModel(stage);

  }

  //https://stackabuse.com/java-check-if-string-is-a-number/
  public boolean isNumeric(String string) {
    int intValue;

    if(string == null || string.equals("")) {
      return false;
    }

    try {
      intValue = Integer.parseInt(string);
      return true;
    } catch (NumberFormatException e) {
    }
    return false;
  }
}
