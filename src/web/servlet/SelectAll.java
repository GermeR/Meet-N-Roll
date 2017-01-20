package web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.struct.Personne;

@WebServlet("/servlet/SelectAll")
public class SelectAll extends HttpServlet {

	static final String NOM = "germer";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://psqlserv/n3p1";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(true);

		if (((Personne) session.getAttribute("personne")) == null) {
			res.sendRedirect("../login.html");
		} else {
			if (((Personne) session.getAttribute("personne")).getRole().equals("etu")) {
				res.sendRedirect("../menu.jsp");
			}

			Connection con = null;
			Statement stmt = null;
			ResultSet rsAbs = null;
		
			String sql = "SELECT p.prenom,p.nom,a.date,a.demiJ FROM personne as p inner join absences as a on a.login = p.login WHERE p.role = 'etu' and p.login='";
			ArrayList<String> personnes = new ArrayList<String>();
			String sqlJus = "SELECT date from justif where login='";
			ArrayList<String> justifs = new ArrayList<String>();

			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(URL, NOM, MDP);
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("Select login from personne where role ='etu';");
				while (rs.next())
					personnes.add(rs.getString(1));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			out.println("<!DOCTYPE html><html lang='fr'>"
					+ "<head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>"
					+ "<title>Vos Absences</title>"
					+ "<!-- Bootstrap core CSS --><link href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' rel='stylesheet'>"
					+ "</head>");
			out.println("<body>");
			out.println("<center><table class='table table-bordered'>");
			try {
				out.println("<th>prenom</th><th>nom</th><th>date</th><th>horaire</th><");
				for (String str : personnes) {
					rsAbs = stmt.executeQuery(sql + str + "';");
					while (rsAbs.next()) {
							out.println("<tr><td>" + rsAbs.getString(1) + "</td><td>" + rsAbs.getString(2) + "</td><td>"
									+ rsAbs.getString(3) + "</td><td>" + rsAbs.getString(4)</tr>");

					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			out.println("</table><center>");
			out.println("</body>");
			out.println("</html>");

			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}