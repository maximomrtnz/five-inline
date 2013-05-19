package com.example.fivecircles;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

public interface PlayerFactory {
	
	public PlayerLeaf createPlayer(BaseScene scene,float posX,float posY,float width,float height,VertexBufferObjectManager vertexBufferObjectManager,int color);

}
