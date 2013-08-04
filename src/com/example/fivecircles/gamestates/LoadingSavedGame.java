package com.example.fivecircles.gamestates;



import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;


import android.util.Log;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.R;
import com.example.fivecircles.activities.GameActivity;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.fivecircles.utilities.MD5Manager;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.managers.NotificationManager;
import com.example.managers.ResourcesManager;
import com.example.managers.SceneManager;
import com.example.storage.DataBaseMapper;

public class LoadingSavedGame extends GameState{

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		try{
			
		
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
		
		}catch(Exception exception){
			//Unable to load game for any reason
			NotificationManager.getInstance().showToastNotification(R.string.error_unable_to_load_game, ResourcesManager.getInstance().getActivity());
			//Return to Menu Scene
			SceneManager.getInstance().loadMenuScene(gameScene.getEngine());
		}
	}

	@Override
	public void instantiateGameEntity() throws Exception{
		// TODO Auto-generated method stub
		//Create a new game instance
		Game savedGame = ResourcesManager.getInstance().getActivity().getGame();
		GameActivity gameActivity = ResourcesManager.getInstance().getActivity();
		DataBaseMapper dataBaseMapper = DataBaseMapper.getInstance();
		if(savedGame == null){
			savedGame = dataBaseMapper.getSavedGame(gameActivity);
			gameActivity.setGame(savedGame);
			if(!MD5Manager.getInstance().checkGameBoardMD5Hash(savedGame))
				throw new Exception("Corrupted Data");
		}
		if(savedGame.getBoard().isEmpty()){
			//Get board from DataBase
			ArrayList<GameRectangle>gameRectangles = dataBaseMapper.getGameRectangles(gameActivity, savedGame);
			savedGame.setBoard(gameRectangles);
			if(!MD5Manager.getInstance().checkGameBoardMD5Hash(savedGame))
				throw new Exception("Corrupted Data");
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
