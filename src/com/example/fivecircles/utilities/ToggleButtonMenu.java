package com.example.fivecircles.utilities;

import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.example.fivecircles.gamescenes.BaseScene;

public class ToggleButtonMenu extends SpriteMenuItem{

	private Sprite  firstStateSprite;
	private Sprite  secondStateSprite;
	private ToggleButtonState toggleButtonState;

	public ToggleButtonMenu(int pID,
			ITextureRegion iTextureRegion,
			Sprite firstStateSprite,
			Sprite secondStateSprite,	
			VertexBufferObjectManager pVertexBufferObjectManager, 
			ToggleButtonState toggleButtonState) {
		
		super(pID, iTextureRegion, pVertexBufferObjectManager);
		
		// TODO Auto-generated constructor stub
	
		this.firstStateSprite = firstStateSprite;
		this.secondStateSprite = secondStateSprite;
		this.toggleButtonState = toggleButtonState;
	}
	
	
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
    		float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	// TODO Auto-generated method stub
    	if(pSceneTouchEvent.isActionUp()){
    		this.toggleButtonState.touch(this);
    	}
    	return false;
    }
	
	public void setButtonState(ToggleButtonState toggleButtonState){
		this.toggleButtonState = toggleButtonState;
	}


	public Sprite getFirstStateSprite() {
		return firstStateSprite;
	}

	public Sprite getSecondStateSprite() {
		return secondStateSprite;
	}
	
	
}
