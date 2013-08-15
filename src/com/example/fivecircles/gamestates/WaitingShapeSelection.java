package com.example.fivecircles.gamestates;

import java.util.ArrayList;
import java.util.Stack;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;

import com.example.entities.GameRectangle;
import com.example.entities.SuperPower;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.SearchAlgorithms;

public class WaitingShapeSelection extends GameState{

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void areaTouch(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		//Check if player was touch
		switch (((IEntity)iTouchArea).getTag()) {
			case 2:
				Sprite shape = (Sprite)iTouchArea;
				Rectangle rectangle = (Rectangle)shape.getUserData();
				GameRectangle gameRectangle = (GameRectangle)rectangle.getUserData();
				shape.setZIndex(3);
				gameScene.sortChildren();
				
				//This is important becouse we need to know
				//when shape is touch again
				shape.setTag(3);
			
				//Show bad path
				showBadPath(gameScene,getGoodPath(gameScene, gameRectangle.getRow(),gameRectangle.getColumn()));
				
				//Pass to the next state
				gameScene.setGameState(new WaitingShapeMove());
				break;
	
			case 5:
				//A super power was touched
				Sprite superPowerDraw = (Sprite)iTouchArea;
				SuperPower superPower = (SuperPower)superPowerDraw.getUserData();
				//Execute super power
				superPower.executePower(gameScene);
				break;
			}

	}

	
	private ArrayList<GameRectangle> getGoodPath(GameScene gameScene,int row, int column){
		ArrayList<GameRectangle> goodRectangles = new ArrayList<GameRectangle>();
		ArrayList<GameRectangle> checkedRectangles = new ArrayList<GameRectangle>();
		Stack<GameRectangle> stack = new Stack<GameRectangle>();
		Stack<GameRectangle> stackNeighbor;
		GameRectangle gameRectangle = SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row, column);
		
		stackNeighbor = new Stack<GameRectangle>();
		stackNeighbor.push(SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row, column-1));
		stackNeighbor.push(SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row, column+1));
		stackNeighbor.push(SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row+1, column));
		stackNeighbor.push(SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row-1, column));
		
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
						stackNeighbor.push(SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row, column-1));
						stackNeighbor.push(SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row, column+1));
						stackNeighbor.push(SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row+1, column));
						stackNeighbor.push(SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, row-1, column));
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
			if(gameScene.getChildByIndex(i).getTag()==1){
				Rectangle rectangle = (Rectangle)gameScene.getChildByIndex(i);
				GameRectangle gameRectangle = (GameRectangle)rectangle.getUserData();
				if(!goodRectangles.contains(gameRectangle) && gameRectangle.getShape()==null){
					gameScene.drawCross(rectangle);
					rectangle.setTag(4);
				}
			}
		}
	}

	@Override
	public void shapeDrag(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void instantiateGameEntity() {
		// TODO Auto-generated method stub
		
	}
	
}
