package com.belly;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.belly.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

public class Register extends Activity  {
	
	String QrCode;
	EditText emailText;
	EditText FirstName;
	EditText LastName;
	Button registration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		QrCode = intent.getStringExtra("qrCode");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
	registration = (Button) findViewById(R.id.register);
	registration.setVisibility(View.GONE);//hiding the button on initialization
	registration.setOnClickListener(new OnClickListener (){
		public void onClick(View v){
			ajs();
		}
	});
	//registration button functions
	InputValidation();
	}
	private void InputValidation() {
		emailText = (EditText) findViewById(R.id.email);
		FirstName=(EditText)findViewById(R.id.name);
		emailText.addTextChangedListener(new LocalTextWatcher());
	}

	//JSON FUNCTIONS
	public void ajs(){
	new JSONFeedTask()
	.execute("http://pointsbykilo.azurewebsites.net/api/PointsApi/GetStoreRewardsInformation/?storeId=1");
}
	private class JSONFeedTask extends AsyncTask<String, Void, String> {
	protected String doInBackground(String... urls) {
		try {
			return SendJson(FirstName.getText().toString(),emailText.getText().toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
}

	   protected void onPostExecute(String result) {
	        //now we will have the logic of whether the email was valid or not
		   //or duplicate and what we should do.
		   //SACHIN CAN ADD SOME LOGIC HERE
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		String UserInserted = null;
		int UserID = 0;
		try {
			UserInserted = jsonObject.getString("Success");
			UserID=jsonObject.getInt("UserId");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (UserInserted.toLowerCase().contentEquals("true"))
		{
			MovetoRewardsPage(UserID);
		}
		else
		{
			//the sequence is invalid we will show the Registration screen
			MovetoSplashScreen();
			//and also pass the QR code in the screen
		}
		
}
}
	String SendJson(String name, String email) throws ClientProtocolException, IOException{
	 HttpPost request = new HttpPost("http://pointsbykilo.azurewebsites.net/api/PointsApi/UserApi/InsertUser");
	 String myString = null;
	 try {
	myString=new JSONStringer()
		 .object()
		 .key("Email")
		 .value(email)
		 .key("QRCode")
		 .value(QrCode)
		 .endObject()
		 .toString();
	} catch (JSONException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 
	 StringEntity entity = null;
	try {
		entity = new StringEntity(myString,"UTF-8");
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			entity.setContentType("application/json;charset=UTF-8");
	       entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
	       request.setEntity(entity); 
	                      // Send request to WCF service 
	                      DefaultHttpClient httpClient = new DefaultHttpClient();
	                 //   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	                   // StrictMode.setThreadPolicy(policy); 
	 HttpResponse response = httpClient.execute(request); 
	   int resCode = response.getStatusLine().getStatusCode();
	   if (resCode==200)
	   {
		   String result=GloballyUsedFunctions.ReadHTTPResponse(response);
		   return result;
	   }
	   else return "fail";
	}
	//END OF JSON FUNCTIONS
	
	//NAVIGATION FUNCTIONS
	void MovetoRewardsPage(int UserId)
{
	Context view=findViewById(android.R.id.content).getRootView().getContext();
	Intent intent = new Intent(view,Rewards.class);
	int userId = UserId;
	intent.putExtra("userId" , userId);
	startActivityForResult(intent, 2);
}
	void MovetoSplashScreen()
{
	Context view=findViewById(android.R.id.content).getRootView().getContext();
	Intent intent = new Intent(view,SplashScreen.class);
	startActivityForResult(intent, 1);
}
	//END OF NAVIGATION FUNCTIONS
	
	//REGISTRATION FUNCTIONS///////////////////////
	void updateSubmitButtonState() {
	    boolean enabled = checkEditEmail(emailText)&& checkEditText(FirstName);
	    registration.setEnabled(enabled);
        if (enabled)registration.setVisibility(View.VISIBLE);

	}
	private boolean checkEditText(EditText edit) {
		return true;
	   // return Integer.getInteger(edit.getText().toString()) != null;
	}
	
	private boolean checkEditEmail(EditText email){
		  return true;
		//return Integer.getInteger(email.getText().toString()) != null;
	}
	
	private class LocalTextWatcher implements TextWatcher {
	    public void afterTextChanged(Editable s) {
	    	updateSubmitButtonState();
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	    //i dont think we need to use any
	    }

	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	    //dont think we have to use these
	    }
	}
//END OF REGISTRATION FUNCTIONS/////////////////////////////////////////////
}//do not delete this
	
