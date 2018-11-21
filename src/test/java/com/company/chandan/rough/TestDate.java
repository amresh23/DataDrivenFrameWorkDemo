package com.company.chandan.rough;

import java.io.IOException;
import java.util.Date;

import org.testng.annotations.Test;

public class TestDate {
	
	@Test
	public void datetest() throws IOException{
		
		Date date = new Date();
		String currenttime = date.toString().replace(":", "_").replace(" ", "_");
		System.out.println(currenttime);

		
		
	}

}
