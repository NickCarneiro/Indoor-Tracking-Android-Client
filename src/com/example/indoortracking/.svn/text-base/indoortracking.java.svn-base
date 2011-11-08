package com.example.indoortracking;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class indoortracking extends Activity {
	/** Called when the activity is first created. */

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.useragreement);
		final Button buttonProceed = (Button) findViewById(R.id.buttonProceed);
		buttonProceed.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//setContentView(R.layout.map);
				startActivity(new Intent(indoortracking.this, Map.class));
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