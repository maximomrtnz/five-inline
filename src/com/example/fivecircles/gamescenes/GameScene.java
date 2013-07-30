package com.example.fivecircles.gamescenes;

import java.io.IOException;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.example.entities.GameRectangle;
import com.example.fivecircles.ShapeFactory;
import com.example.fivecircles.ShapeFactoryTypeOne;
import com.example.fivecircles.activities.GameActivity;
import com.example.fivecircles.gamestates.GameState;
import com.example.gamealgorithms.SearchAlgorithms;
import com.example.managers.AudioManager;
import com.example.managers.ResourcesManager;
import com.example.managers.SceneManager;
import com.example.managers.SceneManager.SceneType;
import com.example.storage.LevelGameContract;

public class GameScene extends BaseScene implements IOnAreaTouchListener {
	

	private GameState gameState;

	
	public GameScene(GameState gameState){
		this.gameState = gameState;
		this.gameState.loadGame(this);
	}
	
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		// If you pressed BackKeyButton We will show you the Menu Scene
		SceneManager.getInstance().loadMenuScene(super.getEngine());
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}

	// This method is responsible for disposing scene
	// removing all game scene objects.
	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		super.getCamera().setHUD(null);
		// Set camera`s center position to
		// its default value
		super.getCamera().setCenter(240, 400);
		
	}


	public void drawHUD() {
		// We will to create a HUD to show the score everytime
		// A HUD is important to "memory optimization"
		HUD gameHUD = new HUD(); 
		
		Text scoreTittle = new Text(super.getCamera().getCenterX(), 700, super.getResourcesManager().getScoreFont(),
				"score", new TextOptions(HorizontalAlign.LEFT),
				super.getVbom());
		
		Text highScoreTittle = new Text(super.getCamera().getCenterX(), 600, super.getResourcesManager().getScoreFont(),
				"high score", new TextOptions(HorizontalAlign.LEFT),
				super.getVbom());
		
		scoreTittle.setText("score");
		highScoreTittle.setText("high score");
		
		scoreTittle.setSkewCenter(0, 0);
		highScoreTittle.setSkew(0,0);
		
		gameHUD.attachChild(scoreTittle);
		gameHUD.attachChild(highScoreTittle);
		
		
		//Create Current Score Text
		Text scoreText = new Text(super.getCamera().getCenterX(), 650, super.getResourcesManager().getScoreFont(),
				"0123456789", new TextOptions(HorizontalAlign.LEFT),
				super.getVbom());
		
		scoreText.setSkewCenter(0, 0);
		
		scoreText.setTag(1);
		
		gameHUD.attachChild(scoreText);
		
		
		//Create High Score Text
		Text maxScoreText = new Text(super.getCamera().getCenterX(), 550,
				super.getResourcesManager().getScoreFont(),
				"0123456789",
				new TextOptions(HorizontalAlign.LEFT), super.getVbom());
		
		maxScoreText.setTag(2);
		
		maxScoreText.setSkew(0, 0);

		gameHUD.attachChild(maxScoreText);
		
		Sprite pausedSprite = new Sprite(super.getCamera().getCenterX()+(super.getResourcesManager().getPause().getWidth()*2.5f), 765,super.getResourcesManager().getPause() , super.getVbom()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	   			 if(pSceneTouchEvent.getAction()==MotionEvent.ACTION_UP){
	   				 setChildScene(new PauseScene(), false, true, true);
	   			 }
	   			 return true;
       	}};
       	
       	pausedSprite.setTag(3);
       	
       	gameHUD.attachChild(pausedSprite);
		
		gameHUD.registerTouchArea(pausedSprite);
	
		//Add Hud to Scene
		super.getCamera().setHUD(gameHUD);
		
	}
	
	public void loadNewLevel() {

		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(super.getVbom());

		

		levelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						LevelConstants.TAG_LEVEL) {
					@Override
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {
						final int width = SAXUtils.getIntAttributeOrThrow(
								pAttributes,
								LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
						final int height = SAXUtils.getIntAttributeOrThrow(
								pAttributes,
								LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
						return GameScene.this;
					}
				});

		levelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
					LevelGameContract.LEVEL_GAME_TAG_ENTITY) {
					@Override
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {
						
						final int row = SAXUtils.getIntAttributeOrThrow(
								pAttributes, LevelGameContract.LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_ROW);
						
						final int column = SAXUtils.getIntAttributeOrThrow(
								pAttributes, LevelGameContract.LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_COLUMN);
						
						final float posX = SAXUtils.getFloatAttributeOrThrow(
								pAttributes, LevelGameContract.LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_X);
						final float posY = SAXUtils.getFloatAttributeOrThrow(
								pAttributes, LevelGameContract.LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_Y);
						final float width = SAXUtils.getFloatAttributeOrThrow(
								pAttributes, LevelGameContract.LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_WIDTH);
						final float height = SAXUtils.getFloatAttributeOrThrow(
								pAttributes, LevelGameContract.LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_HEIGHT);

						final String type = SAXUtils.getAttributeOrThrow(
								pAttributes, LevelGameContract.LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_TYPE);

						IEntity entity = null;
						
						if (type.equals(LevelGameContract.LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SQUARE)) {
							entity = drawRectangle(row,column,posX, posY, width,height);
						}
						return entity;
					}
				});

		levelLoader.loadLevelFromAsset(super.getActivity().getAssets(),	"level/" + "level.xml");
	}
	
	
	public void drawBackgroundGame(){
		
		Sprite background = new Sprite(0, 0, ResourcesManager.getInstance().getGameScreenBackground(), ResourcesManager.getInstance().getVbom()){
			//We will override this method to enabled dithering
		    @Override
		    protected void preDraw(GLState pGLState, Camera pCamera){
		       super.preDraw(pGLState, pCamera);
		       pGLState.enableDither();
		    }
		};
		     
		background.setPosition(240,400);
		
		attachChild(background);
	}
	
	
	public IEntity drawRectangle(int row, int column, float posX, float posY,float width, float height) {
		
		Rectangle rectangle = new Rectangle(posX, posY, width, height, super.getVbom());
		
		rectangle.setColor(Color.GRAY);
		
		registerTouchArea(rectangle);
		
		GameRectangle gameRectangle = SearchAlgorithms.getGameRectangleByRowAndColumnFromGameEntity(ResourcesManager.getInstance().getActivity().getGame(), row, column);
		
		rectangle.setUserData(gameRectangle);
		
		rectangle.setTag(1);
		
		rectangle.setZIndex(1);
		
		return rectangle;
	}
	
	public IEntity drawShape(float posX, float posY,float width, float height, int type){
		
		ShapeFactory shapeFactory = new ShapeFactoryTypeOne();
		
		Sprite shape = shapeFactory.createShape(posX, posY, width, height, getVbom(), type);
		
		attachChild(shape);
		
		registerTouchArea(shape);
		
		shape.setTag(2);
		
		shape.setZIndex(2);
		
		sortChildren();
		
		return shape;
	}
	
	public IEntity drawCross(Rectangle rectangle){
		Sprite cross = new Sprite(rectangle.getWidth()/2, rectangle.getHeight()/2, ResourcesManager.getInstance().getCross(), super.getVbom());
		rectangle.attachChild(cross);
		return cross;
	}
	
	public void displayGameOverScene(){
		AudioManager.getInstance().soundGameOver();
		setChildScene(new GameOverScene(), false, true, true);
	}
	
	public Text drawPointText(float posX, float posY, String pText){
		Text text = new Text(posX, posY, ResourcesManager.getInstance().getFreckleFaceRegular(), pText, super.getVbom());
		attachChild(text);
		return text;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	
	private boolean longTouch = false;
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		
		if(pSceneTouchEvent.isActionDown()){
			if(!this.longTouch)
				this.gameState.areaTouch(this, pTouchArea);
			this.longTouch = true;
		}
		
		if(pSceneTouchEvent.isActionMove()){
			if(this.longTouch && ((IEntity)pTouchArea).getTag()==1){
				this.gameState.shapeDrag(this, pTouchArea);
			}
		}
		
		if(pSceneTouchEvent.isActionCancel()){
			Log.d("Touch","3");
		}
		
		if(pSceneTouchEvent.isActionUp()) {
			if(this.longTouch)
				this.gameState.areaTouch(this, pTouchArea);
			this.longTouch = false;
			return true;
		}
		return false;
	}


	@Override
	public void updateScene() {
		// TODO Auto-generated method stub
		
	}
	
	
}
