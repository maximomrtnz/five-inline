package com.example.entities;

public class GameRectangle {
	
	private int row;
	private int column;
	
	private GameShape shape;
	
	public GameRectangle(int row,int column){
		this.row = row;
		this.column = column;
	}
	
	/*
	 * GETTERS
	 */
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}

	
	public GameShape getShape() {
		return shape;
	}
	/*
	 * SETTERS
	 */
	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setShape(GameShape shape) {
		this.shape = shape;
	}
	
	
	
}
