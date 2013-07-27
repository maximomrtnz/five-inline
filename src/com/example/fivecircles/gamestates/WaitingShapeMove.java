package com.example.fivecircles.gamestates;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

import com.example.entities.GameRectangle;
import com.example.fivecircles.ShapeFactory;
import com.example.fivecircles.ShapeFactoryTypeOne;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.CheckSameShapeAlgorithm;
import com.example.gamealgorithms.CheckSameShapeDiagonallyLeft;
import com.example.gamealgorithms.CheckSameShapeDiagonallyRight;
import com.example.gamealgorithms.CheckSameShapeHorizontally;
import com.example.gamealgorithms.CheckSameShapeVertically;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.managers.ResourcesManager;

public class WaitingShapeMove extends GameState{

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void areaTouch(final GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		//If some rectangle on the board was touched
		//Then We move the shape
		
		switch (((IEntity)iTouchArea).getTag()) {
			case 1:
				
				Sprite shape = (Sprite) gameScene.getChildByTag(3);
				shape.setVisible(true);
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
				shape.setScale(1f);
				shape.setZIndex(2);
				shape.sortChildren();
				hideBadPath(gameScene);
				checkFive(gameScene, touchedGameRectangle.getRow(),touchedGameRectangle.getColumn(), touchedGameRectangle.getShape().getShapeType());
				gameScene.setGameState(new WaitingShapeSelection());
				break;	
			case 3:
				//It's important to set the shape tag number
				((IEntity)iTouchArea).setTag(2);
				((IEntity)iTouchArea).setScale(1f);
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
	
		
	private void checkFive(final GameScene gameScene, int row, int column, int type){
		
		CheckSameShapeAlgorithm checkSameShapeAlgorithm = null;
		
		Stack<GameRectangle> stack 				= new Stack<GameRectangle>();
		ArrayList<GameRectangle> gameRectangles = null;
		
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
			if(gameRectangles != null)
				stack.addAll(gameRectangles);
		}
		
		if(!stack.isEmpty()){
			GameRectangle gameRectangle = SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row, column);
			stack.push(gameRectangle);
		}
		
		for(GameRectangle gameRectangleToDelete : stack){
			gameRectangleToDelete.setShape(null);
			final Rectangle rectangle = SearchAlgorithms.getRectangleFromGameRectangle(gameScene, gameRectangleToDelete);
			final Sprite shape = SearchAlgorithms.getShapeByRectangle(gameScene, rectangle);
			
			AlphaModifier alphaModifier = new AlphaModifier(1, 1f, 0f) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					super.onModifierStarted(pItem);
					// Your action after starting modifier
				}

				@Override
				protected void onModifierFinished(final IEntity pItem) {
					super.onModifierFinished(pItem);
					// Your action after finishing modifier
					ResourcesManager.getInstance().getEngine()
							.runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									//Delete Shape
									gameScene.unregisterTouchArea(pItem);
									pItem.detachSelf();
									
									//Restore Rectangle Properties
									gameScene.registerTouchArea(rectangle);
									rectangle.setTag(1);
								}
							});
				}
			};

			shape.registerEntityModifier(alphaModifier);
		
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
	
}
