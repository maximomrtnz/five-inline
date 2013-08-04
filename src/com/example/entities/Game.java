package com.example.entities;

import java.util.ArrayList;

import com.example.fivecircles.utilities.MD5Manager;

/*
 * This class represent the current game
 */
public class Game {
	
	private int currentScore;
	private int highScore;
	private int gameId;
	private String md5Hash;
	
	private ArrayList<GameRectangle> board = new ArrayList<GameRectangle>();
	
	/*
	 * GETTERS
	 */
	
	public int getCurrentScore() {
		return currentScore;
	}
	public ArrayList<GameRectangle> getBoard() {
		return board;
	}
	public int getHighScore() {
		return highScore;
	}
	public int getGameId() {
		return gameId;
	}
	
	public String getMD5Hash(){
		return this.md5Hash;
	}
	
	
	/*
	 * SETTERS
	 */
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	public void setBoard(ArrayList<GameRectangle> board) {
		this.board = board;
	}
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public void setMD5Hash(String md5Hash){
		this.md5Hash = md5Hash;
	}
	
	/*
	 * MD5 Hash
	 */
	
	//Method which generate a MD5 hash using object information
	public String generateMD5Hash() throws Exception{
		ArrayList<String>strings = new ArrayList<String>();
		strings.add(Integer.toString(this.gameId));
		strings.add(Integer.toString(this.currentScore));
		this.md5Hash = MD5Manager.getInstance().generateMD5Hash(strings);
		return this.md5Hash;
	}
	
}
