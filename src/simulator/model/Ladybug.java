package simulator.model;

import simulator.model.exceptions.*;

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

    public void moveForward() throws RanOutsideFieldException, RanAgainstWallException, LandedOnLogException {
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
        if (this.isAirborne) {
            if (this.fruitFuel.getFruitFuelAsInt() > 0 && this.fruitFuel.getFruitFuelAsInt() != 1) {
                this.fruitFuel.setFruitFuel((this.fruitFuel.getFruitFuelAsInt() - 1));
                this.row = newRow;
                this.column = newColumn;
            } else {
                this.changeIsAirborne();
                this.fruitFuel.setFruitFuel((this.fruitFuel.getFruitFuelAsInt() - 1));
                this.row = newRow;
                this.column = newColumn;
                Tile currentTile = this.territory.getPlayingField()[this.row][this.column];
                if (currentTile.getState() == 1) {
                    throw new LandedOnLogException();
                }
            }
            if (newRow < 0 || newRow >= territory.getRows() ||
                    newColumn < 0 || newColumn >= territory.getColumns()) {
                throw new RanOutsideFieldException();
            }
        } else {
            if (newRow < 0 || newRow >= territory.getRows() ||
                    newColumn < 0 || newColumn >= territory.getColumns()) {
                throw new RanOutsideFieldException();
            } else if (this.territory.getPlayingField()[newRow][newColumn].getState() == 1) {
                throw new RanAgainstWallException();
            } else {
                this.row = newRow;
                this.column = newColumn;
            }
        }
    }

    public void rightTurn() {
        this.direction = (this.direction + 1) % 4;
    }

    public void eatFruit() throws NoFruitOnThisTileException, CantDoThisWhileAirborneException {
        if (!this.isAirborne) {
            Tile currentTile = this.territory.getPlayingField()[this.row][this.column];
            if (currentTile.getState() == 2) {
                currentTile.setState(0);
                this.fruitFuel.setFruitFuel((this.getFruitFuel().getFruitFuelAsInt() + 3));
            } else {
                throw new NoFruitOnThisTileException();
            }
        } else {
            throw new CantDoThisWhileAirborneException();
        }
    }

    public void pullLeaf() throws CantDoThisWhileAirborneException {
        if (!this.isAirborne) {
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
        } else {
            throw new CantDoThisWhileAirborneException();
        }
    }

    public void changeIsAirborne() throws LandedOnLogException, NotEnoughFuelToFlyException {
        if (isAirborne) {
            Tile currentTile = this.territory.getPlayingField()[this.row][this.column];
            if (currentTile.getState() == 1) {
                throw new LandedOnLogException();
            } else {
                isAirborne = false;
            }
        } else if (this.fruitFuel.getFruitFuelAsInt() == 0) {
            throw new NotEnoughFuelToFlyException();
        } else {
            isAirborne = true;
        }
    }

    public boolean getIsFrontEmpty() {
        return getTileInfrontOfYou() != null;
    }

    @Invisible
    public int getDirection() {
        return direction;
    }

    @Invisible
    public void setDirection(int newDirection) {
        this.direction = newDirection;
    }

    @Invisible
    public boolean isAirborne() {
        return isAirborne;
    }

    @Invisible
    public FruitFuel getFruitFuel() {
        return fruitFuel;
    }

    @Invisible
    public void setFruitFuel(int newFruitFuel) {
        fruitFuel.setFruitFuel(newFruitFuel);
    }

    @Invisible
    public int getRow() {
        return row;
    }

    @Invisible
    public int getColumn() {
        return column;
    }

    @Invisible
    public void setTerritory(Territory territory) {
        this.territory = territory;

    }

    @Invisible
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


    @Invisible
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

    @Invisible
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
        if (this.isAirborne) {
            if (newRow < 0 || newRow > territory.getRows() ||
                    newColumn < 0 || newColumn > territory.getColumns()) {
                throw new RanOutsideFieldException();
            }
        } else {
            if (newRow < 0 || newRow > territory.getRows() ||
                    newColumn < 0 || newColumn > territory.getColumns()) {
                throw new RanOutsideFieldException();
            }
            if (this.territory.getPlayingField()[newRow][newColumn].getState() == 1) {
                throw new RanAgainstWallException();
            }
        }
        this.row = newRow;
        this.column = newColumn;
    }

    @Invisible
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

    public Territory getTerritory(){
        return this.territory;
    }
}
