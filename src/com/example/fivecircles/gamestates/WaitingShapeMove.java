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
import com.example.gamealgorithms.CheckSameShapeHorizontally;
import com.example.gamealgorithms.CheckSameShapeVertically;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.gamealgorithms.ZombiesRemover;
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
				if(!checkFive(gameScene, touchedGameRectangle.getRow(),touchedGameRectangle.getColumn(), touchedGameRectangle.getShape().getShapeType())){
					//Play Appear Shape Sound
					AudioManager.getInstance().playSound(AudioManager.SOUND_APPEAR_PLAYER);
					//When you add a new shape you need to check five shape joined
					ArrayList<GameRectangle> addedGameRecatngles = addNewShapes(gameScene, 2);
					for(GameRectangle addedGameRectangle : addedGameRecatngles){
						checkFive(gameScene, addedGameRectangle.getRow(),  addedGameRectangle.getColumn(), addedGameRectangle.getShape().getShapeType());
					}
					//After that you need to check game over below
					
					/*
					 * Code For Check Game Over
					 */
					checkGameOver(gameScene);
				}
				setScore();
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
	
		
	private boolean checkFive(final GameScene gameScene, int row, int column, int type){
		
		CheckSameShapeAlgorithm checkSameShapeAlgorithm = null;
		
		ArrayList<GameRectangle> gameRectanglesToClear	= new ArrayList<GameRectangle>();
		
		ArrayList<GameRectangle> gameRectangles = null;
		
		int multiplyPointBy						= 0;
		
		for(int i = 0; i < 4;i++){
			//Erase ArraList
			gameRectangles							= null;
			switch (i) {
				case 0:
					checkSameShapeAlgorithm 				= new CheckSameShapeVertically();
					break;
					case 1:
					checkSameShapeAlgorithm 				= new CheckSameShapeHorizontally();
					break;
				case 2:
					checkSameShapeAlgorithm					= new CheckSameShapeDiagonallyLeft();
					break;		
				default:
					checkSameShapeAlgorithm					= new CheckSameShapeDiagonallyRight();
					break;
			}
			gameRectangles	= checkSameShapeAlgorithm.checkSameShape(gameScene, row, column, type);
			if(gameRectangles != null){
				multiplyPointBy++;
				gameRectanglesToClear.addAll(gameRectangles);
			}	
		}
		
		if(!gameRectanglesToClear.isEmpty()){
			Game game = ResourcesManager.getInstance().getActivity().getGame();
			GameRectangle gameRectangle = SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row, column);
			gameRectanglesToClear.add(gameRectangle);
			ZombiesRemover.removeZombies(gameScene, gameRectanglesToClear, multiplyPointBy);
			if(game.getCurrentScore() % 10 == 0)
				gameScene.drawSuperPower(new SuperPowerRemoveZombiesFactory());
		}

		return !gameRectanglesToClear.isEmpty();
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
