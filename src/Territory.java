public class Territory {


  private Tile[][] playingField;
  private Ladybug ladybug;
  private int rows;
  private int columns;


  public Territory(int rows, int columns) {
    this.playingField = new Tile[rows][columns];
    this.rows = rows;
    this.columns = columns;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        playingField[i][j] = new Tile();
      }
    }
    this.ladybug = new Ladybug(this);
  }

  public Territory() {
    int rows = 5;
    int columns = 5;
    this.rows = rows;
    this.columns = columns;
    this.playingField = new Tile[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        playingField[i][j] = new Tile();
      }
    }

    this.playingField[1][1].setState(1);
    this.playingField[3][2].setState(1);
    this.playingField[2][4].setState(1);
    this.playingField[4][4].setState(1);
    this.playingField[2][1].setState(1);

    this.ladybug = new Ladybug(this);
  }

  public void printPlayingField() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (i == ladybug.getRow() && j == ladybug.getColumn()) {
          ladybug.printLadybug();
        } else {
          playingField[i][j].printTile();
        }
      }
      System.out.println();
    }
  }

  public void resizeTerritory(Territory this, int newRows, int newColumns) {
    Tile[][] newPlayingfield = new Tile[newRows][newColumns];
    for (int i = 0; i < newRows; i++) {
      for (int j = 0; j < newColumns; j++) {
        newPlayingfield[i][j] = playingField[i][j];
      }
    }
    this.rows = newRows;
    this.columns = newColumns;
    if (this.rows >= ladybug.getRow() || this.columns >= ladybug.getColumn()) {
      ladybug.setCoordinates(0,0);
    }
    this.playingField = newPlayingfield;
  }

  public void placeWood(int row, int column) {
    playingField[row][column].setState(1);
  }

  public void placeFruit(int row, int column) {
    playingField[row][column].setState(2);
  }

  public Ladybug getLadybug() {
    return ladybug;
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public Tile[][] getPlayingField() {
    return playingField;
  }
}
