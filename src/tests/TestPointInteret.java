package tests;


import entities.PointInteretDB;
import entities.UserDB;

import java.sql.*;
import java.util.ArrayList;

import myconnections.*;

public class TestPointInteret {
    public static void main(String[] args) {
        DBConnection dbc =new DBConnection();
        //User
        Connection con = dbc.getConnection();
        if(con==null) { 
            System.out.println("connexion impossible");
            System.exit(0);
        }
        PointInteretDB.setConnection(con);
        PointInteretDB p1=null,p2=null;  
		try {
			System.out.println("test ajout");
			UserDB us=new UserDB(3);
			p1 = new PointInteretDB(us.getIduser(),"ffff", "ffff", 12, 13);
			System.out.println("numuser :"+us.getIduser());
			p1.create();
			
			int numpoint = p1.getIdpoint();
			p2 = new PointInteretDB(numpoint);
			p2.read();
			System.out.println("P2 = " + p2);
			if (p2.getUser().getIduser()==4 && p2.getNom().equals("ffff") && p2.getDescription().equals("ffff")
					&& p2.getLongitude()==12
					&& p2.getLatitude()==13)
				System.out.println("OK");
			else
				System.err.println("BAD récupération de données invalide");
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout" + e);
			// e.printStackTrace();
		}
		try {
			p1.delete();
		} catch (Exception e) {
		}

		try{ 
            System.out.println("test doublon");
            p1=new PointInteretDB(4,"dddd", "dddd", 12, 13);
            p1.create();
            p2=new PointInteretDB(4,"dddd", "dddd", 12, 13);
            p2.create();
            System.out.println("munpoint1 :"+p1.getIdpoint());
            System.out.println("numpoint : "+p2.getIdpoint());
            System.out.println("BAD");
        }
        catch(Exception e){
            System.out.println("OK exception normale de doublon "+e);
           
            //e.printStackTrace();
        }
        
        try{ 
            p1.delete();
            p2.delete();
        }catch(Exception e){}

        try {
			System.out.println("test d'effacement fructueux");
			p1=new PointInteretDB(1,"dddd", "dddd", 12, 13);
			p1.create();
			int numpoint = p1.getIdpoint();
			p1.delete();
			p2= new PointInteretDB(numpoint);
			p2.read();
			System.err.println("BAD effacement non effectué pour id ="
					+ p2.getIdpoint());
		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement " + e);
			// e.printStackTrace();
		}

		try {
			p1.delete();
		} catch (Exception e) {
		}
		
		try {
			System.out.println("test d'effacement infructueux( point d'intérêt aappartent a un utilisateur)");
			p1 = new PointInteretDB(4);
			p1.delete();
			System.err.println("BAD effacement effectué");
		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement" + e);
		}

         
		try {
			System.out.println("test mise à jour");
			p1 = new PointInteretDB(1,"dddd", "dddd", 12, 13);
			p1.create();
			int numpoint = p1.getIdpoint();
			p1.setNom("gggg");
			p1.setDescription("gggg");
			p1.setLongitude(20);
			p1.setLatitude(25);
			p1.update();
			//u1.misajour();
			p2 = new PointInteretDB(numpoint);
			p2.read();
			System.out.println("p2=" + p2);
			if (p2.getNom().equals("gggg") && p2.getDescription().equals("gggg")
					&& p2.getLongitude()==20
					&& p2.getLatitude()==25)
				System.out.println("OK");
			else
				System.out.println("BAD utilisateur non mis à jour");
		} catch (Exception e) {
			System.err.println("BAD exception de mise à jour " + e);
			// e.printStackTrace();
		}

		try {
			p1.delete();
		} catch (Exception e) {
		}
		
		
		
		try {
			System.out.println("test de recherche fructueuse du point interet fonction de son nom");
			ArrayList<PointInteretDB> liste = PointInteretDB.rechpointinteretnom("eeee");
			for (PointInteretDB pr : liste) {
				if (pr.getNom().indexOf("eeee") < 0)
					throw new Exception("nom  du point ne contenant pas eeee nom= "
							+ pr.getNom());
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.err.println("exception de recherche " + e);
		}

		try {
			System.out.println("test de recherche infructueuse du point d'interet fonction de son nom");
			ArrayList<PointInteretDB> liste = PointInteretDB.rechpointinteretnom("ZZZZ");
			for (PointInteretDB pr : liste) {
				System.out.println(pr);
			}
			System.err.println("BAD");
		} catch (Exception e) {
			System.out.println("OK exception normale recherche " + e);
		}
		
		try {
			System.out.println("test de recherche fructueuse du point interet fonction de sa categorie");
			ArrayList<PointInteretDB> liste = PointInteretDB.rechpointinteretcategorie("qqqq");
			for (PointInteretDB pr : liste) {
				if (pr.getNom().indexOf("qqqq") < 0)
					throw new Exception("nom de la categorie du point ne contenant pas qqqq nom_cat= "
							+ pr.getNom());
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.err.println("exception de recherche " + e);
		}
		
		
		try {
			System.out.println("test de recherche infructueuse du point d'interet fonction de sa categorie");
			ArrayList<PointInteretDB> liste = PointInteretDB.rechpointinteretcategorie("yyyy");
			for (PointInteretDB pr : liste) {
				System.out.println(pr);
			}
			System.err.println("BAD");
		} catch (Exception e) {
			System.out.println("OK exception normale recherche " + e);
		}

		try {
			System.out.println("test de recherche fructueuse du point interet fonction d'une distance donnee");
			ArrayList<PointInteretDB> liste = PointInteretDB.rechpointinteretdistance(0,4444,0,4444);
			for (PointInteretDB pr : liste) {
				if (pr.getLongitude()!=4444 && pr.getLatitude()!=4444)
					throw new Exception("nom de la categorie du point ne contenant pas qqqq nom_cat= "
							+ pr.getNom());
			}
			System.out.println("OK");
		} catch (Exception e) {
			System.err.println("exception de recherche " + e);
		}


		try {
			con.close();
		} catch (Exception e) {
		}
    } 
}