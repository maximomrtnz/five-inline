package com.example.fivecircles.gamestates;

import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.PlayerLeaf;
import com.example.fivecircles.gamescenes.GameScene;

public class MovePlayer extends GameState{

	@Override
	public void playerTouched(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backgroundTouched(GameScene gameScene,
			IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub

		IPlayer player = gameScene.getSelectedPlayer();
		
		if(((PlayerLeaf)player).getRectangle().equals(rectangle)){
			
			gameScene.setGameState(new CancelMovePlayer());
		
		}else if(rectangle.isChecked()){
		
			rectangle.addIPlayer(player);
			gameScene.setGameState(new MovedPlayer());
		
		}
		
	}
	
	
}
