package com.example.fivecircles.gamestates;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.SingleValueChangeEntityModifier;

import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;

import android.util.Log;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.entities.SuperPowerRemoveZombies;
import com.example.entities.creators.SuperPowerRemoveZombiesFactory;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.CheckSameShapeAlgorithm;
import com.example.gamealgorithms.CheckSameShapeDiagonallyLeft;
import com.example.gamealgorithms.CheckSameShapeDiagonallyRight;
import com.example.gamealgorithms.CheckSameShapeFive;
import com.example.gamealgorithms.CheckSameShapeHorizontally;
import com.example.gamealgorithms.CheckSameShapeVertically;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.gamealgorithms.ShapeABM;
import com.example.managers.AudioManager;
import com.example.managers.ResourcesManager;

public class WaitingShapeMove extends GameState{

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void areaTouch(final GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
		switch (((IEntity)iTouchArea).getTag()) {
			//If some rectangle on the board was touched
			//Then We move the shape
			case 1:
				Sprite shape = (Sprite) gameScene.getChildByTag(3);
				Rectangle oldRectangle = (Rectangle)shape.getUserData();
				GameRectangle gameRectangleFromOldRectangle = (GameRectangle)oldRectangle.getUserData();
				gameScene.registerTouchArea(oldRectangle);
				
				
				final Rectangle touchedRectangle = (Rectangle)iTouchArea;
				shape.setPosition(touchedRectangle.getX(), touchedRectangle.getY());
				shape.setUserData(touchedRectangle);
				GameRectangle touchedGameRectangle = (GameRectangle)touchedRectangle.getUserData();
				touchedGameRectangle.setShape(gameRectangleFromOldRectangle.getShape());
				gameRectangleFromOldRectangle.setShape(null);
				
				ResourcesManager.getInstance().getEngine().runOnUpdateThread(new Runnable(){
	                @Override
	                public void run(){
	                	try{
	                		gameScene.unregisterTouchArea(touchedRectangle);
	                	}catch(Exception exception){
	                		Log.d("Error", exception.getMessage());
	                	}
	                }});
				
				shape.setTag(2);
				shape.setZIndex(2);
				shape.sortChildren();
				hideBadPath(gameScene);
				CheckSameShapeAlgorithm checkFive = new CheckSameShapeFive();
				if(checkFive.checkSameShape(gameScene, touchedGameRectangle.getRow(),touchedGameRectangle.getColumn(), touchedGameRectangle.getShape().getShapeType()).isEmpty()){
					//After that you need to check game over below
					ShapeABM.getInstance().add(gameScene, 2);
					// Code For Check Game Over
					checkGameOver(gameScene);
				}
				gameScene.drawScore();
				gameScene.setGameState(new WaitingShapeSelection());
				break;	
			case 2:
				//Case two happened when you drag the shape and drop over other shape
				//It's important to set the shape tag number
				Sprite shapeSelected = (Sprite) gameScene.getChildByTag(3);
				shapeSelected.setTag(2);
				hideBadPath(gameScene);
				gameScene.setGameState(new WaitingShapeSelection());
				break;
			case 3:
				//Case two happened when you drag the shape and drop over the same shape
				//It's important to set the shape tag number
				((IEntity)iTouchArea).setTag(2);
				hideBadPath(gameScene);
				gameScene.setGameState(new WaitingShapeSelection());
				break;
			default:
				break;
		}
		
		
		final Sprite oldShapeCopy = (Sprite) gameScene.getChildByTag(5);
		
		ResourcesManager.getInstance().getEngine().runOnUpdateThread(new Runnable(){
            @Override
            public void run(){
            	try{
            		if(oldShapeCopy!=null){
            			oldShapeCopy.detachSelf();
            		}
            	}catch(Exception exception){
            		Log.d("Error", exception.getMessage());
            	}
            }});
		
		
	}
	
	private void hideBadPath(GameScene gameScene){
		for(int i=0;i<gameScene.getChildCount();i++){
			if(gameScene.getChildByIndex(i).getTag()==4){
				final Rectangle rectangle = (Rectangle)gameScene.getChildByIndex(i);
				ResourcesManager.getInstance().getEngine()
				.runOnUpdateThread(new Runnable() {
					@Override
					public void run() {
						rectangle.detachChildren();
					}
				});
				rectangle.setTag(1);
			}
		}
	}
	

	@Override
	public void shapeDrag(final GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		if(((IEntity)iTouchArea).getTag()==1){
			
			final Sprite oldShapeCopy = (Sprite) gameScene.getChildByTag(5);
			
			Sprite shape = (Sprite) gameScene.getChildByTag(3);
			Rectangle rectangle = (Rectangle)shape.getUserData();
			GameRectangle gameRectangle = (GameRectangle)rectangle.getUserData();
			
			final Sprite shapeCopy = (Sprite) gameScene.drawShape(((IEntity)iTouchArea).getX(),((IEntity)iTouchArea).getY(), shape.getWidth(),shape.getHeight(), gameRectangle.getShape().getShapeType());
			shapeCopy.setScale(1.5f);
			shapeCopy.setTag(5);
			
			ResourcesManager.getInstance().getEngine()
			.runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					if(oldShapeCopy!=null)
						oldShapeCopy.detachSelf();
					gameScene.unregisterTouchArea(shapeCopy);	
				}
			});

			
		}
	}

	@Override
	public void instantiateGameEntity() {
		// TODO Auto-generated method stub
		
	}
	
}
