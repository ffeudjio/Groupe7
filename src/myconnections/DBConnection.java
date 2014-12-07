package myconnections;

import java.sql.*;
import java.util.*;

//import android.util.Log;
public class DBConnection {

  protected String serverName;
  protected String username;
  protected String password;
  protected String dbName;
  protected String dbPort;
 
  public DBConnection(){
        PropertyResourceBundle properties = (PropertyResourceBundle)
		PropertyResourceBundle.getBundle("resources.application");
		//nom du fichier properties � utiliser
		serverName=properties.getString("server");
		dbName =properties.getString("database");
		username=properties.getString("login");
		password=properties.getString("password");
		dbPort=properties.getString("port");    
}

  
  public DBConnection(String username,String password){
      this();
      this.username=username;
      this.password=password;
   }


 
 public Connection getConnection() {
  try {
	  
	        	
        Class.forName ("oracle.jdbc.OracleDriver");
        
        String url = "jdbc:oracle:thin:@//"+serverName+":"+dbPort+"/"+dbName;
      Connection  dbConnect = DriverManager.getConnection(url, username, password);
      System.out.println("connexion réussie");
      return dbConnect;
    }
    catch(Exception e) {
     //Log.e("connection","erreur de connexion "+e);
     return null ;
    }
   
  }
  }
 

