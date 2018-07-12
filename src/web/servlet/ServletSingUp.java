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

@WebServlet("/servlet/singup")
public class ServletSingUp extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:80/MeetNRoll";

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		// PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String querry = "SELECT * FROM personne WHERE login='" + request.getParameter("login") + "';";

		if (!request.getParameter("login").equals("") && !request.getParameter("password").equals("")
				&& !request.getParameter("mail").equals("")
				&& request.getParameter("password").equals(request.getParameter("repassword")))
		{
			try
			{
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, NOM, MDP);
				statement = connection.createStatement();
				resultSet = statement.executeQuery(querry);
				if (resultSet.next())
				{
					session.setAttribute("personne",
							new Personne(request.getParameter("login"), null, null, null, null));
				}
				else
				{
					statement.execute("insert into personne(login,nom,prenom,mail,naiss,password) values('"
							+ request.getParameter("login") + "','" + request.getParameter("nom") + "','"
							+ request.getParameter("prenom") + "','" + request.getParameter("mail") + "','"
							+ request.getParameter("naiss") + "','" + request.getParameter("password") + "');");
				}
				response.sendRedirect("../login.html");

			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (statement != null)
					{
						statement.close();
					}
					if (connection != null)
					{
						connection.close();
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			response.sendRedirect("../new.html");
		}
	}
}
