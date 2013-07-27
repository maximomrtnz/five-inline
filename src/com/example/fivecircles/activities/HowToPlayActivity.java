package com.example.fivecircles.activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.fivecircles.R;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class HowToPlayActivity extends SherlockFragmentActivity {
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	  
		super.onCreate(savedInstanceState);
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.howtoplay);
		
		//Enable Action Bar
		
		 ActionBar actionBar = getSupportActionBar();
		 
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_HOME_AS_UP);
	
		//Get custom font
		Typeface font = Typeface.createFromAsset(getAssets(), "font/Quicksand-Regular.otf");  
		
		Typeface fontBold = Typeface.createFromAsset(getAssets(), "font/Quicksand-Bold.otf");  
		
		//This is one way to set your custom fonts
		
		TextView firstTitle = (TextView) findViewById(R.id.firstTitle);  
		firstTitle.setTypeface(fontBold);  
		
		TextView secondTitle = (TextView) findViewById(R.id.secondTitle);  
		secondTitle.setTypeface(fontBold);  
		
		TextView thirdTitle = (TextView) findViewById(R.id.thirdTitle);  
		thirdTitle.setTypeface(fontBold);  
		
		TextView fourTitle = (TextView) findViewById(R.id.fourthTitle);  
		fourTitle.setTypeface(fontBold);  
		
		
		TextView firstParagraph = (TextView) findViewById(R.id.firstParagraph);  
		firstParagraph.setTypeface(font);
		
		TextView secondParagraph = (TextView) findViewById(R.id.secondParagraph);  
		secondParagraph.setTypeface(font);
		
		TextView thirParagraph = (TextView) findViewById(R.id.thirdParagraph);
		thirParagraph.setTypeface(font);
		
		TextView fourParagraph = (TextView) findViewById(R.id.fourthParagraph);  
		fourParagraph.setTypeface(font);  
		
		
	 }
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);        
            finish();
            return true;
        }
        return true;
    }
	
}
