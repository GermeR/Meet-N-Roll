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

@WebServlet("/servlet/singup")
public class ServletSingUp extends HttpServlet {

	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:22042/MeetNRoll";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		PrintWriter out = res.getWriter();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM personne WHERE login='"
				+ req.getParameter("login") + "';";

		if (req.getParameter("password").equals(req.getParameter("repassword"))) {

			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection(URL, NOM, MDP);
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					/*session.setAttribute("personne",
							new Personne(req.getParameter("login"), null, null,
									null, null, null));*/
				} else {
					stmt.execute("insert into personne(login,nom,prenom,mail,naiss,password) values('"
							+ req.getParameter("login")
							+ "','"
							+ req.getParameter("nom")
							+ "','"
							+ req.getParameter("prenom")
							+ "','"
							+ req.getParameter("mail")
							+ "','"
							+ req.getParameter("naiss")
							+ "','"
							+ req.getParameter("password") + "');");
				}
				res.sendRedirect("../login.html");

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
