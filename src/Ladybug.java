public class Ladybug {

    private Territory territory;
    private int direction;
    private int fruitFuel;
    private int row;
    private int column;
    private boolean isAirborne;

    public void setCoordinates(int newRow, int newColumn){
        if ((newRow < 0 || newRow > territory.getRows()) && newColumn < 0 || (newColumn > territory.getColumns())) {
            throw new RanOutsideFieldException();
        }
        if ((this.territory.getPlayingField()[newRow][newColumn].getState() == 1) ||
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
        this.fruitFuel = 0;
        this.column = 0;
        this.row = 0;
        this.isAirborne = false;
    }

    public void printLadybug(){
        switch (direction) {
            case 0:
                System.out.print("^");
                break;
            case 1:
                System.out.print(">");
                break;
            case 2:
                System.out.print("v");
                break;
            case 3:
                System.out.print("<");
                break;
            default:
                System.err.print("X");
        }
    }

    public void moveForward() throws RanOutsideFieldException, RanAgainstWallException{
        switch(direction) {
            case 0:
                this.territory.getPlayingField()[row][column].setLadybugThere(false);
                this.row--;
                break;
            case 1:
                this.territory.getPlayingField()[row][column].setLadybugThere(false);
                this.column++;
                break;
            case 2:
                this.territory.getPlayingField()[row][column].setLadybugThere(false);
                this.row++;
                break;
            case 3:
                this.territory.getPlayingField()[row][column].setLadybugThere(false);
                this.column--;
                break;
            default:
                System.err.println("X");
        }
        if (this.row < 0 || this.row > territory.getRows() ||
            this.column < 0 || this.column > territory.getColumns() ) {
            throw new RanOutsideFieldException();
        }
        if (this.territory.getPlayingField()[this.row][this.column].getState() == 1) {
            throw new RanAgainstWallException();
        }
        this.territory.getPlayingField()[row][column].setLadybugThere(true);

    }

    public void eatFruit(){
        Tile currentTile = this.territory.getPlayingField()[this.row][this.column];
        if (currentTile.isFruitThere()) {
            currentTile.setState(0);
            this.fruitFuel += 3;
        }

    }

    public boolean isAirborne(){
        return isAirborne;
    }

    public void rightTurn(){
        this.direction = (this.direction+1)%4;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getFruitFuel(){
        return fruitFuel;
    }

    public int getDirection() {
        return direction;
    }
}
