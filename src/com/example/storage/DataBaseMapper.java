package com.example.storage;

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
	/*
	
	public  getWidgetInitData(String widgetId, Context context) throws Exception{
	 	
		
		
		//Get Yawa DataBase
		SQLiteDatabase db = DataBaseHandler.getInstance(context).getReadableDatabase();
		
		Cursor cursorWidgets = db.query(GameContract.TABLE_WIDGETS, this.widgetsColumn, GameContract.KEY_WIDGETS_ID + " = " + widgetId , null, null, null, null);
		
		if(cursorWidgets==null){
			return null;
		}else if(!cursorWidgets.moveToFirst()){
			cursorWidgets.close();
			return null;
		}
		
		Widget widget = new Widget();
		
		widget.setWidgetID(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_ID)));
		widget.setCountryName(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_COUNTRY_NAME)));
		widget.setCityName(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_CITY_NAME)));
		widget.setTemperature(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_LAST_TEMPERATURE)));
		widget.setPressure(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_LAST_PRESURE)));
		widget.setHumidity(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_LAST_HUMEDITY)));
		widget.setHighTemperature(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_HIGH_TEMPERATURE)));
		widget.setLowTemperature(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_LOW_TEMPERATURE)));
		widget.setScale(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_SCALE_DATA)));
		widget.setWoeid(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_WOEID)));
		widget.setSkyConditions(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_LAST_SKY_CONDITIONS)));
		widget.setUpdateDateTime(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_LAST_UPDATE_DATETIME)));
		widget.setWindDegree(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_WIND_DEGREE)));
		widget.setWindVelocity(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_WIND_VELOCITY)));
		widget.setYahooWeatherImages(cursorWidgets.getString(cursorWidgets.getColumnIndex(GameContract.KEY_WIDGETS_YAHOO_IMAGE)));
		
		cursorWidgets.close();
		// return Widget Init Data
		return widget;
	}
	
	
	public void addWidget(Widget widget, Context context) throws Exception{
		
	
				
		//Get Yawa DataBase
		SQLiteDatabase db = DataBaseHandler.getInstance(context).getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put(GameContract.KEY_WIDGETS_ID, widget.getWidgetID()); 
		
		values.put(GameContract.KEY_WIDGETS_COUNTRY_NAME,widget.getCountryName()); 
		
		values.put(GameContract.KEY_WIDGETS_CITY_NAME,widget.getCityName()); 
		
		values.put(GameContract.KEY_WIDGETS_LAST_TEMPERATURE,widget.getTemperature()); 
		
		values.put(GameContract.KEY_WIDGETS_LAST_HUMEDITY,widget.getHumidity());
		
		values.put(GameContract.KEY_WIDGETS_LAST_PRESURE,widget.getPressure()); 
		
		values.put(GameContract.KEY_WIDGETS_LOW_TEMPERATURE,widget.getLowTemperature()); 
		
		values.put(GameContract.KEY_WIDGETS_HIGH_TEMPERATURE,widget.getHighTemperature()); 
		
		values.put(GameContract.KEY_WIDGETS_SCALE_DATA,widget.getScale()); 
		
		values.put(GameContract.KEY_WIDGETS_WOEID,widget.getWoeid()); 
		
		values.put(GameContract.KEY_WIDGETS_LAST_SKY_CONDITIONS,widget.getSkyConditions()); 
		
		values.put(GameContract.KEY_WIDGETS_LAST_UPDATE_DATETIME, widget.getUpdateDateTime());
		
		values.put(GameContract.KEY_WIDGETS_WIND_DEGREE, widget.getWindDegree());
		
		values.put(GameContract.KEY_WIDGETS_WIND_VELOCITY, widget.getWindVelocity());
		
		values.put(GameContract.KEY_WIDGETS_WIND_VELOCITY, widget.getYahooWeatherImages());
		
		// Inserting Row
		db.insert(GameContract.TABLE_WIDGETS, null, values);
	

	}
	
	
	public void updateWidget(Widget widget,Context context) throws Exception{
						
		//Get Yawa DataBase
		SQLiteDatabase db = DataBaseHandler.getInstance(context).getWritableDatabase();
				
		ContentValues values = new ContentValues();
		
		values.put(GameContract.KEY_WIDGETS_LAST_TEMPERATURE,widget.getTemperature()); 
		
		values.put(GameContract.KEY_WIDGETS_LAST_HUMEDITY,widget.getHumidity());
		
		values.put(GameContract.KEY_WIDGETS_LAST_PRESURE,widget.getPressure()); 
		
		values.put(GameContract.KEY_WIDGETS_LOW_TEMPERATURE,widget.getLowTemperature()); 
		
		values.put(GameContract.KEY_WIDGETS_HIGH_TEMPERATURE,widget.getHighTemperature()); 
		
		values.put(GameContract.KEY_WIDGETS_SCALE_DATA,widget.getScale()); 
		
		values.put(GameContract.KEY_WIDGETS_LAST_SKY_CONDITIONS,widget.getSkyConditions()); 
		
		values.put(GameContract.KEY_WIDGETS_LAST_UPDATE_DATETIME, widget.getUpdateDateTime());
		
		values.put(GameContract.KEY_WIDGETS_WIND_DEGREE, widget.getWindDegree());
		
		values.put(GameContract.KEY_WIDGETS_WIND_VELOCITY, widget.getWindVelocity());
		
		values.put(GameContract.KEY_WIDGETS_WIND_VELOCITY, widget.getYahooWeatherImages());
		
		//Actualizamos el registro en la base de datos
		db.update(GameContract.TABLE_WIDGETS, values,GameContract.KEY_WIDGETS_ID + " = ?" , new String[] { widget.getWidgetID() });
		
	
	}
	
	
	
	// Deleting single Widget
	public void deleteWidget(Widget widget, Context context) throws Exception{
							
		//Get Yawa DataBase
		SQLiteDatabase db = DataBaseHandler.getInstance(context).getWritableDatabase();
			
		db.delete(GameContract.TABLE_WIDGETS, GameContract.KEY_WIDGETS_ID + " = ?",
				new String[] { widget.getWidgetID() });
		

	}
	*/
	
}
