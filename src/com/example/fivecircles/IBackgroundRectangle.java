package com.example.fivecircles;

public interface IBackgroundRectangle{
	
	public void addNeighbor(IBackgroundRectangle rectangle);
	
	public void removeNeighbor(IBackgroundRectangle rectangle);
	
	public void checkPath();
	
	public void disable();
	
	public void addIPlayer(IPlayer iPlayer);
	
	public void removeIPlayer();
	
	public int getId();
	
	public void setId(int id);
	
	public boolean isTaken();
	
	public void initToCheckPath();
	
	public boolean isChecked();
	
	public void drawCross();
	
}
