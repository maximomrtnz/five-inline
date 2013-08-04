package com.example.storage;

import android.provider.BaseColumns;

public class GameContract implements BaseColumns {
	

	/*
	 * Table which storages information about the game
	 */
	
	// Game Table Name
	public static final String TABLE_GAMES = "Games";
	// Rectangle Table Columns Names
	public static final String KEY_GAMES_GAME_ID = "Game_Id";
	public static final String KEY_GAMES_CURRENT_SCORE = "Current_Score";
	public static final String KEY_GAMES_MD5_HASH = "MD5_Hash";
	
	
	/*
	 * Table which storages information about board's rectangles
	 */
	
	// Rectangle Table Name
	public static final String TABLE_RECTANGLES = "Rectangles";		

	// Rectangle Table Columns Names
	public static final String KEY_RECTANGLES_GAME_ID = "Game_Id";
	public static final String KEY_RECTANGLES_RECTANGLE_ROW = "Row";
	public static final String KEY_RECTANGLES_RECTANGLE_COLUMN = "Column";
	public static final String KEY_RECTANGLES_SHAPE_ID = "Shape_Id";
	public static final String KEY_RECTANGLES_MD5_HASH = "MD5_HASH";
	
	
}
