package com.example.fivecircles.gamescenes;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.MailUtils;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.algorithm.path.Path;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.IEntityLoader;
import org.andengine.util.level.LevelLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.ease.EaseSineInOut;
import org.xml.sax.Attributes;

import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import com.example.fivecircles.BackgroundRectangle;
import com.example.fivecircles.GameActivity;
import com.example.fivecircles.IBackgroundRectangle;
import com.example.fivecircles.IPlayer;
import com.example.fivecircles.Observable;
import com.example.fivecircles.Observer;
import com.example.fivecircles.PlayerContainer;
import com.example.fivecircles.PlayerFactory;
import com.example.fivecircles.PlayerFactoryKindOneNeighbor;
import com.example.fivecircles.PlayerLeaf;
import com.example.fivecircles.PlayerRemover;
import com.example.fivecircles.ResourcesManager;
import com.example.fivecircles.SceneManager;
import com.example.fivecircles.SceneManager.SceneType;
import com.example.fivecircles.gamestates.GameState;
import com.example.fivecircles.gamestates.SelectPlayer;
import com.example.fivecircles.utilities.AudioManager;
import com.example.fivecircles.utilities.SoundButtonStateOff;
import com.example.fivecircles.utilities.SoundButtonStateOn;
import com.example.fivecircles.utilities.ToggleButtonMenu;
import com.example.fivecircles.utilities.ToggleButtonState;

public class GameScene extends BaseScene implements Observer {

	// --------------------------------------------------
	// VARIABLES
	// ---------------------------------------------------

	private HUD gameHUD;
	private Text scoreText;
	private Text maxScoreText;
	private Sprite pausedSprite;
	
	// Our Score
	private int score = 0;

	private Text gameOverText;

	private boolean gameOverDisplayed = false;

	private GameState state;

	private IPlayer player;

	private int idBackgroundRectangle = 0;

	private ArrayList<IBackgroundRectangle> rectangles;

	private ArrayList<IPlayer> playersToRemove;

	private IPlayer selectedPlayer;

	private boolean isGameRunning = true;
	
	//-----------------------------------------------------
	//	PAUSE BUTTONS
	//-----------------------------------------------------
	
	private SpriteMenuItem btnPlay;
	private SpriteMenuItem btnReload;
	private SpriteMenuItem btnSound;
	private SpriteMenuItem btnBack;
	
	// ----------------------------------------------------
	//	CONSTANTS
	// ----------------------------------------------------

	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_WIDTH = "width";
	private static final String TAG_ENTITY_ATTRIBUTE_HEIGHT = "height";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SQUARE = "square";
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER = "player";

	public static final String PREFS_NAME = "FiveNeighborPreferences";

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createHUD();
		loadLevel(1);
		setBackgroundRectanglesNeighbors();
		// Set Game State
		setGameState(new SelectPlayer());
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

	private void createBackground() {
		setBackground(new Background(1.0f, 1.0f, 1.0f));
	}

	private void createHUD() {
		// We will to create a HUD to show the score everytime
		// A HUD is important to "memory optimization"
		gameHUD = new HUD();

		//Create Current Score Text
		scoreText = new Text(100, 700, super.getResourcesManager().getFont(),
				"Score: 0123456789", new TextOptions(HorizontalAlign.LEFT),
				super.getVbom());
		scoreText.setScale(0.8f);
		scoreText.setSkewCenter(0, 0);
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);
		
		//Create High Score Text
		maxScoreText = new Text(160, 600,
				super.getResourcesManager().getFont(),
				"High Score: 0123456789",
				new TextOptions(HorizontalAlign.LEFT), super.getVbom());
		maxScoreText.setScale(0.8f);
		maxScoreText.setSkew(0, 0);
		maxScoreText.setText("High Score:" + getHighScore());
		gameHUD.attachChild(maxScoreText);
		
		
		
