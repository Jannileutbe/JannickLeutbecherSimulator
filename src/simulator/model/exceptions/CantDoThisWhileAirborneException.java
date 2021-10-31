package simulator.model.exceptions;

public class CantDoThisWhileAirborneException extends RuntimeException {
    public CantDoThisWhileAirborneException() {
        super("You can't do this action while in the air!");
    }
}
