package edu.training;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet
{
	Connection con = null;
	
	public void init(ServletConfig sc)
	{
		try
		{
			Class.forName((String)sc.getInitParameter("dClass"));
			con = DriverManager.getConnection((String)sc.getInitParameter("url"), (String)sc.getInitParameter("userName"), (String)sc.getInitParameter("password"));
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	{
		try
		{
			PreparedStatement ps = con.prepareStatement("select password,last_name from employees where email=?");
			ps.setString(1, req.getParameter("email"));
			ResultSet rs = ps.executeQuery();
			PrintWriter pw = res.getWriter();
			
			
			while(rs.next())
			{
				if(req.getParameter("password").equals(rs.getString(1)))
				{
					pw.println("Welcome "+rs.getString(2));
				}
				else
				{
					pw.println("<!DOCTYPE html>\r\n"
							+ "<html>\r\n"
							+ "<head>\r\n"
							+ "<meta charset=\"ISO-8859-1\">\r\n"
							+ "<title>Insert title here</title>\r\n"
							+ "</head>\r\n"
							+ "<body>\r\n"
							+ "<center>\r\n"
							+ "<h1>Welcome to EMS</h1>\r\n"
							+ "<form action=\"\">\r\n"
							+ "<p style=\"color: red\">Invalid Username/Password</p>\r\n"
							+ "<table>\r\n"
							+ "<thead>\r\n"
							+ "<tr>\r\n"
							+ "<th colspan=\"2\">Enter your Login Details </th>\r\n"
							+ "</tr>\r\n"
							+ "</thead>\r\n"
							+ "<tbody>\r\n"
							+ "<tr><td><label>User Name</label></td><td><input type=\"text\" name=\"email\" value=\"\" placeholder=\"Enter the User Name\"></td></tr>\r\n"
							+ "<tr><td><label>Password</label></td><td><input type=\"password\" name=\"password\" value=\"\" placeholder=\"Enter the password\"></td></tr>\r\n"
							+ "<tr><td colspan=\"2\"><input type=\"submit\" value=\"Login\"><input type=\"reset\" value=\"Clear\"></td></tr>\r\n"
							+ "</tbody>\r\n"
							+ "</table>\r\n"
							+ "</form>\r\n"
							+ "</center>\r\n"
							+ "</body>\r\n"
							+ "</html>");
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
