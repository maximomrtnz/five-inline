package com.example.entities;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.util.math.MathUtils;




import com.example.fivecircles.R;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.gamealgorithms.ShapeABM;
import com.example.managers.NotificationManager;
import com.example.managers.ResourcesManager;

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
		ShapeABM.getInstance().removeZombies(gameScene, gameRectanglesToClear, 1);
		
		//Show a message after remove
		Camera camera = ResourcesManager.getInstance().getCamera();
		NotificationManager.getInstance().showGameMessage(camera.getCenterX(),camera.getCenterY(), gameScene,R.string.game_message_one, ResourcesManager.getInstance().getFreckleFaceRegular(), ResourcesManager.getInstance().getActivity(), 3f);
		
		//Add new zombies
		ShapeABM.getInstance().add(gameScene, 2);
		
		//Draw Score
		gameScene.drawScore();
		
	}
	
	
}
