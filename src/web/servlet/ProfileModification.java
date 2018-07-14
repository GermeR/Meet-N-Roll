package web.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import types.UserType;
import utils.LoggerUtils;
import utils.SessionUtils;

@WebServlet("/servlet/modifProfil")
public class ProfileModification extends HttpServlet
{

	private static final long serialVersionUID = 4530370119054454583L;
	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:80/MeetNRoll";

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		// PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		// ResultSet rs = null;

		if (!SessionUtils.isSessionValide(session))
		{
			// TODO changer page de login pour afficher message personalisé
			// ("vous avez ete deco")
			response.sendRedirect("/Meet-N-Roll/login.html");

		}
		else
		{
			UserType p = (UserType) session.getAttribute("personne");

			if (!request.getParameter("mail").equals(""))
			{
				try
				{
					Class.forName("org.postgresql.Driver");
					connection = DriverManager.getConnection(URL, NOM, MDP);
					statement = connection.createStatement();
					statement.executeUpdate("UPDATE personne " + "SET mail = '" + request.getParameter("mail")
							+ "', naiss = '" + Date.valueOf(request.getParameter("naiss")) + "', nom = '"
							+ request.getParameter("nom") + "', prenom = '" + request.getParameter("prenom")
							+ "' WHERE login = '" + p.getLogin() + "'");
					session.setAttribute("personne",
							new UserType(p.getLogin(), request.getParameter("mail"),
									Date.valueOf(request.getParameter("naiss")), request.getParameter("nom"),
									request.getParameter("prenom")));
					response.sendRedirect("../servlet/profil");
				}
				catch (ClassNotFoundException e)
				{
					LoggerUtils.writeException(e);
				}
				catch (SQLException e)
				{
					LoggerUtils.writeException(e);
					// TODO changer pour rajouter message erreur dans la page
					// HTML
					response.sendRedirect("../servlet/profil");
				}
				finally
				{
					try
					{
						if (statement != null)
						{
							statement.close();
						}
						if (connection != null)
						{
							connection.close();
						}
					}
					catch (SQLException e)
					{
						LoggerUtils.writeException(e);
					}
				}
			}
			else
			{
				response.sendRedirect("../new.html");
			}
		}
	}
}
