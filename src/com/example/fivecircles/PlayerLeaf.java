package com.example.fivecircles;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

public class PlayerLeaf extends Rectangle implements IPlayer,Observable{
	
	private IBackgroundRectangle rectangle;
	private ArrayList<Observer> observers;
	
	
	public PlayerLeaf(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.observers = new ArrayList<Observer>();
		this.setZIndex(1);
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
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		if(pSceneTouchEvent.isActionUp()){
			notifyObservers("TOUCHED");
		}
		return false;
	}

	@Override
	public void paint() {
		// TODO Auto-generated method stub
		setScale(1.2f);
		this.rectangle.initToCheckPath();
		setZIndex(2);
	}

	@Override
	public void addIBackgroundRectabgle(IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		
		if(this.rectangle !=null){
			//Leave to the old rectangle
			this.rectangle.removeIPlayer();
		}
		
		//Add new rectangle
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

	@Override
	public void notifyObservers(Object object) {
		// TODO Auto-generated method stub
		for(Observer observer : this.observers){
			observer.update(this, object);
		}
	}

	@Override
	public void removeObservers(Observer observer) {
		// TODO Auto-generated method stub
		this.observers.remove(observer);
	}

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		this.observers.add(observer);
	}

	@Override
	public void unPaint() {
		// TODO Auto-generated method stub
		setScale(1f);
		this.setZIndex(2);
	}

	public IBackgroundRectangle getRectangle(){
		return this.rectangle;
	}

	@Override
	public IBackgroundRectangle getIBackgroundRectabgle() {
		// TODO Auto-generated method stub
		return this.rectangle;
	}

}
