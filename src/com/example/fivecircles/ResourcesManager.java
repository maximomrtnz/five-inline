package com.example.fivecircles;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import org.andengine.util.debug.Debug;

import android.graphics.Color;

public class ResourcesManager {
	//+---------------------------------------------+
    //| 				VARIABLES					|
    //+---------------------------------------------+
    
	//Singleton Pattern
    private static final ResourcesManager instance = new ResourcesManager();
    
    private Engine engine;
    private GameActivity activity;
    private Camera camera;
    private VertexBufferObjectManager vbom;
    
    //---------------------------------------------
    // SOUND
    //---------------------------------------------
    
    private Sound selectPlayerSound; 
    private Sound removePlayersSound; 
    private Sound appearPlayersSound; 
    private Sound gameOverPlayersSound; 
    
    
    
    
    
    //---------------------------------------------
    // FONTS
    //---------------------------------------------
    
    private Font font;
    
    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    
    
    private BitmapTextureAtlas splashTextureAtlas; 
    private ITextureRegion menuBackgroundRegion;
    private ITextureRegion playRegion;
    private ITextureRegion optionsRegion;
    private ITextureRegion splashRegion;
    
    
    // Game Texture
    private BuildableBitmapTextureAtlas gameTextureAtlas;
        
    // Game Texture Regions
    private ITextureRegion square;
    
    //Game Texture Region (Pause Scene)
    private ITextureRegion pause;
    private ITextureRegion play;
    private ITextureRegion sound;
    private ITextureRegion soundOn;
    private ITextureRegion soundOff;
    private ITextureRegion reload;
    private ITextureRegion back;
    
    //Game Texture Region (Game Scene)
    private ITextureRegion kindOneNeighborOne;
    private ITextureRegion kindOneNeighborTwo;
    private ITextureRegion kindOneNeighborThree;
    private ITextureRegion kindOneNeighborFour;
    private ITextureRegion kindOneNeighborFive;
    private ITextureRegion cross;
    
        
    private BuildableBitmapTextureAtlas menuTextureAtlas;
    
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    private ResourcesManager(){}
    
    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------
    
    public void loadMenuResources(){
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }
    
    public void loadGameResources(){
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }
    
