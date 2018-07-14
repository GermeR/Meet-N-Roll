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

import types.UserType;
import utils.LoggerUtils;
import utils.SessionUtils;

@WebServlet("/servlet/listeGens")
public class ListUsers extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:80/MeetNRoll";

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		
		if (!SessionUtils.isSessionValide(session))
		{
			response.sendRedirect("../login.html");
		}
		else
		{

			UserType p = (UserType) session.getAttribute("personne");

			out.println("<!DOCTYPE html>" + "<html lang=\"fr\">"
					+ "<head><meta charset=\"utf-8\"><meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">"
					+ "<title>Liste des gens</title>"
					+ "<link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
					+ "<link rel=\"stylesheet\"href=\"/Meet-N-Roll/css/style.css\">" + "</head>" + "<body>"
					+ "<div class=\"container\">" + "<div class=\"page-header\">" + "<center>"
					+ "		<h1 class=\"display-1\">Meet'N'Roll : Rencontrez plus!</h1>" + "</center>" + "</div>");
			// + "<div class=\"row\">"
			// + "<div class=\"col-xs-6 col-xs-offset-3\">");

			out.println("<div class=\"menu\">");
			out.println("<ul class=\"onglets\">");
			out.println("<li><a href=\"/Meet-N-Roll/servlet/Menu\"> Menu </a></li>");
			out.println("<li><a class=\"active\" href=\"/Meet-N-Roll/servlet/profil\"> Profil </a></li>");
			out.println("<li><a href=\"/Meet-N-Roll/servlet/log?delog=true\"> Deconnexion </a></li>");
			out.println("</ul>");
			out.println("</div>");
			out.println("</div>" + "</div>" + "<div class=\"row\">" + "<div class=\"col-xs-6 col-xs-offset-3\">");

			try
			{
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, NOM, MDP);
				statement = connection.createStatement();

				out.println("<div class=\"menu\">");
				out.println("<ul class=\"onglets\">");
				String querry = "SELECT * FROM jeux;";
				resultSet = statement.executeQuery(querry);
				if (resultSet != null)
				{
					if (request.getParameter("type") == null)
					{

						out.println(
								"<li class=\"active\"><a href=\"/Meet-N-Roll/servlet/listeGens\"> Tous les joueurs </a></li>");

						while (resultSet.next())
						{
							out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens?type=" + resultSet.getString(1)
									+ "\"> " + resultSet.getString(1) + " </a></li>");
						}
					}
					else
					{
						out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens\"> Tous les joueurs </a></li>");

						while (resultSet.next())
						{
							if (resultSet.getString(1).equals(request.getParameter("type")))
								out.println("<li class=\"active\"><a href=\"/Meet-N-Roll/servlet/listeGens?type="
										+ resultSet.getString(1) + "\"> " + resultSet.getString(1) + " </a></li>");
							else
								out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens?type="
										+ resultSet.getString(1) + "\"> " + resultSet.getString(1) + " </a></li>");
						}
					}
				}
				out.println("</ul>");
				out.println("</div>");
				// TODO changer le SELECT * par un SELECT des champs utiles
				querry = "SELECT * FROM personne where login !='" + p.getLogin() + request.getParameter("type") != null
						? "' and type = '" + request.getParameter("type") : "" + "';";
				resultSet = statement.executeQuery(querry);
				if (resultSet != null)
				{
					out.println("<table class=\"table table-striped\">");
					out.println("<th>Liste des gens</th>");
					while (resultSet.next())
						out.println("<tr><td><a href=\"profUser?user=" + resultSet.getString(1) + "\">"
								+ resultSet.getString(1) + "</a></td></tr>");
					out.println("</table>");
				}
				out.print("</div></div>");
				out.println("</body></html>");

			}
			catch (ClassNotFoundException e)
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
}
