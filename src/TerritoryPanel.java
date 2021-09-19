import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class TerritoryPanel extends Region {

  private final Territory territory;
  private final GraphicsContext gc;
/*

  private final Image imageLog = new Image(getClass().getResource(".\\resources\\LadybugAdventure\\Log.png").toString());
  private final Image imageFruit = new Image(getClass().getResource(".\\resources\\LadybugAdventure\\Cherry.png").toString());
  private final Image imageLeaf = new Image(getClass().getResource(".\\resources\\LadybugAdventure\\Leaf.png").toString());
  private final Image imageFruitUnderLeaf = new Image(getClass().getResource(".\\resources\\LadybugAdventure\\LeafWithFruit.png").toString());
  private final Image imageLadybug = new Image(getClass().getResource(".\\resources\\LadybugAdventure\\Ladybug.png").toString());
  private final Image imageFlyingLadybug = new Image(getClass().getResource(".\\resources\\LadybugAdventure\\FlyingLadybug.png").toString());

 */


  public TerritoryPanel(Territory territory){
    this.territory = territory;
    Canvas canvas = new Canvas();
    canvas.setWidth((territory.getSpalten() + 2) * 35);
    canvas.setHeight((territory.getZeilen() + 2) * 35);
    this.gc = canvas.getGraphicsContext2D();
    buildPlayingField();
    this.getChildren().addAll(canvas);
    this.setVisible(true);
  }

  private void buildPlayingField(){
    int xCoordinate;
    int yCoordinate = 35;

    gc.setFill(Color.WHITE);
    gc.fillRect(0,0, ((territory.getSpalten() + 2)*34), ((territory.getSpalten() + 2)*34));

    for (int i = 0; i < territory.getPlayingField().length; i++) {
      xCoordinate = 35;
      for (int j = 0; j < territory.getPlayingField()[i].length; j++) {
        buildTile(xCoordinate, yCoordinate, territory.getPlayingField()[i][j]);
        xCoordinate += 34;
      }
      yCoordinate += 34;
    }
  }

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
        //gc.drawImage(this.imageLog, xCoordinate, yCoordinate);
        break;
      case 2:
        //gc.drawImage(this.imageFruit, xCoordinate, yCoordinate);
        break;
      case 3:
        //gc.drawImage(this.imageLeaf, xCoordinate, yCoordinate);
        break;
      case 4:
        //gc.drawImage(this.imageFruitUnderLeaf, xCoordinate,yCoordinate);
        break;
      default :
        System.err.println("FAIL");
    }

  }
}
