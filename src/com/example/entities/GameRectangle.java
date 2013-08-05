package com.example.entities;

import java.util.ArrayList;

import com.example.fivecircles.utilities.MD5Manager;

public class GameRectangle {
	
	private int row;
	private int column;
	private String md5Hash;
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
	
	public String getMD5Hash(){
		return this.md5Hash;
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
	
	public void setMD5Shape(String md5Hash){
		this.md5Hash = md5Hash;
	}
	
	/*
	 * MD5 Hash
	 */
	
	//Method which generate a MD5 hash using object information
	public String generateMD5Hash() throws Exception{
		ArrayList<String>strings = new ArrayList<String>();
		int shapeId = -1;
		if(this.shape != null)
			shapeId = this.shape.getShapeType();
		
		strings.add(Integer.toString(this.row));
		strings.add(Integer.toString(this.column));
		strings.add(Integer.toString(shapeId));
		
		return  MD5Manager.getInstance().generateMD5Hash(strings);
	}
	
}
