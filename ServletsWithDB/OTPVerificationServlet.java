package com.syam.restapi;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class OTPVerificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//    public static Map<String,String> map;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String mobileNumber = request.getParameter("mobileNumber");
        String otp = request.getParameter("otp");
        HttpSession session = request.getSession();
        String storedOTP = (String) session.getAttribute("otp");
        String object = (String) session.getAttribute("mobileNumbers");
        String name = (String) session.getAttribute("name");
        // Retrieve OTP from map based on mobile number
//        String storedOTP=getOTP().get(mobileNumber);
        
        if (storedOTP != null && storedOTP.equals(otp)) {
            // Correct OTP
        	String id = getId(name,object);
//        	map.put(object, otp);
			try {
				insertElements(id, object, storedOTP);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3>OTP verified successfully!</h3>");
            out.println("</body></html>");

        } else {
            // Incorrect OTP
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3>Incorrect OTP</h3>");
            out.println("</body></html>");
        }
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	public static String getId( String name,String mobileNumber) {
		String str="";
		for(int i=0;i<mobileNumber.length();i++) {
			if(i==0 || i==mobileNumber.length()-1)
				str+=mobileNumber.charAt(i);
		}
		System.out.println(str);
		return name+"_"+str;
	}
	
	public static void insertElements(String id,String object,String otp) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url="jdbc:mysql://sql6.freesqldatabase.com:3306/sql6703228";
		String userName="sql6703228";
		String password="4FpMzzNGLy";
		Connection conn = DriverManager.getConnection(url, userName, password);
		String stmt="insert into user_info values(?,?,?)";
		PreparedStatement statement;
		statement = conn.prepareStatement(stmt);
//		if(map.containsKey(object)) update();
		statement.setString(1, id);
		statement.setString(2, object);
		statement.setString(3, otp);
		int set = statement.executeUpdate();
		System.out.println(set);
		conn.close();
	} 
}
