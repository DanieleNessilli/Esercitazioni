package com.example.esercitazione2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class intent extends Activity{
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.intent);
            
	        Intent intent2=getIntent();
	        TextView title= (TextView) findViewById(R.id.titolo);
			title.setText(intent2.getStringExtra("titolo"));            //setto il titolo della nuova activity con il titolo passato dall'intent
			
			TextView data=(TextView) findViewById(R.id.data);
			data.setText(intent2.getStringExtra("data"));               //setto la data della nuova activity con la data passato dall'intent
			

			TextView descrizione=(TextView) findViewById(R.id.descrizione);
			descrizione.setText(intent2.getStringExtra("descrizione")); //setto la descrizione della nuova activity con la descrizione passato dall'intent
	        
	 }
}
