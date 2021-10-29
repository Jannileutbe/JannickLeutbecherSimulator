package simulator.model;

import simulator.model.exceptions.NoFruitOnThisTileException;
import simulator.model.exceptions.NoLeafInfrontOfYouException;
import simulator.model.exceptions.RanAgainstWallException;
import simulator.model.exceptions.RanOutsideFieldException;

public class Ladybug {

  private final Territory territory;
  private int direction;
  private FruitFuel fruitFuel;
  private int row;
  private int column;
  private boolean isAirborne;

  public void rightTurn() {
    this.direction = (this.direction + 1) % 4;
  }

  public boolean isAirborne() {
    return isAirborne;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public FruitFuel getFruitFuel() {
    return fruitFuel;
  }

  public int getDirection() {
    return direction;
  }


  public void main() {

  }

  public void setCoordinates(int newRow, int newColumn) {
    if (
        (newRow < 0 || newRow > territory.getRows()) ||
            newColumn < 0 || newColumn > territory.getColumns()
    ) {
      throw new RanOutsideFieldException();
    }
    if (
        (this.territory.getPlayingField()[newRow][newColumn].getState() == 1) ||
            this.territory.getPlayingField()[newRow][newColumn].getState() == 1
    ) {
      throw new RanAgainstWallException();
    }
    this.row = newRow;
    this.column = newColumn;
  }

  public void setRow(int newRow) {
    if (newRow < 0 || newRow > territory.getRows()) {
      throw new RanOutsideFieldException();
    }
    if (this.territory.getPlayingField()[newRow][this.column].getState() == 1) {
      throw new RanAgainstWallException();
    }
    this.row = newRow;
  }

  public void setColumn(int newColumn) {
    if (newColumn < 0 || newColumn > territory.getColumns()) {
      throw new RanOutsideFieldException();
    }
    if (this.territory.getPlayingField()[this.row][newColumn].getState() == 1) {
      throw new RanAgainstWallException();
    }
    this.column = newColumn;
  }

  public Ladybug(Territory territory) {
    this.territory = territory;
    this.direction = 1;
    this.fruitFuel = new FruitFuel(0);
    this.column = 0;
    this.row = 0;
    this.isAirborne = false;
  }

  public void moveForward() throws RanOutsideFieldException, RanAgainstWallException {
    int newRow = this.getRow();
    int newColumn = this.getColumn();
    switch (direction) {
      case 0:
        newRow--;
        break;
      case 1:
        newColumn++;
        break;
      case 2:
        newRow++;
        break;
      case 3:
        newColumn--;
        break;
      default:
        System.err.println("X");
    }
    if (newRow < 0 || newRow > territory.getRows() ||
        newColumn < 0 || newColumn > territory.getColumns()) {
      throw new RanOutsideFieldException();
    }
    if (this.territory.getPlayingField()[newRow][newColumn].getState() == 1) {
      throw new RanAgainstWallException();
    }
    this.row = newRow;
    this.column = newColumn;
  }

  public void moveBackward() {
    int newRow = this.getRow();
    int newColumn = this.getColumn();
    switch (direction) {
      case 0:
        newRow++;
        break;
      case 1:
        newColumn--;
        break;
      case 2:
        newRow--;
        break;
      case 3:
        newColumn++;
        break;
      default:
        System.err.println("X");
    }
    if (newRow < 0 || newRow > territory.getRows() ||
        newColumn < 0 || newColumn > territory.getColumns()) {
      throw new RanOutsideFieldException();
    }
    if (this.territory.getPlayingField()[newRow][newColumn].getState() == 1) {
      throw new RanAgainstWallException();
    }
    this.row = newRow;
    this.column = newColumn;
  }

  public void eatFruit() {
    Tile currentTile = this.territory.getPlayingField()[this.row][this.column];
    if (currentTile.getState() == 2) {
      currentTile.setState(0);
      this.fruitFuel.setFruitFuel((this.getFruitFuel().getFruitFuelAsInt()+3));
    } else {
      throw new NoFruitOnThisTileException();
    }
  }

  public void pullLeaf() {
    Tile frontTile = getTileInfrontOfYou();
    Tile currentTile = territory.getPlayingField()[this.row][this.column];
    Tile backTile = getTileBehindYou();
    if (frontTile.getState() == 3 || frontTile.getState() == 4) {
      if (backTile != null) {
        this.moveBackward();
        currentTile.setState(3);
        if (frontTile.getState() == 3) {
          frontTile.setState(0);
        } else {
          frontTile.setState(0);
          frontTile.setState(2);
        }
      }
    }
  }

  public Tile getTileInfrontOfYou() {
    Tile frontTile;
    switch (this.direction) {
      case 0:
        frontTile = this.territory.getPlayingField()[(this.row - 1)][this.column];
        break;
      case 1:
        frontTile = this.territory.getPlayingField()[this.row][(this.column + 1)];
        break;
      case 2:
        frontTile = this.territory.getPlayingField()[(this.row + 1)][this.column];
        break;
      case 3:
        frontTile = this.territory.getPlayingField()[this.row][(this.column - 1)];
        break;
      default:
        throw new NoLeafInfrontOfYouException();
    }
    return frontTile;
  }

  public Tile getTileBehindYou() {
    Tile backTile;
    switch (this.direction) {
      case 0:
        backTile = this.territory.getPlayingField()[(this.row + 1)][this.column];
        break;
      case 1:
        backTile = this.territory.getPlayingField()[this.row][(this.column - 1)];
        break;
      case 2:
        backTile = this.territory.getPlayingField()[(this.row - 1)][this.column];
        break;
      case 3:
        backTile = this.territory.getPlayingField()[this.row][(this.column + 1)];
        break;
      default:
        throw new RanAgainstWallException();
    }
    return backTile;
  }
}
