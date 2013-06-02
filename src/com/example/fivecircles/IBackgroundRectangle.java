package com.example.fivecircles;

import java.util.ArrayList;

public interface IBackgroundRectangle{
	
	public void addNeighbor(IBackgroundRectangle rectangle);
	
	public void removeNeighbor(IBackgroundRectangle rectangle);
	
	public ArrayList<IBackgroundRectangle> getNeighbors();
	
	public void checkPath();
	
	public void disable();
	
	public void addIPlayer(IPlayer iPlayer);
	
	public IPlayer getIPlayer();
	
	public void removeIPlayer();
	
	public int getId();
	
	public void setId(int id);
	
	public boolean isTaken();
	
	public void initToCheckPath();
	
	public boolean isChecked();
	
	public void drawCross();
	
	public void eraseCross();
	
	public void unChecked();
	
	public ArrayList<IBackgroundRectangle> checkColorNeighbors();
}
