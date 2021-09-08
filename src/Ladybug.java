public class Ladybug {

    private int direction;
    private int fruitFuel;
    private int xCoordinate;
    private int yCoordinate;
    private int playingfieldColoumns;
    private int playingfieldLines;

    public Ladybug(int playingfieldColoumns, int playingfieldLines) {
        this.playingfieldColoumns = playingfieldColoumns;
        this.playingfieldLines = playingfieldLines;
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

    public void moveForward() throws RanIntoWallException {
        switch(direction) {
            case 0:
                this.xCoordinate--;
                break;
            case 1:
                this.yCoordinate++;
                break;
            case 2:
                this.xCoordinate++;
                break;
            case 3:
                this.yCoordinate--;
                break;
            default:
                System.err.println("X");
        }
        if (this.xCoordinate < 0 || this.xCoordinate >= playingfieldColoumns || this.yCoordinate < 0 || this.yCoordinate >= playingfieldLines ) {
            throw new RanIntoWallException();
        }
    }

    public void rightTurn(){
        this.direction = (this.direction+1)%4;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }


}
