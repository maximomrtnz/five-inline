package com.example.gamealgorithms;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;
import org.andengine.util.math.MathUtils;

import android.util.Log;

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.entities.GameShape;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.AudioManager;
import com.example.managers.ResourcesManager;

public class ShapeABM {
	
	/*
	 * Algorithm which contains the functionality that we need to remove shapes from board
	 */
	
	public static void removeZombies(final GameScene gameScene, ArrayList<GameRectangle> gameRectanglesToClear,int multiplyPointBy){
		for(GameRectangle gameRectangleToDelete : gameRectanglesToClear){
			gameRectangleToDelete.setShape(null);
			final Rectangle rectangle = SearchAlgorithms.getRectangleFromGameRectangle(gameScene, gameRectangleToDelete);
			final Sprite shape = SearchAlgorithms.getShapeByRectangle(gameScene, rectangle);
			

			//Add number to show the points that player wins
			Text text = gameScene.drawPointText(rectangle.getX(),rectangle.getY(), "+1",ResourcesManager.getInstance().getFreckleFaceRegular());
			
			ScaleModifier scaleModifier = new ScaleModifier(2, 2f, 0f){
				@Override
				protected void onModifierFinished(final IEntity pItem) {
					// TODO Auto-generated method stub
					super.onModifierFinished(pItem);
					ResourcesManager.getInstance().getEngine()
					.runOnUpdateThread(new Runnable() {
						@Override
						public void run() {
							//Delete Text
							pItem.detachSelf();
						}
					});
				}
			};
			
			text.registerEntityModifier(scaleModifier);
			
			AlphaModifier alphaModifier = new AlphaModifier(1, 1f, 0f) {
				@Override
				protected void onModifierFinished(final IEntity pItem) {
					super.onModifierFinished(pItem);
					// Your action after finishing modifier
					ResourcesManager.getInstance().getEngine()
							.runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									
									//Delete Shape
									gameScene.unregisterTouchArea(pItem);
									pItem.detachSelf();
									
									//Restore Rectangle Properties
									gameScene.registerTouchArea(rectangle);
									rectangle.setTag(1);
								}
							});
				}
			};

			shape.registerEntityModifier(alphaModifier);
			
		}
		
		if(gameRectanglesToClear.size() != 0){
			
			Game game = ResourcesManager.getInstance().getActivity().getGame();
			
			//Add Score To Total Score
			game.setCurrentScore((game.getCurrentScore()+gameRectanglesToClear.size())*multiplyPointBy);
			
			//Play Remove Zombies Sound
			AudioManager.getInstance().playSound(AudioManager.SOUND_REMOVE_PLAYER);
			
			//Show number with total zombies removed
			final Text text = gameScene.drawPointText(ResourcesManager.getInstance().getCamera().getCenterX()+200,ResourcesManager.getInstance().getCamera().getCenterY()+300, "x"+gameRectanglesToClear.size(),ResourcesManager.getInstance().getFreckleFaceRegular());
			
			text.setRotation(-15f);
			
			text.setColor(Color.GREEN);
			
			FadeOutModifier fadeOutModifier = new FadeOutModifier(4f){
				@Override
				protected void onModifierFinished(final IEntity pItem) {
					super.onModifierFinished(pItem);
					// Your action after finishing modifier
					ResourcesManager.getInstance().getEngine()
							.runOnUpdateThread(new Runnable() {
								@Override
								public void run() {
									
									//Delete Text
									
									pItem.detachSelf();
								}
							});
				}
			};
			
			text.registerEntityModifier(fadeOutModifier);
		}
	}
	
	/*
	 * Algorithm which contains the functionality that we need to add on board
	 */
	
	public static GameRectangle add(final GameScene gameScene){
		GameRectangle gameRectangle= SearchAlgorithms.getEmptyGameRectangle(gameScene);
		if(gameRectangle != null){
			
			final Rectangle rectangle = SearchAlgorithms.getRectangleFromGameRectangle(gameScene,gameRectangle);
			int type = MathUtils.random(1, 5);
			Sprite shape = (Sprite) gameScene.drawShape(rectangle.getX(),rectangle.getY(), rectangle.getWidth(), rectangle.getWidth(), type);
			gameRectangle.setShape(new GameShape(type));
			
			//Storage rectangle because We need it then
			shape.setUserData(rectangle);
			
			ResourcesManager.getInstance().getEngine().runOnUpdateThread(new Runnable(){
                @Override
                public void run(){
                	try{
                        gameScene.unregisterTouchArea(rectangle);
                	}catch(Exception exception){
                		Log.d("Error On Remove Player", exception.getMessage());
                	}
                }});
		}
		return gameRectangle;
	}
	
	/*
	 * Algorithm which contains the functionality that
	 * we need to add one or more than one shape on board
	 */
	
	public static void add(GameScene gameScene, int quantity){
		//Steps to add new zombies after remove
		
		//Play Appear Shape Sound
		AudioManager.getInstance().playSound(AudioManager.SOUND_APPEAR_PLAYER);
		
		//Create an algorithm instance to check five
		
		CheckSameShapeAlgorithm algorithm = new CheckSameShapeFive();
		
		int i = 0;
		GameRectangle addedGameRectangle = null;
		//When you add a new shape you need to check five shape joined
		do{ 
			//Add a new shape on the board an get it
			addedGameRectangle= ShapeABM.add(gameScene);
			
			//Check if null and then check five
			if(addedGameRectangle != null)
				algorithm.checkSameShape(gameScene, addedGameRectangle.getRow(),  addedGameRectangle.getColumn(), addedGameRectangle.getShape().getShapeType());
			//If the new shape didn't be inserted on the board we will get a new value
			//This situation is because the board is full so we finish the process of add shape
			i++;
		}while(addedGameRectangle != null && i < quantity);
	
	}
}
