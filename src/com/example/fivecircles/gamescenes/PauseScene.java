package com.example.fivecircles.gamescenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import com.example.fivecircles.utilities.SoundButtonStateOff;
import com.example.fivecircles.utilities.SoundButtonStateOn;
import com.example.fivecircles.utilities.ToggleButtonMenu;
import com.example.fivecircles.utilities.ToggleButtonState;
import com.example.managers.AudioManager;
import com.example.managers.ResourcesManager;
import com.example.managers.SceneManager;
import com.example.managers.SceneManager.SceneType;

public class PauseScene extends BaseScene implements IOnMenuItemClickListener{
	
	private MenuScene menuChildScene;
	private ToggleButtonMenu soundButton;
	private final int MENU_PLAY = 0;
	private final int MENU_RELOAD = 1;
	private final int MENU_BACK = 2;
	
	
	
	@Override
	public void createScene() {
		setBackgroundEnabled(false);
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createMenuChildScene(){
		menuChildScene = new MenuScene(super.getResourcesManager().getCamera());
		menuChildScene.setPosition(0, 0);
		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
		menuChildScene.setBackgroundEnabled(false);
		
		//Add Play Button
		SpriteMenuItem playButton = new SpriteMenuItem(0, ResourcesManager.getInstance().getPlay(), super.getVbom());
		playButton.setPosition(120, super.getCamera().getCenterY()-40);
		menuChildScene.addMenuItem(playButton);
		
		//Add Reload Button
		SpriteMenuItem reloadButton = new SpriteMenuItem(1, ResourcesManager.getInstance().getReload(), super.getVbom());
		reloadButton.setPosition(200, super.getCamera().getCenterY()-40);
		menuChildScene.addMenuItem(reloadButton);
		
		//Add Sound Item
		Sprite soundOn = new Sprite(0, 0, super.getResourcesManager().getSoundOn(), super.getVbom());
		Sprite soundOff = new Sprite(0, 0, super.getResourcesManager().getSoundOff(), super.getVbom());	
		ToggleButtonState toggleButtonState = new SoundButtonStateOn();
		if(!AudioManager.getInstance().isSoundEnable()){
			toggleButtonState = new SoundButtonStateOff();
		}
		soundButton = new ToggleButtonMenu(280, super.getCamera().getCenterY()-40, super.getResourcesManager().getSound(), soundOn,soundOff, super.getVbom(), toggleButtonState);
		if(AudioManager.getInstance().isSoundEnable()){
			soundButton.attachChild(soundOn);
		}else{
			soundButton.attachChild(soundOff);
		}
		soundOn.setPosition(soundButton.getWidth()/2, soundButton.getHeight()/2);
		soundOff.setPosition(soundButton.getWidth()/2, soundButton.getHeight()/2);
		menuChildScene.attachChild(soundButton);
		registerTouchArea(soundButton);
		
		//Add Back Button
		SpriteMenuItem backButton = new SpriteMenuItem(2, ResourcesManager.getInstance().getBack(), super.getVbom());
		backButton.setPosition(360, super.getCamera().getCenterY()-40);
		menuChildScene.addMenuItem(backButton);
		
	}
	
	private void createBackground(){
		
		//Background Rectangle
		final Rectangle rectangle = new Rectangle(super.getCamera().getCenterX(), super.getCamera().getCenterY(), super.getCamera().getWidth(), super.getCamera().getHeight(),super.getVbom());
		rectangle.setColor(0f, 0f, 0f, 0.5f);
		attachChild(rectangle);
		
		//Pause Rectangle
		final Rectangle pauseRectangle = new Rectangle(super.getCamera().getCenterX(), super.getCamera().getCenterY(), 400, 250,super.getVbom());
		pauseRectangle.setColor(1f, 1f, 1f, 1f);
		attachChild(pauseRectangle);
		
		//Pause Text
		Text pauseText = new Text(super.getCamera().getCenterX(), super.getCamera().getCenterY()+80, super.getResourcesManager().getFreckleFaceRegular(),
				"paused", new TextOptions(HorizontalAlign.LEFT),
				super.getVbom());
		
		pauseText.setColor(153f/255f, 56f/255f,159f/255f );
		
		attachChild(pauseText);
		
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()){
    		case MENU_PLAY:
    			ResourcesManager.getInstance().getEngine()
				.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						if(hasChildScene()){
							menuChildScene.unregisterTouchArea(soundButton);
							menuChildScene.back();
						}
						back();
					}
				});
    		return true;
    		
    		case MENU_RELOAD:
    			menuChildScene.back();
    			SceneManager.getInstance().reloadGameScene(super.getEngine());
    		return true;	
    		
    		case MENU_BACK:
    			menuChildScene.back();
    			SceneManager.getInstance().loadMenuScene(super.getEngine());
    		return true;	
    	default:
    		return false;
		}

	}

}
