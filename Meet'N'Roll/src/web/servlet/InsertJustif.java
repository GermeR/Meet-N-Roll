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

@WebServlet("/servlet/InsertJustif")
public class InsertJustif extends HttpServlet {

	static final String NOM = "germer";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://psqlserv/n3p1";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "update absences set justifie =true where login='" + req.getParameter("login")
				+ "' and date between '" + req.getParameter("date") + "' and '" + req.getParameter("datefin") + "'";

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, NOM, MDP);
			stmt = con.createStatement();
			stmt.execute(sql);
			sql = "insert into justif (login,datedeb,datefin) values ('" + req.getParameter("login") + "','"
					+ req.getParameter("date") + "','" + req.getParameter("datefin") + "');";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		res.sendRedirect("../servlet/justif");
	}
}