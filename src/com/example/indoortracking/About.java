package com.example.indoortracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class About extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		Button buttonBackMapAbout = (Button) findViewById(R.id.buttonBackMapAbout);
		buttonBackMapAbout.setOnClickListener(new View.OnClickListener()  { 
			public void onClick(View v) {
				startActivity(new Intent(About.this, Map.class));               
			}
		});

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuItemMap: startActivity(new Intent(this, Map.class));  
		break;
		case R.id.menuItemHelp:startActivity(new Intent(this, Help.class));    
		break;
		case R.id.menuItemAbout: startActivity(new Intent(this, About.class)); 
		break;
		}
		return true;
	}
}
