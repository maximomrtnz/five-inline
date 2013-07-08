package com.example.fivecircles.gamescenes;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.util.GLState;

import android.content.Intent;
import android.util.Log;

import com.example.fivecircles.SceneManager;
import com.example.fivecircles.SceneManager.SceneType;
import com.example.fivecircles.activities.HowToPlayActivity;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{
	
	//-------------------------------------------
	//				VARIABLES
	//-------------------------------------------
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	//private final int MENU_SCORES = 1;
	private final int MENU_HOWTOPLAY = 1;
	
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
		
		Sprite gameMenu = new Sprite(240,400, super.getResourcesManager().getMenuBackgroundRegion(), super.getVbom())
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
		menuChildScene = new MenuScene(super.getResourcesManager().getCamera());
		menuChildScene.setPosition(0, 0);
	  
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, super.getResourcesManager().getPlayMenuButtonRegion(), super.getVbom()), 1.1f, 1);
	    //final IMenuItem scoresMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_SCORES, super.getResourcesManager().getScoreMenuButtonRegion(), super.getVbom()), 1.1f, 1);
	    final IMenuItem howToPlayMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HOWTOPLAY, super.getResourcesManager().getHowToPlayMenuButtonRegion(), super.getVbom()), 1.1f, 1);
	    
	  
	    
	    menuChildScene.addMenuItem(playMenuItem);
	    //menuChildScene.addMenuItem(scoresMenuItem);
	    menuChildScene.addMenuItem(howToPlayMenuItem);
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() + 10);
	    //scoresMenuItem.setPosition(scoresMenuItem.getX(), scoresMenuItem.getY()-30);
	    howToPlayMenuItem.setPosition(howToPlayMenuItem.getX(), howToPlayMenuItem.getY()-30);
	    menuChildScene.setOnMenuItemClickListener(this);
	    
	    setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		switch(pMenuItem.getID()){
        	case MENU_PLAY:
        		 //Load Game Scene
                SceneManager.getInstance().loadGameScene(super.getEngine());
        		return true;
        	case MENU_HOWTOPLAY:
        		//Load How To Play Scene
        		//SceneManager.getInstance().loadHowToPlayScene(super.getEngine());
        		Intent i = new Intent(super.getActivity(), HowToPlayActivity.class);
        		super.getActivity().startActivity(i);
        		return true;
	
        	default:
        		return false;
		}
	}



}
