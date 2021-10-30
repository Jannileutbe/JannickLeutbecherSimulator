package simulator.model.exceptions;

public class NoFruitOnThisTileException extends RuntimeException{

  public NoFruitOnThisTileException(){
    super("There is no Fruit on tile!");
  }

}
