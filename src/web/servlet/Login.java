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

import types.UserType;

@WebServlet("/servlet/log")
public class Login extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	static final String NOM = "kwin";
	static final String MDP = "moi";
	static final String URL = "jdbc:postgresql://kwinserv.ddns.net:80/MeetNRoll";

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		if (request != null)
		{
			HttpSession session = request.getSession();
			// PrintWriter out = res.getWriter();

			if (request.getParameter("delog") != null && request.getParameter("delog").equals("true") && session != null)
			{
				session.invalidate();
			}
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			String querry = "SELECT * FROM personne WHERE login='" + request.getParameter("login") + "' and password='"
					+ request.getParameter("password") + "';";
			// out.println(sql);

			try
			{
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, NOM, MDP);
				statement = connection.createStatement();
				resultSet = statement.executeQuery(querry);
				if (resultSet != null && resultSet.next())
				{
					session.setAttribute("personne", new UserType(resultSet.getString(1), resultSet.getString(3), resultSet.getDate(4),
							resultSet.getString(5), resultSet.getString(6)));
					response.sendRedirect("/Meet-N-Roll/servlet/Menu");
				}
				else
				{
					response.sendRedirect("../login.html");
				}
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
	}
}
