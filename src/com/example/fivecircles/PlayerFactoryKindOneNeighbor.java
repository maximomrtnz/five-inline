package com.example.fivecircles;


import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.fivecircles.gamescenes.BaseScene;

public class PlayerFactoryKindOneNeighbor implements PlayerFactory{

	@Override
	public PlayerLeaf createPlayer(BaseScene scene, float posX, float posY,
			float width, float height,
			VertexBufferObjectManager vertexBufferObjectManager, int color) {
		// TODO Auto-generated method stub
		
		PlayerLeaf player = null;
		
		switch (color) {
			case 1:
				player = new PlayerLeaf(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborOne(), vertexBufferObjectManager);
				break;
			case 2:
				player = new PlayerLeaf(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborTwo(), vertexBufferObjectManager);
				break;
			case 3:
				player = new PlayerLeaf(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborThree(), vertexBufferObjectManager);
				break;
			case 4:
				player = new PlayerLeaf(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborFour(), vertexBufferObjectManager);
				break;
			case 5:
				player = new PlayerLeaf(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborFive(), vertexBufferObjectManager);
				break;	
		}
		
		return player;
	}

	

}
