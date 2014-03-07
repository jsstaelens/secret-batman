package com.example.whattowatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IndexActivity extends Activity {

	Button ins;
	Button conn;
	Intent inscription;
	Intent connexion;
	private OnClickListener inscriptionlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startActivity(inscription);
			
			
		}
	};
	
	private OnClickListener connectionlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			startActivity(connexion);
			
		}
	};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inscription = new Intent(IndexActivity.this, InscriptionActivity.class);
		connexion = new Intent(IndexActivity.this, ConnectionActivity.class);
		ins =  (Button) findViewById(R.id.signin);
		conn =  (Button) findViewById(R.id.login);
		ins.setOnClickListener(inscriptionlistener);
		conn.setOnClickListener(connectionlistener);
		

		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
