public class TestMain {

    public static void main(String[] args) {
        Territory gameField = new Territory(8, 4);

        gameField.placeFruit(0, 1);
        gameField.placeWood(1,1);
        gameField.placeWood(3,3);
        gameField.placeWood(6,2);
        gameField.placeWood(7,1);

        gameField.printPlayingField();


        System.out.println();
        gameField.getLadybug().moveForward();
        gameField.getLadybug().eatFruit();
        gameField.getLadybug().moveForward();

        gameField.printPlayingField();

        System.out.println();

        gameField.resizeTerritory(2, 4);

        gameField.printPlayingField();

        System.out.println(gameField.getLadybug().getFruitFuel());

        gameField.getLadybug().setColumn(4);

        gameField.printPlayingField();

    }
}
