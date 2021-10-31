package simulator.model.exceptions;

public class LandedOnLogException extends RuntimeException{
    public LandedOnLogException(){
        super("You landed on a Log!");
    }
}
