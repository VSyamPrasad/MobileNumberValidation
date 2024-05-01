package com.syam.restapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MobileNumberValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Map<String, String> mobileNumberOtpMap = new HashMap<>();
	//public MobileNumberList mobileNumbers;
	
	public static List<String> list = new ArrayList<String>();
	
	public static List<String> getNumber(){
		
		list.add("7661014404");
		list.add("9553767027");
		list.add("6303602622");
		list.add("9121966415");
		list.add("6281690439");
		list.add("9553714404");
		return list;
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String name=request.getParameter("name");
		 String mobileNumber = request.getParameter("mobileNumber");
		 List<String> number = MobileNumberValidationServlet.getNumber();
			Iterator<String> it=number.iterator();
			
			while(it.hasNext()) {
				if(it.next().equals(mobileNumber)) {
					String otp=generateOTP();
					mobileNumberOtpMap.put(mobileNumber, otp);
					
					System.out.println("OTP for " + mobileNumber + ": " + otp);
					HttpSession session = request.getSession();
					session.setAttribute("mobileNumbers", mobileNumber);
					session.setAttribute("otp", otp);
					session.setAttribute("name", name);
					response.sendRedirect("otp.html");
				}
			}
			response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3>Invalid mobile number</h3>");
            out.println("</body></html>");
	    }
	    
	    // Method to generate random 6-digit OTP
	    private String generateOTP() {
	    	int otp = (int) (Math.random()*(900000-100000)+100000);
			return String.valueOf(otp);
	    }
	    @Override
	    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	// TODO Auto-generated method stub
	    	doPost(req, resp);
	    }
}
