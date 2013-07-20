package com.example.fivecircles.gamestates;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.math.MathUtils;

import com.example.entities.GameRectangle;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.AudioManager;
import com.example.managers.ResourcesManager;

public abstract class GameState {
	
	public abstract void playerTouched(GameScene gameScene,IPlayer player);
	
	public abstract void backgroundTouched(GameScene gameScene, IBackgroundRectangle rectangle);
	
	protected void unCheckBackgroundRectangles(GameScene gameScene){
		ArrayList<IBackgroundRectangle> rectangles = gameScene.getRectangles();
		for(IBackgroundRectangle rectangle : rectangles){
			rectangle.unChecked();
		}
	}
	
	protected void paintForbidden(GameScene gameScene){
		ArrayList<IBackgroundRectangle> rectangles = gameScene.getRectangles();
		for(IBackgroundRectangle rectangle : rectangles){
			if(!rectangle.isChecked()){
				rectangle.drawCross();
			}
		}
	}
	
	protected void unPaintForbidden(GameScene gameScene){
		ArrayList<IBackgroundRectangle> rectangles = gameScene.getRectangles();
		for(IBackgroundRectangle rectangle : rectangles){
			rectangle.eraseCross();
		}
	}
	
	protected synchronized void checkFive(GameScene gameScene, IPlayer player) {
		IBackgroundRectangle rectangle = player.getIBackgroundRectabgle();
		if(rectangle != null){
			ArrayList<IBackgroundRectangle> rectangles = rectangle.checkColorNeighbors();
			if(rectangles.size() > 4){
				gameScene.addToScore(10*rectangles.size());
				for(IBackgroundRectangle rectangle2 : rectangles){
					IPlayer iPlayer = rectangle2.getIPlayer();
					rectangle2.removeIPlayer();
					gameScene.removePlayer(iPlayer);
				}
				AudioManager.getInstance().soundRemovePlayers();
			}
		}
	}
	
	protected void checkGameOver(GameScene gameScene){
		ArrayList<IBackgroundRectangle> rectangles = gameScene.getRectangles();
		int i = 0;
		while(i<rectangles.size() && rectangles.get(i).isTaken()){
			i++;
		}
		if(i==rectangles.size()){
			gameScene.displayGameOverScene();
			gameScene.saveHighScore();
		}
	}
	
	/*
	 * Common Methods
	 */
	

	
	protected GameRectangle getEmptyGameRectangle(GameScene gameScene){
		GameRectangle returnGameRectangle = null;
		ArrayList<GameRectangle> emptyGameRectangles = new ArrayList<GameRectangle>(); 
		
		//Found empty game rectangles
		for(int i = 0 ;i < gameScene.getChildCount();i++){
		     if(gameScene.getChildByIndex(i).getTag() == 1){
		    	 GameRectangle gameRectangle = (GameRectangle)gameScene.getChildByIndex(i).getUserData();
		    	 if(gameRectangle.getShape()==null){
		    		emptyGameRectangles.add(gameRectangle);
		    	 }
		     }
		}
		
		//Set random empty game rectangles from empty list
		if (emptyGameRectangles.size() > 0) {
			int next = MathUtils.random(0, emptyGameRectangles.size() - 1);
			returnGameRectangle = emptyGameRectangles.get(next);
		}
		
		return returnGameRectangle;
	}
	
	protected Rectangle getRectangleFromGameRectangle(GameScene gameScene,GameRectangle gameRectangle){
		Rectangle rectangle = null;
		int i = 0;
		boolean foundIt = false;
		while(i < gameScene.getChildCount() && !foundIt){
			if(gameScene.getChildByIndex(i).getTag() == 1){
		    	 foundIt = gameScene.getChildByIndex(i).getUserData().equals(gameRectangle);
		    }
			if(!foundIt)
				i++;
		}
		if(i < gameScene.getChildCount() && foundIt){
			rectangle = (Rectangle) gameScene.getChildByIndex(i);
		}
		return rectangle;
	}
	
	/*
	 * Abstract Methods
	 */
	
	public abstract void loadGame(GameScene gameScene);
	
	public abstract void areaTouch(GameScene gameScene, ITouchArea iTouchArea);
	
	
	
	
}
