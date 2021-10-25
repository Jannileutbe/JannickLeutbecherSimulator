import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CurrentEvent {

  private PossibleEvents currentEvent;

  public PossibleEvents getCurrentEvent(){
    return this.currentEvent;
  }

  public void setCurrentEvent(PossibleEvents possibleEvent){
    this.currentEvent = possibleEvent;
  }


}
