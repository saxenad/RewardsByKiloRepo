package com.belly;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.Inflater;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;

public class Rewards extends Activity {

	ArrayList<String> mylist = new ArrayList<String>();

	private static final View View = null;
	private ScrollView lv;
	private Context mContext;
	CaptureActivity c = new CaptureActivity();
	Intent intent = getIntent();
	int UserID;

	public static String Rewards[];
	public static String RewardsText[];
	int count;
	int GlobalPoints;
	Drawable RedeemButton;
	Drawable DisableRedeemButton;
	Resources resourcesInstance;
	ArrayAdapter<String> arrayAdapter;
	ArrayList<RewardsInformation> map = new ArrayList<RewardsInformation>();

	public String readJSONFeed(String URL) {

		return GloballyUsedFunctions.MakeandReceiveHTTPResponse(URL);
	}

	private class JSONFeedTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			return readJSONFeed(urls[0]);
		}

		protected void onPostExecute(String result) {
			try {

				JSONArray ja = new JSONArray(result);
				for (int i = 0; i < ja.length(); i++) {
					JSONObject e = ja.getJSONObject(i);
					RewardsInformation aa = new RewardsInformation();
					aa.name = e.getString("RewardsText");
					aa.points = e.getInt("RewardsPoints");
					map.add(aa);
				}
				lv = (ScrollView) findViewById(R.id.ListViewForPoints);
				FillListViewTable();
			} catch (Exception e) {
				Log.d("JSONFeedTask", e.getLocalizedMessage());
			}
		}
	}

	private class AJSONFeedTask extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			return readJSONFeed(urls[0]);
		}

		protected void onPostExecute(String result) {
			try {

				JSONObject jsonObject = new JSONObject(result);
				String Message = jsonObject.getString("Message");
				String Points = jsonObject.getString("Points");
				GlobalPoints = Integer.parseInt(Points);
				TextView t = (TextView) findViewById(R.id.totalpoints);
				t.setText(Points);
				TextView u = (TextView) findViewById(R.id.displaymessage);
				u.setText(Message);

			} catch (Exception e) {
				Log.d("JSONFeedTask", e.getLocalizedMessage());
			}
		}
	}

	
	private class DrawBackground extends AsyncTask<TextView,BitmapDrawable,BitmapDrawable>
	{
		TextView rewards_local;
		
		protected BitmapDrawable doInBackground(TextView... args) {
			rewards_local=args[0];
			return GloballyUsedFunctions.writeOnDrawable(resourcesInstance, R.drawable.footer, "hiThereFromDhruvSaxena18");
		}
		
		protected void onPostExecute(BitmapDrawable result) {
			result.setBounds(0, 0, 50, 50);
			rewards_local.setCompoundDrawables(null, null,result, null);
		rewards_local.setText("fool");
		}



		
	}
	void FillListViewTable() {

		mContext = this.getApplicationContext();
		
		lv = (ScrollView) findViewById(R.id.ListViewForPoints);
		LinearLayout childln = (LinearLayout) findViewById(R.id.LinearLayoutForPoints);
		for (int current = 0; current < map.size(); current++) {
			Log.v("log_tag", "Current ::: " + current);
			TextView rewards_Text = (TextView)getLayoutInflater().inflate(R.layout.rewards_text_view, null);
			rewards_Text.setWidth(660);
			rewards_Text.setHeight(150);
			RelativeLayout.LayoutParams params = 
				    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				        RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.CENTER_HORIZONTAL);
			rewards_Text.setLayoutParams(params);
			rewards_Text.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			rewards_Text.setText(map.get(current).points+"points "+ map.get(current).name);
			rewards_Text.setText(Html.fromHtml("<b>" + map.get(current).name + "</b>"));
			
			new DrawBackground().execute(rewards_Text);
			//RedeemButton=GloballyUsedFunctions.writeOnButton(resourcesInstance, RedeemButton., map.get(current).points);
			RedeemButton.setBounds(0, 0, 50, 50);
		//	rewards_Text.setCompoundDrawables(null, null,RedeemButton, null);
			rewards_Text.setOnClickListener(getOnClickDoSomething(map.get(current)));
			childln.addView(rewards_Text);
			}
		lv.addView(childln);
		;
	}

	// Store ID = 1
	public void js(View view) {
		new JSONFeedTask()
				.execute("http://pointsbykilo.azurewebsites.net/api/PointsApi/GetStoreRewardsInformation/?storeId=1");
	}

	public void ajs(View view) {
		new AJSONFeedTask()
				.execute("http://pointsbykilo.azurewebsites.net/api/PointsApi/AddPointsForUser/?userId="
						+ UserID + "&storeId=1");
	}

	public void RewardsTextClicked(View v) {

	}

	View.OnClickListener getOnClickDoSomething(
			final RewardsInformation rewardInformation) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				// we will now bind the expandable window with some text
				// supplied to us by the input function
				TextView DisplayMessage = (TextView) findViewById(R.id.expandedRewardsMessage);
				DisplayMessage.setText(rewardInformation.name);
				int points = rewardInformation.points;
				if (points < GlobalPoints) {
					DisplayMessage.setCompoundDrawables(null, null,
							RedeemButton, null);
				} else {
					DisplayMessage.setCompoundDrawables(null, null,
							DisableRedeemButton, null);
				}

			}
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		UserID = i.getIntExtra("userId", 1);
		setContentView(R.layout.activity_rewards);
		RedeemButton = this.getResources().getDrawable(
			R.drawable.fourhomebutton);// FIND AN IMAGE SAYING REDEEM
		//RedeemButton.setBounds(0, 0, 50, 50);
	//	DisableRedeemButton = this.getResources().getDrawable(R.drawable.logo);// FIND AN
																				// IMAGE
																				// SAYING
																				// REDEEM
																				// WITH
																				// DIFFERNT
																				// BACKGRND
	//	DisableRedeemButton.setBounds(0, 0, 50, 50);
		resourcesInstance=this.getResources();
		
	//	DisableRedeemButton=GloballyUsedFunctions.writeOnDrawable(this.getResources(), R.drawable.footer, "FUCK YOU");
		//DisableRedeemButton.setBounds(0, 0, 50, 50);
		ajs(View);
		js(View);
		Button home = (Button) findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MainActivity.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rewards, menu);
		return true;
	}
}
