package com.example.gamealgorithms;

import java.util.ArrayList;

import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.gamescenes.GameScene;

public class CheckSameShapeVertically implements CheckSameShapeAlgorithm{

	@Override
	public ArrayList<GameRectangle> checkSameShape(GameScene gameScene, int row,
			int column, int type) {
		ArrayList<GameRectangle> gameRectangles = new ArrayList<GameRectangle>();
		int n = 0;
		int i = row-1;
		boolean follow = true;
		
		while(i>n && follow){
			GameRectangle sameRowGameRectangle = SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, i, column);
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
			GameRectangle sameRowGameRectangle = SearchAlgorithms.getGameRectangleByRowAndColumn(gameScene, i, column);
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

}
