package be.condorcet.geolocalisation;

import java.sql.Connection;
import java.util.ArrayList;






import be.condorcet.geolocalisation.AuthentificationActivity.MyAccesDBIns;
import myconnections.DBConnection;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import entities.CategorieDB;
import entities.PointInteretDB;

public class CreerActivity extends ActionBarActivity {
	private Connection con = null;
	private String msgcreation, patientez, echec, ptcreer,champvide, ptexistedeja,
			creerpt, pascreer;
	private EditText ednom, eddescription, edlongitude, edcategorie,
			edlatitude;
	private Button btcreerpoint;
	private LocationManager locationManager;
	private EditText edDegLat,edMinLat,edSecLat,edDegLong,edMinLong,edSecLong,edDist;
	private boolean unique;
	public static final String IDUSER="userid";
	private int iduser;
	private double longitude,latitude;
	private Point ancienPt=new Point(0,0),pt=new Point(0,0);
	public static final String CAT = "cat";
	ArrayList<String> c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creer);

		ednom = (EditText) findViewById(R.id.ednom);
		eddescription = (EditText) findViewById(R.id.eddescription);
		btcreerpoint=(Button) findViewById(R.id.btcreerpoint);
		
		edcategorie = (EditText) findViewById(R.id.edcategorie);
		edDegLat=(EditText) findViewById(R.id.degLat);
		edDegLat.setEnabled(false);//pour desactiver les boutons
		edMinLat=(EditText) findViewById(R.id.minLat);
		edMinLat.setEnabled(false);
		edSecLat=(EditText) findViewById(R.id.secLat);
		edSecLat.setEnabled(false);
		edDegLong=(EditText) findViewById(R.id.degLong);
	    edDegLong.setEnabled(false);
		edMinLong=(EditText) findViewById(R.id.minLong);
		edMinLong.setEnabled(false);
		edSecLong=(EditText) findViewById(R.id.secLong);
	    edSecLong.setEnabled(false);
	    
		champvide = getResources().getString(R.string.champvide);
		pascreer = getResources().getString(R.string.pascreer);
		ptexistedeja = getResources().getString(R.string.ptexistedeja);
		creerpt = getResources().getString(R.string.creerpt);
		echec = getResources().getString(R.string.echec);
		patientez = getResources().getString(R.string.patientez);
		ptcreer=getResources().getString(R.string.ptcreer);
		msgcreation=getResources().getString(R.string.msgcreation);
		
		ArrayList sauvegardes = (ArrayList) getLastCustomNonConfigurationInstance();
		if(sauvegardes != null) {
			ancienPt=(Point)sauvegardes.get(0);
			pt=(Point)sauvegardes.get(1);
			
		}
		
		btcreerpoint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// edmsg.setText(ins);
				ancienPt=pt;
				MyAccesDBPointInteret adb = new MyAccesDBPointInteret(
						CreerActivity.this);
				adb.execute();
			}
		});

		
		//récupération des services de localisation disponibles (GPS,signal GSM,...)
		ArrayList<LocationProvider> providers = new	ArrayList<LocationProvider>();
			locationManager  = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		   	    ArrayList<String> names =(ArrayList<String>) locationManager.getProviders(true);
			for(String name : names)
					providers.add(locationManager.getProvider(name));
		    
					
			//locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 0,new LocationListener()	le 0 :c'est combien de metre pour que vous voulez que sa vous localise  
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener()
			{
		//quand on change de position la methode onlocaionchanged s active et on met les intructions qui vont avec quand on s'est deplace
		        @Override
		        public void onLocationChanged(android.location.Location location) {
		            // TODO Auto-generated method stub
		            if(location!=null){
		            	          	                        	
		                 longitude = location.getLongitude();
		                 latitude = location.getLatitude();
	  	                pt =new Point(latitude,longitude);

		       		    edDegLat.setText(""+pt.deglat);
		                edMinLat.setText(""+pt.minlat);
		                edSecLat.setText(""+pt.seclat+"-"+pt.centiseclat);
		                
		                edDegLong.setText(""+pt.deglong);
		                edMinLong.setText(""+pt.minlong);
		                edSecLong.setText(""+pt.seclong+"-"+pt.centiseclong);
		                //long dist=pt.dist(ancienPt);
	                    //edDist.setText(""+dist);
	                    
	                    
		            }

		        }



		        @Override
		        public void onProviderDisabled(String provider) {
		            // TODO Auto-generated method stub

		            //Pass views as parameters? DIscuss
		            //DeviceName.setText(String.valueOf(Latitude) +" "+String.valueOf(Longitude));

		        }

		        @Override
		        public void onProviderEnabled(String provider) {
		            // TODO Auto-generated method stub

		        }

		        @Override
		        public void onStatusChanged(String provider, int status,
		                Bundle extras) {
		            // TODO Auto-generated method stub

		        }
			 
		    });
		 


		Intent i = getIntent();
		c = i.getStringArrayListExtra(MenuActivity.CAT);
		iduser=i.getIntExtra(MenuActivity.IDUSER,0);
		Log.d("numero utilisateur","utilisateur"+iduser);
		final Spinner list = (Spinner) findViewById(R.id.spin);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, c);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		list.setAdapter(adapter);
		final Button active = (Button) findViewById(R.id.activation);
		active.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (active != null) {
					if ((list != null) && list.isEnabled()) {
						int i = list.getSelectedItemPosition();
						edcategorie.setText(c.get(i));
					}
					list.setEnabled(false);
					active.setText("Désactivé");
				} else {
					list.setEnabled(true);
					active.setText("Activé");
		    	}
			}
		});

	}
	
	
	 public Object onRetainCustumNonConfigurationInstance() {
	      ArrayList sauvegardes= new ArrayList();
		  sauvegardes.add(ancienPt);
		  sauvegardes.add(pt);
		  return sauvegardes;
		}
	 
	 class Point{
		int deglat,minlat,seclat,centiseclat; 
		int deglong,minlong,seclong,centiseclong; 
		double latitude,longitude;
		public Point(double latitude,double longitude){
			 this.latitude=latitude;
			 this.longitude=longitude;
			 
			 double valeur=latitude;
			 int valConv=(int)(valeur*3600);
			 centiseclat=(int)((valeur*3600-valConv)*100);
	         deglat=valConv/3600;
	         valConv=valConv%3600;
	         minlat=valConv/60;
	         seclat=valConv % 60;
	         
	         valeur=longitude;
			 valConv=(int)(valeur*3600);
			 centiseclong=(int)((valeur*3600-valConv)*100);
	         deglong=valConv/3600;
	         valConv=valConv%3600;
	         minlong=valConv/60;
	         seclong=valConv % 60;
	 	 
	    }
		
		public Point(int deglat, int minlat, int seclat, int centiseclat,
				int deglong, int minlong, int seclong, int centiseclong) {
			super();
			this.deglat = deglat;
			this.minlat = minlat;
			this.seclat = seclat;
			this.centiseclat = centiseclat;
			this.deglong = deglong;
			this.minlong = minlong;
			this.seclong = seclong;
			this.centiseclong = centiseclong;
			latitude=(deglat*3600+minlat*60+seclat+centiseclat/100.0)/3600;
			longitude=(deglong*3600+minlong*60+seclong+centiseclong/100.0)/3600;
			
		}

		
		
		public long dist(Point pt){//formules approximatives
			double dlat=Math.abs(latitude-pt.latitude)/90 * 10000000;
			double latInvRadians= Math.abs(90-latitude)/180*Math.PI;
			double circLat=10000000 * Math.sin(latInvRadians);
			double dlong=Math.abs(longitude-pt.longitude)/90*circLat;
			double distance= Math.sqrt(dlat*dlat + dlong*dlong);
			return (long)distance;
		}
		 
	 }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.creer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			con.close();
			con = null;
			Log.d("connexion", "deconnexion OK");
		} catch (Exception e) {
		}
		Log.d("connexion", "deconnexion OK");
	}

	class MyAccesDBPointInteret extends AsyncTask<String, Integer, Boolean> {
		private String resultat;
		private ProgressDialog pgd = null;
		private String nom, description,categorie;
		private boolean flag=false;
		private int cpt=0;

		public MyAccesDBPointInteret(CreerActivity pActivity) {
			link(pActivity);
			// TODO Auto-generated constructor stub
		}

		private void link(CreerActivity pActivity) {
			// TODO Auto-generated method stub
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pgd = new ProgressDialog(CreerActivity.this);
			pgd.setMessage(msgcreation);
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.setTitle(patientez);
			pgd.show();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			if (con == null) {// premier invocation
				con = new DBConnection().getConnection();
				if (con == null) {
					resultat = echec;
					return false;
				}
				PointInteretDB.setConnection(con);
				CategorieDB.setConnection(con);
			}
			// int id=Integer.parseInt(ed.getText().toString());
			nom = ednom.getText().toString();
			description = eddescription.getText().toString();
			//longitude = Double.parseDouble(edlongitude.getText().toString());
			//latitude = Double.parseDouble(edlatitude.getText().toString());
			categorie=edcategorie.getText().toString();
			try {
				PointInteretDB p = new PointInteretDB(iduser,nom, description,
						longitude, latitude);
				p.create();
				unique = true;
				//p.read();
				
				do{
					if(c.get(cpt).equalsIgnoreCase(categorie)){
						flag=true;
					}
					cpt++;
					
				}
				 while(flag==false && cpt<c.size());
				
			if(!flag){
				 CategorieDB catg=new CategorieDB(categorie);
				  catg.create();
			}	
				
				unique = true;
				
				// resultat = u.toString();
			} catch (Exception e) {
				
				// resultat = e.getMessage();
				return false;
			}
						return true;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			// edmsg.setText(resultat);
			if (resultat == echec) {
				Toast.makeText(CreerActivity.this, echec, Toast.LENGTH_LONG)
						.show();
				return;
			}
			if (nom.trim().equals("") || description.trim().equals("")
					|| edcategorie.getText().toString().trim().equals("") ||
					edDegLong.getText().toString().trim().equals("") || 
					edMinLong.getText().toString().trim().equals("") ||
					edSecLong.getText().toString().trim().equals("") ||
					edDegLat.getText().toString().trim().equals("") ||
					edMinLat.getText().toString().trim().equals("") ||
					edSecLat.getText().toString().trim().equals("") 
					) {
				Toast.makeText(CreerActivity.this, champvide, Toast.LENGTH_LONG)
						.show();
				return;
			}
			if (!unique) {
				Toast.makeText(CreerActivity.this, ptexistedeja,
						Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(CreerActivity.this, ptcreer,
						Toast.LENGTH_LONG).show();
			}
		}
	}
}