package com.example.storage;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.entities.GameShape;

public class DataBaseMapper {
	
	private String[] gamesColumn = 		{
									  		GameContract.KEY_GAMES_GAME_ID,
								  			GameContract.KEY_GAMES_CURRENT_SCORE
								   		};
	
	private String[] rectanglesColumn = {
											GameContract.KEY_RECTANGLES_GAME_ID,
											GameContract.KEY_RECTANGLES_RECTANGLE_ROW,
											GameContract.KEY_RECTANGLES_RECTANGLE_COLUMN,
											GameContract.KEY_RECTANGLES_SHAPE_ID
		   								};

	
	//Make This Class Singleton
	public static DataBaseMapper instance;
	
	private DataBaseMapper(){}
	
	public synchronized static DataBaseMapper getInstance(){
		if(instance == null){
			instance =  new DataBaseMapper();
		}
		return instance;
	}
	
	public Game getSavedGame(Context context){
		Game game = null;
		//Get Data Base
		SQLiteDatabase db = DataBaseHandler.getInstance(context).getReadableDatabase();
		
		Cursor cursorGame = db.rawQuery("SELECT * FROM "+GameContract.TABLE_GAMES,null);
		
		if (cursorGame.moveToFirst()) {
			do {
	            game = new Game();
	            game.setGameId(cursorGame.getInt(cursorGame.getColumnIndex(GameContract.KEY_GAMES_GAME_ID)));
	            game.setCurrentScore(cursorGame.getInt(cursorGame.getColumnIndex(GameContract.KEY_GAMES_CURRENT_SCORE)));
			} while (cursorGame.moveToNext());
        }
		
		return game;
	}
	
	
	public void addGame(Context context, Game game){
		SQLiteDatabase db =  DataBaseHandler.getInstance(context).getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(GameContract.KEY_GAMES_GAME_ID, game.getGameId()); 
	    values.put(GameContract.KEY_GAMES_CURRENT_SCORE, game.getCurrentScore()); 
	    
	    // Inserting Row
	    db.insert(GameContract.TABLE_GAMES, null, values);
	    // Closing database connection
	    db.close(); 
	}
	
	public void deleteGame(Context context, Game game) {
	    SQLiteDatabase db = DataBaseHandler.getInstance(context).getWritableDatabase();
	    db.delete(GameContract.TABLE_GAMES, GameContract.KEY_GAMES_GAME_ID + " = ?",
	            new String[] { String.valueOf(game.getGameId()) });
	    db.close();
	}
	
	public ArrayList<GameRectangle> getGameRectangle(Context context, Game game){
		ArrayList<GameRectangle> gameRectangles = null;
		
		//Get Data Base
		SQLiteDatabase db = DataBaseHandler.getInstance(context).getReadableDatabase();
		
		Cursor cursorRectangles = db.rawQuery("SELECT * FROM "+GameContract.TABLE_RECTANGLES+" WHERE "+GameContract.KEY_RECTANGLES_GAME_ID+" = "+Integer.toString(game.getGameId()),null);
		
		if (cursorRectangles.moveToFirst()) {
			gameRectangles = new ArrayList<GameRectangle>();
			do {
	            int row = cursorRectangles.getInt(cursorRectangles.getColumnIndex(GameContract.KEY_RECTANGLES_RECTANGLE_ROW));
	            int column = cursorRectangles.getInt(cursorRectangles.getColumnIndex(GameContract.KEY_RECTANGLES_RECTANGLE_COLUMN));
	            int shapeType = cursorRectangles.getInt(cursorRectangles.getColumnIndex(GameContract.KEY_RECTANGLES_SHAPE_ID));
	            GameRectangle gameRectangle = new GameRectangle(row, column);
	            if(shapeType >= 0){
	            	GameShape gameShape = new GameShape(shapeType);
	            	gameRectangle.setShape(gameShape);
	            }
	            gameRectangles.add(gameRectangle);
			} while (cursorRectangles.moveToNext());
        }
		
		return gameRectangles;
	}
	
	public void addGameRectangles(Context context, Game game){
		ArrayList<GameRectangle> gameRectangles = game.getBoard();
		for(GameRectangle gameRectangle : gameRectangles){
			
		}
		
	}
}
