package edu.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
	
	Connection con = null;
	
	
	public void init(ServletConfig sc)
	{
		String driverClass = (String)sc.getInitParameter("dClass");
		String url = (String)sc.getInitParameter("url");
		String userName = (String)sc.getInitParameter("userName");
		String password = (String)sc.getInitParameter("password");
		try
		{
		Class.forName(driverClass);
		con = DriverManager.getConnection(url,userName,password);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	{
		
		
		try
		{
            PreparedStatement ps = con.prepareStatement("insert into employees values(?,?,?,?,?)");
            ps.setString(1, req.getParameter("lName"));
            ps.setString(2, req.getParameter("fName"));
            ps.setString(3, req.getParameter("email"));
            ps.setString(4, req.getParameter("phone"));
            ps.setString(5, req.getParameter("pwd"));
            ps.executeUpdate();
            
            res.getWriter().println("<h1>Employee Registration Successfull</h1>");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void destory() throws SQLException
	{
		con.close();
	}

}
