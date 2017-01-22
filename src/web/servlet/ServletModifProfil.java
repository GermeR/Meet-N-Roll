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

@WebServlet("/servlet/modifProfil")
public class ServletModifProfil extends HttpServlet {

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

			if (!req.getParameter("mail").equals("")) {
				try {
					Class.forName("org.postgresql.Driver");
					con = DriverManager.getConnection(URL, NOM, MDP);
					stmt = con.createStatement();
					session.setAttribute("personne",
							new Personne(p.getLogin(), req.getParameter("mail"),
									Date.valueOf(req.getParameter("naiss")), req.getParameter("nom"),
									req.getParameter("prenom")));
					stmt.executeUpdate("UPDATE personne " + "SET mail = '" + req.getParameter("mail") + "', naiss = '"
							+ Date.valueOf(req.getParameter("naiss")) + "', nom = '" + req.getParameter("nom")
							+ "', prenom = '" + req.getParameter("prenom") + "' WHERE login = '" + p.getLogin() + "'");
					res.sendRedirect("../servlet/profil");

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
			} else
				res.sendRedirect("../new.html");
		}
	}
}
