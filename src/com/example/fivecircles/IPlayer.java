package com.example.fivecircles;

public interface IPlayer {
	
	public void addPlayer(IPlayer player);

	public void removePlayer(IPlayer player);
	
	public boolean selectedPlayer();
	
	public void deselectPlayers();
	
	public void paint();
	
	public void addIBackgroundRectabgle(IBackgroundRectangle rectangle);
	
	public void removeIBackgroundRectabgle();
	
	public int getPlayerNumber();

}