    private void loadMenuGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
    	this.menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
    	this.menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
    	this.playRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "play.png");
    	this.optionsRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "options.png");
    	
    	//Here We are going to create a buildable bitmap texture atlas  
    	//so we don't have to specify positions of particular graphics inside texture. 
    	try{
    	    this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
    	    this.menuTextureAtlas.load();
    	}catch (final TextureAtlasBuilderException e){
    	        Debug.e(e);
    	}
    
    }
    
    private void loadMenuAudio(){
   
    	
    }

    private void loadGameGraphics(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        
        // Pause/Game Over Scene  Graphics
        pause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "pause.png");
        play = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "play.png");
        sound = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "button_green_background.png");
        soundOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "soundoff.png");
        soundOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "soundon.png");
        reload = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "reload.png");
        back = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "back.png");
        
        // Game Scene Graphics
        kindOneNeighborOne = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "neighbor-1.png");
        kindOneNeighborTwo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "neighbor-2.png");
        kindOneNeighborThree = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "neighbor-3.png");
        kindOneNeighborFour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "neighbor-4.png");
        kindOneNeighborFive = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "neighbor-5.png");
        
        cross = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "cross.png");
        
        try{
            this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            this.gameTextureAtlas.load();
        }catch (final TextureAtlasBuilderException e){
            Debug.e(e);
        }
    }
    
    private void loadGameAudio(){
    	try{
   		 this.selectPlayerSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "sound/pick_up.wav");
   		 this.removePlayersSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "sound/remove.wav");
   		 this.appearPlayersSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "sound/appear.wav");
   		 this.gameOverPlayersSound = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "sound/game_over.wav");
    	}catch (IOException e){
   			e.printStackTrace();
   		}
    }
    
    public ITextureRegion getPlay() {
		return play;
	}

	public void setPlay(ITextureRegion play) {
		this.play = play;
	}

	public ITextureRegion getSoundOn() {
		return soundOn;
	}

	public void setSoundOn(ITextureRegion soundOn) {
		this.soundOn = soundOn;
	}

	public ITextureRegion getSoundOff() {
		return soundOff;
	}

	public void setSoundOff(ITextureRegion soundOff) {
		this.soundOff = soundOff;
	}

	public ITextureRegion getReload() {
		return reload;
	}

	public void setReload(ITextureRegion reload) {
		this.reload = reload;
	}

	private void loadGameFonts(){
        
    }
    
    

    private void loadMenuFonts(){
    	 FontFactory.setAssetBasePath("font/");
    	 final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
    	 font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "Roboto-Thin.ttf", 50, true, Color.WHITE, 2, Color.BLACK);
    	 font.load();
    }

    
   
    
    public void loadSplashScreen(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 1024, TextureOptions.BILINEAR);
    	splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
    	splashTextureAtlas.load();
    }
    
    public void unloadSplashScreen(){
    	splashTextureAtlas.unload();
    	splashRegion = null;
    }
    
    //This method will help us to free some memory 
    //while texture is currently not needed
    public void unloadMenuTextures(){
        menuTextureAtlas.unload();
    }
        
    public void loadMenuTextures(){
        menuTextureAtlas.load();
    }
    
    public void unloadGameTextures(){
        // TODO (Since we did not create any textures for game scene yet)
    }
    
    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom){
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }
    
    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
    
    public static ResourcesManager getInstance(){
        return instance;
    }

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public GameActivity getActivity() {
		return activity;
	}

	public void setActivity(GameActivity activity) {
		this.activity = activity;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public VertexBufferObjectManager getVbom() {
		return vbom;
	}

	public void setVbom(VertexBufferObjectManager vbom) {
		this.vbom = vbom;
	}

	public ITextureRegion getSplashRegion() {
		return splashRegion;
	}

	public void setSplashRegion(ITextureRegion splashRegion) {
		this.splashRegion = splashRegion;
	}

	public BitmapTextureAtlas getSplashTextureAtlas() {
		return splashTextureAtlas;
	}

	public void setSplashTextureAtlas(BitmapTextureAtlas splashTextureAtlas) {
		this.splashTextureAtlas = splashTextureAtlas;
	}

	public ITextureRegion getMenuBackgroundRegion() {
		return menuBackgroundRegion;
	}

	public void setMenuBackgroundRegion(ITextureRegion menuBackgroundRegion) {
		this.menuBackgroundRegion = menuBackgroundRegion;
	}

	public ITextureRegion getPlayRegion() {
		return playRegion;
	}

	public void setPlayRegion(ITextureRegion playRegion) {
		this.playRegion = playRegion;
	}

	public ITextureRegion getOptionsRegion() {
		return optionsRegion;
	}

	public void setOptionsRegion(ITextureRegion optionsRegion) {
		this.optionsRegion = optionsRegion;
	}

	public BuildableBitmapTextureAtlas getMenuTextureAtlas() {
		return menuTextureAtlas;
	}

	public void setMenuTextureAtlas(BuildableBitmapTextureAtlas menuTextureAtlas) {
		this.menuTextureAtlas = menuTextureAtlas;
	}

	public Font getFont() {
		return font;
	}

	public ITextureRegion getSquare() {
		return square;
	}
	
	public ITextureRegion getPause() {
		return pause;
	}

	public ITextureRegion getBack() {
		return back;
	}
	
	
	public ITextureRegion getSound() {
		return sound;
	}

	public Sound getSelectPlayerSound() {
		return selectPlayerSound;
	}

	public Sound getRemovePlayersSound() {
		return removePlayersSound;
	}
	

	public Sound getAppearPlayersSound() {
		return appearPlayersSound;
	}
	
	
	
	public Sound getGameOverPlayersSound() {
		return gameOverPlayersSound;
	}

	public ITextureRegion getKindOneNeighborOne() {
		return kindOneNeighborOne;
	}

	public ITextureRegion getKindOneNeighborTwo() {
		return kindOneNeighborTwo;
	}

	public ITextureRegion getKindOneNeighborThree() {
		return kindOneNeighborThree;
	}

	public ITextureRegion getKindOneNeighborFour() {
		return kindOneNeighborFour;
	}

	public ITextureRegion getKindOneNeighborFive() {
		return kindOneNeighborFive;
	}

	public ITextureRegion getCross() {
		return cross;
	}


	
	
	
}
