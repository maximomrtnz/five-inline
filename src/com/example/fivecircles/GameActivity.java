package com.example.fivecircles;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;


import android.graphics.Point;
import android.view.Display;

public class GameActivity extends BaseGameActivity {
	
	

	private Camera camera;
	private ResourcesManager resourcesManager;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		
		//Get Display Device Information 
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
		Camera camera = new Camera(0, 0, 800, 480);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						800, 480), camera);
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
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO Auto-generated method stub
		
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
	    resourcesManager = ResourcesManager.getInstance();
	    pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		// TODO Auto-generated method stub
		//First We will start the splash scene
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
		//mEngine.registerUpdateHandler(new TimerHandler(2f,new GameTimeCallback(mEngine)));
		 mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
		    {
		        public void onTimePassed(final TimerHandler pTimerHandler) 
		        {
		            mEngine.unregisterUpdateHandler(pTimerHandler);
		            SceneManager.getInstance().createMenuScene();
		        }
		    }));
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}

}
