
package com.belly;

import com.belly.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	ViewFlipper flipper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setContentView(R.layout.main);
		flipper = (ViewFlipper) findViewById(R.id.flipper);
	
	Button reg = (Button) findViewById(R.id.reg);
	reg.setOnClickListener(new OnClickListener (){
		
		public void onClick(View v){
		
		Intent intent = new Intent(v.getContext() , Register.class);
		startActivityForResult(intent, 0);
		}
		  
	});
	

	Button tap = (Button) findViewById(R.id.tap);
	tap.setOnClickListener(new OnClickListener (){
		
		Slide slids=new Slide();
		
		public void onClick(View v){
			//flipper.setInAnimation(slids.inFromRightAnimation());
			//flipper.setOutAnimation(slids.outToLeftAnimation());
			//flipper.showNext();     
		Intent intent = new Intent(v.getContext() , SplashScreen.class);
		startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

		}
		  
	});
	}
}
