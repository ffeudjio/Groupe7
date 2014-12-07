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
public class UserDB extends User implements CRUD {
	/**
	 * connexion à la base de données partagée entre toutes les
	 * instances(statique)
	 */
	protected static Connection dbConnect = null;

	/**
	 * constructeur par défaut
	 */
	public UserDB() {
	}

	/**
	 * constructeur paramétré à utiliser lorsque l'utilisateur est déjà présent
	 * dans la base de données
	 * 
	 * @param iduser
	 * @param nom
	 * @param prenom
	 * @param login
	 * @param password
	 */
	public UserDB(int iduser, String nom, String prenom, String login,
			String password) {
		super(iduser, nom, prenom, login, password);
		// TODO Auto-generated constructor stub
	}

	/**
	 * constructeur paramétré à utiliser lors de la création, l'iduser ne doit
	 * pas être précisé,il sera affecté par la base de données lors de la
	 * création
	 * 
	 * @param nom
	 * @param prenom
	 * @param login
	 * @param password
	 */
	public UserDB(String nom, String prenom, String login, String password) {
		super(0, nom, prenom, login, password);
	}

	/**
	 * constructeur à utiliser lorsque l'utilisateur est déjà présent en base de
	 * données mais qu'on ne connaît que son identifiant, à utiliser avec read
	 * 
	 * @see #read()
	 * @param iduser
	 */
	public UserDB(int iduser) {
		super(iduser, "", "", "", "");
	}

	/**
	 * constructeur permettant de mettre à jour le login de l'utilisateur sur
	 * base de son identifiant
	 * 
	 * @see #updateuserlogin()
	 * @param iduser
	 */
	public UserDB(int iduser, String login) {
		super(iduser, login);
	}

	/**
	 * constructeur permettant de mettre à jour le login de l'utilisateur sur
	 * base de son identifiant
	 * 
	 * @see #updateuserlogin()
	 * @param iduser
	 */
	public UserDB(String login, String password) {
		super(login, password);
	}

	/**
	 * méthode statique permettant de partager la connexion entre toutes les
	 * instances de UserDB
	 * 
	 * @param nouvdbConnect
	 *            connexion à la base de données
	 */
	public static void setConnection(Connection nouvdbConnect) {
		dbConnect = nouvdbConnect;
	}

	/**
	 * méthode permettant d'enregistrer un nouvel utilisateur dans la base de
	 * données
	 * 
	 * @throws Exception
	 *             erreur de création
	 * @see entities.CRUD#create()
	 */
	@Override
	public void create() throws Exception {
		// TODO Auto-generated method stub
		CallableStatement cstmt = null;
		try {
			String req = "call createuser(?,?,?,?,?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
			cstmt.setString(2, nom);
			cstmt.setString(3, prenom);
			cstmt.setString(4, login);
			cstmt.setString(5, password);
			cstmt.executeUpdate();
			//System.out.println("creation.... ");
			this.iduser = cstmt.getInt(1);
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
	 * méthode permettant de lire les informations d'un utilisateur dans la base
	 * de données
	 * 
	 * @throws Exception
	 *             erreur de lecture
	 * @see entities.CRUD#read()
	 */
	@Override
	public void read() throws Exception {
		// TODO Auto-generated method stub
		String req = "{?=call readuser(?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setInt(2, iduser);
			cstmt.executeQuery();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			boolean trouve = false;
			if (rs.next()) {
				trouve = true;
				iduser = rs.getInt("id_user");
				nom = rs.getString("nom_user");
				prenom = rs.getString("prenom_user");
				login = rs.getString("login");
				password = rs.getString("password");
				System.out.println(iduser + " " + nom + " " + prenom + " "
						+ login + " " + password);
			}
			if (!trouve)
				throw new Exception("numéro d'utilisateur inconnu");
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
	 * méthode permettant de lire les informations d'un utilisateur dans la base
	 * de données
	 * 
	 * @throws Exception
	 *             erreur de lecture
	 * @see entities.CRUD#read()
	 */
	public void connexion() throws Exception {
		// TODO Auto-generated method stub
		String req = "{?=call read_connexion_user(?,?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = dbConnect.prepareCall(req);
			cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			cstmt.setString(2, login);
			cstmt.setString(3, password);
			cstmt.executeQuery();
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			boolean trouve = false;
			if (rs.next()) {
				trouve = true;
				login = rs.getString("login");
				password = rs.getString("password");
				System.out.println(iduser + " " + nom + " " + prenom + " "
						+ login + " " + password+"trouve :"+trouve);
			}
			if (!trouve)
				throw new Exception("echec de connexion ");
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
	 * méthode statique permettant de récupérer tous les utilisateurs portant un
	 * certain nom
	 * 
	 * @param nomrech
	 *            nom recherché
	 * @return liste d'utilisateurs
	 * @throws Exception
	 *             nom inconnu
	 */
	public static ArrayList<UserDB> rechNom(String nomrech) throws Exception {
		ArrayList<UserDB> plusieurs = new ArrayList<UserDB>();
		String req = "{?=call readusernom(?)}";
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
				int iduser = rs.getInt("id_user");
				String nom = rs.getString("nom_user");
				String prenom = rs.getString("prenom_user");
				String login = rs.getString("login");
				String password = rs.getString("password");
				plusieurs.add(new UserDB(iduser, nom, prenom, login, password));
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
	 * mise à jour du login l'utilisateur sur base de son identifiant
	 * 
	 * @throws Exception
	 *             erreur de mise à jour
	 */
	@Override
	public void update() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call updateuserlogin(?,?)";
			cstmt = dbConnect.prepareCall(req);
			// PreparedStatement pstm = dbConnect.prepareStatement(req);
			cstmt.setInt(1, iduser);
			cstmt.setString(2, login);
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
	 * mise à jour du mot de passe de l'utilisateur sur base de son login
	 * 
	 * @throws Exception
	 *             erreur de mise à jour
	 */
	public void misajour() throws Exception {
		CallableStatement cstmt = null;
		try {
			String req = "call updateuserpassword(?,?)";
			cstmt = dbConnect.prepareCall(req);
			// PreparedStatement pstm = dbConnect.prepareStatement(req);
			cstmt.setString(1, login);
			cstmt.setString(2, password);
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
			String req = "call deluser(?)";
			cstmt = dbConnect.prepareCall(req);
			cstmt.setInt(1, iduser);
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
