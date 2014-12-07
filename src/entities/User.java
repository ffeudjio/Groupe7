/**
 * 
 */
package entities;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author user
 *
 */
public class User implements Parcelable {
	protected int iduser;
	protected String nom;
	protected String prenom;
	protected String login;
	protected String password;

	/**
	 * Default constructor
	 */
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param iduser
	 * @param nom
	 * @param prenom
	 * @param login
	 * @param password
	 */
	public User(int iduser, String nom, String prenom, String login,
			String password) {
		super();
		this.iduser = iduser;
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.password = password;
	}

	/**
	 * constructeur permettant de mettre à jour le login de l'utilisateur sur
	 * base de son identifiant
	 * 
	 * @see #updateuserlogin()
	 * @param iduser
	 */
	public User(int iduser, String login) {
		super();
		this.iduser = iduser;
		this.login = login;
	}

	/**
	 * constructeur permettant de mettre à jour le login de l'utilisateur sur
	 * base de son identifiant
	 * 
	 * @see #updateuserlogin()
	 * @param login
	 * @param password
	 */
	public User(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	/**
	 * @return the iduser
	 */
	public int getIduser() {
		return iduser;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param iduser
	 *            the iduser to set
	 */
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * methode toString
	 * 
	 * @toString affiche les informations de l'utilisateur
	 */
	@Override
	public String toString() {
		return "User{" + "iduser=" + iduser + ", nom=" + nom + ", prenom="
				+ prenom + ", login=" + login + ", password=" + password + '}';
	}
	
	@Override
	public int describeContents() {
	  //On renvoie 0, car notre classe ne contient pas de FileDescriptor
	  return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
	  // On ajoute les objets dans l'ordre dans lequel on les a déclarés
	  //dest.writeInt(iduser);
	  dest.writeString(nom);
	  dest.writeString(prenom);
	  dest.writeString(login);	  
	  dest.writeString(password);
	}

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
	  @Override
	  public User createFromParcel(Parcel source) {
	    return new User(source);
	  }
	  @Override
	  public User[] newArray(int size) {
	    return new User[size];
	  }
	};
	public User(Parcel in) {//operation inverse c-a-d avec un parcel on reconstruire une personne 
		  nom = in.readString();
		  prenom = in.readString();
		  login = in.readString();
		  password=in.readString();
		}
}


