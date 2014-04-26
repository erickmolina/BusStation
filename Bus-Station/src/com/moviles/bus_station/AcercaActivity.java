package com.moviles.bus_station;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class AcercaActivity extends Activity {
	
	private TextView texv;
	private ImageView imgv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acercade);
		ActionBar actionBar = getActionBar();
				
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		texv = (TextView)findViewById(R.id.txt_welcom);
		imgv = (ImageView)findViewById(R.id.imageView1);
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            Intent upIntent = getIntent();
	            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
	                TaskStackBuilder.from(this).addNextIntent(upIntent).startActivities();
	                finish();
	            } 
	            else {
	                NavUtils.navigateUpTo(this, upIntent);
	            }
	            return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}