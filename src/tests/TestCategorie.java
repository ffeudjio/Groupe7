/**
 * 
 */
package tests;

/**
 * @author frankylove
 *
 */

import java.sql.Connection;
import java.util.ArrayList;

import myconnections.DBConnection;
import entities.CategorieDB;


public class TestCategorie {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBConnection dbc = new DBConnection();
		Connection con = dbc.getConnection();
		if (con == null) {
			System.out.println("connexion impossible");
			System.exit(0);
		}
		CategorieDB.setConnection(con);
		CategorieDB c1 = null, c2 = null;
		try {
			System.out.println("test ajout");
			c1 = new CategorieDB("bbbb");
			
			c1.create();
			int numcat = c1.getIdcategorie();
			//c2 = new CategorieDB(numcat);
			//c2.read();
			//System.out.println(numcat);
			System.out.println("c1 = " + c1);
			/*if (c2.getNom().equals("bbbb"))
				System.out.println("OK");
			else
				System.err.println("BAD récupération de données invalide");*/
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout" + e);
			// e.printStackTrace();
		}
		try {
			c1.delete();
		} catch (Exception e) {
		}
		
	   try{
			System.out.println("test doublon");
			c1 = new CategorieDB("bbbb");
			c1.create();
			c2 = new CategorieDB("bbbb");
			c2.create();
			System.err.println("BAD doublon accepté");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon " + e);
			// e.printStackTrace();
		}

		try {
			c1.delete();
			c2.delete();
		} catch (Exception e) {
		}

		}}
