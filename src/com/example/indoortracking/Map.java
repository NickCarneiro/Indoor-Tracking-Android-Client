package com.example.indoortracking;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class Map extends Activity {
	public static final String address = "http://trillworks.com:3010/coordinates"; 
	public static RelativeLayout mainLayout; 
	public static Context context; 
	public static RelativeLayout.LayoutParams layoutParam; 
	public static String device_id;
	MyCustomView tempView = null;
	Button buttonTransmit;
		
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		Random gen = new Random();
		device_id = "device " + gen.nextInt(10000);
		final RelativeLayout mainLayout = (RelativeLayout)findViewById(R.layout.relLayout);
		context = this; 
		
	
		buttonTransmit = (Button) findViewById(R.id.buttonTransmit);

		Button buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonBack.setOnClickListener(new View.OnClickListener()  {
			public void onClick(View v) {
				startActivity(new Intent(Map.this, indoortracking.class));
				}
		});
		
		
		Button buttonTransmit = (Button) findViewById(R.id.buttonTransmit);
		buttonTransmit.setOnClickListener(new View.OnClickListener()  {
			public void onClick(View v) {
				
				//print screen size
				
				HttpClient client = new DefaultHttpClient();

				List<NameValuePair> pairsN = new ArrayList<NameValuePair>();
				
				HttpPost postN = new HttpPost(address); 


				Random gen = new Random();
				//dimensions on map image on android
				
				//black space above map image
				int y_padding_top = 25;
				int y_padding_bottom = 25;
				int map_width = 480;
				int map_height = 724 - (y_padding_top + y_padding_bottom);
				
				//dimensions of map image in web app
				int web_width = 1279;
				int web_height = 1795;
				Integer x_input = gen.nextInt(map_width);
				Integer y_input = gen.nextInt(map_height);
				
				float x_scale = (float) web_width / map_width;
				float y_scale = (float) web_height / map_height;
				//x has no padding on either side.
				//adjust for the tip of the marker which is at the bottom middle. The marker should be scaled down
				//at the same amount as the map. I'm fudging these a little bit because the scaling is off.
				Integer x_adjusted = x_input - 16;
				Integer y_adjusted = y_padding_top + y_input - 36;
				
				//remove previous
				if(tempView != null){
					mainLayout.removeView(tempView); 
				}
				
				//plot marker
				tempView = new MyCustomView(context, x_adjusted, y_adjusted); 
				
				layoutParam = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				layoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				mainLayout.addView(tempView,layoutParam); 
				
				//calculate position for web app (Openlayers coordinate system)
				
				int x_mid = web_width / 2;
				int y_mid = web_height / 2;
				Integer x_out = (int) (x_input * x_scale);
				Integer y_out = (int)(y_input * y_scale);
				if(x_out >= x_mid){
					//past middle
					x_out = (x_out - web_width/2);
				} else{
					//less than middle
					x_out = -1 *  (web_width / 2 - x_out);
				}
				
				if(y_out >= y_mid){
					//past middle
					y_out = -1*  (y_out - (web_height/2));
				} else{
					//less than middle
					y_out = (web_height / 2 - y_out);
				}
				
				System.out.println("x "+ x_out + "y "+ y_out);
				pairsN.add(new BasicNameValuePair("x", x_out.toString()));
				pairsN.add(new BasicNameValuePair("y", y_out.toString()));
				pairsN.add(new BasicNameValuePair("device_id", device_id));
				try {
					postN.setEntity(new UrlEncodedFormEntity(pairsN));
					pairsN.clear();

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					HttpResponse response = client.execute(postN);



				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
