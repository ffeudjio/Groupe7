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
public class PointInteretDB extends PointInteret implements CRUD {
	private String nom_categorie;
	private int iduser;
	/**
	 * connexion à la base de données partagée entre toutes les
	 * instances(statique)
	 */
	protected static Connection dbConnect = null;
	
	public PointInteretDB(int idpoint){
		super(idpoint);
	}

	/**
	 * @param idpoint
	 * @param nom
	 * @param description
	 * @param longitude
	 * @param latitude
	 */
	public PointInteretDB(int idpoint, String nom, String description,
			double longitude, double latitude) {
		super(idpoint, nom, description, longitude, latitude);
		// TODO Auto-generated constructor stub
	}

	public PointInteretDB( String nom, String description,
			double longitude, double latitude) {
		super( nom, description, longitude, latitude);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param nom
	 * @param description
	 * @param longitude
	 * @param latitude
	 */
	public PointInteretDB(String nom_point, String description,
			String nom_categorie) {
		super(nom_point, description);
		this.nom_categorie = nom_categorie;
		// TODO Auto-generated constructor stub
	}
	
	public PointInteretDB(User iduser,String nom,String description,double longitude,double latitude){
		super(iduser,nom,description,longitude,latitude);
			}

	/**
	 * méthode statique permettant de partager la connexion entre toutes les
	 * instances de PointInteretDB
	 * 
	 * @param nouvdbConnect
	 *            connexion à la base de données
	 */
	public static void setConnection(Connection nouvdbConnect) {
		dbConnect = nouvdbConnect;
	}

	/**
	 * enregistrement d'une nouvelle réservation dans la base de données
	 * 
	 * @throws Exception
	 *             erreur de création
	 */
	@Override
	public void create() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call createpointinteret(?,?,?,?,?,?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			//cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.setInt(2,getUser().getIduser());
			cstmt.setString(3, nom);
			cstmt.setString(4, description);
			cstmt.setDouble(5, longitude);
			cstmt.setDouble(6, latitude);
		} catch (Exception e) {
			throw new Exception("Erreur de création " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * méthode permettant de lire les informations d'un point d'intérêt dans la
	 * base de données
	 * 
	 * @throws Exception
	 *             erreur de lecture
	 * @see entities.CRUD#read()
	 */

	public void read() throws Exception {
		// TODO Auto-generated method stub
		String req = "{?=call readpointinteret(?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setInt(2, idpoint);
			cstmt.executeQuery();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			boolean trouve = false;
			if (rs.next()) {
				trouve = true;
				idpoint = rs.getInt("id_point");
				int iduser = rs.getInt("id_user_user");
				nom = rs.getString("nom_point");
				description = rs.getString("description");
				longitude = rs.getFloat("longitude");
				latitude = rs.getFloat("latitude");
				System.out.println(idpoint + " " + iduser + " " + nom + " "
						+ description + " " + longitude + " " + latitude);
			}
			if (!trouve)
				throw new Exception("numéro de point d'intérêt inconnu");
		} catch (Exception e) {
			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * méthode statique permettant de récupérer tous un point d'interet a apartir de son nom
	 * 
	 * 
	 * @param nomrech
	 *            nom recherché
	 * @return liste d'utilisateurs
	 * @throws Exception
	 *             nom inconnu
	 */
/*	public void rechPointInteretNom() throws Exception {
		// TODO Auto-generated method stub
		String req = "{?=call readpointinteretnom(?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setString(2, nom);
			cstmt.executeQuery();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			boolean trouve = false;
			if (rs.next()) {
				trouve = true;
				String nom_point = rs.getString("nom_point");
				String description = rs.getString("description");
				String nom_categorie = rs.getString("nom_categorie");
				System.out.println(nom_point + " " + description + " "
						+ nom_categorie);
			}
			if (!trouve)
				throw new Exception("nom de point d'intérêt inconnu");
		} catch (Exception e) {
			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}
	
	*/
	
	/**
 * méthode statique permettant de récupérer tous les points d'interet appartenant a une meme categorie
 * 
 * 
 * @param nomrech
 *            nom recherché
 * @return liste d'utilisateurs
 * @throws Exception
 *             nom inconnu
 */
public static ArrayList<PointInteretDB> rechpointinteretnom(String nomrech) throws Exception {
	ArrayList<PointInteretDB> plusieurs = new ArrayList<PointInteretDB>();
	String req = "{?=call readpointinteretnom(?)}";
	CallableStatement cstmt = null;
	try {
		cstmt = dbConnect.prepareCall(req);
		cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
		cstmt.setString(2, nomrech);
		cstmt.executeQuery();
		ResultSet rs = (ResultSet) cstmt.getObject(1);
		boolean trouve = false;
		while (rs.next()) {
			trouve = true;
			String nom_point = rs.getString("nom_point");
			String description = rs.getString("description");
			String nom_categorie = rs.getString("nom_categorie");
			plusieurs.add(new PointInteretDB(nom_point, description,
					nom_categorie));
		}
		if (!trouve)
			throw new Exception("nom inconnu");
		else
			return plusieurs;
	} catch (Exception e) {
		throw new Exception("Erreur de lecture " + e.getMessage());
	} finally {// effectué dans tous les cas
		try {
			cstmt.close();
		} catch (Exception e) {
		}
	}
}


		/**
	 * méthode statique permettant de récupérer tous les points d'interet appartenant a une meme categorie
	 * 
	 * 
	 * @param nomrech
	 *            nom recherché
	 * @return liste d'utilisateurs
	 * @throws Exception
	 *             nom inconnu
	 */
	public static ArrayList<PointInteretDB> rechpointinteretcategorie(
			String catrech) throws Exception {
		ArrayList<PointInteretDB> plusieurs = new ArrayList<PointInteretDB>();
		String req = "{?=call readpointinteretcategorie(?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setString(2, catrech);
			cstmt.executeQuery();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			boolean trouve = false;
			while (rs.next()) {
				trouve = true;
				String nom_point = rs.getString("nom_point");
				String description = rs.getString("description");
				String nom_categorie = rs.getString("nom_categorie");
				plusieurs.add(new PointInteretDB(nom_point, description,
						nom_categorie));
			}
			if (!trouve)
				throw new Exception("categorie inconnu");
			else
				return plusieurs;
		} catch (Exception e) {
			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * méthode statique permettant de récupérer tous les points d'interet par rapport a une certaine distance
	 * 
	 * 
	 * @param nomrech
	 *            nom recherché
	 * @return liste d'utilisateurs
	 * @throws Exception
	 *             nom inconnu
	 */
	public static ArrayList<PointInteretDB> rechpointinteretdistance(
			float longinit, float longfinal, float latinit, float latfinal)
			throws Exception {
		ArrayList<PointInteretDB> plusieurs = new ArrayList<PointInteretDB>();
		String req = "{?=call readpointinteretdistance(?,?,?,?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setFloat(2, longinit);
			cstmt.setFloat(3, longfinal);
			cstmt.setFloat(4, latinit);
			cstmt.setFloat(5, latfinal);
			cstmt.executeQuery();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			boolean trouve = false;
			while (rs.next()) {
				trouve = true;
				String nom_point = rs.getString("nom_point");
				String description = rs.getString("description");
				String nom_categorie = rs.getString("nom_categorie");
				plusieurs.add(new PointInteretDB(nom_point, description,
						nom_categorie));
			}
			if (!trouve)
				throw new Exception("pas de point d'intérêt à cette distance");
			else
				return plusieurs;
		} catch (Exception e) {
			throw new Exception("Erreur de lecture " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * mise à jour du point d'intérêt sur base de son identifiant
	 * 
	 * @throws Exception
	 *             erreur de mise à jour
	 */
	@Override
	public void update() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call updatepointinteret(?,?,?)";
			cstmt = dbConnect.prepareCall(req);
			// PreparedStatement pstm = dbConnect.prepareStatement(req);
			cstmt.setInt(1, idpoint);
			cstmt.setString(2, nom);
			cstmt.setString(3, description);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Erreur de mise à jour " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * effacement d'un utilisateur sur base de son identifiant
	 * 
	 * @throws Exception
	 *             erreur d'effacement
	 */
	@Override
	public void delete() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call delpointinteret(?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, idpoint);
			cstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Erreur d'effacement " + e.getMessage());
		} finally {// effectué dans tous les cas
			try {
				cstmt.close();
			} catch (Exception e) {
			}
		}
	}
}
