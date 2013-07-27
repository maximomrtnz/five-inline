package com.example.gamealgorithms;

import java.util.ArrayList;

import com.example.entities.GameRectangle;
import com.example.fivecircles.gamescenes.GameScene;

public interface CheckSameShapeAlgorithm {
	
	public ArrayList<GameRectangle> checkSameShape(GameScene gameScene, int row, int column, int type);
	
}
