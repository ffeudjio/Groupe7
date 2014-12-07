package be.condorcet.geolocalisation;

import java.sql.Connection;

import myconnections.DBConnection;
import entities.PointInteretDB;
import entities.UserDB;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreerActivity extends ActionBarActivity {

	private Connection con = null;
	private String msgcreation,patientez,champvide,echec;
	private String pascreer,creerpt,ptexistedeja;
	private EditText ednom;
	private EditText eddescription;
	private EditText edlongitude;
	private EditText edlatitude;
	private String existedeja;
    private boolean unique=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ednom = (EditText) findViewById(R.id.ednom);
		eddescription = (EditText) findViewById(R.id.eddescription);
		//edlongitude = (EditText) findViewById(R.id.edlongitude);
		//edlatitude = (EditText) findViewById(R.id.edlatitude);
		champvide = getResources().getString(R.string.champvide);
		//pascreer = getResources().getString(R.string.pascreer);
	    //ptexistedeja = getResources().getString(R.string.ptexistedeja);
	    //creerpt = getResources().getString(R.string.creerpt);
	    echec = getResources().getString(R.string.echec);
	    patientez = getResources().getString(R.string.patientez);
		
		setContentView(R.layout.activity_creer);
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
		String nom ;
		String description;
		double longitude;
		double latitude;


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
			pgd.setTitle(patientez);
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
			}
			// int id=Integer.parseInt(ed.getText().toString());
			 nom = ednom.getText().toString();
			 description = eddescription.getText().toString();
			 longitude=Double.parseDouble(edlongitude.getText().toString());
			 latitude=Double.parseDouble(edlatitude.getText().toString());
			try {
				PointInteretDB p = new PointInteretDB(nom,description,longitude, latitude);
				p.create();
				p.read();
				resultat = p.toString();
				unique=true;
			} catch (Exception e) {
				resultat = "erreur" + e.getMessage();
				return false;
			}
			return true;
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			if (resultat == echec) {
				Toast.makeText(CreerActivity.this, echec,
						Toast.LENGTH_LONG).show();
				return;
			}
			if (nom.trim().equals("") || description.trim().equals("")
					) {
				Toast.makeText(CreerActivity.this, champvide,
						Toast.LENGTH_LONG).show();
				return;
			}
			if (!unique) {
				Toast.makeText(CreerActivity.this, existedeja,
						Toast.LENGTH_LONG).show();
			}			
		}
	}

}
