package com.example.fivecircles.utilities;


import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class ToggleButtonMenu extends Sprite{

	private Sprite  firstStateSprite;
	private Sprite  secondStateSprite;
	private ToggleButtonState toggleButtonState;

	public ToggleButtonMenu(float pX,
			float pY,
			ITextureRegion iTextureRegion,
			Sprite firstStateSprite,
			Sprite secondStateSprite,	
			VertexBufferObjectManager pVertexBufferObjectManager, 
			ToggleButtonState toggleButtonState) {
		
		super(pX, pY, iTextureRegion, pVertexBufferObjectManager);
		
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
