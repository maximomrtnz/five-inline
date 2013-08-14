package com.example.gamealgorithms;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.math.MathUtils;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.fivecircles.gamescenes.GameScene;

public class SearchAlgorithms {
	
	public static GameRectangle getEmptyGameRectangle(GameScene gameScene){
		GameRectangle returnGameRectangle = null;
		ArrayList<GameRectangle> emptyGameRectangles = new ArrayList<GameRectangle>(); 
		
		//Found empty game rectangles
		for(int i = 0 ;i < gameScene.getChildCount();i++){
		     if(gameScene.getChildByIndex(i).getTag() == 1){
		    	 GameRectangle gameRectangle = (GameRectangle)gameScene.getChildByIndex(i).getUserData();
		    	 if(gameRectangle.getShape()==null){
		    		emptyGameRectangles.add(gameRectangle);
		    	 }
		     }
		}
		
		//Set random empty game rectangles from empty list
		if (emptyGameRectangles.size() > 0) {
			int next = MathUtils.random(0, emptyGameRectangles.size() - 1);
			returnGameRectangle = emptyGameRectangles.get(next);
		}
		
		return returnGameRectangle;
	}
	
	public static ArrayList<GameRectangle> getTakenGameRectangles(GameScene gameScene){
		ArrayList<GameRectangle> takenGameRectangles = new ArrayList<GameRectangle>(); 
		
		//Found taken game rectangles
		for(int i = 0 ;i < gameScene.getChildCount();i++){
		     if(gameScene.getChildByIndex(i).getTag() == 1){
		    	 GameRectangle gameRectangle = (GameRectangle)gameScene.getChildByIndex(i).getUserData();
		    	 if(gameRectangle.getShape()!=null){
		    		 takenGameRectangles.add(gameRectangle);
		    	 }
		     }
		}
						
		return takenGameRectangles;
	}
	
	
	public static Rectangle getRectangleFromGameRectangle(GameScene gameScene,GameRectangle gameRectangle){
		Rectangle rectangle = null;
		int i = 0;
		boolean findIt = false;
		while(i < gameScene.getChildCount() && !findIt){
			if(gameScene.getChildByIndex(i).getTag() == 1){
				findIt = gameScene.getChildByIndex(i).getUserData().equals(gameRectangle);
		    }
			if(!findIt)
				i++;
		}
		if(i < gameScene.getChildCount() && findIt){
			rectangle = (Rectangle) gameScene.getChildByIndex(i);
		}
		return rectangle;
	}
	
	public static Rectangle getRectangleByRowAndColumn(GameScene gameScene,int row, int column){
		Rectangle rectangle = null;
		int i = 0;
		boolean findIt = false;
		while(i < gameScene.getChildCount() && !findIt){
			if(gameScene.getChildByIndex(i).getTag() == 1){
				GameRectangle gameRectangle = (GameRectangle)gameScene.getChildByIndex(i);
				findIt = gameRectangle.getRow() == row && gameRectangle.getColumn() == column; 
		    }
			if(!findIt)
				i++;
		}
		if(i < gameScene.getChildCount() && findIt){
			rectangle = (Rectangle) gameScene.getChildByIndex(i);
		}
		return rectangle;
	}
	
	public static GameRectangle getGameRectangleByRowAndColumn(GameScene gameScene,int row, int column){
		GameRectangle rectangle = null;
		int i = 0;
		boolean findIt = false;
		while(i < gameScene.getChildCount() && !findIt){
			if(gameScene.getChildByIndex(i).getTag() == 1){
				GameRectangle gameRectangle = (GameRectangle)gameScene.getChildByIndex(i).getUserData();
				findIt = (gameRectangle.getRow() == row && gameRectangle.getColumn() == column); 
		    }
			if(!findIt)
				i++;
		}
		if(i < gameScene.getChildCount() && findIt){
			rectangle = (GameRectangle) gameScene.getChildByIndex(i).getUserData();
		}
		return rectangle;
	}
	
	public static GameRectangle getGameRectangleByRowAndColumnFromGameEntity(Game game,int row, int column){
		GameRectangle rectangle = null;
		int i = 0;
		ArrayList<GameRectangle> gameRectangles = game.getBoard();
		boolean findIt = false;
		while(i < gameRectangles.size() && !findIt){
			findIt = gameRectangles.get(i).getRow() == row && gameRectangles.get(i).getColumn() == column;
			if(!findIt)
				i++;
		}
		if(i < gameRectangles.size() && gameRectangles.get(i).getRow() == row && gameRectangles.get(i).getColumn()==column){
			rectangle = gameRectangles.get(i);
		}
		return rectangle;
	}
	
	
	public static Sprite getShapeByRectangle(GameScene gameScene,Rectangle rectangle){
		Sprite shape = null;
		int i = 0;
		boolean findIt = false;
		while(i < gameScene.getChildCount() && !findIt){
			if(gameScene.getChildByIndex(i).getTag() == 2){
				Rectangle rectangle2 = (Rectangle)gameScene.getChildByIndex(i).getUserData();
				findIt = rectangle.equals(rectangle2); 
		    }
			if(!findIt)
				i++;
		}
		if(i < gameScene.getChildCount() && findIt){
			shape = (Sprite)gameScene.getChildByIndex(i);
		}
		return shape;
	}
	
}
