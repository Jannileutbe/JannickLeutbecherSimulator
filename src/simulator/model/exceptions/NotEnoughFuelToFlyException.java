package simulator.model.exceptions;

public class NotEnoughFuelToFlyException extends RuntimeException{
    public NotEnoughFuelToFlyException(){
        super("You dont have enough Fuel to fly right now!");
    }
}
