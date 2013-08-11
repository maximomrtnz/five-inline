package com.example.managers;


import java.util.Hashtable;

import org.andengine.audio.IAudioEntity;
import org.andengine.audio.music.Music;
import org.andengine.audio.sound.Sound;


import android.content.SharedPreferences;
import android.util.Log;

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
	
	// Game's Sound and Music List
	private Hashtable<Integer,IAudioEntity> gameAudio = new Hashtable<Integer,IAudioEntity>();

	//Singleton Pattern
    private static final AudioManager instance = new AudioManager();
    
    private AudioManager(){}
    
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
    
    public void playMusic(Integer musicId){
    	//TODO: check isMusicEnable
    	Music music = (Music)this.gameAudio.get(musicId);
    	if(!music.isPlaying()){
    		music.play();
    	}
    }

    public void stopMusic(Integer musicId){
    	//TODO: check isMusicEnable
    	Music music = (Music)this.gameAudio.get(musicId);
    	if(music.isPlaying()){
    		//Because the stop method has a bug we must to simulate the stop method
    		music.pause();
    		music.seekTo(0);
    	}
    }
    
    public void pauseMusic(Integer musicId){
    	//TODO: check isMusicEnable
    	Music music = (Music)this.gameAudio.get(musicId);
    	if(music.isPlaying()){
    		music.pause();
    	}
    }
    
    public void resumeMusic(Integer musicId){
    	//TODO: check isMusicEnable
    	Music music = (Music)this.gameAudio.get(musicId);
    	if(!music.isPlaying()){
    		music.resume();
    	}
    }
    
    public void putAudio(Integer audioId,Music music){
    	this.gameAudio.put(audioId, music);
    	music.setLooping(true);
    }
    
    public void putAudio(Integer audioId,Sound sound){
    	this.gameAudio.put(audioId, sound);
    }
}
