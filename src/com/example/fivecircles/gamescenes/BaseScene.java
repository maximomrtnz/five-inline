package com.example.fivecircles.gamescenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.managers.ResourcesManager;
import com.example.managers.SceneManager.SceneType;

import android.app.Activity;

public abstract class BaseScene extends Scene{
	
	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private Engine engine;
    private Activity activity;
    private ResourcesManager resourcesManager;
    private VertexBufferObjectManager vbom;
    private Camera camera;
    
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    public BaseScene(){
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = this.resourcesManager.getEngine();
        this.activity = this.resourcesManager.getActivity();
        this.vbom = this.resourcesManager.getVbom();
        this.camera = this.resourcesManager.getCamera();
        createScene();
    }
    
    //---------------------------------------------
    // ABSTRACT METHOD
    //---------------------------------------------
    
    public abstract void createScene();
    
    public abstract void onBackKeyPressed();
    
    public abstract SceneType getSceneType();
    
    public abstract void disposeScene();
    
    //---------------------------------------------
    //	COMMON METHOD
    //---------------------------------------------
    
    //Method to Manage Sounds
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public ResourcesManager getResourcesManager() {
		return resourcesManager;
	}

	public void setResourcesManager(ResourcesManager resourcesManager) {
		this.resourcesManager = resourcesManager;
	}

	public VertexBufferObjectManager getVbom() {
		return vbom;
	}

	public void setVbom(VertexBufferObjectManager vbom) {
		this.vbom = vbom;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
    
    

    
}
