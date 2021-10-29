package simulator.model;

public class RanOutsideFieldException extends RuntimeException {
  public RanOutsideFieldException() {
    super("Oh no! You aliven't yourself!");
  }
}
