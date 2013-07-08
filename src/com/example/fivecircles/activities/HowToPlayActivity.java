package com.example.fivecircles.activities;

import com.example.fivecircles.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class HowToPlayActivity extends Activity {
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	  
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.howtoplay);
	  
		//Get custom font
		Typeface font = Typeface.createFromAsset(getAssets(), "font/Inconsolata.otf");  
		
		//This is one way to set your custom fonts
		
		TextView firstTitle = (TextView) findViewById(R.id.firstTitle);  
		firstTitle.setTypeface(font);  
		
		TextView secondTitle = (TextView) findViewById(R.id.secondTitle);  
		secondTitle.setTypeface(font);  
		
		TextView thirdTitle = (TextView) findViewById(R.id.thirdTitle);  
		thirdTitle.setTypeface(font);  
		
		TextView fourTitle = (TextView) findViewById(R.id.fourthTitle);  
		fourTitle.setTypeface(font);  
		
		
		TextView firstParagraph = (TextView) findViewById(R.id.firstParagraph);  
		firstParagraph.setTypeface(font);
		
		TextView secondParagraph = (TextView) findViewById(R.id.secondParagraph);  
		secondParagraph.setTypeface(font);
		
		TextView thirParagraph = (TextView) findViewById(R.id.thirdParagraph);
		thirParagraph.setTypeface(font);
		
		TextView fourParagraph = (TextView) findViewById(R.id.fourthParagraph);  
		fourParagraph.setTypeface(font);  
		
		
	 }
}
