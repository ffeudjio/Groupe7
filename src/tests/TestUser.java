/**
 * 
 */
package tests;

import java.sql.Connection;
import java.util.ArrayList;

import myconnections.DBConnection;
import entities.UserDB;

/**
 * @author user
 *
 */
public class TestUser {

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
		UserDB.setConnection(con);
		UserDB u1 = null, u2 = null;
		try {
			System.out.println("test ajout");
			u1 = new UserDB("eeee", "eeee", "eeee", "eeee");
			
			u1.create();
			System.out.println("U2 ");
			int numuser = u1.getIduser();
			u2 = new UserDB(numuser);
			u2.read();
			System.out.println("U2 = " + u2);
			if (u2.getNom().equals("eeee") && u2.getPrenom().equals("eeee")
					&& u2.getLogin().equals("eeee")
					&& u2.getPassword().equals("eeee"))
				System.out.println("OK");
			else
				System.err.println("BAD récupération de données invalide");
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout" + e);
			// e.printStackTrace();
		}
		try {
			u1.delete();
		} catch (Exception e) {
		}

	   try{
			System.out.println("test doublon");
			u1 = new UserDB("eeee", "eeee", "eeee", "eeee");
			u1.create();
			u2 = new UserDB("eeee", "eeee", "eeee", "eeee");
			u2.create();
			System.err.println("BAD doublon accepté");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon " + e);
			// e.printStackTrace();
		}

		try {
			u1.delete();
			u2.delete();
		} catch (Exception e) {
		}

		try {
			System.out.println("test d'effacement fructueux");
			u1 = new UserDB("eeee", "eeee", "eeee", "eeee");
			u1.create();
			int numuser = u1.getIduser();
			//System.out.println("numuser ="+numuser);
			u1.delete();
			u2 = new UserDB(numuser);
			u2.read();
			System.err.println("BAD effacement non effectué pour id ="
					+ u2.getIduser());
		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement " + e);
			// e.printStackTrace();
		}

		try {
			u1.delete();
		} catch (Exception e) {
		}

		try {
			System.out.println("test d'effacement infructueux(utilisateur propriétaire d'un point d'intérêt)");
			u1 = new UserDB(3);
			u1.delete();
			System.err.println("BAD effacement effectué");
		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement" + e);
		}

		try {
			System.out.println("test mise à jour");
			u1 = new UserDB("eeee", "eeee", "eeee", "eeee");
			u1.create();
			int numuser = u1.getIduser();
			u1.setLogin("hhhh");
			u1.setPassword("hhhh");
			u1.update();
			u1.misajour();
			u2 = new UserDB(numuser);
			u2.read();
			System.out.println("u2=" + u2);
			if (u2.getNom().equals("eeee") && u2.getPrenom().equals("eeee")
					&& u2.getLogin().equals("hhhh")
					&& u2.getPassword().equals("hhhh"))
				System.out.println("OK");
			else
				System.out.println("BAD utilisateur non mis à jour");
		} catch (Exception e) {
			System.err.println("BAD exception de mise à jour " + e);
			// e.printStackTrace();
		}

		try {
			u1.delete();
		} catch (Exception e) {
		}

		try {
			System.out.println("test de recherche fructueuse");
			ArrayList<UserDB> liste = UserDB.rechNom("dddd");
			for (UserDB pr : liste) {
				if (pr.getNom().indexOf("dddd") < 0)
					throw new Exception("nom ne contenant pas eeee nom= "
							+ pr.getNom());
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.err.println("exception de recherche " + e);
		}

		try {
			System.out.println("test de recherche infructueuse");
			ArrayList<UserDB> liste = UserDB.rechNom("ZZZZ");
			for (UserDB pr : liste) {
				System.out.println(pr);
			}
			System.err.println("BAD");
		} catch (Exception e) {
			System.out.println("OK exception normale recherche " + e);
		}

		try {
			con.close();
		} catch (Exception e) {
		}
	

}
}