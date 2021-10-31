package simulator.view;

import simulator.viewmodel.ViewModel;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import simulator.model.CurrentEvent;
import simulator.model.Territory;
import simulator.model.Tile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class TerritoryPanel extends Region {

  private Territory territory;
  private final ViewModel viewModel;
  private GraphicsContext gc;


  private final Image imageLog = new Image(new FileInputStream("./resources/LadybugAdventure/Log.png"));
  private final Image imageFruit = new Image(new FileInputStream("./resources/LadybugAdventure/Cherry.png"));
  private final Image imageLeaf = new Image(new FileInputStream("./resources/LadybugAdventure/Leaf.png"));
  private final Image imageFruitUnderLeaf = new Image(new FileInputStream("./resources/LadybugAdventure/LeafWithFruit.png"));
  private final Image imageLadybug = new Image(new FileInputStream("./resources/LadybugAdventure/Ladybug.png"));
  private final Image imageLadybug90Degree = new Image(new FileInputStream("./resources/LadybugAdventure/Ladybug90Degree.png"));
  private final Image imageLadybug180Degree = new Image(new FileInputStream("./resources/LadybugAdventure/Ladybug180Degree.png"));
  private final Image imageLadybug270Degree = new Image(new FileInputStream("./resources/LadybugAdventure/Ladybug270Degree.png"));
  private final Image imageFlyingLadybug = new Image(new FileInputStream("./resources/LadybugAdventure/FlyingLadybug.png"));
  private final Image imageFlyingLadybug90Degree = new Image(new FileInputStream("./resources/LadybugAdventure/FlyingLadybug90Degree.png"));
  private final Image imageFlyingLadybug180Degree = new Image(new FileInputStream("./resources/LadybugAdventure/FlyingLadybug180Degree.png"));
  private final Image imageFlyingLadybug270Degree = new Image(new FileInputStream("./resources/LadybugAdventure/FlyingLadybug270Degree.png"));


  public TerritoryPanel(Territory territory, ViewModel viewModel) throws FileNotFoundException {
    this.territory = territory;
    this.viewModel = viewModel;
    buildPlayingField(territory);
  }

  // Mit Dana Warmbold zusammengearbeitet
  public void buildPlayingField(Territory territory) {
    this.territory = territory;
    this.getChildren().clear();
    Canvas canvas = new Canvas();
    canvas.setWidth((territory.getColumns() + 2) * 34);
    canvas.setHeight((territory.getRows() + 2) * 34);
    this.gc = canvas.getGraphicsContext2D();
    this.getChildren().add(canvas);
    this.setVisible(true);
    canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent me) {
        viewModel.handleEvent(me);
        buildPlayingField(territory);
      }
    });

    int xCoordinate;
    int yCoordinate = 34;

    gc.setFill(Color.WHITE);
    gc.fillRect(0, 0, ((territory.getColumns() + 2) * 34), ((territory.getRows() + 2) * 34));

    for (int i = 0; i < territory.getPlayingField().length; i++) {
      xCoordinate = 34;
      for (int j = 0; j < territory.getPlayingField()[i].length; j++) {
        buildTile(xCoordinate, yCoordinate, territory.getPlayingField()[i][j]);
        xCoordinate += 34;
      }
      yCoordinate += 34;
    }
    printLadybug();
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
        gc.fillRect(xCoordinate, yCoordinate, 34, 34);
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
  }

  public void printLadybug() {
    int xCoordinateLadybug = 34 + ((territory.getLadybug().getColumn() * 34) + 1);
    int yCoordinateLadybug = 34 + ((territory.getLadybug().getRow() * 34) + 1);
    if (!territory.getLadybug().isAirborne()) {
      switch (territory.getLadybug().getDirection()) {
        case 0:
          gc.drawImage(this.imageLadybug, xCoordinateLadybug, yCoordinateLadybug);
          break;
        case 1:
          gc.drawImage(this.imageLadybug90Degree, xCoordinateLadybug, yCoordinateLadybug);
          break;
        case 2:
          gc.drawImage(this.imageLadybug180Degree, xCoordinateLadybug, yCoordinateLadybug);
          break;
        case 3:
          gc.drawImage(this.imageLadybug270Degree, xCoordinateLadybug, yCoordinateLadybug);
      }
    } else {
      switch (territory.getLadybug().getDirection()) {
        case 0:
          gc.drawImage(this.imageFlyingLadybug, xCoordinateLadybug, yCoordinateLadybug);
          break;
        case 1:
          gc.drawImage(this.imageFlyingLadybug90Degree, xCoordinateLadybug, yCoordinateLadybug);
          break;
        case 2:
          gc.drawImage(this.imageFlyingLadybug180Degree, xCoordinateLadybug, yCoordinateLadybug);
          break;
        case 3:
          gc.drawImage(this.imageFlyingLadybug270Degree, xCoordinateLadybug, yCoordinateLadybug);
      }
    }
  }
}
