package com.htyl.adc.page;

import org.testng.annotations.DataProvider;
public class TestDataFactory {
	
	@DataProvider(name = "auth")
	public static Object[][] getUserInfo() {
		return new Object[][] { { "15901071953", "yw123456" } };
	}
	
}
