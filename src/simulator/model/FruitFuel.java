package simulator.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


//https://stackoverflow.com/questions/26313756/implementing-an-observablevalue
public class FruitFuel {

  private final IntegerProperty fruitFuel = new SimpleIntegerProperty();

  public FruitFuel(int fruitFuel) {
    this.fruitFuel.set(fruitFuel);
  }

  public IntegerProperty valueProperty(){
    return this.fruitFuel;
  }

  public int getFruitFuelAsInt() {
    return this.fruitFuel.get();
  }

  public void setFruitFuel(int newFruitFuel) {
    this.fruitFuel.set(newFruitFuel);
  }
}
