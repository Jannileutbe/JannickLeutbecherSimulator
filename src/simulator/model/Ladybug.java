package simulator.model;

import simulator.model.exceptions.NoFruitOnThisTileException;
import simulator.model.exceptions.NoLeafInfrontOfYouException;
import simulator.model.exceptions.RanAgainstWallException;
import simulator.model.exceptions.RanOutsideFieldException;

public class Ladybug {

    private Territory territory;
    private int direction;
    private int row;
    private int column;
    private boolean isAirborne;
    private FruitFuel fruitFuel;

    public Ladybug(Territory territory) {
        this.territory = territory;
        this.direction = 1;
        this.fruitFuel = new FruitFuel(0);
        this.column = 0;
        this.row = 0;
        this.isAirborne = false;
    }

    public Ladybug() {
        this.direction = 1;
        this.fruitFuel = new FruitFuel(0);
        this.column = 0;
        this.row = 0;
        this.isAirborne = false;
    }

    public void main() {
    }

    public int getDirection() {
        return direction;
    }

    public boolean isAirborne() {
        return isAirborne;
    }

    public FruitFuel getFruitFuel() {
        return fruitFuel;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;

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

    public void rightTurn() {
        this.direction = (this.direction + 1) % 4;
    }

    public void eatFruit() {
        Tile currentTile = this.territory.getPlayingField()[this.row][this.column];
        if (currentTile.getState() == 2) {
            currentTile.setState(0);
            this.fruitFuel.setFruitFuel((this.getFruitFuel().getFruitFuelAsInt() + 3));
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

    public boolean getIsFrontEmpty() {
        return getTileInfrontOfYou() != null;
    }

    private Tile getTileInfrontOfYou() {
        Tile frontTile;
        try {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return frontTile;
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

    private void moveBackward() {
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

    private Tile getTileBehindYou() {
        Tile backTile;
        try {
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
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        return backTile;
    }
}
