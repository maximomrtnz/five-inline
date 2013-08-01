package com.example.fivecircles.gamestates;



import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;


import android.util.Log;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.managers.ResourcesManager;

public class LoadingSavedGame extends GameState{

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		instantiateGameEntity();
		gameScene.drawBackgroundGame();
		gameScene.drawHUD();
		setScore();
		setHighScore();
		gameScene.loadNewLevel();
		//Draw Shape From Store Data
		drawSavedSahpes(gameScene);
		gameScene.setOnAreaTouchListener(gameScene);
		gameScene.setTouchAreaBindingOnActionDownEnabled(true);
		gameScene.setGameState(new WaitingShapeSelection());
	}

	@Override
	public void instantiateGameEntity() {
		// TODO Auto-generated method stub
		//Create a new game instance
		Game savedGame = ResourcesManager.getInstance().getActivity().getGame();
		
		if(savedGame.getBoard() == null){
			//Get board from DataBase
		}
		savedGame.setCurrentScore(savedGame.getCurrentScore());
		savedGame.setHighScore(getHighScore());
					
	}

	@Override
	public void areaTouch(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shapeDrag(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}
	
	private void drawSavedSahpes(final GameScene gameScene){
		Game savedGame = ResourcesManager.getInstance().getActivity().getGame();
		ArrayList<GameRectangle> gameRectangles = savedGame.getBoard();
		for(GameRectangle gameRectangle : gameRectangles){
			GameShape gameShape = gameRectangle.getShape();
			if(gameShape != null){
				//Shape exists Let's draw it!
				final Rectangle rectangle = SearchAlgorithms.getRectangleFromGameRectangle(gameScene, gameRectangle);
				Sprite shape = (Sprite) gameScene.drawShape(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), rectangle.getWidth(), gameShape.getShapeType());
				gameScene.setTag(2);
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
	}
	
}
