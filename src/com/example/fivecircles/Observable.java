package com.example.fivecircles;

public interface Observable {
	
	public void notifyObservers(Object object);
	
	public void removeObservers(Observer observer);
	
	public void addObserver(Observer observer);

}
