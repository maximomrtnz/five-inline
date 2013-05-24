package com.example.fivecircles;

import java.util.ArrayList;

public class PlayerContainer implements IPlayer{
	
	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();
	
	@Override
	public void addPlayer(IPlayer player) {
		// TODO Auto-generated method stub
		this.players.add(player);
	}

	@Override
	public void removePlayer(IPlayer player) {
		// TODO Auto-generated method stub
		this.players.remove(player);
	}

	@Override
	public boolean selectedPlayer() {
		// TODO Auto-generated method stub
		int i = 0;
		while(i<this.players.size() && !this.players.get(i).selectedPlayer()){
			i++;
		}
		return i<this.players.size() && this.players.get(i).selectedPlayer();
	}

	@Override
	public void deselectPlayers() {
		// TODO Auto-generated method stub
		for(IPlayer player : this.players){
			player.deselectPlayers();
		}
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub
		int i = 0;
		while(i<this.players.size() && !this.players.get(i).selectedPlayer()){
			i++;
		}
		if(i<this.players.size() && this.players.get(i).selectedPlayer()){
			this.players.get(i).paint();
		}
	}

	@Override
	public void addIBackgroundRectabgle(IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		//Container class doesn't implement it
	}

	@Override
	public void removeIBackgroundRectabgle() {
		// TODO Auto-generated method stub
		//Container class doesn't implement it
	}

	@Override
	public int getPlayerNumber() {
		// TODO Auto-generated method stub
		int count = 0;
		for(IPlayer iPlayer : this.players){
			count += iPlayer.getPlayerNumber();
		}
		return count;
	}
	
	

}
