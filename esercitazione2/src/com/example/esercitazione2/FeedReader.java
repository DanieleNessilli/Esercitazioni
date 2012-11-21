package com.example.esercitazione2;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class FeedReader extends Activity {

	String titolo[] = new String[15];
	String descrizione[] = new String[15];
	String ora[] = new String[15];
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_reader);
       String url= "http://feeds.feedburner.com/Androidiani?format=xml";
       try {
        	SAXParserFactory factory=SAXParserFactory.newInstance();
    		SAXParser parser=factory.newSAXParser();
			InputStream in =new URL(url).openStream();
			RSSHandler handler=new RSSHandler();
			XMLReader reader=parser.getXMLReader();
			reader.setContentHandler(handler);
			reader.parse(new InputSource(in));
				
			titolo=handler.tit;                    
			ora=handler.date;
			descrizione=handler.descr;

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ListView listView = (ListView) findViewById(R.id.arrayList);
        ArrayAdapter<CustomItem> arrayAdapter = new ArrayAdapter<CustomItem>(
				this, R.layout.row, R.id.titolo, createItems()) {
        	         
        	public View getView(int position, View convertView, ViewGroup parent){	
        		
        	  return getViewHolder(position,convertView,parent);
        	}
        	
        	public View getViewHolder(int position, View convertView, ViewGroup parent) {
				// Creiamo il riferimento all'holder
				ViewHolder viewHolder = null;
				// Se non presente una istanza della View la creiamo attraverso
				// inflating. Se già presente la riutilizziamo
				if(convertView==null){
					// Otteniamo il riferimento alla View da parserizzare
					LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(R.layout.row, null);
					// Creiamo un Holder associato
					viewHolder = new ViewHolder();
					// Assegnamo i riferimenti alle textView
					viewHolder.titoloView = (TextView)convertView.findViewById(R.id.titolo);
					viewHolder.oraView = (TextView)convertView.findViewById(R.id.ora);
					// Lo assegnamo come Tag della View
					convertView.setTag(viewHolder);
				}else{
					// La View esiste già per cui possiede già l'holder
					viewHolder = (ViewHolder)convertView.getTag();
				}
				// Otteniamo l'elemento i-esimo
				CustomItem item = getItem(position);
				// Assegnamo i valori
				viewHolder.titoloView.setText(item.titolo);
				viewHolder.oraView.setText(item.ora);
				// Ritorniamo la View
				return convertView;
			}			

        	
        	
		};
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub                                     
				
				Intent intent=new Intent(FeedReader.this,intent.class);                 //creo un nuovo intent
				intent.putExtra("titolo", titolo[arg2]);                                //creo un collegamento tra la stringa titolo e la cella specificata da arg2 dell'array di titoli 
				intent.putExtra("data", ora[arg2]);                                     //creo un collegamento tra la stringa data e la cella specificata da arg2 dell'array di date    
				intent.putExtra("descrizione",descrizione[arg2]);                       //creo un collegamento tra la stringa descrizione e la cella specificata da arg2 dell'array di descrizioni  
			    startActivity(intent); 
			}
			

		});
		listView.setAdapter(arrayAdapter);
	}
    
    public static class CustomItem {
		public String titolo;
		public String ora;
	}

	private CustomItem[] createItems() {
		
		CustomItem items[] = new CustomItem[15];
		
		
		for(int i=0;i<15;i++){
		items[i] = new CustomItem();
		items[i].titolo=titolo[i];
		items[i].ora=ora[i];
		}
		
		return items;
	}
	
	public static class ViewHolder{
		public TextView titoloView;
		public TextView oraView;
	}
	

    
}

