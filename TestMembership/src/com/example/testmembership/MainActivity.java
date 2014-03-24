package com.example.testmembership;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	final String EXTRA_LOGIN = "user_login";
	final String EXTRA_PASSWORD = "user_password";
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	        
	    final EditText pseudo = (EditText) findViewById(R.id.user_pseudo);
	    final EditText pass = (EditText) findViewById(R.id.user_password);
	    final Button loginButton = (Button) findViewById(R.id.connect);
	    final Button signinButton = (Button) findViewById(R.id.create_account);

	    loginButton.setOnClickListener(new OnClickListener() {

	    	@Override
	    	public void onClick(View v) {
	    		//Si un des deux champs est vide, alors on affiche l'erreurs
	    	final String loginTxt = pseudo.getText().toString();
	    	final String passTxt = pass.getText().toString();

	    	if (loginTxt.equals("") || passTxt.equals("")) {
	    		Toast.makeText(MainActivity.this,
	    		R.string.email_or_password_empty,
	    		Toast.LENGTH_SHORT).show();
	    		return;
	    	}

	    	/*// On déclare le pattern que l’on doit vérifier
	    	Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
	    	// On déclare un matcher, qui comparera le pattern avec la
	    	// string passée en argument
	    	Matcher m = p.matcher(loginTxt);
	    	// Si l’adresse mail saisie ne correspond au format d’une
	    	// adresse mail on un affiche un message à l'utilisateur
	    	if (!m.matches()) {
	    	        // Toast est une classe fournie par le SDK Android
	    		// pour afficher les messages (indications) à l'intention de   
	                    // l'utilisateur. Ces messages ne possédent pas d'interaction avec l'utilisateur
	    		// Le premier argument représente le contexte, puis
	    		// le message et à la fin la durée d'affichage du Toast (constante 
	                    // LENGTH_SHORT ou LENGTH_LONG). Sans oublier d'appeler la méthode
	                    //show pour afficher le Toast
	    		Toast.makeText(MainActivity.this, R.string.email_format_error,
	    	        Toast.LENGTH_SHORT).show();
	    			return;
	    		}*/

	    	Intent intent = new Intent(MainActivity.this,LoginDisplayActivity.class);
	        intent.putExtra(EXTRA_LOGIN, loginTxt);
	    	intent.putExtra(EXTRA_PASSWORD, passTxt);
	    	startActivity(intent);
	    	}
	    });
	
	
	signinButton.setOnClickListener(new OnClickListener() {

    	@Override
    	public void onClick(View v) {
	    	Intent intent = new Intent(MainActivity.this,SignInDisplayActivity.class);

    		startActivity(intent);
    		}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
