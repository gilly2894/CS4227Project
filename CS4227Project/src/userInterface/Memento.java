package userInterface;

public class Memento
{
	   private String state;

	   public Memento(String stateToSave){
	      this.state = stateToSave;
	   }

	   public String getSavedState(){
	      return state;
	   }	
}
