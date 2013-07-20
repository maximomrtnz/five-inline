package com.example.fivecircles;


import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.fivecircles.gamescenes.BaseScene;
import com.example.managers.ResourcesManager;

public class ShapeFactoryTypeOne implements ShapeFactory{

	@Override
	public Sprite createShape(float posX, float posY,
			float width, float height,
			VertexBufferObjectManager vertexBufferObjectManager, int type) {
		// TODO Auto-generated method stub
		
		Sprite shape = null;
		
		switch (type) {
			case 1:
				shape = new Sprite(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborOne(), vertexBufferObjectManager);
				break;
			case 2:
				shape = new Sprite(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborTwo(), vertexBufferObjectManager);
				break;
			case 3:
				shape = new Sprite(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborThree(), vertexBufferObjectManager);
				break;
			case 4:
				shape = new Sprite(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborFour(), vertexBufferObjectManager);
				break;
			case 5:
				shape = new Sprite(posX, posY, height, width, ResourcesManager.getInstance().getKindOneNeighborFive(), vertexBufferObjectManager);
				break;	
		}
		
		return shape;
	}

	

}
