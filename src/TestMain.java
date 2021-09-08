public class TestMain {

    public static void main(String[] args) {
        Territory gameField = new Territory(8, 4);

        gameField.printPlayingField();
        gameField.placeWood(4, 2);
        gameField.placeWood(4, 3);
        gameField.placeWood(5, 2);
        gameField.placeWood(6, 2);
        gameField.placeFruit(5,3);

        System.out.println();
        gameField.printPlayingField();


    }
}
