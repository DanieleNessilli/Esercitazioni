package com.example.esercitazione2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class RSSHandler extends DefaultHandler{

    
boolean inTitle=false;
boolean inEntry=false;
String Summary[]=new String[15];
boolean inSummary=false;
boolean inDate=false;

int d=0,t=0,i=0;
boolean contrSum=true;   // variabili usate per far in modo che il parser non divida le descrizioni e i titoli a metà
boolean contrTit=true;   //quando true indica che ha iniziato il parser, se false significa che ha diviso la stringa in due e quindi la deve concatenare

String tit[]=new String[15];        //array contenente i titoli
String descr[]=new String[15];	    //array contenente le descrizioni
String date[]=new String[15];       //array contenente le date


public void startDocument() throws SAXException {
	
	super.startDocument();
}

@Override
public void endDocument() throws SAXException {
	// TODO Auto-generated method stub
	super.endDocument();	
	
}

@Override
public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
	// TODO Auto-generated method stub
	super.startElement(uri, localName, qName, attributes);
	
	
	if(qName.equals("title")){
		inTitle=true;
		t++;                                     //incrementa per memorizzare nella stringa successiva
		contrTit=true;                           
	}
	else if(qName.equalsIgnoreCase("entry")) {
		inEntry=true;
	}
   else if(qName.equalsIgnoreCase("summary")){
		inSummary=true;
		d++;                                     //incrementa per memorizzare nella stringa successiva
		contrSum=true;
	} 
   else if(qName.equalsIgnoreCase("published")){
   	inDate=true;
   }
	}

@Override
public void endElement(String uri, String localName, String qName)
		throws SAXException {
	// TODO Auto-generated method 		
	super.endElement(uri, localName, qName);
	
	
	if(qName.equals("title")){
		inTitle=false;
	}
	else if(qName.equalsIgnoreCase("item")) {
		inEntry=false;
	}
	else if(qName.equalsIgnoreCase("summary")){
		inSummary=false;
	}
	else if(qName.equalsIgnoreCase("published")){
		inDate=false;
	}
}

@Override
public void characters(char[] ch, int start, int length){
	// TODO Auto-generated method stub
	
	try {
		super.characters(ch, start, length);
		String s=new String(ch,start,length); 
		
		if(inSummary & inEntry){
			if(contrSum) {
				descr[d-1]=s;
				contrSum=false;
			    }
			else{
				descr[d-1]=descr[d-1]+s;
			}
		}
		else if(inTitle & inEntry){
			if(contrTit){
				tit[t-2]=s;
				contrTit=false;
			   }
			else{
				tit[t-2]=tit[t-2]+s;
			}
		}
		else if(inDate & inEntry){
			date[i]=s;
			i++;
		}
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();			
	}
	
	
}


}
