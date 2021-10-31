package simulator.model;

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

  public Territory(int rows, int columns, Ladybug ladybug) {
    this.playingField = new Tile[rows][columns];
    this.rows = rows;
    this.columns = columns;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        playingField[i][j] = new Tile();
      }
    }
    this.ladybug = ladybug;
    ladybug.setTerritory(this);
  }

  public Territory(Ladybug ladybug) {
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

    this.ladybug = ladybug;
    ladybug.setTerritory(this);
  }

  public void resizeTerritory(Territory this, int newRows, int newColumns) {
    Tile[][] newPlayingfield = new Tile[newRows][newColumns];
    for (int i = 0; i < newRows; i++) {
      for (int j = 0; j < newColumns; j++) {
        if (i < this.getRows() && j < this.getColumns()) {
          newPlayingfield[i][j] = playingField[i][j];
        } else {
          newPlayingfield[i][j] = new Tile();
        }
      }
    }
    if (newRows <= ladybug.getRow() || newColumns <= ladybug.getColumn()) {
      ladybug.setCoordinates(0, 0);
      ladybug.setDirection(1);
    }
    this.playingField = newPlayingfield;
    this.rows = newRows;
    this.columns = newColumns;
  }

  public Ladybug getLadybug() {
    return ladybug;
  }

  public void setLadybug(Ladybug ladybug) {
    this.ladybug = ladybug;
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
