package com.syam.restapi;

import java.util.ArrayList;
import java.util.List;

public class MobileNumberList {
	private List<String> list;
	
	public List<String> getNumber(){
		list = new ArrayList<String>();
		list.add("7661014404");
		list.add("9553767027");
		list.add("6303602622");
		
		return list;
	}

}
