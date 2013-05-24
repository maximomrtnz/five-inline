package com.example.fivecircles;

public interface IBackgroundRectangle{
	
	public void addNeighbor(IBackgroundRectangle rectangle);
	
	public void removeNeighbor(IBackgroundRectangle rectangle);
	
	public void checkPath();
	
	public void disable();
	
	public void addIPlayer(IPlayer iPlayer);
	
	public void removeIPlayer(IPlayer iPlayer);
	
	public int getId();
	
	public void setId(int id);
	
	public boolean isTaken();
	
}
