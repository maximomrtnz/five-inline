package com.example.fivecircles.gamestates;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;

import android.graphics.Color;
import android.util.Log;

import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.ResourcesManager;

public class WaitingShapeMove extends GameState{

	@Override
	public void playerTouched(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backgroundTouched(GameScene gameScene,
			IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		
	}

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
		
		
	}
	
	private void hideBadPath(GameScene gameScene){
		for(int i=0;i<gameScene.getChildCount();i++){
			if(((IEntity)gameScene.getChildByIndex(i)).getTag()==4){
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
	
	private ArrayList<GameRectangle> checkHorizontally(GameScene gameScene,int row, int column, int type){
		ArrayList<GameRectangle> gameRectangles = new ArrayList<GameRectangle>();
		int n = 0;
		int i = column-1;
		boolean follow = true;
		
		while(i>n && follow){
			GameRectangle sameRowGameRectangle = getGameRectangleByRowAndColumn(gameScene, row,i);
			follow = false;
			if(sameRowGameRectangle!=null){
				GameShape gameShape = sameRowGameRectangle.getShape();
				if(gameShape!=null){
					follow = gameShape.getShapeType() == type;
					if(follow){
						gameRectangles.add(sameRowGameRectangle);
					}
				}
			}
			i--;
		}
		follow = true;
		n = 9;
		i = column+1;
		while(i<n && follow){
			GameRectangle sameRowGameRectangle = getGameRectangleByRowAndColumn(gameScene, row,i);
			follow = false;
			if(sameRowGameRectangle!=null){
				GameShape gameShape = sameRowGameRectangle.getShape();
				if(gameShape!=null){
					follow = gameShape.getShapeType() == type;
					if(follow){
						gameRectangles.add(sameRowGameRectangle);
					}
				}
			}
			i++;
		}
		
		if(gameRectangles.size() < 4){
			gameRectangles = null;
		}
		
		return gameRectangles;
		
	}
	
	private ArrayList<GameRectangle> checkVertically(GameScene gameScene,int row, int column, int type){
		ArrayList<GameRectangle> gameRectangles = new ArrayList<GameRectangle>();
		int n = 0;
		int i = row-1;
		boolean follow = true;
		
		while(i>n && follow){
			GameRectangle sameRowGameRectangle = getGameRectangleByRowAndColumn(gameScene, i, column);
			follow = false;
			if(sameRowGameRectangle!=null){
				GameShape gameShape = sameRowGameRectangle.getShape();
				if(gameShape!=null){
					follow = gameShape.getShapeType() == type;
					if(follow){
						gameRectangles.add(sameRowGameRectangle);
					}
				}
			}
			i--;
		}
		follow = true;
		n = 9;
		i = row+1;
		while(i<n && follow){
			GameRectangle sameRowGameRectangle = getGameRectangleByRowAndColumn(gameScene, i, column);
			follow = false;
			if(sameRowGameRectangle!=null){
				GameShape gameShape = sameRowGameRectangle.getShape();
				if(gameShape!=null){
					follow = gameShape.getShapeType() == type;
					if(follow){
						gameRectangles.add(sameRowGameRectangle);
					}
				}
			}
			i++;
		}
		
		if(gameRectangles.size() < 4){
			gameRectangles = null;
		}
		
		return gameRectangles;
	}
	
	
	private ArrayList<GameRectangle> checkDiagonally(GameScene gameScene,int row, int column, int type){
		ArrayList<GameRectangle> gameRectangles = new ArrayList<GameRectangle>();
		int n = 0;
		int i = row-1;
		int j = column - 1;
		boolean follow = true;
		
		while(i>n && j > n && follow){
			GameRectangle sameRowGameRectangle = getGameRectangleByRowAndColumn(gameScene, i, j);
			if(sameRowGameRectangle!=null){
				GameShape gameShape = sameRowGameRectangle.getShape();
				if(gameShape!=null){
					follow = gameShape.getShapeType() == type;
					if(follow){
						gameRectangles.add(sameRowGameRectangle);
					}
				}
			}
			i--;
			j--;
		}
		
		follow = true;
		n = 9;
		i = row+1;
		j = column + 1;
		while(i<n && j<n && follow){
			GameRectangle sameRowGameRectangle = getGameRectangleByRowAndColumn(gameScene, i, j);
			if(sameRowGameRectangle!=null){
				GameShape gameShape = sameRowGameRectangle.getShape();
				if(gameShape!=null){
					follow = gameShape.getShapeType() == type;
					if(follow){
						gameRectangles.add(sameRowGameRectangle);
					}
				}
			}
			i++;
			j++;
		}
		
		if(gameRectangles.size() < 4){
			gameRectangles = null;
		}
		
		return gameRectangles;
	}
	
	private void checkFive(final GameScene gameScene, int row, int column, int type){
		
		Stack<GameRectangle> stack = new Stack<GameRectangle>();
		
		ArrayList<GameRectangle> gameRectanglesVertically = new ArrayList<GameRectangle>();
		ArrayList<GameRectangle> gameRectanglesHorizantally = new ArrayList<GameRectangle>();
		ArrayList<GameRectangle> gameRectanglesDiagonally = new ArrayList<GameRectangle>();
		
		gameRectanglesVertically = checkVertically(gameScene, row, column, type);
		gameRectanglesHorizantally = checkHorizontally(gameScene, row, column, type);
		gameRectanglesDiagonally = checkDiagonally(gameScene, row, column, type);
		
		
		//Combo 1
		if(gameRectanglesVertically != null){
			stack.addAll(gameRectanglesVertically);
		}
		//Combo 2
		if(gameRectanglesHorizantally != null){
			stack.addAll(gameRectanglesHorizantally);
		}
		//Combo 3
		if(gameRectanglesDiagonally != null){
			stack.addAll(gameRectanglesDiagonally);
		}
		
		
		if(!stack.isEmpty()){
			GameRectangle gameRectangle = getGameRectangleByRowAndColumn(gameScene, row, column);
			stack.push(gameRectangle);
		}
		
		for(GameRectangle gameRectangleToDelete : stack){
			gameRectangleToDelete.setShape(null);
			Rectangle rectangle = getRectangleFromGameRectangle(gameScene, gameRectangleToDelete);
			final Sprite shape = getShapeByRectangle(gameScene, rectangle);
			
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
									gameScene.unregisterTouchArea(pItem);
									pItem.detachSelf();
								}
							});
				}
			};

			shape.registerEntityModifier(alphaModifier);
		
		}
		
	}
	
}
