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
	public static boolean firstTime; 
	public static boolean alreadyAdded; 
	MyCustomView tempView = null;
	Button buttonTransmit;
		
	public void plotPoint(){
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		 
		final RelativeLayout mainLayout = (RelativeLayout)findViewById(R.layout.relLayout); 
		//Line added 
		context = this; 
		
	
		buttonTransmit = (Button) findViewById(R.id.buttonTransmit);

		Button buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonBack.setOnClickListener(new View.OnClickListener()  {
			public void onClick(View v) {
				startActivity(new Intent(Map.this, indoortracking.class));
				}
		});
		
		ArrayList<Integer> points = new ArrayList<Integer>();
		
		Button buttonTransmit = (Button) findViewById(R.id.buttonTransmit);
		buttonTransmit.setOnClickListener(new View.OnClickListener()  {
			public void onClick(View v) {
				
				//print screen size
				
				HttpClient client = new DefaultHttpClient();

				List<NameValuePair> pairsN = new ArrayList<NameValuePair>();
				
				HttpPost postN = new HttpPost(address); 


				Random gen = new Random();
				Integer x = gen.nextInt(480);
				Integer y = gen.nextInt(724);
				//remove previous
				if(tempView != null){
					mainLayout.removeView(tempView); 
				}



				Display display = getWindowManager().getDefaultDisplay(); 
				
				int width = display.getWidth();
				int height = display.getHeight();
				System.out.println("BOTTOM " + mainLayout.getBottom());
				System.out.println("TOP " + mainLayout.getTop());
				System.out.println("LEFT " + mainLayout.getLeft());
				System.out.println("RIGHT " + mainLayout.getRight());
				
				
				tempView = new MyCustomView(context, x, y); 
				layoutParam = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				layoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);



				mainLayout.addView(tempView,layoutParam); 
				ImageView img = (ImageView)findViewById(R.id.sixthFloorMap);
				int x_image = img.getWidth(); 
				int y_image = img.getHeight(); 
				System.out.println("x image "+ x_image + "y image "+ y_image);

				Integer image_width = 480;
				Integer image_height = 697;
				
				float x_scale = 1279 / image_width;
				float y_scale = 1795 / image_height;
				System.out.println("x orig "+ x + "y orig "+ y);
				//convert to OpenLayers coordinate system.
				Integer x_out = (int) (x * x_scale - 1279/2);
				Integer y_out = (int)(-1*y * y_scale + 1795/2);
				System.out.println("x "+ x_out + "y "+ y_out);
				pairsN.add(new BasicNameValuePair("x", x_out.toString()));
				pairsN.add(new BasicNameValuePair("y", y_out.toString()));
				pairsN.add(new BasicNameValuePair("device_id", "Merwan's"));
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
