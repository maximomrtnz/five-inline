package com.example.fivecircles.gamescenes;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.engine.camera.Camera;

import com.example.managers.SceneManager.SceneType;

public class SplashScene extends BaseScene{
	
	//------------------------------------------------
	//					VARIABLES
	//------------------------------------------------
	
	private Sprite splash;

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		
		splash = new Sprite(0, 0, super.getResourcesManager().getSplashRegion(), super.getVbom())
		{
			//We will override this method to enabled dithering
		    @Override
		    protected void preDraw(GLState pGLState, Camera pCamera) 
		    {
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		        
		//splash.setScale(1.5f);
		splash.setPosition(240,400);
		attachChild(splash);
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		splash.detachSelf();
	    splash.dispose();
	    this.detachSelf();
	    this.dispose();
	}

}
