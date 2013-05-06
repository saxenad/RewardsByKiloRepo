package com.belly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.string;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class GloballyUsedFunctions {

	
	public static String MakeandReceiveHTTPResponse(String Url)
	{
		StringBuilder stringBuilder = new StringBuilder();
		String Result = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(Url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				
				Result=ReadHTTPResponse(response);
			} else {
				Log.d("JSON", "Failed to download file");
			}
		} catch (Exception e) {
			Log.d("readJSONFeed", e.getLocalizedMessage());
		}
		return Result;
	}

	
	public static  String ReadHTTPResponse(HttpResponse response) throws IllegalStateException, IOException
	{
		StringBuilder stringBuilder = new StringBuilder();
		HttpEntity entity = response.getEntity();
		InputStream inputStream = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
	}
		inputStream.close();
		return stringBuilder.toString();
}


	@SuppressWarnings("deprecation")
	public static BitmapDrawable writeOnDrawable(Resources drawableObject, int id,String args){

        Bitmap bm = BitmapFactory.decodeResource(drawableObject,id).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint(); 
        paint.setStyle(Style.FILL);  
        paint.setColor(Color.BLACK); 
        paint.setTextSize(20); 

        Canvas canvas = new Canvas(bm);
        canvas.drawText(( String.valueOf(args)), 0, bm.getHeight()/2, paint);

        return new BitmapDrawable(drawableObject,bm);
    }

	public static BitmapDrawable writeOnButton(Resources resource,Bitmap drawableObject,int text){

     //   Bitmap bm = BitmapFactory.decodeResource(drawableObject,id).copy(Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint(); 
      paint.setStyle(Style.FILL);  
       paint.setColor(Color.BLACK); 
       paint.setTextSize(20); 

        Canvas canvas = new Canvas(drawableObject);
        canvas.drawText(( String.valueOf(text)), 0, drawableObject.getHeight()/2, paint);

        return new BitmapDrawable(resource,drawableObject);
    }
	
	
		



}