package com.example.restservice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileNumberValidation {
	private Map<String, String> mobileNumberOtpMap=new HashMap<>();
	private MobileNumberList mobileNumbers;
	
	@PostMapping("/api/validate-number")
	public ResponseEntity<String> validateMobileNumber(@RequestParam("mobileNumber") String mobileNumber,
													MobileNumberList mobileNumbers){
		List<String> number = mobileNumbers.getNumber();
		Iterator<String> it=number.iterator();
		
		while(it.hasNext()) {
			if(it.next().equals(mobileNumber)) {
				String otp=generateOTP();
				mobileNumberOtpMap.put(mobileNumber, otp);
				
				return ResponseEntity.ok("Otp sent to "+mobileNumber+" "+otp);
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Mobile number");
		/*if(mobileNumber.matches("//d{10}")) {
			String otp=generateOTP();
			mobileNumberOtpMap.put(mobileNumber, otp);
			
			return ResponseEntity.ok("Otp sent to "+mobileNumber);
		}else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Mobile number");
			*/
	}

	private String generateOTP() {
		int otp = (int) (Math.random()*(900000-100000)+100000);
		return String.valueOf(otp);
	}
	@PostMapping("/api/verify-otp")
	public ResponseEntity<String> verifyOTP(@RequestParam("mobileNumber") String mobileNumber,
											@RequestParam("otp") String otp){
		if(mobileNumberOtpMap.containsKey(mobileNumber) && mobileNumberOtpMap.get(mobileNumber).equals(otp)) {
			mobileNumberOtpMap.remove(mobileNumber);
			return ResponseEntity.ok("OTP verified successfully");
		}else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
	}
	
}
