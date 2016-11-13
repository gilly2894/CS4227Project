package media;

import media.I_Observer;
public interface I_Subject {
	public void registerObserver(I_Observer o);
	public void removeObserver(I_Observer o);
	public void notifyObservers();
}
