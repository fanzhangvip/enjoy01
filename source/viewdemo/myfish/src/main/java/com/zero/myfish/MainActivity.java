package com.zero.myfish;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main4);
//		setContentView(new PathTan(this));
//		main1();
	}

	public  void main1(){
	  //TODO:
		final PathMoveView1 myView = findViewById(R.id.myview);
		findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				myView.reset();
			}
		});
	}
}
