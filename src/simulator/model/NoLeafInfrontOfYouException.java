package simulator.model;

public class NoLeafInfrontOfYouException extends RuntimeException{

  public NoLeafInfrontOfYouException(){
    super("There is nothing infront of you to grab!");
  }

}
