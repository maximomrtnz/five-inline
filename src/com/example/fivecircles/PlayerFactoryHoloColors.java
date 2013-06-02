package com.example.fivecircles;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.fivecircles.gamescenes.BaseScene;

public class PlayerFactoryHoloColors implements PlayerFactory{

	@Override
	public PlayerLeaf createPlayer(BaseScene scene, float posX, float posY,
			float width, float height,
			VertexBufferObjectManager vertexBufferObjectManager, int color) {
		// TODO Auto-generated method stub
		
		PlayerLeaf player = new PlayerLeaf(posY, posY, width, height, vertexBufferObjectManager);
	
		switch (color) {
			case 1:
				player.setColor(51f/255f, 181f/255f, 229f/255f);
				break;
			case 2:
				player.setColor(170f/255f,42f/255f,204f/255f);
				break;
			case 3:
				player.setColor(153f/255f,204f/255,0f);
				break;
			case 4:
				player.setColor(1f,187f/255f,51f/255f);
				break;
			case 5:
				player.setColor(1f,68f/255f,68f/255f);
				break;	
		}
		
		return player;
	}

	

}
