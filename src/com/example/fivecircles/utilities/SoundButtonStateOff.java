package com.example.fivecircles.utilities;

import com.example.managers.AudioManager;

public class SoundButtonStateOff implements ToggleButtonState{

	@Override
	public void touch(ToggleButtonMenu toggleButtonMenu) {
		// TODO Auto-generated method stub
		AudioManager.getInstance().turnOnSound();
		//Remove all children
		toggleButtonMenu.detachChildren();
		//Add Sound Off Sprite
		toggleButtonMenu.attachChild(toggleButtonMenu.getFirstStateSprite());
		//Change button state
		toggleButtonMenu.setButtonState(new SoundButtonStateOn());
	}

}
