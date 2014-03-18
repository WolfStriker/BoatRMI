package db;

public class Boat {
	private String nom;
	private String notice;
	private String photo;
	private int groupe;
	
	public Boat(String nom, String notice, String photo, int gr){
		this.nom = nom;
		this.notice = notice;
		this.photo = photo;
		this.groupe = gr;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getGroupe() {
		return groupe;
	}

	public void setGroupe(int groupe) {
		this.groupe = groupe;
	}
}
