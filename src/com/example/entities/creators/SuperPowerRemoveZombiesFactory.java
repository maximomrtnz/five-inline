package com.example.entities.creators;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.entities.SuperPower;
import com.example.entities.SuperPowerRemoveZombies;
import com.example.managers.ResourcesManager;

public class SuperPowerRemoveZombiesFactory implements ISuperPowerFactory{

	@Override
	public IEntity createSuperPowerDraw() {
		// TODO Auto-generated method stub
		Camera camera = ResourcesManager.getInstance().getCamera();
		ITextureRegion iTextureRegion = ResourcesManager.getInstance().getSuperPowerI();
		VertexBufferObjectManager vertexBufferObjectManager = ResourcesManager.getInstance().getVbom();
		Sprite sprite = new Sprite(camera.getXMin()+iTextureRegion.getHeight()/2,camera.getYMax()-iTextureRegion.getWidth()*3/2, iTextureRegion, vertexBufferObjectManager);
		return sprite;
	}

	@Override
	public SuperPower createSuperPower() {
		// TODO Auto-generated method stub
		return new SuperPowerRemoveZombies(10);
	}

}
