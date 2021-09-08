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
      case 0:
        System.out.print("O");
        break;
      case 1:
        System.out.print("H");
        break;
      case 2:
        System.out.print("F");
        break;
      default:
        System.err.print("X");
    }
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
