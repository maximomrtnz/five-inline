package com.example.fivecircles.gamestates;

import java.util.ArrayList;

import org.andengine.entity.scene.ITouchArea;

import android.util.Log;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.fivecircles.R;
import com.example.fivecircles.activities.GameActivity;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.ShapeABM;
import com.example.managers.NotificationManager;
import com.example.managers.ResourcesManager;
import com.example.storage.DataBaseMapper;

public class LoadingNewGame extends GameState{

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		try{
			instantiateGameEntity();
			gameScene.drawBackgroundGame();
			gameScene.drawHUD();
			gameScene.drawScore();
			gameScene.drawHighScore();
			gameScene.loadNewLevel();
			ShapeABM.getInstance().add(gameScene, 3);
			gameScene.setOnAreaTouchListener(gameScene);
			gameScene.setTouchAreaBindingOnActionDownEnabled(true);
			gameScene.setGameState(new WaitingShapeSelection());
		}catch(Exception e){
			//Unable to load game for any reason
			NotificationManager.getInstance().showToastNotification(R.string.error_unable_to_load_game, ResourcesManager.getInstance().getActivity());
			//Return to Menu Scene
			gameScene.onBackKeyPressed();
		}	
	}

	@Override
	public void areaTouch(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void shapeDrag(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void instantiateGameEntity() {
		// TODO Auto-generated method stub
		
		//Get Old Saved Game
		GameActivity gameActivity = ResourcesManager.getInstance().getActivity();
		DataBaseMapper dataBaseMapper = DataBaseMapper.getInstance();
		Game oldGame = dataBaseMapper.getSavedGame(gameActivity);
		
		//Delete old Game and its Game Rectangles
		if(oldGame != null){
			dataBaseMapper.deleteGameRectangles(gameActivity, oldGame);
			dataBaseMapper.deleteGame(gameActivity, oldGame);
		}
		
		//Create a new game instance
		Game newGame = new Game(); 
		
		//Put Game Rectangles
		ArrayList<GameRectangle> board = new ArrayList<GameRectangle>();
		for(int i = 1 ; i < 9 ; i++){
			for(int j = 1 ; j < 9 ; j++){
				board.add(new GameRectangle(i, j));
			}
		}
		
		newGame.setBoard(board);
		newGame.setCurrentScore(0);
		newGame.setHighScore(getHighScore());
		
		ResourcesManager.getInstance().getActivity().setGame(newGame);
				
	}
	

}
