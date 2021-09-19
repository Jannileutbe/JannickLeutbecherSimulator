public class Ladybug {

    private Territory territory;
    private int direction;
    private int fruitFuel;
    private int yCoordinate;
    private int xCoordinate;
    private boolean isAirborne;

    public Ladybug(Territory territory) {
        this.territory = territory;
        this.direction = 1;
        this.fruitFuel = 0;
        this.xCoordinate = 0;
        this.yCoordinate = 0;
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
                this.territory.getPlayingField()[yCoordinate][xCoordinate].setLadybugThere(false);
                this.yCoordinate--;
                break;
            case 1:
                this.territory.getPlayingField()[yCoordinate][xCoordinate].setLadybugThere(false);
                this.xCoordinate++;
                break;
            case 2:
                this.territory.getPlayingField()[yCoordinate][xCoordinate].setLadybugThere(false);
                this.yCoordinate++;
                break;
            case 3:
                this.territory.getPlayingField()[yCoordinate][xCoordinate].setLadybugThere(false);
                this.xCoordinate--;
                break;
            default:
                System.err.println("X");
        }
        if (this.yCoordinate < 0 || this.yCoordinate >= territory.getZeilen() ||
            this.xCoordinate < 0 || this.xCoordinate >= territory.getSpalten() ) {
            throw new RanOutsideFieldException();
        }
        if (this.territory.getPlayingField()[this.yCoordinate][this.xCoordinate].getState() == 1) {
            throw new RanAgainstWallException();
        }
        this.territory.getPlayingField()[yCoordinate][xCoordinate].setLadybugThere(true);

    }

    public void eatFruit(){
        Tile currentTile = this.territory.getPlayingField()[this.yCoordinate][this.xCoordinate];
        if (currentTile.isFruitThere()) {
            currentTile.setState(0);
            this.fruitFuel += 3;
        }

    }

    public void rightTurn(){
        this.direction = (this.direction+1)%4;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getFruitFuel(){
        return fruitFuel;
    }


}
