package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter printWriter = response.getWriter();

		String name = request.getParameter("uname");
		String password = request.getParameter("pass");

		System.out.println(name + " " + password);

		boolean flag = true;

		try {
			Class.forName("org.h2.Driver");

			Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/logindemo", "sa", "123");

			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from userdetails where name = ? and pass = ?");
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();

			flag = resultSet.next();

			if (flag) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/welcome.html");
				requestDispatcher.forward(request, response);
			} else {
				
				printWriter.print("<b>Invalid login!!! Plz login again</b>");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login.html");
				requestDispatcher.include(request, response);
			}
			connection.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
