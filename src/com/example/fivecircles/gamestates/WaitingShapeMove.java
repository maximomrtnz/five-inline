package com.example.fivecircles.gamestates;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;

import com.example.entities.Game;
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
import com.example.managers.AudioManager;
import com.example.managers.NotificationManager;
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
		
		Game game = ResourcesManager.getInstance().getActivity().getGame();
		
		boolean isThereFive = false;
		
		CheckSameShapeAlgorithm checkSameShapeAlgorithm = null;
		
		Stack<GameRectangle> stack 				= new Stack<GameRectangle>();
		
		ArrayList<GameRectangle> gameRectangles = null;
		
		int multiplyPointBy						= 0;
		int totalScore							= 0;
		
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
				stack.addAll(gameRectangles);
			}	
		}
		
		if(!stack.isEmpty()){
			GameRectangle gameRectangle = SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row, column);
			stack.push(gameRectangle);
			isThereFive = true;
		}
		
		
		
		for(GameRectangle gameRectangleToDelete : stack){
			gameRectangleToDelete.setShape(null);
			final Rectangle rectangle = SearchAlgorithms.getRectangleFromGameRectangle(gameScene, gameRectangleToDelete);
			final Sprite shape = SearchAlgorithms.getShapeByRectangle(gameScene, rectangle);
			

			//Add number to show the points that player wins
			Text text = gameScene.drawPointText(rectangle.getX(),rectangle.getY(), "+10");
			
			ScaleModifier scaleModifier = new ScaleModifier(2, 2f, 0f){
				@Override
				protected void onModifierFinished(final IEntity pItem) {
					// TODO Auto-generated method stub
					super.onModifierFinished(pItem);
					ResourcesManager.getInstance().getEngine()
					.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							
							//Delete Shape
					
							pItem.detachSelf();
							
						}
					});
				}
			};
			
			text.registerEntityModifier(scaleModifier);
			
			AlphaModifier alphaModifier = new AlphaModifier(1, 1f, 0f) {
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
			
			game.setCurrentScore(game.getCurrentScore()+10*multiplyPointBy);
			
			totalScore += 10*multiplyPointBy;
		}
		
		if(totalScore != 0){
			//Play Remove Zombies Sound
			AudioManager.getInstance().playSound(AudioManager.SOUND_REMOVE_PLAYER);
			//Show Toast notification
			NotificationManager.getInstance().showToastNotification("+"+totalScore, ResourcesManager.getInstance().getActivity());
		}
		return isThereFive;
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
