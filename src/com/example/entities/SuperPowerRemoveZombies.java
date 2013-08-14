package com.example.entities;

import java.util.ArrayList;

import org.andengine.util.math.MathUtils;

import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.gamealgorithms.ZombiesRemover;

public class SuperPowerRemoveZombies implements SuperPower{
	
	private int zombiesToRemove;
	
	public SuperPowerRemoveZombies(int zombiesToRemove){
		this.zombiesToRemove = zombiesToRemove;
	}

	@Override
	public void executePower(GameScene gameScene) {
		// TODO Auto-generated method stub
		
		
		ArrayList<GameRectangle> takenGameRectangles = SearchAlgorithms.getTakenGameRectangles(gameScene);
		ArrayList<GameRectangle> gameRectanglesToClear = new ArrayList<GameRectangle>();
		
		int i = 0;
		
		//Collect as zombies to remove as zombiesToRemove says
		//but be careful maybe you have less zombies on the board
		while (i < this.zombiesToRemove && !takenGameRectangles.isEmpty()) {
			gameRectanglesToClear.add(takenGameRectangles.remove(MathUtils.random(0, takenGameRectangles.size()-1)));
			i++;
		}
		
		//Pass Zombies to the remover algorithm
		ZombiesRemover.removeZombies(gameScene, gameRectanglesToClear, 1);
	}
	
	
}
