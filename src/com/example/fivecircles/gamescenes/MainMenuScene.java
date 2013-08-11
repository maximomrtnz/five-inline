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

import com.example.entities.Game;
import com.example.fivecircles.R;
import com.example.fivecircles.activities.GameActivity;
import com.example.fivecircles.activities.HowToPlayActivity;
import com.example.fivecircles.gamestates.LoadingNewGame;
import com.example.fivecircles.gamestates.LoadingSavedGame;
import com.example.fivecircles.utilities.MD5Manager;
import com.example.managers.AudioManager;
import com.example.managers.NotificationManager;
import com.example.managers.ResourcesManager;
import com.example.managers.SceneManager;
import com.example.managers.SceneManager.SceneType;
import com.example.storage.DataBaseMapper;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{
	
	//-------------------------------------------
	//				VARIABLES
	//-------------------------------------------
	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_HOWTOPLAY = 1;
	private final int MENU_CONTINUE = 2;
	
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
	  
		
		//Add New Game Menu Item
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, super.getResourcesManager().getPlayMenuButtonRegion(), super.getVbom()), 1.1f, 1);
	    playMenuItem.setTag(1);
	    menuChildScene.addMenuItem(playMenuItem);
	    playMenuItem.setPosition(super.getResourcesManager().getCamera().getCenterX(), super.getResourcesManager().getCamera().getCenterY()+playMenuItem.getHeight());
	    
	    
	    //Add How to Play
	    final IMenuItem howToPlayMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HOWTOPLAY, super.getResourcesManager().getHowToPlayMenuButtonRegion(), super.getVbom()), 1.1f, 1);
	    howToPlayMenuItem.setTag(2);    
	    menuChildScene.addMenuItem(howToPlayMenuItem);
	    howToPlayMenuItem.setPosition(super.getResourcesManager().getCamera().getCenterX(), super.getResourcesManager().getCamera().getCenterY()-howToPlayMenuItem.getHeight());
	    
	    
	    //Add Continue Game (if it's necessary)
	    addContinueMenuItem();
	    
	    menuChildScene.buildAnimations();
	    menuChildScene.setBackgroundEnabled(false);
	    
	    
	    
	    
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
                SceneManager.getInstance().loadGameScene(super.getEngine(),new LoadingNewGame());
        		return true;
        	case MENU_CONTINUE: 
       		 	//Load Game Scene
        		SceneManager.getInstance().loadGameScene(super.getEngine(),new LoadingSavedGame());
       			return true;	
        	case MENU_HOWTOPLAY:
        		//Load How To Play Scene
        		//SceneManager.getInstance().loadHowToPlayScene(super.getEngine());
        		Intent i = new Intent(super.getActivity(), HowToPlayActivity.class);
        		super.getActivity().startActivityForResult(i, 1);
        		return true;
	
        	default:
        		return false;
		}
	}
	
	private void addContinueMenuItem(){
		//Check for saved game	
		 Game game = ResourcesManager.getInstance().getActivity().getGame();
		 IMenuItem continueMenuItem = (IMenuItem)menuChildScene.getChildByTag(3);
		 if(game != null && continueMenuItem == null){
			 IMenuItem playMenuItem= (IMenuItem)menuChildScene.getChildByTag(1);
		   	 IMenuItem howToPlayMenuItem = (IMenuItem)menuChildScene.getChildByTag(2);
		   	 if(playMenuItem != null &&  howToPlayMenuItem != null){
		   		 
		   		 //Change Button position
		   		 playMenuItem.setPosition(super.getResourcesManager().getCamera().getCenterX(), super.getResourcesManager().getCamera().getCenterY()-playMenuItem.getHeight()/2);
		   		 howToPlayMenuItem.setPosition(super.getResourcesManager().getCamera().getCenterX(), super.getResourcesManager().getCamera().getCenterY()-howToPlayMenuItem.getHeight()*2);
		 	    
		   		 
		   		 continueMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_CONTINUE, super.getResourcesManager().getContinueMenuButton(), super.getVbom()), 1.1f, 1);
		    	 continueMenuItem.setTag(3);
		    	 menuChildScene.addMenuItem(continueMenuItem);
		    	 continueMenuItem.setPosition(super.getResourcesManager().getCamera().getCenterX(), super.getResourcesManager().getCamera().getCenterY()+continueMenuItem.getHeight()/2);
		    	
		   	 }
	    	 
	    }
	}
	
	@Override
	public void updateScene() {
		// TODO Auto-generated method stub
		addContinueMenuItem();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		//Pause Music
		AudioManager.getInstance().pauseMusic(AudioManager.MUSIC_MAIN_MENU_SCENE);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		//Resume Music
		AudioManager.getInstance().resumeMusic(AudioManager.MUSIC_MAIN_MENU_SCENE);
	}


	@Override
	public void playMusicScene() {
		// TODO Auto-generated method stub
		AudioManager.getInstance().playMusic(AudioManager.MUSIC_MAIN_MENU_SCENE);
	}

	@Override
	public void stopMusicScene() {
		// TODO Auto-generated method stub
		AudioManager.getInstance().stopMusic(AudioManager.MUSIC_MAIN_MENU_SCENE);
	}



}
