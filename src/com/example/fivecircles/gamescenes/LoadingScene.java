package com.example.fivecircles.gamescenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;


import com.example.fivecircles.SceneManager;
import com.example.fivecircles.SceneManager.SceneType;

public class LoadingScene extends BaseScene{

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		setBackground(new Background(Color.WHITE));
		attachChild(new Text(220, 400,super.getResourcesManager().getFont(), "Loading...", super.getVbom()));
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

}
