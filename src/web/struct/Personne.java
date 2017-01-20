package web.struct;

public class Personne {
	
	private String login;
	private String prenom;
	private String nom;
	private String mail;
	private String tel;
	private String role;
	
	public Personne(String login, String prenom, String nom, String mail, String tel, String role) {
		super();
		this.login = login;
		this.prenom = prenom;
		this.nom = nom;
		this.mail = mail;
		this.tel = tel;
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "Personne [login=" + login + ", prenom=" + prenom + ", nom=" + nom + ", mail=" + mail + ", tel=" + tel
				+ ", role=" + role + "]";
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}