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
public class ServletProfil extends HttpServlet {

	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:22042/MeetNRoll";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();

		if (session.getAttribute("personne") == null)
			res.sendRedirect("../login.html");

		Personne p = ((Personne) session.getAttribute("personne"));

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM joueurs where login='" + p.getLogin() + "';";

		out.println("<!DOCTYPE html>"
				+ "<html lang=\"fr\">"
				+ "<head>"
				+ "		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				+ "		<meta http-equiv='X-UA-Compatible' content='IE=edge'>"
				+ "		<meta name='viewport' content='width=device-width, initial-scale=1'>"
				+ "		<link rel=\"stylesheet\" href=\"StyleDevoir.css\" />"
				+ "<title>Meet'N'Role: Profil</title>"
				+ "</head>"
				+ "<body>"
				+"<h1>Profil</h1>"
				//login
				+ "<h2>" + p.getLogin() + "</h2>"
				//nom
				+ "<p>" + p.getNom() + "</p>"
				//Prenom
				+ "<p>" + p.getPrenom() + "</p>"
				+ "<table>");
		
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, NOM, MDP);
			stmt = con.createStatement();
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			out.println("<th>JDR favoris</th>");
			while (rs.next())
				out.println("<tr><td>" + rs.getString(2) + "</td></tr>");
			out.println("</table></body></html>");
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
