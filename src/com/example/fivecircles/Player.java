package com.example.fivecircles;

public interface Player {
	
	public void addPlayer(Player player);

	public void removePlayer(Player player);
	
	public boolean selectedPlayer();
	
	public void deselectPlayers();
	
	public void paint();

}
