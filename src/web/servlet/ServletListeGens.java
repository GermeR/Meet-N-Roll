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

			out.println("<!DOCTYPE html>" 
					+ "<html lang=\"fr\">"
					+ "<head><meta charset=\"utf-8\"><meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">"
					+ "<title>Liste des gens</title>"
					+ "<link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
					+ "<link rel=\"stylesheet\"href=\"/Meet-N-Roll/css/style.css\">" 
					+ "</head>" 
					+ "<body>"
					+ "<div class=\"container\">" 
					+ "<div class=\"page-header\">" 
					+ "<center>"
					+ "		<h1 class=\"display-1\">Meet'N'Roll : Rencontrez plus!</h1>" 
					+ "</center>" 
					+ "</div>");
					//+ "<div class=\"row\">" 
					//+ "<div class=\"col-xs-6 col-xs-offset-3\">");
			
					out.println("<div class=\"menu\">");
					out.println("<ul class=\"onglets\">");
					out.println("<li><a href=\"/Meet-N-Roll/servlet/Menu\"> Menu </a></li>");
					out.println("<li><a class=\"active\" href=\"/Meet-N-Roll/servlet/profil\"> Profil </a></li>");
					out.println("<li><a href=\"/Meet-N-Roll/servlet/log?delog=true\"> Deconnexion </a></li>");
					out.println("</ul>");
					out.println("</div>"); 
					out.println("</div>"
					+ "</div>" 
					+ "<div class=\"row\">" 
					+ "<div class=\"col-xs-6 col-xs-offset-3\">");

			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(URL, NOM, MDP);
				stmt = con.createStatement();


				out.println("<div class=\"menu\">");
				out.println("<ul class=\"onglets\">");
				
				if (req.getParameter("type") == null) {
					String sql = "SELECT * FROM jeux;";
					rs = stmt.executeQuery(sql);
					out.println(
							"<li class=\"active\"><a href=\"/Meet-N-Roll/servlet/listeGens\"> Tous les joueurs </a></li>");

					while (rs.next()) {
						out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens?type=" + rs.getString(1) + "\"> "
								+ rs.getString(1) + " </a></li>");
					}

					out.println("</ul>");
					out.println("</div>");

					sql = "SELECT * FROM personne where login !='" + p.getLogin() + "';";
					rs = stmt.executeQuery(sql);
				} else {

					String sql = "SELECT * FROM jeux;";
					rs = stmt.executeQuery(sql);

					
					out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens\"> Tous les joueurs </a></li>");

					while (rs.next()) {
						if (rs.getString(1).equals(req.getParameter("type")))
							out.println("<li class=\"active\"><a href=\"/Meet-N-Roll/servlet/listeGens?type="
									+ rs.getString(1) + "\"> " + rs.getString(1) + " </a></li>");
						else
							out.println("<li><a href=\"/Meet-N-Roll/servlet/listeGens?type=" + rs.getString(1) + "\"> "
									+ rs.getString(1) + " </a></li>");
					}

					out.println("</ul>");
					out.println("</div>");

					sql = "SELECT login FROM joueurs where login !='" + p.getLogin()
							+ "' and type = '" + req.getParameter("type") + "';";
					rs = stmt.executeQuery(sql);
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
