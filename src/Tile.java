public class Tile {
  private boolean isLadybugThere;
  private int state;

  public Tile() {
    this.isLadybugThere = false;
    this.state = 0;
  }

  public Tile (boolean isLadybugThere){
    this.isLadybugThere = isLadybugThere;
    this.state = 0;
  }

  public void printTile(){
    switch (state) {
      case 0: // Free Field
        System.out.print("O");
        break;
      case 1: // Field with Piece of Wood
        System.out.print("W");
        break;
      case 2: // Field with Fruit
        System.out.print("F");
        break;
      case 3: // Field with Leaf
        System.out.print("L");
        break;
      case 4: // Field with Fruit under piece of Wood
        System.out.print("S");
        break;
      default:
        System.err.print("X");
    }
  }

  public boolean isFruitThere(){
    return state == 2;
  }

  public boolean isLadybugThere() {
    return isLadybugThere;
  }

  public void setLadybugThere(boolean ladybugThere) {
    isLadybugThere = ladybugThere;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }
}
