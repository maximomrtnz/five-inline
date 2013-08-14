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

import com.example.entities.Game;
import com.example.entities.GameRectangle;
import com.example.fivecircles.gamescenes.GameScene;
import com.example.managers.AudioManager;
import com.example.managers.ResourcesManager;

public class ZombiesRemover {
	
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
	
}
