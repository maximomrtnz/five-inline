package com.example.fivecircles;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.util.GLState;

import com.example.fivecircles.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{
	
	//-------------------------------------------
	//				VARIABLES
	//-------------------------------------------
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		//This way, we make sure, activity will be fully killed
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createBackground(){
		
		Sprite gameMenu = new Sprite(400, 240, super.getResourcesManager().getMenuBackgroundRegion(), super.getResourcesManager().getVbom())
	    {
	        @Override
	        //We will override this method to enabled dithering
	        protected void preDraw(GLState pGLState, Camera pCamera){
	            super.preDraw(pGLState, pCamera);
	            pGLState.enableDither();
	        }
	    };
					
	    attachChild(gameMenu);
	}
	
	private void createMenuChildScene(){
	    menuChildScene = new MenuScene(super.getCamera());
	    menuChildScene.setPosition(400, 240);
	    
	    final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, super.getResourcesManager().getPlayRegion(), super.getResourcesManager().getVbom()), 1.2f, 1);
	    final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, super.getResourcesManager().getOptionsRegion(), super.getResourcesManager().getVbom()), 1.2f, 1);
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    menuChildScene.addMenuItem(optionsMenuItem);
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() + 10);
	    optionsMenuItem.setPosition(optionsMenuItem.getX(), optionsMenuItem.getY() - 110);
	    
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		switch(pMenuItem.getID()){
        case MENU_PLAY:
            return true;
        case MENU_OPTIONS:
            return true;
        default:
            return false;
		}
	}



}
