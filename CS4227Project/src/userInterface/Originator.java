package userInterface;

public class Originator
{
	   private String state;

	   public void setState(String theState){
	      state = theState;
	   }

	   public String getState(){
	      return state;
	   }

	   public Memento saveStateToMemento(){
	      return new Memento(state);
	   }

	   public String restoreFromMemento(Memento memento){
	      state = memento.getSavedState();
	      return state;
	   }
}