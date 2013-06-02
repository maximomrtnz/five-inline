package com.example.fivecircles.gamestates;

import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;

public class CancelMovePlayer extends GameState{

	@Override
	public void playerTouched(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		player.unPaint();
		unCheckBackgroundRectangles(gameScene);
		unPaintForbidden(gameScene);
		gameScene.setGameState(new SelectPlayer());
	}

	@Override
	public void backgroundTouched(GameScene gameScene,
			IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		
	}

	

}
