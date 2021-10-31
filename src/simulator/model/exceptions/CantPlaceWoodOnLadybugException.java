package simulator.model.exceptions;

public class CantPlaceWoodOnLadybugException extends RuntimeException{
    public CantPlaceWoodOnLadybugException() {
        super("You can't place a piece of wood on your Ladybug");
    }
}
