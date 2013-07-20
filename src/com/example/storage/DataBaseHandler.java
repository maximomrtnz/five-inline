package com.example.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper{
	
	private static DataBaseHandler instance;

	public static synchronized DataBaseHandler getInstance(Context context){
		if(instance == null){
			instance = new DataBaseHandler(context);
		}
		return instance;
	}
	
	
	// Database Version
	public static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "GameDataBase";

			
	
	public DataBaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
			
		//Create a Games Table
		String CREATE_GAMES_TABLE = "CREATE TABLE " + GameContract.TABLE_GAMES + 
				"(" + GameContract.KEY_GAMES_GAME_ID + " INTEGER PRIMARY KEY,"+ 
				GameContract.KEY_GAMES_CURRENT_SCORE + " INTEGER );";
		db.execSQL(CREATE_GAMES_TABLE);
		
		//Create a Rectangles Table
		String CREATE_RECTANGLES_TABLE = "CREATE TABLE " + GameContract.TABLE_RECTANGLES + 
				"(" + GameContract.KEY_RECTANGLES_GAME_ID + " INTEGER,"+ 
					GameContract.KEY_RECTANGLES_RECTANGLE_ROW + " INTEGER,"+
					GameContract.KEY_RECTANGLES_RECTANGLE_COLUMN + " INTEGER,"+
					GameContract.KEY_RECTANGLES_SHAPE_ID + " INTEGER,"+ 
					"PRIMARY KEY ("+GameContract.KEY_RECTANGLES_RECTANGLE_ROW+","+GameContract.KEY_RECTANGLES_RECTANGLE_COLUMN+"),"+
					" FOREIGN KEY ("+GameContract.KEY_RECTANGLES_GAME_ID+") REFERENCES "+GameContract.TABLE_GAMES+" ("+GameContract.KEY_GAMES_GAME_ID+"));";
		db.execSQL(CREATE_RECTANGLES_TABLE);
				
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		// Drop older tables if existed
		db.execSQL("DROP TABLE IF EXISTS " + GameContract.TABLE_GAMES);
		db.execSQL("DROP TABLE IF EXISTS " + GameContract.TABLE_RECTANGLES);
				
		// Create tables again
		onCreate(db);
	}
	
}
