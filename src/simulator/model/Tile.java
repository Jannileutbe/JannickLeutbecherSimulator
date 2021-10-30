package simulator.model;

public class Tile {

  private int state;

  public Tile() {
    this.state = 0;
  }

  public void printTile() {
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
      case 4: // Field with Fruit under a Leaf
        System.out.print("S");
        break;
      default:
        System.err.print("X");
    }
  }

  public int getState() {
    return state;
  }

  public void setState(int newState) {
    if ((this.state == 2 && newState == 3) || (this.state == 3 && newState == 2)) {
      newState = 4;
    }
    this.state = newState;
  }
}
