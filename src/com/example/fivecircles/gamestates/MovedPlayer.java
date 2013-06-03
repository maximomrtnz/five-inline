package com.example.fivecircles.gamestates;

import org.andengine.entity.IEntity;

import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;

public class MovedPlayer extends GameState{

	
	@Override
	public void playerTouched(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		player.unPaint();
		unCheckBackgroundRectangles(gameScene);
		unPaintForbidden(gameScene);
		checkFive(gameScene,player);
		addNewPlayers(gameScene, 3);
		checkGameOver(gameScene);
		gameScene.setGameState(new SelectPlayer());
	}

	@Override
	public void backgroundTouched(GameScene gameScene,
			IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		
	}

	private void addNewPlayers(GameScene gameScene,int numberOfPlayer){
		int i = 0;
		while(i<numberOfPlayer){
			IEntity player = gameScene.addPlayer(gameScene, 0, 0, 0, 0);
			gameScene.attachChild(player);
			gameScene.setPlayerToBackgroundRectangle((IPlayer)player);
			checkFive(gameScene, (IPlayer)player);
			i++;
		}
	}

	
}
