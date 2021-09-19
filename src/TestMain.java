public class TestMain {

    public static void main(String[] args) {
        Territory gameField = new Territory(8, 4);

        gameField.placeFruit(0, 1);
        gameField.printPlayingField();


        System.out.println();
        gameField.getLadybug().moveForward();
        gameField.getLadybug().eatFruit();
        gameField.getLadybug().moveForward();

        gameField.printPlayingField();
        System.out.println(gameField.getLadybug().getFruitFuel());


    }
}
