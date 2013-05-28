package com.example.fivecircles.gamestates;

import org.andengine.entity.IEntity;
import org.andengine.util.debug.Debug;

import com.example.fivecircles.GameScene;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;

public class MovePlayer implements GameState{


	@Override
	public void selectPlayer(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
	}

	@Override
	public void movePlayer(GameScene gameScene, IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		if(rectangle.isChecked()){
			IPlayer player = gameScene.getSelectedPlayer();
			rectangle.addIPlayer(player);
			player.unPaint();
			addNewPlayers(gameScene, 4);
			//gameScene.setGameState(new SelectPlayer());
		}
	}
	
	private void addNewPlayers(GameScene gameScene,int numberOfPlayer){
		int i = 0;
		while(i<numberOfPlayer){
			IEntity player = gameScene.addPlayer(gameScene, 0, 0, 0, 0);
			gameScene.attachChild(player);
			gameScene.setPlayerToBackgroundRectangle((IPlayer)player);
			i++;
		}
	}
}
