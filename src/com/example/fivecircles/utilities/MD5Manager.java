package com.example.fivecircles.utilities;

import java.security.MessageDigest;
import java.util.ArrayList;

import com.example.entities.Game;
import com.example.entities.GameRectangle;

public class MD5Manager {
	
	private static MD5Manager instance;

	public static synchronized MD5Manager getInstance(){
		if(instance == null){
			instance = new MD5Manager();
		}
		return instance;
	}
	
	private MD5Manager(){}
	
	private static String keyString = "827ccb0eea8a706c4c34a16891f84e7b";
	
	public String generateMD5Hash(ArrayList<String> strings) throws Exception{

		//Concatenate string to keystring
		strings.add(keyString);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(strings.toString().getBytes());
        byte byteData[] = md.digest();
 
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
          sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
       
        return sb.toString();
	}
	
	public boolean checkMD5Hash(String stringToCheck, ArrayList<String> stringsInsideHash) throws Exception{
		return stringToCheck.equals(generateMD5Hash(stringsInsideHash));
	}
	
	public boolean checkGameMD5Hash(Game game) throws Exception{
		String storedMD5Hash = game.getMD5Hash();
		return storedMD5Hash.equals(game.generateMD5Hash());
	}
	
	public boolean checkGameBoardMD5Hash(Game game) throws Exception{
		boolean check = true;
		int i = 0;
		ArrayList<GameRectangle> gameRectangles = game.getBoard();
		while(i<gameRectangles.size() && check){
			GameRectangle gameRectangle = gameRectangles.get(i);
			String storedMD5Hash = gameRectangle.getMD5Hash();
			check = storedMD5Hash.equals(gameRectangle.generateMD5Hash());
			i++;
		}
		return check;
	}
}
