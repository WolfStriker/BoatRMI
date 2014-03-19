package bean;

import java.io.Serializable;

public class Groupe implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	
	public Groupe(String n){
		this.nom = n;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
