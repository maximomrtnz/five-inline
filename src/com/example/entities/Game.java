package com.example.entities;

import java.util.ArrayList;

/*
 * This class represent the current game
 */
public class Game {
	
	private int currentScore;
	private int highScore;
	private int gameId;
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
	
	
	
}
