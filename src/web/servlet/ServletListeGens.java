package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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

@WebServlet("/servlet/listeGens")
public class ServletListeGens extends HttpServlet {

	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:22042/MeetNRoll";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		PrintWriter out = res.getWriter();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		if (session == null)
			System.out.println("session = null");
		if (session.getAttribute("personne") == null)
			System.out.println("personne = null");
		if (session == null || session.getAttribute("personne") == null) {
			res.sendRedirect("../login.html");
		} else {

			Personne p = (Personne) session.getAttribute("personne");
			

			out.println("<!DOCTYPE html>" + "<html lang=\"fr\">"
					+ "<head><meta charset=\"utf-8\"><meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">"
					+ "<title>Creation de Compte</title>"
					+ "<link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
					+ "<link rel=\"stylesheet\"href=\"/Meet-N-Roll/css/style.css\">" + "</head>" + "<body>"
					+ "<div class=\"container\">" + "<div class=\"page-header\">" + "<center>"
					+ "		<h1 class=\"display-1\">Meet'N'Roll : Profil</h1>" + "</center>" + "</div>"
					+ "<div class=\"row\">" + "<div class=\"col-xs-6 col-xs-offset-3\">"
					+ "<a href=\"profil\" class=\"btn btn-primary\"role=\"button\">Profil</a>"
					+ "<a href=\"/Meet-N-Roll/menu.html\" class=\"btn btn-primary\"role=\"button\">Menu</a>" + "</div>"
					+ "</div>" + "<div class=\"row\">" + "<div class=\"col-xs-6 col-xs-offset-3\">");

			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(URL, NOM, MDP);
				stmt = con.createStatement();
				

				if (req.getParameter("type") == null) {
					
					String sql = "SELECT * FROM personne where login !='" + p.getLogin() + "';";
					rs = stmt.executeQuery(sql);
					
					out.println("<div id=\"menu\">");
					out.println("<ul id=\"onglets\">");
					out.println(
							"<li class=\"active\"><a href=\"/Meet-N-Roll/servlet/listeGens\"> Tous les joueurs </a></li>");
					out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens?type=hf\"> Heroic Fantaisy </a></li>");
					out.println(
							"<li><a href=\"/Meet-N-Roll/servlet/listeGens?type=mf\">  Medieval Fantastique </a></li>");
					out.println("</ul>");
					out.println("</div>");
				} else if (req.getParameter("type").equals("hf")) {
					
					String sql = "SELECT login FROM joueurs where login !='" + p.getLogin() + "' and type = 'Heroic_Fantaisy';";
					rs = stmt.executeQuery(sql);
					
					out.println("<div id=\"menu\">");
					out.println("<ul id=\"onglets\">");
					out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens\"> Tous les joueurs </a></li>");
					out.println(
							"<li class=\"active\"><a href=\"/Meet-N-Roll/servlet/listeGens?type=hf\"> Heroic Fantaisy </a></li>");
					out.println(
							"<li><a href=\"/Meet-N-Roll/servlet/listeGens?type=mf\">  Medieval Fantastique </a></li>");
					out.println("</ul>");
					out.println("</div>");
				} else if (req.getParameter("type").equals("mf")) {
					
					String sql = "SELECT login FROM joueurs where login !='" + p.getLogin() + "' and type = 'Medieval_Fantastique';";
					rs = stmt.executeQuery(sql);
					
					out.println("<div id=\"menu\">");
					out.println("<ul id=\"onglets\">");
					out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens\"> Tous les joueurs </a></li>");
					out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens?type=hf\"> Heroic Fantaisy </a></li>");
					out.println(
							"<li class=\"active\"><a href=\"/Meet-N-Roll/servlet/listeGens?type=mf\">  Medieval Fantastique </a></li>");
					out.println("</ul>");
					out.println("</div>");
				}

				out.println("<table class=\"table table-striped\">");
				out.println("<th>Liste des gens</th>");
				while (rs.next())
					out.println("<tr><td><a href=\"profUser?user=" + rs.getString(1) + "\">" + rs.getString(1)
							+ "</a></td></tr>");
				out.println("</table>");
				out.print("</div></div>");
				out.println("</body></html>");

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
