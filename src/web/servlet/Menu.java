package web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servlet/Menu")
public class Menu extends HttpServlet
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

			// Personne p = ((Personne) session.getAttribute("personne"));

			out.println("<!DOCTYPE html>" + "<html lang=\"fr\">" + "<head>" + "<meta charset=\"utf-8\">"
					+ "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">" + "<title>Menu</title>"
					+ "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">");
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=\"container\">");
			out.println("<div class=\"page-header\">");
			out.println("<center>");
			out.println("<h1 class=\"display-1\">Meet'N'Roll : Menu</h1>");
			out.println("</center>");
			out.println("</div>");
			out.println("<div class=\"row\">");
			out.println("<div class=\"col-xs-6 col-xs-offset-3\">");
			out.println("<a href=\"/Meet-N-Roll/servlet/profil\" class=\"btn btn-primary\" role=\"button\">Profil</a>");
			out.println(
					"<a href=\"/Meet-N-Roll/servlet/listeGens\" class=\"btn btn-primary\"role=\"button\">Liste des gens</a>");
			out.println(
					"<a href=\"/Meet-N-Roll/servlet/log?delog=true\" class=\"btn btn-primary\"role=\"button\">Deconnexion</a>");
			out.println("</div>");
			out.println("</div>");
			out.println(lectureLogs());

			out.println("</body></html>");
		}
	}

	private String lectureLogs()
	{
		String rez = "";

		BufferedReader bf = null;

		try
		{
			bf = Files.newBufferedReader(Paths.get("README.md"));
			String ligne;
			while ((ligne = bf.readLine()) != null)
			{
				if (ligne.startsWith("\t"))
					rez += "<h2>" + ligne + "</h2>";
				if (ligne.substring(0, 9).matches("[0-9]*[/]*[0-9]*[/]*[0-9]"))
					rez += "<h3>" + ligne + "</h3>";
				if (ligne.startsWith("#"))
				{
				}
				if (ligne.startsWith("["))
					rez += "<ul>";
				if (ligne.startsWith("]"))
					rez += "</ul>";
				if (ligne.startsWith("."))
					rez += "<li>" + ligne + "</li>";

				else
					rez += "<p>" + ligne + "</p>";

			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return rez;
	}
}
