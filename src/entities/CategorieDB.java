/**
 * 
 */
package entities;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @author user
 *
 */
public class CategorieDB extends Categorie implements CRUD {
	/**
	 * connexion à la base de données partagée entre toutes les
	 * instances(statique)
	 */
	protected static Connection dbConnect = null;
	
	public CategorieDB(int idcategorie) {
		super(idcategorie);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param idcategorie
	 * @param nom
	 */
	public CategorieDB(int idcategorie, String nom) {
		super(idcategorie, nom);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param nom
	 */
	public CategorieDB(String nom) {
		super( nom);
		// TODO Auto-generated constructor stub
	}

	/**
	 * méthode statique permettant de partager la connexion entre toutes les
	 * instances de CategorieDB
	 * 
	 * @param nouvdbConnect
	 *            connexion à la base de données
	 */
	public static void setConnection(Connection nouvdbConnect) {
		dbConnect = nouvdbConnect;
	}

	/**
	 * enregistrement d'une nouvelle catégorie dans la base de données
	 * 
	 * @throws Exception
	 *             erreur de création
	 */
	@Override
	public void create() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call createcategorie(?,?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setString(2, nom);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Erreur de création " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entities.CRUD#read()
	 */
	@Override
	public void read() throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entities.CRUD#update()
	 */
	@Override
	public void update() throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see entities.CRUD#delete()
	 */
	@Override
	public void delete() throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * méthode statique permettant de récupérer tous les noms de categories
	 * 
	 * 
	 *            
	 * @return liste de categorie
	 * @throws Exception
	 *             pas de categorie
	 */
	public static ArrayList<String> liste() throws Exception {
		ArrayList<String> l = new ArrayList<String>();
		String req = "{?=call listecategorie()}";
		CallableStatement cstmt = null;
		try {
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.executeQuery();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			boolean trouve = false;
			while (rs.next()) {
				trouve = true;
				String nom = rs.getString("nom_categorie");
				l.add(nom);
			}
			if (!trouve)
				throw new Exception("pas de categorie");
			else
				return l;
		} catch (Exception e) {
			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}



}
