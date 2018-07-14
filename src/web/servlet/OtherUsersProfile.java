package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.LoggerUtils;
import utils.SessionUtils;

@WebServlet("/servlet/profUser")
public class OtherUsersProfile extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:80/MeetNRoll";

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		if (!SessionUtils.isSessionValide(session))
		{
			// TODO changer page de login pour afficher message personalisé
			// ("vous avez ete deco")
			response.sendRedirect("/Meet-N-Roll/login.html");
		}
		else
		{

			// Personne p = ((Personne) session.getAttribute("personne"));

			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			String querry = "SELECT * FROM personne where login='" + request.getParameter("user") + "';";

			out.println("<!DOCTYPE html>" + "<html lang=\"fr\">" + "<head><meta charset=\"utf-8\">"
					+ "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">" + "<title>Profil de "
					+ request.getParameter("user") + "</title>"
					+ "<link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
					+ "</head>" + "<body>" + "<div class=\"container\">" + "<div class=\"page-header\">"
					+ "<center><h1 class=\"display-1\">Meet'N'Roll : Profil de " + request.getParameter("user")
					+ "</h1>" + "</center></d" + "iv>" + "<div class=\"row\">"
					+ "<div class=\"col-xs-6 col-xs-offset-3\">"
					+ "<a href=\"profil\" class=\"btn btn-primary\"role=\"button\">Profil</a>"
					+ "<a href=\"/Meet-N-Roll/servlet/Menu\" class=\"btn btn-primary\"role=\"button\">Menu</a>"
					+ "<a href=\"log?delog=true\" class=\"btn btn-primary\"role=\"button\">Deconnexion</a>"
					+ "</div></div><div class=\"row\"><div class=\"col-xs-6 col-xs-offset-3\">");
			try
			{
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, NOM, MDP);
				statement = connection.createStatement();
				resultSet = statement.executeQuery(querry);

				if (resultSet.next())
				{
					out.println("<table>");

					out.println("<th>" + resultSet.getString(1) + "</th>");
					out.println("<tr><td>Nom: </td><td>" + resultSet.getString(5) + "</td></tr>");
					out.println("<tr><td>Prenom: </td><td>" + resultSet.getString(6) + "</td></tr>");
					out.println("<tr><td>E-Mail: </td><td>" + resultSet.getString(3) + "</td></tr>");

					out.println("</table>");

				}

				querry = "SELECT * FROM joueurs where login='" + request.getParameter("user") + "';";
				resultSet = statement.executeQuery(querry);

				out.println("<table class=\"table table-striped\">");
				out.println("<th>JDR favoris</th>");
				while (resultSet.next())
					out.println("<tr><td>" + fromMatch(resultSet.getString(2)) + "</td></tr>");
				out.println("</table>");
				out.print("</div></div>");
				out.println("</body></html>");
			}
			catch (

			ClassNotFoundException e)
			{
				LoggerUtils.writeException(e);
			}
			catch (SQLException e)
			{
				LoggerUtils.writeException(e);
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
	}

	private String fromMatch(String str)
	{
		String rez = "";
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == '_')
				rez += " ";
			else
				rez += str.charAt(i);
		return rez;
	}
}
