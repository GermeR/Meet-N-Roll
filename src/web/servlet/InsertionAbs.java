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

@WebServlet("/servlet/addabs")
public class InsertionAbs extends HttpServlet {

	static final String NOM = "germer";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://psqlserv/n3p1";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String journee;
		String role="";
		Connection con = null;
		Statement stmt = null;

		if(req.getParameter("matin")!=null)
			journee=req.getParameter("matin");
		else if(req.getParameter("soir")!=null)
			journee=req.getParameter("soir");
		else
			journee="complet";

		ResultSet rs = null;
		String sql = "SELECT role from personne where login='"+req.getParameter("login")+"'";
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
					role=rs.getString(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(!role.equals("prof")){
			sql="INSERT INTO absences (date,demij,login) VALUES ('"+req.getParameter("date")+"','"+journee+"','"+req.getParameter("login")+"')";
			System.out.println(sql);

			try {
				stmt = con.createStatement();
				System.out.println(sql);
				stmt.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		res.sendRedirect("../servlet/abs");
	}	
}
