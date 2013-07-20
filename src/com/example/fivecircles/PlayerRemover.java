package com.example.fivecircles;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;

import android.util.Log;

import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.ResourcesManager;

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
			final IPlayer player = iterator.next();
			this.gameScene.unregisterTouchArea((IEntity)player);
			//this.gameScene.detachChild((IEntity)player);
			
			AlphaModifier alphaModifier = new AlphaModifier(3,1f,0f){
				@Override
		        protected void onModifierStarted(IEntity pItem)
		        {
		                super.onModifierStarted(pItem);
		                // Your action after starting modifier
		        }
		       
		        @Override
		        protected void onModifierFinished(final IEntity pItem)
		        {
		                super.onModifierFinished(pItem);
		                // Your action after finishing modifier
		                ResourcesManager.getInstance().getEngine().runOnUpdateThread(new Runnable(){
                            @Override
                            public void run(){
                            	try{
                                    pItem.detachSelf();
                            	}catch(Exception exception){
                            		Log.d("Error On Remove Player", exception.getMessage());
                            	}
                            }});
		        }
			};
			
			((IEntity)player).registerEntityModifier(alphaModifier);
			
			iterator.remove();	
			System.gc();
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
