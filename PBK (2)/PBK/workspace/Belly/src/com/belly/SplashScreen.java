package com.belly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.zxing.client.android.CaptureActivity;


public class SplashScreen extends Activity {

	String URL="http://pointsbykilo.azurewebsites.net/api/UserApi/VerifyUser/?QRCode=";
	String QRCode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
	}
		//function name is scan now ..so thats why it runs when u press the button
		public void scanNow(View view)
		{
			///ValidateQRCodes("Test1");
		//	Intent intent = new Intent(view.getContext(),Rewards.class);
			//startActivityForResult(intent, 0);
			//READING qr CODE UNCOMMENT LAST TWO LINES
			Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
			startActivityForResult(intent,0);
	        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

		}
		
		public void onActivityResult(int requestCode, int resultCode, Intent intent) 
		{
		    if (requestCode == 0) 
		    {
		        if (resultCode == RESULT_OK)
		        {
		            String contents = intent.getStringExtra("SCAN_RESULT");
		            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		            Log.i("xZing", "contents: "+contents+" format: "+format);
		            ValidateQRCodes(contents);
		            	//here we should be validating if the QR code is fit or not.	            
		        } 
		        else if (resultCode == RESULT_CANCELED)
		        {
		            // Handle cancel
		            Log.i("xZing", "Cancelled");
		        }
		    }
					}
		
		
	public void ValidateQRCodes(String string)
		{
			new AuthenticateQRCode().execute(string);
		}
	
	class AuthenticateQRCode extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... params) {
		
		QRCode=params[0];
		URL=URL.concat(QRCode);
        String result= GloballyUsedFunctions.MakeandReceiveHTTPResponse(URL);
        return result;
    }
    
    @Override
    protected void onPreExecute() {
       //show progress bar future  task
    }

    protected void onPostExecute(String result) {
        // result is the value returned from doInBackground
    	try {
			JSONObject jsonObject=new JSONObject(result);
			String ValidQR = jsonObject.getString("Valid");
			int userId = jsonObject.getInt("UserID");
			if (ValidQR.toLowerCase().contentEquals("true"))
			{
				MovetoRewardsPage(userId);	
			}
			else
			{
				//the sequence is invalid we will show the Registration screen
				MovetoRegistrationPage();
				//and also pass the QR code in the screen
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
	void MovetoRewardsPage(int userId)
	{
		Context view=findViewById(android.R.id.content).getRootView().getContext();
		Intent intent = new Intent(view,Rewards.class);
		String str = QRCode;
		intent.putExtra("userId" , userId);
		startActivityForResult(intent, 2);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		 
	}

	void MovetoRegistrationPage()
	{
	//	Context view=findViewById(android.R.id.content).getRootView().getContext();
		//Intent intent = new Intent(view,Rewards.class);
		//startActivityForResult(intent, 2);
		
		Context view=findViewById(android.R.id.content).getRootView().getContext();
		Intent i = new Intent(view, Register.class);
		String str = QRCode;
		i.putExtra("qrCode" , str);
		startActivityForResult(i,0);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

	}
	
}
