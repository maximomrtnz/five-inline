package com.example.fivecircles;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;



public class BackgroundRectangle extends Rectangle implements IBackgroundRectangle{
	
	private boolean isChecked = false;
	private ArrayList<IBackgroundRectangle> neighbors;
	private IPlayer player;
	private int id;
	
	public BackgroundRectangle(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		
		this.neighbors = new ArrayList<IBackgroundRectangle>();
		this.setColor(0.1f,0.1f,0.1f,0.1f);
	}

	@Override
	public void addNeighbor(IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		this.neighbors.add(rectangle);
	}

	@Override
	public void removeNeighbor(IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		this.neighbors.remove(rectangle);
	}

	@Override
	public void checkPath() {
		// TODO Auto-generated method stub
		if(!this.isChecked && this.player !=null){
			for(IBackgroundRectangle rectangle : this.neighbors){
				rectangle.checkPath();
			}
		}
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		if(this.player == null){
			this.setScale(0.8f);
			this.setColor(Color.ABGR_PACKED_RED_SHIFT);
		}
	}

	@Override
	public void addIPlayer(IPlayer iPlayer) {
		// TODO Auto-generated method stub
		this.player = iPlayer;
		
		//Set its position and height and width to player inside its
		//We need cast it
		((Rectangle)this.player).setX(getX());
		((Rectangle)this.player).setX(getY());
		((Rectangle)this.player).setX(getY());
		((Rectangle)this.player).setX(getY());
		((Rectangle)this.player).setWidth(getWidth());
		((Rectangle)this.player).setHeight(getHeight());
		
		
		
	}

	@Override
	public void removeIPlayer(IPlayer iPlayer) {
		// TODO Auto-generated method stub
		this.player = null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public boolean isTaken() {
		// TODO Auto-generated method stub
		
		//Return true if it has a player in it.
		return this.player != null;
	}
	
	
	
	
}
