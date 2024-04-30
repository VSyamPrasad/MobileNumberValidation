package com.syam.restapi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class OTPVerificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//	static MobileNumberValidationServlet mobileNumbers;
//	
//	public static Map<String,String> getOTP(){
//		return mobileNumbers.mobileNumberOtpMap;
//	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mobileNumber = request.getParameter("mobileNumber");
        String otp = request.getParameter("otp");
        
        HttpSession session = request.getSession();
        String storedOTP = (String) session.getAttribute("otp");
        // Retrieve OTP from map based on mobile number
//        String storedOTP=getOTP().get(mobileNumber);
        
        if (storedOTP != null && storedOTP.equals(otp)) {
            // Correct OTP
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

}
