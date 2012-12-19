package example.drawreceiver;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class MyView extends View {

	int x = 100;
	int y = 100;
	private Bitmap bmp = null;
	private boolean selected = false;
	XMPPConnection connection;
	Paint mPaint;
	Handler handler;

	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		bmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.matita);
		path.moveTo(x, y);
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE); // lo stato viene settato su linea
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.SQUARE);
		mPaint.setStrokeWidth(7); // imposta lo spessore
		mPaint.setColor(Color.BLACK); // imposta il colore
		
        handler=new Handler(); 
		connection();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(bmp, x, y, null);

		canvas.drawPath(path, mPaint);
	}

	Path path = new Path();
	int deltax = 0;
	int deltay = 0;
	String[] mess;

	public void connection() {
		Runnable rn = new Runnable() {

			public void run() {
				ConnectionConfiguration config = new ConnectionConfiguration("ppl.eln.uniroma2.it", 5222); // Configurazione per
														                                                   // connettersi al server
														                                                   // (indirizzo e porta)

				config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled); // Sicurezza
																						// disabilitata
																						// per
																						// le
																						// nostre
																						// prove

				connection = new XMPPConnection(config); // Imposto la
															// configurazione
															// nella connessione
				try {
					connection.connect(); // Ci connettiamo alla chat

					connection.login("nessilli2", "qwerty"); // Username e
															// password per
															// accedere
			
              
				connection.addPacketListener(new PacketListener() {

					public void processPacket(Packet arg0) {
						// TODO Auto-generated method stub
						Message msg = (Message) arg0; // Esegue il casting
														// dell'arg0
						final String to = msg.getTo(); // Prende i vari segmenti
														// del messaggio
						final String body = msg.getBody();
						mess=body.split("@");
						for (int i = 0; i < mess.length; i++) {
							System.out.println(mess[i]);
						}
						Log.d("XMPPChat", "Hai ricevuto un messaggio: " + to+ " " + body);
						handler.post( new Runnable() {
							
							public void run() {
								// TODO Auto-generated method stub
								if (mess[0].equals("nessilli")) {
									if (mess[1].equals("1")) {
										x = Integer.parseInt(mess[2]);
										y = Integer.parseInt(mess[3]);
										path.moveTo(x, y);
										invalidate();
										
									} else if (mess[1].equals("2")) {
											x = Integer.parseInt(mess[2]);
											y = Integer.parseInt(mess[3]);
											path.lineTo(x, y);
											invalidate();
										} else if (mess[1].equals("3")) {
												x = Integer.parseInt(mess[2]);
												y = Integer.parseInt(mess[3]);
												path.lineTo(x, y);
												invalidate();
											}
											
										
									}

														}		
							
						});
					}
						
				}, new MessageTypeFilter(Message.Type.normal));	
				
			}
			 catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}};
		new Thread(rn).start();
	}
}
