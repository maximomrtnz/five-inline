package com.example.fivecircles.gamestates;

import com.example.fivecircles.GameScene;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;

public class SelectPlayer implements GameState{

	@Override
	public void selectPlayer(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		
		//Paint the player
		//Mark the path where player can not move
		
		player.paint();
		
		//Store selected Player
		
		gameScene.setSelectedPlayer(player);
		
		//Pass to the other game state
		gameScene.setGameState(new MovePlayer());

	
	}

	@Override
	public void movePlayer(GameScene gameScene, IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
	}



}
