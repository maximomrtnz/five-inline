package com.example.fivecircles;

public interface IPlayer {
	
	public void addPlayer(IPlayer player);

	public void removePlayer(IPlayer player);
	
	public void paint();
	
	public void unPaint();
	
	public void addIBackgroundRectabgle(IBackgroundRectangle rectangle);
	
	public void removeIBackgroundRectabgle();
	
	public IBackgroundRectangle getIBackgroundRectabgle();
	
	public int getPlayerNumber();

}
