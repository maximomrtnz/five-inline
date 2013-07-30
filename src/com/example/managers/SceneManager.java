package com.example.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.example.fivecircles.gamescenes.BaseScene;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.fivecircles.gamescenes.HowToPlayScene;
import com.example.fivecircles.gamescenes.LoadingScene;
import com.example.fivecircles.gamescenes.MainMenuScene;
import com.example.fivecircles.gamescenes.SplashScene;
import com.example.fivecircles.gamestates.GameState;
import com.example.fivecircles.gamestates.LoadingNewGame;

public class SceneManager {
	 //---------------------------------------------
    // SCENES
    //---------------------------------------------
    
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;
    private BaseScene howToPlay;
    
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    private static final SceneManager instance = new SceneManager();
    
    private SceneType currentSceneType = SceneType.SCENE_SPLASH;
    
    private BaseScene currentScene;
    
    private Engine engine = ResourcesManager.getInstance().getEngine();
    
    public enum SceneType{
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_HOWTOPLAY
    }
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void setScene(BaseScene scene){
    	scene.updateScene();
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }
    
    public void setScene(SceneType sceneType){
        switch (sceneType){
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            case SCENE_HOWTOPLAY:
            	setScene(howToPlay);
            default:
                break;
        }
    }
    
    
    //We will create method responsible for initializing splash scene
    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback){
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }
    
    //We will create method responsible for disposing splash scene
    private void disposeSplashScene(){
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }
    
    public void createMenuScene() {
    	ResourcesManager.getInstance().loadMenuResources();
    	ResourcesManager.getInstance().loadLoadingResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
        disposeSplashScene();
    }
    
    
    //This method is responsible for displaying loading scene, 
    //while initializing game scene and loading its resources, 
    //and unloading menu texture.
    public void loadHowToPlayScene(final Engine mEngine){
    	ResourcesManager.getInstance().unloadMenuTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback(){
            @Override
			public void onTimePassed(final TimerHandler pTimerHandler){
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                howToPlay = new HowToPlayScene();
                setScene(howToPlay);
            }
        }));
    }
    
    
    //This method is responsible for displaying loading scene, 
    //while initializing game scene and loading its resources, 
    //and unloading menu texture.
    public void loadGameScene(final Engine mEngine, final GameState gameState){
    	ResourcesManager.getInstance().loadLoadingResources();
        setScene(loadingScene);
        ResourcesManager.getInstance().unloadMenuTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback(){
            @Override
			public void onTimePassed(final TimerHandler pTimerHandler){
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().unloadLoadingScreen();
                ResourcesManager.getInstance().loadGameResources();
                gameScene = new GameScene(gameState);
                setScene(gameScene);
            }
        }));
    }
    
    
    //This method is responsible for 
    //loading menu scene and displaying loading scene
    public void loadMenuScene(final Engine mEngine){
    	ResourcesManager.getInstance().loadLoadingResources();
        setScene(loadingScene);
        gameScene.disposeScene();
        ResourcesManager.getInstance().unloadGameTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback(){
            @Override
			public void onTimePassed(final TimerHandler pTimerHandler){
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().unloadLoadingScreen();
                ResourcesManager.getInstance().loadMenuTextures();
                setScene(menuScene);
            }
        }));
    }
    
    public void reloadGameScene(final Engine mEngine){
    	ResourcesManager.getInstance().loadLoadingResources();
    	setScene(loadingScene);
    	gameScene.disposeScene();
    	ResourcesManager.getInstance().unloadGameTextures();
    	mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback(){
             @Override
			public void onTimePassed(final TimerHandler pTimerHandler){
                 mEngine.unregisterUpdateHandler(pTimerHandler);
                 ResourcesManager.getInstance().unloadLoadingScreen();
                 ResourcesManager.getInstance().loadGameResources();
                 gameScene = new GameScene(new LoadingNewGame());
                 setScene(gameScene);
             }
         }));
    }
    
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static SceneManager getInstance(){
        return instance;
    }
    
    public SceneType getCurrentSceneType(){
        return currentSceneType;
    }
    
    public BaseScene getCurrentScene(){
        return currentScene;
    }
}
