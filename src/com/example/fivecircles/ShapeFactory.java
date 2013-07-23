package com.example.fivecircles;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.fivecircles.gamescenes.BaseScene;

public interface ShapeFactory {
	
	public Sprite createShape(float posX,float posY,float width,float height,VertexBufferObjectManager vertexBufferObjectManager,int type);

}