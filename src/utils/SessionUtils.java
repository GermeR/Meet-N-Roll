package utils;

import javax.servlet.http.HttpSession;

public class SessionUtils
{
	public static boolean isSessionValide(HttpSession pSession)
	{
		if (pSession == null)
		{
			System.out.println("session = null");
			return false;
		}
		if (pSession.getAttribute("personne") == null)
		{
			System.out.println("personne = null");
			return false;
		}
		return true;
	}

}
