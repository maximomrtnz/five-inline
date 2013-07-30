package com.example.fivecircles.gamescenes;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.util.adt.color.Color;

import com.example.managers.SceneManager;
import com.example.managers.SceneManager.SceneType;

public class HowToPlayScene extends BaseScene implements IScrollDetectorListener, IOnSceneTouchListener{
    // ===========================================================
    // Constants
    // ===========================================================
   
protected static int PADDING = 50;

protected static int MENUITEMS = 3;

    // ===========================================================
    // Fields
    // ===========================================================

	
	private Scene scene;

	// Scrolling
	private SurfaceScrollDetector scrollDetector;

	
	//private float minX = 0;
	//private float maxX = 0;
	//private float currentX = 0;
	private float minY = 0;
	private float maxY = 0;
	private float currentY = 0;
	private int itemClicked = -1;
	
	private Rectangle scrollBar; 
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		setScroll();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		SceneManager.getInstance().loadMenuScene(super.getEngine());
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_HOWTOPLAY;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void createBackground(){
		setBackground(new Background(new Color(1f, 1f, 1f)));
		//Pause Text
		scrollBar = new Rectangle(super.getCamera().getCenterX(), super.getCamera().getCenterY(), 100, 100,super.getVbom()); 
		
		scrollBar.setColor(0f, 0f, 0f);
		
		attachChild(scrollBar);
	}
	
	public void setScroll(){
		
		scrollDetector = new SurfaceScrollDetector(this);
		setOnSceneTouchListener(this); 
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		setOnSceneTouchListenerBindingOnActionDownEnabled(true);
	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		 scrollDetector.onTouchEvent(pSceneTouchEvent);
         return false;
	}

	@Override
	public void onScrollStarted(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		// TODO Auto-generated method stub
		//Disable the menu arrows left and right (15px padding)
        
        //Return if ends are reached
        if ((currentY - pDistanceY) < minY) {
                return;
        } else if ((currentY - pDistanceY) > maxY) {
                return;
        }
       
        //Center camera to the current point
        super.getCamera().offsetCenter(0, -pDistanceY);
        currentY -= pDistanceY;
       
        //Set the scrollbar with the camera
        float tempY = super.getCamera().getCenterY() - super.getCamera().getHeight() / 2;
        // add the % part to the position
        tempY += (tempY / (maxY + super.getCamera().getHeight())) * super.getCamera().getHeight();
        //set the position
        scrollBar.setPosition(scrollBar.getX(), tempY);
        
        //Because Camera can have negative Y values, so set to 0
        if (super.getCamera().getYMin() < 0) {
        		super.getCamera().offsetCenter(0, 0);
                currentY = 0;
        }
    	
	}

	@Override
	public void onScrollFinished(ScrollDetector pScollDetector, int pPointerID,
			float pDistanceX, float pDistanceY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateScene() {
		// TODO Auto-generated method stub
		
	}
	

}
