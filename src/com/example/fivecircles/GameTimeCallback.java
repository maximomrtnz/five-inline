package com.example.fivecircles;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

public class GameTimeCallback implements ITimerCallback{
	
	private Engine engine;
	
	public GameTimeCallback(Engine engine){
		this.engine = engine;
	}
	
	@Override
	public void onTimePassed(TimerHandler pTimerHandler) {
		// TODO Auto-generated method stub
		this.engine.unregisterUpdateHandler(pTimerHandler);
		SceneManager.getInstance().createMenuScene();
	}
	

}
