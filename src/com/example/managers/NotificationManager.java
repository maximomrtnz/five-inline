package com.example.managers;

import com.example.fivecircles.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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

}
