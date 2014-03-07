package com.example.whattowatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConnectionActivity extends Activity{

	Button soumission;
	Intent go;
	EditText pseudo;
	public final static String AGE ="sdz.chapitreTrois.intent.example.AGE";
	private OnClickListener soumissionListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			go = new Intent(ConnectionActivity.this, AccueilActivity.class);
			pseudo = (EditText) findViewById(R.id.pseudo);
			go.putExtra(AGE, pseudo.getText());
			startActivity(go);
			
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		soumission = (Button) findViewById(R.id.soumission_but);
		soumission.setOnClickListener(soumissionListener);
		
	}
}
