package com.example.fivecircles.gamestates;

import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;

public class SelectPlayer extends GameState{

	@Override
	public void playerTouched(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		//Paint the player
		//Mark the path where player can not move
		
		player.paint();
		
		gameScene.sortChildren();
		
		paintForbidden(gameScene);
		
		//Store selected Player
		
		gameScene.setSelectedPlayer(player);
		
		//Pass to the other game state
		gameScene.setGameState(new MovePlayer());
	}

	@Override
	public void backgroundTouched(GameScene gameScene,
			IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		
	}

	



}
