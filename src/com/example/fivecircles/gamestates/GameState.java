package com.example.fivecircles.gamestates;

import java.util.ArrayList;

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

public abstract class GameState {
	
	/*
	 * Common Methods
	 */

	protected boolean checkBundleRow(int row){
		return row<=8 && 1<=row;
	}
	
	protected boolean checkBundleColumn(int column){
		return column<=8 && 1<=column;
	}
	
	protected ArrayList<GameRectangle> addNewShapes(final GameScene gameScene, int quantity){
		ArrayList<GameRectangle> gameRectangles = new ArrayList<GameRectangle>();
		for(int i = 0; i < quantity ; i++){
			GameRectangle gameRectangle= SearchAlgorithms.getEmptyGameRectangle(gameScene);
			if(gameRectangle != null){
				
				final Rectangle rectangle = SearchAlgorithms.getRectangleFromGameRectangle(gameScene,gameRectangle);
				int type = MathUtils.random(1, 5);
				Sprite shape = (Sprite) gameScene.drawShape(rectangle.getX(),rectangle.getY(), rectangle.getWidth(), rectangle.getWidth(), type);
				gameRectangle.setShape(new GameShape(type));
				//gameRectangles.add(gameRectangle);
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
		return gameRectangles;
	}
	
	
	/*
	 * Abstract Methods
	 */
	
	public abstract void loadGame(GameScene gameScene);
	
	public abstract void areaTouch(GameScene gameScene, ITouchArea iTouchArea);
	
	public abstract void shapeDrag(GameScene gameScene, ITouchArea iTouchArea);
	
	
	
}
