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

@WebServlet("/servlet/profUser")
public class ServletProfilAutreUser extends HttpServlet {

	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:22042/MeetNRoll";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();

		if (session == null)
			System.out.println("session = null");
		if (session.getAttribute("personne") == null)
			System.out.println("personne = null");
		if (session == null || session.getAttribute("personne") == null) {
			res.sendRedirect("../login.html");
		} else {

			Personne p = ((Personne) session.getAttribute("personne"));

			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			String sql = "SELECT * FROM personne where login='" + req.getParameter("user") + "';";
			
			
			out.println("<!DOCTYPE html>"
					+ "<html lang=\"fr\">"
					+ "<head><meta charset=\"utf-8\">"
					+ "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\">"
					+ "<title>Profil de "+ req.getParameter("user") + "</title>"
					+ "<link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"
					+ "</head>"
					+ "<body>"
					+ "<div class=\"container\">"
					+ "<div class=\"page-header\">"
					+ "<center><h1 class=\"display-1\">Meet'N'Roll : Profil de "+ req.getParameter("user") + "</h1>"
					+ "</center></d"
					+ "iv>"
					+ "<div class=\"row\">"
					+ "<div class=\"col-xs-6 col-xs-offset-3\">"
					+ "<a href=\"profil\" class=\"btn btn-primary\"role=\"button\">Profil</a>"
					+ "<a href=\"/Meet-N-Roll/servlet/Menu\" class=\"btn btn-primary\"role=\"button\">Menu</a>"
					+ "<a href=\"log?delog=true\" class=\"btn btn-primary\"role=\"button\">Deconnexion</a>"
					+ "</div></div><div class=\"row\"><div class=\"col-xs-6 col-xs-offset-3\">");
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(URL, NOM, MDP);
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					out.println("<table>");
					
					out.println("<th>" + rs.getString(1)+ "</th>");
					out.println("<tr><td>Nom: </td><td>"+ rs.getString(5) +"</td></tr>");
					out.println("<tr><td>Prenom: </td><td>"+ rs.getString(6) +"</td></tr>");
					out.println("<tr><td>E-Mail: </td><td>"+ rs.getString(3) +"</td></tr>");
					
					out.println("</table>");
				
				}
				
				
				sql = "SELECT * FROM joueurs where login='" + req.getParameter("user") + "';";
				rs = stmt.executeQuery(sql);
				
				
				
				out.println("<table class=\"table table-striped\">");
				out.println("<th>JDR favoris</th>");
				while (rs.next())
					out.println("<tr><td>" + fromMatch(rs.getString(2)) +"</td></tr>");
				out.println("</table>");
				out.print("</div></div>");
				out.println("</body></html>");
			} catch (

			ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String fromMatch(String str) {
		String rez = "";
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == '_')
				rez += " ";
			else
				rez += str.charAt(i);
		return rez;
	}
}
