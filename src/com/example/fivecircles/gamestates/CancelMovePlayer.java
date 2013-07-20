package com.example.fivecircles.gamestates;

import org.andengine.entity.scene.ITouchArea;

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
		gameScene.setGameState(new WaitingShapeSelection());
	}

	@Override
	public void backgroundTouched(GameScene gameScene,
			IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void areaTouch(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}

	

}
