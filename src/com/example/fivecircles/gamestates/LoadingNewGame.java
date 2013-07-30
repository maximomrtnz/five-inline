package com.example.fivecircles.gamestates;

import java.util.ArrayList;

import org.andengine.entity.scene.ITouchArea;

import android.util.Log;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.ResourcesManager;

public class LoadingNewGame extends GameState{

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		
		instantiateGameEntity();
		gameScene.drawBackgroundGame();
		gameScene.drawHUD();
		setScore();
		setHighScore();
		gameScene.loadNewLevel();
		addNewShapes(gameScene, 3);
		gameScene.setOnAreaTouchListener(gameScene);
		gameScene.setTouchAreaBindingOnActionDownEnabled(true);
		gameScene.setGameState(new WaitingShapeSelection());
		
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
		
		//Create a new game instance
		Game newGame = new Game(); 
		
		//Put Game Rectangles
		ArrayList<GameRectangle> board = new ArrayList<GameRectangle>();
		for(int i = 1 ; i < 9 ; i++){
			for(int j = 1 ; j < 9 ; j++){
				Log.d("Row", ""+i);
				Log.d("Column", ""+j);
				board.add(new GameRectangle(i, j));
			}
		}
		
		newGame.setBoard(board);
		newGame.setCurrentScore(0);
		newGame.setHighScore(getHighScore());
		
		ResourcesManager.getInstance().getActivity().setGame(newGame);
				
	}
	

}
