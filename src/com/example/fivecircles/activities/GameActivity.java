package com.example.fivecircles.activities;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import com.example.entities.Game;
import com.example.fivecircles.R;
import com.example.fivecircles.utilities.MD5Manager;
import com.example.managers.NotificationManager;
import com.example.managers.ResourcesManager;
import com.example.managers.SceneManager;
import com.example.storage.DataBaseMapper;


import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class GameActivity extends BaseGameActivity {
	
	

	private Camera camera;
	private int CAMERA_WIDTH = 480;
	private int CAMERA_HEIGHT = 800;
	private Game game;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		//Get Display Device Information 
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_SENSOR,  new FillResolutionPolicy(), camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions){
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback) {
		// TODO Auto-generated method stub
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
	    pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		// TODO Auto-generated method stub
		//First We will start the splash scene
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene,
		OnPopulateSceneCallback pOnPopulateSceneCallback) {
		// TODO Auto-generated method stub
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback(){
		        @Override
				public void onTimePassed(final TimerHandler pTimerHandler){
		            mEngine.unregisterUpdateHandler(pTimerHandler);
		            SceneManager.getInstance().createMenuScene();
		        }
		  }));
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}
	
	@Override
	protected void onDestroy(){
	    super.onDestroy();
	    System.exit(0);    
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try{
			//Check if we have a game
			if(this.game != null){
				//Save Current Game
				DataBaseMapper dataBaseMapper = DataBaseMapper.getInstance();
				this.game.setGameId(1);
				//Delete old game
				Game oldGame = dataBaseMapper.getSavedGame(this);
				if(oldGame !=null){
					dataBaseMapper.deleteGameRectangles(this, oldGame);
					dataBaseMapper.deleteGame(this, oldGame);	
				}
				//Save Current Game
				dataBaseMapper.addGame(this, this.game);
				dataBaseMapper.addGameRectangles(this, this.game);
			}
		}catch(Exception e){
			Log.d("Error Saving Game Data", e.getMessage());
		}
		
		
	}
	
	@Override
	protected synchronized void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//If We don't have any game stored
		try {	
			if(this.game == null){
				//Get Stored Game
				DataBaseMapper dataBaseMapper = DataBaseMapper.getInstance();
				Game savedGame = dataBaseMapper.getSavedGame(this);
				if(savedGame != null){
				  if(!MD5Manager.getInstance().checkGameMD5Hash(savedGame))
				    	throw new Exception("Corrupted Data");
					this.game = savedGame;		
				}
			  
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			//Unable to load game for any reason
			NotificationManager.getInstance().showToastNotificationFromActivity(R.string.error_unable_to_load_game, getApplicationContext());
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){  
	    if (keyCode == KeyEvent.KEYCODE_BACK){
	        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
	    }
	    return false; 
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	
}
