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
			String sql = "SELECT * FROM joueurs where login='" + p.getLogin() + "';";
			out.println(
					"<!DOCTYPE html><html lang=\"fr\"><head><meta charset=\"utf-8\"><meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"><meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"><title>Creation de Compte</title><link rel=\"stylesheet\"href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\"></head><body><div class=\"container\"><div class=\"page-header\"><center><h1 class=\"display-1\">Meet'N'Roll : Profil</h1></center></div><div class=\"row\"><div class=\"col-xs-6 col-xs-offset-3\"><a href=\"servlet/log?delog=true\" class=\"btn btn-primary\"role=\"button\">Deconnexion</a></div></div><div class=\"row\"><div class=\"col-xs-6 col-xs-offset-3\">");
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(URL, NOM, MDP);
				stmt = con.createStatement();
				if (req.getParameter("add") != null && req.getParameter("add").length() > 1) {
					stmt.execute(
							"insert into joueurs values('" + p.getLogin() + "','" + req.getParameter("add") + "');");
					res.sendRedirect("profil");
				}
				if (req.getParameter("del") != null && req.getParameter("del").length() > 1) {
					stmt.execute("delete from joueurs where login='" + p.getLogin() + "' and type='"
							+ req.getParameter("del") + "';");

					res.sendRedirect("profil");
				}

				rs = stmt.executeQuery(sql);
				out.println("<table class=\"table table-striped\">");
				out.println("<th>JDR favoris</th>");
				while (rs.next())
					out.println("<tr><td><a href =profil?del=" + rs.getString(2) + ">" + fromMatch(rs.getString(2)) +"</td></tr>");
				out.println("</table>");
				sql = "select * from jeux where type not in (select type from joueurs where login ='" + p.getLogin()
						+ "');";
				rs = stmt.executeQuery(sql);
				out.println("<table class=\"table table-striped\">");
				out.println("<th> JDR DISPONIBLES </th>");
				while (rs.next())
					out.println("<tr><td><a href=profil?add=" + rs.getString(1) + ">" + fromMatch(rs.getString(1))
							+ "</a></td></tr>");
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
