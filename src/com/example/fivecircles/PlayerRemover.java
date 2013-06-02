package com.example.fivecircles;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;

import com.example.fivecircles.gamescenes.GameScene;

public class PlayerRemover implements IUpdateHandler{
	
	private ArrayList<IPlayer> playersToRemove;
	private GameScene gameScene;
	
	public PlayerRemover(ArrayList<IPlayer> playersToRemove, GameScene gameScene){
		this.playersToRemove = playersToRemove;
		this.gameScene = gameScene;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		Iterator<IPlayer> iterator = this.playersToRemove.iterator();
		while(iterator.hasNext()){
			IPlayer player = iterator.next();
			this.gameScene.unregisterTouchArea((IEntity)player);
			this.gameScene.detachChild((IEntity)player);
			iterator.remove();
			System.gc();
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
