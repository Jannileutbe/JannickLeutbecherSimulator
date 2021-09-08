public class TestMain {

    public static void main(String[] args) {
        Territory gameField = new Territory();

        gameField.printPlayingField();
        gameField.getLadybug().rightTurn();
        System.out.println();

        gameField.printPlayingField();
        gameField.getLadybug().moveForward();
        System.out.println();

        gameField.printPlayingField();
        gameField.getLadybug().moveForward();
        gameField.getLadybug().moveForward();
        gameField.getLadybug().moveForward();
        System.out.println();

        gameField.printPlayingField();
        gameField.getLadybug().rightTurn();
        System.out.println();

        gameField.printPlayingField();
        gameField.getLadybug().rightTurn();
        gameField.printPlayingField();
        gameField.getLadybug().rightTurn();
        gameField.printPlayingField();
    }
}
