package com.example.managers;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.util.adt.color.Color;

import com.example.fivecircles.gamescenes.BaseScene;

import android.content.Context;
import android.widget.Toast;

public class NotificationManager {

	 private static final NotificationManager instance = new NotificationManager();
	 
	 public static NotificationManager getInstance(){
	        return instance;
	 }
	 
	 private NotificationManager(){}
	 
	 public void showToastNotification(final int messageId, final Context context){
		 
		 ResourcesManager.getInstance().getActivity().runOnUiThread(new Runnable() {

             @Override
             public void run() {
                 // TODO Auto-generated method stub
            	 String message = context.getResources().getString(messageId);
        		 int duration = Toast.LENGTH_SHORT;
        		 Toast toast = Toast.makeText(context, message, duration);
        		 toast.show();
             }
         });

	 }
	 
	 public void showToastNotification(final String message, final Context context){
		 ResourcesManager.getInstance().getActivity().runOnUiThread(new Runnable() {

             @Override
             public void run() {
                 // TODO Auto-generated method stub
        		 int duration = Toast.LENGTH_SHORT;
        		 Toast toast = Toast.makeText(context, message, duration);
        		 toast.show();
             }
         });
	 }
	 
	 public void showToastNotificationFromActivity(final int messageId, final Context context){
		 String message = context.getResources().getString(messageId);
		 int duration = Toast.LENGTH_SHORT;
		 Toast toast = Toast.makeText(context, message, duration);
		 toast.show();
	 }
	 
	 public void showGameMessage(float posX, float posY,BaseScene baseScene, int messageId, Font font, Context context,float duration){
		 String message = context.getResources().getString(messageId);
		 Text text = new Text(posX, posY, font, message, ResourcesManager.getInstance().getVbom());
		 baseScene.attachChild(text);
		 FadeOutModifier fadeOutModifier = new FadeOutModifier(duration){
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
