package com.example.fivecircles;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;

import com.example.fivecircles.gamescenes.GameScene;

public class PlayerRemover implements IUpdateHandler{
	
	private ArrayList<IPlayer> playersToRemove;
	private ArrayList<IPlayer> playersToDetach;
	private GameScene gameScene;
	
	public PlayerRemover(ArrayList<IPlayer> playersToRemove, GameScene gameScene){
		this.playersToRemove = playersToRemove;
		this.gameScene = gameScene;
		this.playersToDetach = new ArrayList<IPlayer>();
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		Iterator<IPlayer> iterator = this.playersToRemove.iterator();
		while(iterator.hasNext()){
			final IPlayer player = iterator.next();
			this.gameScene.unregisterTouchArea((IEntity)player);
			iterator.remove();
			
			
			
			ScaleModifier scaleModifier = new ScaleModifier(1, 1.0f, 0.0f){
				 @Override
			        protected void onModifierStarted(IEntity pItem)
			        {
			                super.onModifierStarted(pItem);
			                // Your action after starting modifier
			        }
			       
			        @Override
			        protected void onModifierFinished(IEntity pItem)
			        {
			                super.onModifierFinished(pItem);
			                // Your action after finishing modifier
			                playersToRemove.add(player);
			        }
			};
			
			
			//this.gameScene.detachChild((IEntity)player);
			((IEntity)player).registerEntityModifier(scaleModifier);
			
			
		}
		
		Iterator<IPlayer> iteratorDetach = playersToDetach.iterator();
		while(iteratorDetach.hasNext()){
			this.gameScene.detachChild((IEntity)iteratorDetach.next());
			iteratorDetach.remove();
			System.gc();
		}
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
