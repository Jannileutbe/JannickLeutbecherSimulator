package simulator.model;

import simulator.model.enums.PossibleEvents;

public class CurrentEvent {

  private PossibleEvents currentEvent;

  public PossibleEvents getCurrentEvent(){
    return this.currentEvent;
  }

  public void setCurrentEvent(PossibleEvents possibleEvent){
    this.currentEvent = possibleEvent;
  }


}
