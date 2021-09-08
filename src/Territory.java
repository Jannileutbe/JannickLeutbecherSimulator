public class Territory {


    private int[][] playingField;
    private Ladybug ladybug;
    private int coloumns;
    private int lines;

    public Territory(int columns, int lines){
        this.playingField = new int[columns][lines];
        this.coloumns = columns;
        this.lines = lines;
        ladybug = new Ladybug(columns, lines);
    }

    public Territory(){
        this.playingField = new int[5][5];
        this.coloumns = 5;
        this.lines = 5;
        ladybug = new Ladybug(5, 5);
    }

    public void printPlayingField() {
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length; j++) {
                if (i == ladybug.getxCoordinate() && j == ladybug.getyCoordinate()) {
                    ladybug.printLadybug();
                } else {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
    }




    public Ladybug getLadybug() {
        return ladybug;
    }

    public int getColoumns() {
        return coloumns;
    }

    public int getLines() {
        return lines;
    }
}
