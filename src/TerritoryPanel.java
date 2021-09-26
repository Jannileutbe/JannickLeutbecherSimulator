import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TerritoryPanel extends Region {

  private final Territory territory;

  private final GraphicsContext gc;

  private final Image imageLog = new Image(new FileInputStream("./resources/LadybugAdventure/Log.png"));
  private final Image imageFruit = new Image(new FileInputStream("./resources/LadybugAdventure/Cherry.png"));
  private final Image imageLeaf = new Image(new FileInputStream("./resources/LadybugAdventure/Leaf.png"));
  private final Image imageFruitUnderLeaf = new Image(new FileInputStream("./resources/LadybugAdventure/LeafWithFruit.png"));
  private final Image imageLadybug = new Image(new FileInputStream("./resources/LadybugAdventure/Ladybug.png"));
  private final Image imageFlyingLadybug = new Image(new FileInputStream("./resources/LadybugAdventure/flyingLadybug.png"));

  public TerritoryPanel(Territory territory) throws FileNotFoundException {
    this.territory = territory;
    Canvas canvas = new Canvas();
    canvas.setWidth((territory.getSpalten() + 2) * 34);
    canvas.setHeight((territory.getZeilen() + 2) * 34);
    this.gc = canvas.getGraphicsContext2D();
    buildPlayingField();
    this.getChildren().addAll(canvas);
    this.setVisible(true);
  }


  // Mit Dana Warmbold zusammengearbeitet
  private void buildPlayingField(){
    int xCoordinate;
    int yCoordinate = 34;

    gc.setFill(Color.WHITE);
    gc.fillRect(0,0, ((territory.getSpalten() + 2)*34), ((territory.getSpalten() + 2)*34));

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
  private void buildTile(int xCoordinate, int yCoordinate, Tile tile){
    gc.setFill(Color.DARKOLIVEGREEN);
    gc.setStroke(Color.SIENNA);
    gc.setLineWidth(1);
    gc.fillRect(xCoordinate, yCoordinate, 34, 34);
    gc.strokeRect(xCoordinate, yCoordinate, 34, 34);

    switch(tile.getState()) {
      case 0:
        break;
      case 1:
        gc.drawImage(this.imageLog, xCoordinate , yCoordinate);
        break;
      case 2:
        gc.drawImage(this.imageFruit, xCoordinate, yCoordinate);
        break;
      case 3:
        gc.drawImage(this.imageLeaf, xCoordinate, yCoordinate);
        break;
      case 4:
        gc.drawImage(this.imageFruitUnderLeaf, xCoordinate,yCoordinate);
        break;
      default :
        System.err.println("FAIL");
    }

    int xCoordinateLadybug = 34+((territory.getLadybug().getxCoordinate() * 34) + 1);
    int yCoordinateLadybug = 34+((territory.getLadybug().getyCoordinate() * 34) + 1);

    if (!territory.getLadybug().isAirborne()) {
      gc.drawImage(this.imageLadybug, xCoordinateLadybug, yCoordinateLadybug);
    } else {
      gc.drawImage(this.imageFlyingLadybug, xCoordinateLadybug, yCoordinateLadybug);
    }

  }
}
