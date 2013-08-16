package com.example.fivecircles.gamestates;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.math.MathUtils;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.activities.GameActivity;
import com.example.fivecircles.gamescenes.GameOverScene;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.managers.AudioManager;
import com.example.managers.ResourcesManager;

public abstract class GameState {
	
	protected String PREFS_NAME = "SHARE_PREFERENCES_HIGH_SCORES";
	
	
	/*
	 * Common Methods
	 */

	protected boolean checkBundleRow(int row){
		return row<=8 && 1<=row;
	}
	
	protected boolean checkBundleColumn(int column){
		return column<=8 && 1<=column;
	}
	
	
	public int getHighScore() {
		GameActivity gameActivity = ResourcesManager.getInstance()
				.getActivity();
		SharedPreferences settings = gameActivity.getSharedPreferences(PREFS_NAME, 0);
		int highScore = settings.getInt("highScore", 0);
		return highScore;
	}
	
	
	public void saveHighScore(int highScore) {
		GameActivity gameActivity = ResourcesManager.getInstance().getActivity();
		SharedPreferences settings = gameActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("highScore", highScore);
		editor.commit();
	}
	
	public void setScore(){
		HUD hud = ResourcesManager.getInstance().getCamera().getHUD();
		Game game = ResourcesManager.getInstance().getActivity().getGame();
		Text score = (Text)hud.getChildByTag(1);
		score.setText(Integer.toString(game.getCurrentScore()));
	}
	
	public void setHighScore(){
		HUD hud = ResourcesManager.getInstance().getCamera().getHUD();
		Game game = ResourcesManager.getInstance().getActivity().getGame();
		Text score = (Text)hud.getChildByTag(2);
		score.setText(Integer.toString(game.getHighScore()));
	}
	
	public void checkGameOver(GameScene gameScene){
		GameRectangle gameRectangle = SearchAlgorithms.getEmptyGameRectangle(gameScene);
		Game game = ResourcesManager.getInstance().getActivity().getGame();
		//If there isn't an empty game rectangle then We loose
		if(gameRectangle == null){
			//Game Over actions
			gameScene.setChildScene(new GameOverScene(), false, true, true);
			
			//Is there a highest score
			if(game.getCurrentScore() > game.getHighScore()){
				//New Highest Score!
				saveHighScore(game.getCurrentScore());
			}
			
		}
	}
	
	/*
	 * Abstract Methods
	 */
	
	public abstract void loadGame(GameScene gameScene);
	
	public abstract void instantiateGameEntity() throws Exception;
	
	public abstract void areaTouch(GameScene gameScene, ITouchArea iTouchArea);
	
	public abstract void shapeDrag(GameScene gameScene, ITouchArea iTouchArea);
	
	
	
}
