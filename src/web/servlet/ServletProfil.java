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

import web.struct.Personne;

@WebServlet("/servlet/profil")
public class ServletProfil extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:80/MeetNRoll";

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		// TODO changer methode de log
		if (session == null)
		{
			System.out.println("session = null");
		}
		if (session.getAttribute("personne") == null)
		{
			System.out.println("personne = null");
		}
		if (session == null || session.getAttribute("personne") == null)
		{
			// TODO changer page de login pour afficher message personalisé
			// ("vous avez ete deco")
			response.sendRedirect("/Meet-N-Roll/login.html");

		}
		else
		{

			Personne p = ((Personne) session.getAttribute("personne"));

			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			String querry = null;

			out.println("<!DOCTYPE html>" + "<html lang=\"fr\">" + "<head><meta charset=\"utf-8\">"
					+ "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">"
					+ "<title>Profil</title>"
					+ "<link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
					+ "<link rel=\"stylesheet\"href=\"/Meet-N-Roll/css/style.css\">" + "</head>" + "<body>"
					+ "<div class=\"container\">" + "<div class=\"page-header\">"
					+ "<center><h1 class=\"display-1\">Meet'N'Roll : Profil</h1></center>" + "</div>");

			out.println("<div class=\"menu\">");
			out.println("<ul class=\"onglets\">");
			out.println("<li><a href=\"/Meet-N-Roll/servlet/Menu\"> Menu </a></li>");
			out.println("<li><a class=\"active\" href=\"/Meet-N-Roll/servlet/profil\"> Profil </a></li>");
			out.println("<li><a href=\"/Meet-N-Roll/servlet/log?delog=true\"> Deconnexion </a></li>");
			out.println("</ul>");
			out.println("</div>");

			// + "<div class=\"row\"><div class=\"col-xs-6 col-xs-offset-3\">"
			// + "<a href=\"/Meet-N-Roll/menu.html\" class=\"btn
			// btn-primary\"role=\"button\">Menu</a>"
			// + "<a href=\"log?delog=true\" class=\"btn
			// btn-primary\"role=\"button\">Deconnexion</a>" + "</div>"
			out.println("</div>" + "<div class=\"row\">" + "<div class=\"col-xs-6 col-xs-offset-3\">");

			try
			{
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, NOM, MDP);
				statement = connection.createStatement();
				if (request.getParameter("add") != null && request.getParameter("add").length() > 1)
				{
					statement.execute(
							"insert into joueurs values('" + p.getLogin() + "','" + request.getParameter("add") + "');");
					response.sendRedirect("profil");
				}
				if (request.getParameter("del") != null && request.getParameter("del").length() > 1)
				{
					statement.execute("delete from joueurs where login='" + p.getLogin() + "' and type='"
							+ request.getParameter("del") + "';");

					response.sendRedirect("profil");
				}

				querry = "SELECT * FROM personne where login='" + p.getLogin() + "';";
				resultSet = statement.executeQuery(querry);

				out.println("<div id=\"Global\">");
				// info sur le profil
				out.println("<div id=\"gauche\">");
				while (resultSet.next())
				{
					out.println("<table>");

					out.println("<th>" + resultSet.getString(1) + "</th>");
					out.println("<tr><td>Nom: </td><td>" + resultSet.getString(5) + "</td></tr>");
					out.println("<tr><td>Prenom: </td><td>" + resultSet.getString(6) + "</td></tr>");
					out.println("<tr><td>E-Mail: </td><td>" + resultSet.getString(3) + "</td></tr>");

					out.println("</table>");

					out.println(
							"<a href=\"/Meet-N-Roll/modifProfil.jsp\" class=\"btn btn-primary\"role=\"button\">Modifier profil</a>");
				}

				out.println("</div>");

				// jdr joués
				out.println("<div id=\"droite\">");

				out.println("<table class=\"table table-striped\">");
				out.println("<th>JDR favoris</th>");
				querry = "SELECT * FROM joueurs where login='" + p.getLogin() + "';";
				resultSet = statement.executeQuery(querry);

				while (resultSet.next())
					out.println("<tr><td><a href =profil?del=" + resultSet.getString(2) + ">" + fromMatch(resultSet.getString(2))
							+ "</td></tr>");
				out.println("</table>");
				querry = "select * from jeux where type not in (select type from joueurs where login ='" + p.getLogin()
						+ "');";
				resultSet = statement.executeQuery(querry);
				out.println("<table class=\"table table-striped\">");
				out.println("<th> JDR DISPONIBLES </th>");
				while (resultSet.next())
					out.println("<tr><td><a href=profil?add=" + resultSet.getString(1) + ">" + fromMatch(resultSet.getString(1))
							+ "</a></td></tr>");
				out.println("</table>");
				out.println("</div>");
				out.println("</div>");

				out.print("</div></div>");
				out.println("</body></html>");
			}
			catch (

			ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
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
					e.printStackTrace();
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
