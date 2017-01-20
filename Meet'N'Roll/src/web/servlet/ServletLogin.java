package web.servlet;

import java.io.IOException;
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

@WebServlet("/servlet/log")
public class ServletLogin extends HttpServlet {
	
	static final String NOM = "germer";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://psqlserv/n3p1";
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM personne WHERE login='" + req.getParameter("login") + "' AND mdp='"
				+ req.getParameter("mdp") + "' ;";
		System.out.println(sql);
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, NOM, MDP);
			stmt = con.createStatement();
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs.next()) {
				for(int i =1; i<= rs.getMetaData().getColumnCount();i++)
				System.out.println("rs"+i+" : "+rs.getString(i));
				session.setAttribute("login", req.getParameter("login"));
				session.setAttribute("personne", new Personne(rs.getString(1),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(session.getAttribute("personne"));
		res.sendRedirect("../menu.jsp");
	}	
}
