package com.example.fivecircles;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
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
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    
    
    private BitmapTextureAtlas splashTextureAtlas; 
    private ITextureRegion menuBackgroundRegion;
    private ITextureRegion playRegion;
    private ITextureRegion optionsRegion;
    private ITextureRegion splashRegion;
        
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
        
    }
    
    private void loadGameFonts(){
        
    }
    
    private void loadGameAudio(){
        
    }
    
    public void loadSplashScreen(){
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
    	splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash.png", 0, 0);
    	splashTextureAtlas.load();
    }
    
    public void unloadSplashScreen(){
    	splashTextureAtlas.unload();
    	splashRegion = null;
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
	
	
	
    
}
