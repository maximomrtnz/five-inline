package com.example.fivecircles;

import java.util.ArrayList;

public class PlayerContainer implements Player{
	
	private ArrayList<Player> players = new ArrayList<Player>();
	
	@Override
	public void addPlayer(Player player) {
		// TODO Auto-generated method stub
		this.players.add(player);
	}

	@Override
	public void removePlayer(Player player) {
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
		for(Player player : this.players){
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
	
	

}
