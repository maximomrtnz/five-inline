package com.example.entities.creators;

import org.andengine.entity.IEntity;

import com.example.entities.SuperPower;

public interface ISuperPowerFactory {

	public IEntity createSuperPowerDraw();
	
	public SuperPower createSuperPower();

}
