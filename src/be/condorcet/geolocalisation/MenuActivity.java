package be.condorcet.geolocalisation;



import entities.User;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends ActionBarActivity {
	//private TextView txt;
	private Menu monMenu;
	private Intent i=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		//txt=(TextView) findViewById(R.id.infosuser);
		//TextView infospersonne=(TextView) findViewById(R.id.infospersonne);
		Intent i=getIntent();
	   // User u=(User)i.getParcelableExtra(AuthentificationActivity.IDUSER);
	   // String res=i.getStringExtra(AuthentificationActivity.RESULTAT);
	      
	   // txt.setText(res);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		 monMenu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
	 // Intent i=null;
	  
	  //ici la variable monMenu est reconnue et initialisée par la méthode onCreateOptionsMenu
	  //elle pourrait donc être modifiée selon l'évolution des choix de l'utilisateur
	  switch(item.getItemId())
	  {
	    case R.id.btcreer:
	    	          i = new Intent(MenuActivity.this,CreerActivity.class);
			    	  startActivity(i);
	    	    
	    	          return true;//pour dire que le click a bien été pris en charge
	    case R.id.btlister:
	    	          i = new Intent(MenuActivity.this,ListerActivity.class);
	    	          startActivity(i);
	                  return true;//pour dire que le click a bien été pris en charge
	    	
	    case R.id.btrechercher:
	    	           i = new Intent(MenuActivity.this,RechercherActivity.class);
  	                   startActivity(i);
                       return true;//pour dire que le click a bien été pris en charge
	    case R.id.btconsulter:
	           i = new Intent(MenuActivity.this,ConsulterActivity.class);
              startActivity(i);
            return true;//pour dire que le click a bien été pris en charge
               
	    	            	         
	  }
	  return super.onOptionsItemSelected(item);
	  //ligne atteinte seulement si aucune option du switch n'a été sélectionnée, en pratique elle retourne false
	}
	
public void gestionCreer(View v){
		
	 i = new Intent(MenuActivity.this,CreerActivity.class);
	  startActivity(i);	
     }
	
public void gestionLister(View v){
	
	 i = new Intent(MenuActivity.this,ListerActivity.class);
	  startActivity(i);	
    }

public void gestionRechercher(View v){
	
	 i = new Intent(MenuActivity.this,RechercherActivity.class);
	  startActivity(i);	
    }

public void gestionConsulter(View v){
	
	 i = new Intent(MenuActivity.this,ConsulterActivity.class);
	  startActivity(i);	
    }

	
}

