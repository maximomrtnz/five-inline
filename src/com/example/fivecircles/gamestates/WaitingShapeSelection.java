package com.example.fivecircles.gamestates;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;

import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.AudioManager;

public class WaitingShapeSelection extends GameState{

	@Override
	public void playerTouched(GameScene gameScene, IPlayer player) {
		// TODO Auto-generated method stub
		//Paint the player
		//Mark the path where player can not move
		
		player.paint();
		
		//Play select player sound
		AudioManager.getInstance().soundSelectPlayer();
		
		
		gameScene.sortChildren();
		
		paintForbidden(gameScene);
		
		//Store selected Player
		
		gameScene.setSelectedPlayer(player);
		
		//Pass to the other game state
		gameScene.setGameState(new MovePlayer());
	}

	@Override
	public void backgroundTouched(GameScene gameScene,
			IBackgroundRectangle rectangle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame(GameScene gameScene) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void areaTouch(GameScene gameScene, ITouchArea iTouchArea) {
		// TODO Auto-generated method stub
		//Check if player was touch
		if(((IEntity)iTouchArea).getTag()==1){
			Sprite shape = (IEntity)iTouchArea;
		}
	}

	



}
