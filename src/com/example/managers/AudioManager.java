package com.example.managers;

import java.util.Hashtable;
import org.andengine.audio.sound.Sound;

import android.content.SharedPreferences;

import com.example.fivecircles.activities.GameActivity;

public class AudioManager {
	
	
	/**
	 * Constants
	 */
	
	public static String PREFS_NAME = "FiveNeighborPreferences";
	
	//Sounds Id's
	public static Integer SOUND_SELECT_PLAYER = 0;
	public static Integer SOUND_REMOVE_PLAYER = 1;
	public static Integer SOUND_APPEAR_PLAYER = 2;

	//Musics Id's	
	public static Integer MUSIC_GAME_OVER_SCENE = 3;
	public static Integer MUSIC_MAIN_MENU_SCENE = 4;
	public static Integer MUSIC_GAME_SCENE = 5; 
	
	private Hashtable<Integer,Sound> gameAudio = new Hashtable<Integer,Sound>();
	
	
	//Singleton Pattern
    private static final AudioManager instance = new AudioManager();
    
    private AudioManager(){
    	
    	//Load Sounds
    	this.gameAudio.put(SOUND_SELECT_PLAYER, ResourcesManager.getInstance().getSelectPlayerSound());
    	this.gameAudio.put(SOUND_REMOVE_PLAYER, ResourcesManager.getInstance().getRemovePlayersSound());
    	this.gameAudio.put(SOUND_APPEAR_PLAYER, ResourcesManager.getInstance().getAppearPlayersSound());
  
    }
    
    public static AudioManager getInstance(){
    	return instance;
    }
    
    public boolean isSoundEnable(){
    	//Check if the sound is enable
    	//We store sound state inside a Android Preference
    	GameActivity gameActivity = ResourcesManager.getInstance().getActivity();
		SharedPreferences settings = gameActivity.getSharedPreferences(PREFS_NAME, 0);
		return settings.getBoolean("isSoundEnable", true); 
    
    }

    public void turnOnSound(){
		//Set Preference
		GameActivity gameActivity = ResourcesManager.getInstance().getActivity();
		SharedPreferences settings = gameActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("isSoundEnable", true);
		editor.commit();
	}
	
	public void turnOffSound(){
		//Set Preference
		GameActivity gameActivity = ResourcesManager.getInstance().getActivity();
		SharedPreferences settings = gameActivity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("isSoundEnable", false);
		editor.commit();
	}
    
    
    public void playSound(Integer soundId){
    	if(isSoundEnable()){
    		this.gameAudio.get(soundId).play();
    	}
    }
}
