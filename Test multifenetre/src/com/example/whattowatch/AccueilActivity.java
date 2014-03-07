package com.example.whattowatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AccueilActivity extends Activity{
	TextView messageacc;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
		Intent i = getIntent();
		String age = i.getStringExtra(ConnectionActivity.AGE);
		messageacc = (TextView) findViewById(R.id.message_accueil);
		messageacc.setText("Bienvenu: " +age);
		
	}

}