		pausedSprite = new Sprite(100, 750,super.getResourcesManager().getPause() , super.getVbom()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
	   			 if(pSceneTouchEvent.getAction()==MotionEvent.ACTION_UP){
	   				 setChildScene(new PauseScene(), false, true, true);
	   			 }
	   			 return true;
       	}};
       	
       	pausedSprite.setScale(2f);
		gameHUD.attachChild(pausedSprite);
		
		gameHUD.registerTouchArea(pausedSprite);
		
		//Add Hud to Scene
		super.getCamera().setHUD(gameHUD);
	}
	
	public void addToScore(int i) {
		score += i;
		scoreText.setText("Score: " + score);
	}

	private void loadLevel(int levelID) {

		final SimpleLevelLoader levelLoader = new SimpleLevelLoader(
				super.getVbom());

		this.player = new PlayerContainer();

		this.rectangles = new ArrayList<IBackgroundRectangle>();

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
						TAG_ENTITY) {
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {
						final float x = SAXUtils.getFloatAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_X);
						final float y = SAXUtils.getFloatAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
						final float width = SAXUtils.getFloatAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_WIDTH);
						final float height = SAXUtils.getFloatAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_HEIGHT);

						final String type = SAXUtils.getAttributeOrThrow(
								pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);

						IEntity entity = null;
						if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SQUARE)) {
							entity = addShape(GameScene.this, x, y, width,
									height, type);
						} else if (type
								.equals(TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_PLAYER)) {
							entity = addPlayer(GameScene.this, x, y, width,
									height);
						}
						return entity;
					}
				});

		levelLoader.loadLevelFromAsset(super.getActivity().getAssets(),
				"level/" + "level" + levelID + ".xml");
	}

	private IEntity addShape(BaseScene scene, float posX, float posY,
			float width, float height, String type) {

		IBackgroundRectangle rectangle = new BackgroundRectangle(posX, posY,
				width, height, super.getVbom());
		rectangle.setId(this.idBackgroundRectangle);
		this.idBackgroundRectangle++;
		this.rectangles.add(rectangle);
		((Observable) rectangle).addObserver(this);
		registerTouchArea((IEntity) rectangle);
		return (Rectangle) rectangle;
	}

	public synchronized IEntity addPlayer(BaseScene scene, float posX, float posY,
			float width, float height) {

		int color = MathUtils.random(1, 5);
		PlayerFactory playerFactory = new PlayerFactoryKindOneNeighbor();
		IEntity iEntity = playerFactory.createPlayer(scene, posX, posY, width,
				height, super.getVbom(), color);
		registerTouchArea(iEntity);
		this.player.addPlayer((PlayerLeaf) iEntity);
		setPlayerToBackgroundRectangle((PlayerLeaf) iEntity);
		((Observable) iEntity).addObserver(this);
		
		return iEntity;
	}
	
	public void displayGameOverScene(){
		AudioManager.getInstance().soundGameOver();
		setChildScene(new GameOverScene(), false, true, true);
	}
	
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		return false;
	}

	private synchronized void setBackgroundRectanglesNeighbors() {

		for (IBackgroundRectangle rectangle : this.rectangles) {

			int id = rectangle.getId();
			int leftNeighbor = id - 1;
			int rigthNeighbor = id + 1;
			int bottomNeighbor = id - 8;
			int topNeighbor = id + 8;

			if (topNeighbor <= 63) {
				rectangle.addNeighbor(this.rectangles.get(topNeighbor));
			}

			if (bottomNeighbor >= 0) {
				rectangle.addNeighbor(this.rectangles.get(bottomNeighbor));
			}

			// Test special cases (with less than four neighbor)

			if (id % 8 == 0) {
				// Rectangles on the left
				rectangle.addNeighbor(this.rectangles.get(rigthNeighbor));
			} else if ((id + 1) % 8 == 0) {
				// Rectangles on the right
				rectangle.addNeighbor(this.rectangles.get(leftNeighbor));
			} else {
				// The common case with four neighbor
				rectangle.addNeighbor(this.rectangles.get(rigthNeighbor));
				rectangle.addNeighbor(this.rectangles.get(leftNeighbor));
			}

		}

	}

	public synchronized void setPlayerToBackgroundRectangle(IPlayer player) {

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
		
		}

	}

	@Override
	public void update(Observable observable, Object object) {

		if (this.isGameRunning) {
			// TODO Auto-generated method stub
			if (observable instanceof IPlayer) {
				if (String.valueOf(object).equals("TOUCHED")) {
					// Player is touched pass the action to the GameState
					this.state.playerTouched(this, (IPlayer) observable);
				}

			} else if (observable instanceof IBackgroundRectangle) {
				if (String.valueOf(object).equals("TOUCHED")) {
					// Player is touched pass the action to the GameState
					this.state.backgroundTouched(this,
							(IBackgroundRectangle) observable);
				}
			}

		}

	}

	public void setGameState(GameState gameState) {
		this.state = gameState;
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
		return this.rectangles;
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

}
