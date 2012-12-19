package com.example.drawsender;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.util.Log;
import android.view.View;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	private String msg = new String();
	private int x = 100;
	private int y = 100;
	private Bitmap bmp = null;
	private boolean selected = false;
	XMPPConnection connection; 
	Paint mPaint;

	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		connection();
		
		bmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.matita);
		path.moveTo(x, y);
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);   //lo stato viene settato su linea
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.SQUARE);
		mPaint.setStrokeWidth(7);             //imposta lo spessore
		mPaint.setColor(Color.BLACK);          //imposta il colore              
		
	}

	/**
	 * @param args
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bmp, x, y, null);

		canvas.drawPath(path, mPaint);
	}

	Path path = new Path();
	int deltax = 0;
	int deltay = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int eventaction = event.getAction();
		int touchx = (int) event.getX();
		int touchy = (int) event.getY();

		switch (eventaction) {
		case MotionEvent.ACTION_DOWN:
			
				selected = true;
				deltax = touchx - x;
				deltay = touchy - y;
			
			
				path.moveTo(touchx, touchy);
				invalidate();
				msg="nessilli@1@"+touchx+"@"+touchy;
                messaggio();
		  

			break;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(touchx, touchy);
			invalidate();
			msg="nessilli@2@"+touchx+"@"+touchy;
			messaggio();
			break;
		case MotionEvent.ACTION_UP:
			selected = false;
			path.lineTo(touchx, touchy);
			invalidate();
			msg="nessilli@3@"+touchx+"@"+touchy;
			messaggio();
			break;
		default:
			break;
		}
		return true;
	}
	public void connection (){
		Runnable rn = new Runnable() {

			public void run() {
        
 				ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it", 5222); // Configurazione per connettersi al server (indirizzo e porta)

 				config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled); // Sicurezza disabilitata per le nostre prove

 				connection = new XMPPConnection(config); // Imposto la configurazione
 															// nella connessione
 				try {
 					connection.connect(); // Ci connettiamo alla chat

 					connection.login("nessilli", "qwerty"); // Username e password per
 															// accedere
 				} catch (XMPPException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
			}
		};	
		new Thread(rn).start();
     }

	public void messaggio() {
		Runnable rn= new Runnable(){
			
			
			public void run() {
				// TODO Auto-generated method stub
				Log.d("XMPPChat","Hai scritto: "+msg);
				Message mess = new Message();
				mess.setTo("all@broadcast.ppl.eln.uniroma2.it");
				mess.setBody(msg);
				System.out.println(mess.toString());
				connection.sendPacket(mess);
			}
		};
	  Thread da= new Thread(rn);
	  da.start();
	}

	
}
