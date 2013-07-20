package com.example.fivecircles.gamescenes;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
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
import org.andengine.util.math.MathUtils;
import org.xml.sax.Attributes;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.ShapeFactory;
import com.example.fivecircles.ShapeFactoryTypeOne;
import com.example.fivecircles.activities.GameActivity;
import com.example.fivecircles.gamestates.GameState;
import com.example.managers.AudioManager;
import com.example.managers.ResourcesManager;
import com.example.managers.SceneManager;
import com.example.managers.SceneManager.SceneType;
import com.example.storage.LevelGameContract;

public class GameScene extends BaseScene implements IOnAreaTouchListener {
	
	
	private HUD gameHUD;
	private Text scoreText;
	private Text maxScoreText;
	private Sprite pausedSprite;
	
	private int score = 0;
	
	private GameState gameState;

	private IPlayer player;

	
	private IPlayer selectedPlayer;

	public static final String PREFS_NAME = "FiveNeighborPreferences";
	
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


	private void createHUD() {
		// We will to create a HUD to show the score everytime
		// A HUD is important to "memory optimization"
		gameHUD = new HUD(); 
		
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
		
		scoreText = new Text(super.getCamera().getCenterX(), 650, super.getResourcesManager().getScoreFont(),
				"0123456789", new TextOptions(HorizontalAlign.LEFT),
				super.getVbom());
		
		scoreText.setSkewCenter(0, 0);
		
		scoreText.setText("0");
		
		gameHUD.attachChild(scoreText);
		
		//Create High Score Text
		maxScoreText = new Text(super.getCamera().getCenterX(), 550,
				super.getResourcesManager().getScoreFont(),
				"0123456789",
				new TextOptions(HorizontalAlign.LEFT), super.getVbom());
		maxScoreText.setSkew(0, 0);
		maxScoreText.setText(Integer.toString(getHighScore()));
		gameHUD.attachChild(maxScoreText);
		
		
		
		pausedSprite = new Sprite(super.getCamera().getCenterX()+(super.getResourcesManager().getPause().getWidth()*2.5f), 765,super.getResourcesManager().getPause() , super.getVbom()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	   			 if(pSceneTouchEvent.getAction()==MotionEvent.ACTION_UP){
	   				 setChildScene(new PauseScene(), false, true, true);
	   			 }
	   			 return true;
       	}};
       	
       	gameHUD.attachChild(pausedSprite);
		
		gameHUD.registerTouchArea(pausedSprite);
		
		//Add Hud to Scene
		super.getCamera().setHUD(gameHUD);
	}
	
	public void addToScore(int i) {
		score += i;
		scoreText.setText(Integer.toString(score));
	}

	public void loadNewLevel() {

		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(super.getVbom());

		

		levelLoader
				.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						LevelConstants.TAG_LEVEL) {
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
		
		rectangle.setUserData(new GameRectangle(row, column));
		
		rectangle.setTag(1);
		
		return rectangle;
	}
	
	public IEntity drawShape(float posX, float posY,float width, float height, int type){
		
		ShapeFactory shapeFactory = new ShapeFactoryTypeOne();
		
		Sprite shape = shapeFactory.createShape(posX, posY, width, height, getVbom(), type);
		
		registerTouchArea(shape);
		
		attachChild(shape);
		
		shape.setTag(2);
		
		return shape;
	}
	
	public void displayGameOverScene(){
		AudioManager.getInstance().soundGameOver();
		setChildScene(new GameOverScene(), false, true, true);
	}
	
	
	public synchronized void setPlayerToBackgroundRectangle(IPlayer player) {
		/*
		ArrayList<IBackgroundRectangle> emptyRectangles = new ArrayList<IBackgroundRectangle>();

		for (IBackgroundRectangle rectangle : this.rectangles) {
			if (!rectangle.isTaken()) {
				emptyRectangles.add(rectangle);
			}
		}

		if (emptyRectangles.size() > 0) {

			int next = MathUtils.random(0, emptyRectangles.size() - 1);

			IBackgroundRectangle rectangle = emptyRectangles.get(next);

			((IEntity) player).registerEntityModifier(new ScaleModifier(1,
					0.5f, 1.0f));
			rectangle.addIPlayer(player);
		
		}*/

	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public IPlayer getPlayer() {
		return this.player;
	}

	public IPlayer getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(IPlayer selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

	public ArrayList<IBackgroundRectangle> getRectangles() {
		//return this.rectangles;
		return null;
	}

	public void removePlayer(IPlayer player) {

		if (player != null) {

			AlphaModifier alphaModifier = new AlphaModifier(1, 1f, 0f) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					super.onModifierStarted(pItem);
					// Your action after starting modifier
				}

				@Override
				protected void onModifierFinished(final IEntity pItem) {
					super.onModifierFinished(pItem);
					// Your action after finishing modifier
					ResourcesManager.getInstance().getEngine()
							.runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									unregisterTouchArea(pItem);
									pItem.detachSelf();
								}
							});
				}
			};

			((IEntity) player).registerEntityModifier(alphaModifier);
		}
	}


	public void saveHighScore() {

		int highScore = getHighScore();
		if (highScore < this.score) {
			GameActivity gameActivity = ResourcesManager.getInstance()
					.getActivity();
			SharedPreferences settings = gameActivity.getSharedPreferences(
					PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("highScore", this.score);
			editor.commit();
		}

	}

	public int getHighScore() {
		GameActivity gameActivity = ResourcesManager.getInstance()
				.getActivity();
		SharedPreferences settings = gameActivity.getSharedPreferences(
				PREFS_NAME, 0);
		int highScore = settings.getInt("highScore", 0);
		return highScore;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		if(pSceneTouchEvent.isActionDown()) {
			Rectangle rectangle = (Rectangle)pTouchArea;
			GameRectangle gameRectangle = (GameRectangle)rectangle.getUserData();
			Log.d("row", Integer.toString(gameRectangle.getRow()));
			Log.d("column", Integer.toString(gameRectangle.getColumn()));		
			return true;
		}
		return false;
	}
	
	
	
	
}
