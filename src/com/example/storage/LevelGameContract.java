package com.example.storage;

import android.provider.BaseColumns;

public class LevelGameContract implements BaseColumns{
	
	/*
	 * This class represent a level contract
	 */
	
	public static final String LEVEL_GAME_TAG_ENTITY = "entity";
	public static final String LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_X = "x";
	public static final String LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_Y = "y";
	public static final String LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_WIDTH = "width";
	public static final String LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_HEIGHT = "height";
	public static final String LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_ROW = "row";
	public static final String LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_COLUMN = "column";
	public static final String LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_TYPE = "type";

	public static final Object LEVEL_GAME_TAG_ENTITY_ATTRIBUTE_TYPE_VALUE_SQUARE = "rectangle";

	public static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";
}
