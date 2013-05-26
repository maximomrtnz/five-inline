package com.example.fivecircles;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PlayerLeaf extends Rectangle implements IPlayer{
	
	private boolean isTouched;
	private IBackgroundRectangle rectangle;
	
	public PlayerLeaf(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addPlayer(IPlayer player) {
		// TODO Auto-generated method stub
		//We don't implement it
	}

	@Override
	public void removePlayer(IPlayer player) {
		// TODO Auto-generated method stub
		//We don't implement it
	}

	@Override
	public boolean selectedPlayer() {
		// TODO Auto-generated method stub
		//We will check if 
		return this.isTouched;
		
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		this.isTouched = true;
		return false;
	}

	@Override
	public void deselectPlayers() {
		// TODO Auto-generated method stub
		this.isTouched = false;
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub
		setScale(1.2f);
		this.rectangle.initToCheckPath();
	}

	@Override
	public void addIBackgroundRectabgle(IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		this.rectangle = rectangle;
	}

	@Override
	public void removeIBackgroundRectabgle() {
		// TODO Auto-generated method stub
		this.rectangle = null;
	}

	@Override
	public int getPlayerNumber() {
		// TODO Auto-generated method stub
		return 1;
	}

}
