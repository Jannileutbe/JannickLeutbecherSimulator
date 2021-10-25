import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class TerritoryPanel extends Region {

  private final Territory territory;
  private final GraphicsContext gc;
  private CurrentEvent currentEvent;


  private final Image imageLog = new Image(new FileInputStream("./resources/LadybugAdventure/Log.png"));
  private final Image imageFruit = new Image(new FileInputStream("./resources/LadybugAdventure/Cherry.png"));
  private final Image imageLeaf = new Image(new FileInputStream("./resources/LadybugAdventure/Leaf.png"));
  private final Image imageFruitUnderLeaf = new Image(new FileInputStream("./resources/LadybugAdventure/LeafWithFruit.png"));
  private final Image imageLadybug = new Image(new FileInputStream("./resources/LadybugAdventure/Ladybug.png"));
  private final Image imageFlyingLadybug = new Image(new FileInputStream("./resources/LadybugAdventure/flyingLadybug.png"));

  public TerritoryPanel(Territory territory, CurrentEvent currentEvent) throws FileNotFoundException {
    this.territory = territory;
    Canvas canvas = new Canvas();
    canvas.setWidth((territory.getColumns() + 2) * 34);
    canvas.setHeight((territory.getRows() + 2) * 34);
    this.gc = canvas.getGraphicsContext2D();
    buildPlayingField();
    this.getChildren().addAll(canvas);
    this.setVisible(true);
    this.currentEvent = currentEvent;


    canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent me) {
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
          default:
            System.err.println("No Button pressed!");
        }
      }
    });
  }

  // Mit Dana Warmbold zusammengearbeitet
  private void buildPlayingField() {
    int xCoordinate;
    int yCoordinate = 34;

    gc.setFill(Color.WHITE);
    gc.fillRect(0, 0, ((territory.getColumns() + 2) * 34), ((territory.getColumns() + 2) * 34));

    for (int i = 0; i < territory.getPlayingField().length; i++) {
      xCoordinate = 34;
      for (int j = 0; j < territory.getPlayingField()[i].length; j++) {
        buildTile(xCoordinate, yCoordinate, territory.getPlayingField()[i][j]);
        xCoordinate += 34;
      }
      yCoordinate += 34;
    }
  }

  // Mit Dana Warmbold zusammengearbeitet
  private void buildTile(int xCoordinate, int yCoordinate, Tile tile) {
    gc.setFill(Color.DARKOLIVEGREEN);
    gc.setStroke(Color.SIENNA);
    gc.setLineWidth(1);
    gc.fillRect(xCoordinate, yCoordinate, 34, 34);
    gc.strokeRect(xCoordinate, yCoordinate, 34, 34);

    switch (tile.getState()) {
      case 0:
        break;
      case 1:
        gc.drawImage(this.imageLog, xCoordinate, yCoordinate);
        break;
      case 2:
        gc.drawImage(this.imageFruit, xCoordinate, yCoordinate);
        break;
      case 3:
        gc.drawImage(this.imageLeaf, xCoordinate, yCoordinate);
        break;
      case 4:
        gc.drawImage(this.imageFruitUnderLeaf, xCoordinate, yCoordinate);
        break;
      default:
        System.err.println("FAIL");
    }

    int xCoordinateLadybug = 34 + ((territory.getLadybug().getColumn() * 34) + 1);
    int yCoordinateLadybug = 34 + ((territory.getLadybug().getRow() * 34) + 1);

    if (!territory.getLadybug().isAirborne()) {
      gc.drawImage(this.imageLadybug, xCoordinateLadybug, yCoordinateLadybug);
    } else {
      gc.drawImage(this.imageFlyingLadybug, xCoordinateLadybug, yCoordinateLadybug);
    }
  }

}
