package com.example.fivecircles.gamestates;

import java.util.ArrayList;

import org.andengine.entity.IEntity;

import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;

public abstract class GameState {
	
	public abstract void playerTouched(GameScene gameScene,IPlayer player);
	
	public abstract void backgroundTouched(GameScene gameScene, IBackgroundRectangle rectangle);
	
	protected void unCheckBackgroundRectangles(GameScene gameScene){
		ArrayList<IBackgroundRectangle> rectangles = gameScene.getRectangles();
		for(IBackgroundRectangle rectangle : rectangles){
			rectangle.unChecked();
		}
	}
	
	protected void paintForbidden(GameScene gameScene){
		ArrayList<IBackgroundRectangle> rectangles = gameScene.getRectangles();
		for(IBackgroundRectangle rectangle : rectangles){
			if(!rectangle.isChecked()){
				rectangle.drawCross();
			}
		}
	}
	
	protected void unPaintForbidden(GameScene gameScene){
		ArrayList<IBackgroundRectangle> rectangles = gameScene.getRectangles();
		for(IBackgroundRectangle rectangle : rectangles){
			rectangle.eraseCross();
		}
	}
	
	protected  void checkFive(GameScene gameScene) {
		
		IPlayer player = gameScene.getSelectedPlayer();
		IBackgroundRectangle rectangle = player.getIBackgroundRectabgle();
		if(rectangle != null){
			ArrayList<IBackgroundRectangle> rectangles = rectangle.checkColorNeighbors();
			if(rectangles.size() > 4){
				gameScene.addToScore(10*rectangles.size());
				for(IBackgroundRectangle rectangle2 : rectangles){
					IPlayer iPlayer = rectangle2.getIPlayer();
					rectangle.removeIPlayer();
					gameScene.detachChild((IEntity)iPlayer);
				}
			}
		}
	}
}
