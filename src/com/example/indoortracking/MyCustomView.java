package com.example.indoortracking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class MyCustomView extends View{
	private Bitmap bitmap; 
	int x; 
	int y; 
	public MyCustomView(Context context, int x, int y){
		super(context);
		this.x = x; 
		this.y = y;
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marker_pixel);
	}

	protected void onDraw(Canvas pCanvas){
		pCanvas.drawBitmap(bitmap,x,y,null); 
	}
}
