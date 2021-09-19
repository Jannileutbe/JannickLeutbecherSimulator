public class Territory {


    private Tile[][] playingField;
    private Ladybug ladybug;
    private int spalten;
    private int zeilen;

    public Territory(int zeilen, int spalten){
        this.playingField = new Tile[zeilen][spalten];
        this.zeilen = zeilen;
        this.spalten = spalten;
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                if (i + j == 0) {
                    playingField[i][j] = new Tile(true);
                } else {
                    playingField[i][j] = new Tile();
                }
            }
        }
        this.ladybug = new Ladybug(this);
    }

    public Territory(){
        int zeilen = 5;
        int spalten = 5;
        this.zeilen = zeilen;
        this.zeilen = spalten;
        this.playingField = new Tile[zeilen][spalten];
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                if (i + j == 0) {
                    playingField[i][j] = new Tile(true);
                } else {
                    playingField[i][j] = new Tile();
                }
            }
        }
        this.ladybug = new Ladybug(this);
    }

    public void printPlayingField() {
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                if (i == ladybug.getyCoordinate() && j == ladybug.getxCoordinate()) {
                    ladybug.printLadybug();
                } else {
                    playingField[i][j].printTile();
                }
            }
            System.out.println();
        }
    }

    public void resizeTerritory(int zeilen, int spalten) {
        Territory oldTerritory = this;
    }

    public void placeWood(int zeile, int spalte){
        playingField[zeile][spalte].setState(1);
    }

    public void placeFruit(int zeile, int spalte){
        playingField[zeile][spalte].setState(2);
    }

    public Ladybug getLadybug() {
        return ladybug;
    }

    public int getZeilen() {
        return zeilen;
    }

    public int getSpalten() {
        return spalten;
    }

    public Tile[][] getPlayingField() {
        return playingField;
    }
}
