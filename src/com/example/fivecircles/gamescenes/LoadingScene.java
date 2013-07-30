package com.example.fivecircles.gamescenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import com.example.managers.SceneManager.SceneType;

public class LoadingScene extends BaseScene{
	
	private Sprite loadingBackground;
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		loadingBackground = new Sprite(0, 0, super.getResourcesManager().getLoadingBackground(), super.getVbom())
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
		loadingBackground.setPosition(240,400);
		attachChild(loadingBackground);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		loadingBackground.detachSelf();
		loadingBackground.dispose();
	    this.detachSelf();
	    this.dispose();
	}

	@Override
	public void updateScene() {
		// TODO Auto-generated method stub
		
	}

}
