package com.example.fivecircles.utilities;

import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.fivecircles.gamescenes.BaseScene;

public class ToggleButtonMenu extends SpriteMenuItem{

	private ITextureRegion firstStateRegion;
	private ITextureRegion secondStateRegion;
	private ToggleButtonState toggleButtonState;
	private BaseScene baseScene;

	
	public ToggleButtonMenu(int pID, float pWidth, float pHeight,
			ITextureRegion pTextureRegionFirstState,
			ITextureRegion pTextureRegionSecondState,
			VertexBufferObjectManager pVertexBufferObjectManager, 
			ToggleButtonState toggleButtonState,
			BaseScene baseScene) {
		super(pID, pWidth, pHeight, pTextureRegionFirstState, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	
		this.firstStateRegion = pTextureRegionFirstState;
		this.secondStateRegion = pTextureRegionSecondState;
		this.toggleButtonState = toggleButtonState;
		this.baseScene = baseScene;
	}
	
	
	public void touch(){
		this.toggleButtonState.touch(this.baseScene);
	}
	
	

}
