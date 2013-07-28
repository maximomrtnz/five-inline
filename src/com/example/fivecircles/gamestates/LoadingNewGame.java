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
		addNewShapes(gameScene, 20);
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

}
