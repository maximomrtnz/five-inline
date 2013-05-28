package com.example.fivecircles.gamestates;

import com.example.fivecircles.GameScene;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;

public interface GameState {
	
	public void	selectPlayer(GameScene gameScene,IPlayer player);
	
	public void movePlayer(GameScene gameScene, IBackgroundRectangle rectangle);
	
}
