package com.example.gamealgorithms;

import java.util.ArrayList;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.entities.creators.SuperPowerRemoveZombiesFactory;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.ResourcesManager;

public class CheckSameShapeFive implements CheckSameShapeAlgorithm{

	@Override
	public ArrayList<GameRectangle> checkSameShape(GameScene gameScene,
			int row, int column, int type) {
		// TODO Auto-generated method stub
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
			ShapeABM.getInstance().removeZombies(gameScene, gameRectanglesToClear, multiplyPointBy);
			if(game.getCurrentScore() % 10 == 0)
				gameScene.drawSuperPower(new SuperPowerRemoveZombiesFactory());
		}

		return gameRectanglesToClear;
	}

}
