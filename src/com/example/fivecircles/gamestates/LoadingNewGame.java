package com.example.fivecircles.gamestates;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.util.math.MathUtils;

import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;

public class LoadingNewGame extends GameState{

	@Override
	public void playerTouched(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backgroundTouched(GameScene gameScene,
			IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		gameScene.drawBackgroundGame();
		gameScene.loadNewLevel();
		initNewGame(gameScene, 3);
		gameScene.setOnAreaTouchListener(gameScene);
		gameScene.setGameState(new WaitingShapeSelection());
	}

	@Override
	public void areaTouch(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void initNewGame(GameScene gameScene, int initShapes){
		for(int i = 0; i < initShapes ; i++){
			GameRectangle gameRectangle= this.getEmptyGameRectangle(gameScene);
			Rectangle rectangle = this.getRectangleFromGameRectangle(gameScene,gameRectangle);
			int type = MathUtils.random(1, 5);
			gameScene.drawShape(rectangle.getX(),rectangle.getY(), rectangle.getWidth(), rectangle.getWidth(), type);
			gameRectangle.setShape(new GameShape(type));
		}
	}

}
