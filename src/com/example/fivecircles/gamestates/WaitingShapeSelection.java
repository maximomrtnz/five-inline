package com.example.fivecircles.gamestates;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;

import android.util.Log;

import com.example.entities.GameRectangle;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.AudioManager;

public class WaitingShapeSelection extends GameState{

	@Override
	public void playerTouched(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		//Paint the player
		//Mark the path where player can not move
		
		player.paint();
		
		//Play select player sound
		AudioManager.getInstance().soundSelectPlayer();
		
		
		gameScene.sortChildren();
		
		paintForbidden(gameScene);
		
		//Store selected Player
		
		gameScene.setSelectedPlayer(player);
		
		//Pass to the other game state
		gameScene.setGameState(new MovePlayer());
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
	public void areaTouch(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		//Check if player was touch
		if(((IEntity)iTouchArea).getTag()==2){
			
			Sprite shape = (Sprite)iTouchArea;
			Rectangle rectangle = (Rectangle)shape.getUserData();
			GameRectangle gameRectangle = (GameRectangle)rectangle.getUserData();
			shape.setScale(1.5f);
			shape.setZIndex(3);
			gameScene.sortChildren();
			
			//This is important becouse we need to know
			//when shape is touch again
			shape.setTag(3);
			
			//Storage selected shape
			this.setSelectedShape(shape);
			
			//Show bad path
			showBadPath(gameScene,getGoodPath(gameScene, gameRectangle.getRow(),gameRectangle.getColumn()));
			
			//Pass to the next state
			gameScene.setGameState(new WaitingShapeMove());
		}
	}

	
	private ArrayList<GameRectangle> getGoodPath(GameScene gameScene,int row, int column){
		ArrayList<GameRectangle> goodRectangles = new ArrayList<GameRectangle>();
		ArrayList<GameRectangle> checkedRectangles = new ArrayList<GameRectangle>();
		Stack<GameRectangle> stack = new Stack<GameRectangle>();
		Stack<GameRectangle> stackNeighbor;
		GameRectangle gameRectangle = getGameRectangleByRowAndColumn(gameScene, row, column);
		
		stackNeighbor = new Stack<GameRectangle>();
		stackNeighbor.push(getGameRectangleByRowAndColumn(gameScene, row, column-1));
		stackNeighbor.push(getGameRectangleByRowAndColumn(gameScene, row, column+1));
		stackNeighbor.push(getGameRectangleByRowAndColumn(gameScene, row+1, column));
		stackNeighbor.push(getGameRectangleByRowAndColumn(gameScene, row-1, column));
		
		for(GameRectangle gameRectangle2 : stackNeighbor){
			gameRectangle = gameRectangle2;
			while(gameRectangle != null){
				checkedRectangles.add(gameRectangle);
				if(gameRectangle.getShape()==null){
						row = gameRectangle.getRow();
						column = gameRectangle.getColumn();
						if(!goodRectangles.contains(gameRectangle)){
							goodRectangles.add(gameRectangle);
						}
						
						stackNeighbor = new Stack<GameRectangle>();
						stackNeighbor.push(getGameRectangleByRowAndColumn(gameScene, row, column-1));
						stackNeighbor.push(getGameRectangleByRowAndColumn(gameScene, row, column+1));
						stackNeighbor.push(getGameRectangleByRowAndColumn(gameScene, row+1, column));
						stackNeighbor.push(getGameRectangleByRowAndColumn(gameScene, row-1, column));
						for (GameRectangle gameRectangleNeighbor : stackNeighbor) {
							if(!checkedRectangles.contains(gameRectangleNeighbor) && gameRectangleNeighbor != null){
								stack.push(gameRectangleNeighbor);
							}
						}		
				}
				gameRectangle = null;
				if(!stack.empty()){
					gameRectangle = stack.pop();
				}
				
			}
			
		}	
		return goodRectangles;
	}
	
	private void showBadPath(GameScene gameScene,ArrayList<GameRectangle> goodRectangles){
		for(int i = 0; i < gameScene.getChildCount();i++){
			if(((IEntity)gameScene.getChildByIndex(i)).getTag()==1){
				Rectangle rectangle = (Rectangle)gameScene.getChildByIndex(i);
				GameRectangle gameRectangle = (GameRectangle)rectangle.getUserData();
				if(!goodRectangles.contains(gameRectangle) && gameRectangle.getShape()==null){
					gameScene.drawCross(rectangle);
					rectangle.setTag(4);
				}
			}
		}
	}
	
}
