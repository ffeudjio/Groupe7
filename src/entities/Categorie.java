/**
 * 
 */
package entities;

/**
 * @author user
 *
 */
public class Categorie {
	protected int idcategorie;
	protected String nom;
	/**
	 * @param idcategorie
	 * @param nom
	 */
	public Categorie(int idcategorie, String nom) {
		super();
		this.idcategorie = idcategorie;
		this.nom = nom;
	}
	
	public Categorie(int idcategorie){
		super();
		this.idcategorie=idcategorie;
	}
	
	public Categorie(String nom) {
		super();
		this.nom = nom;
		
	}
	/**
	 * @return the idcategorie
	 */
	
	
	public int getIdcategorie() {
		return idcategorie;
	}
	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @param idcategorie the idcategorie to set
	 */
	public void setIdcategorie(int idcategorie) {
		this.idcategorie = idcategorie;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return "Categorie [idcategorie=" + idcategorie + ", nom=" + nom + "]";
	}
	
	

}
