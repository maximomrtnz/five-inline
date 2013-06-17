package com.example.fivecircles.utilities;

public class SoundButtonStateOn implements ToggleButtonState{

	@Override
	public void touch(ToggleButtonMenu toggleButtonMenu) {
		// TODO Auto-generated method stub
		AudioManager.getInstance().turnOffSound();
		//Remove all children
		toggleButtonMenu.detachChildren();
		//Add Sound Off Sprite
		toggleButtonMenu.attachChild(toggleButtonMenu.getSecondStateSprite());
		//Change State
		toggleButtonMenu.setButtonState(new SoundButtonStateOff());
	}

}
