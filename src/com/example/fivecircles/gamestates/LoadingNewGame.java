package com.example.fivecircles.gamestates;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.math.MathUtils;

import android.util.Log;

import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.managers.ResourcesManager;

public class LoadingNewGame extends GameState{

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		gameScene.drawBackgroundGame();
		gameScene.loadNewLevel();
		initNewGame(gameScene, 20);
		gameScene.setOnAreaTouchListener(gameScene);
		gameScene.setTouchAreaBindingOnActionDownEnabled(true);
		gameScene.setGameState(new WaitingShapeSelection());
	}

	@Override
	public void areaTouch(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void initNewGame(final GameScene gameScene, int initShapes){
		for(int i = 0; i < initShapes ; i++){
			
			GameRectangle gameRectangle= SearchAlgorithms.getEmptyGameRectangle(gameScene);
			final Rectangle rectangle = SearchAlgorithms.getRectangleFromGameRectangle(gameScene,gameRectangle);
			int type = MathUtils.random(1, 5);
			Sprite shape = (Sprite) gameScene.drawShape(rectangle.getX(),rectangle.getY(), rectangle.getWidth(), rectangle.getWidth(), type);
			gameRectangle.setShape(new GameShape(type));
			//Storage rectangle because We need it then
			shape.setUserData(rectangle);
			
			ResourcesManager.getInstance().getEngine().runOnUpdateThread(new Runnable(){
                @Override
                public void run(){
                	try{
                        gameScene.unregisterTouchArea(rectangle);
                	}catch(Exception exception){
                		Log.d("Error On Remove Player", exception.getMessage());
                	}
                }});
			
		}
	}

	@Override
	public void shapeDrag(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}

}
