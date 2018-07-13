package types;

import java.util.Date;

/**
 * Objet regroupant les informations de l'utilisateur courant
 * @author gaut-vador
 *
 */
public class UserType
{

	private String login;
	private String firstName;
	private String name;
	private String mail;
	private Date birthday;

	public UserType(String pLogin, String pMail, Date pBirthday, String pName, String pFirstName)
	{
		super();
		this.login = pLogin;
		this.firstName = pFirstName;
		this.name = pName;
		this.mail = pMail;
		this.birthday = pBirthday;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String pLogin)
	{
		this.login = pLogin;
	}

	public String getPrenom()
	{
		return firstName;
	}

	public void setPrenom(String pFirstName)
	{
		this.firstName = pFirstName;
	}

	public String getNom()
	{
		return name;
	}

	public void setNom(String pName)
	{
		this.name = pName;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String pMail)
	{
		this.mail = pMail;
	}

	public Date getNaiss()
	{
		return birthday;
	}

	public void setNaiss(Date pBirthday)
	{
		this.birthday = pBirthday;
	}
}