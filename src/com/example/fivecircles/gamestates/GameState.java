package com.example.fivecircles.gamestates;

import org.andengine.entity.scene.ITouchArea;
import com.example.fivecircles.gamescenes.GameScene;

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
	
	/*
	 * Abstract Methods
	 */
	
	public abstract void loadGame(GameScene gameScene);
	
	public abstract void areaTouch(GameScene gameScene, ITouchArea iTouchArea);
	
	public abstract void shapeDrag(GameScene gameScene, ITouchArea iTouchArea);
	
	
	
}
