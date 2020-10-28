package com.htyl.adc.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
/**
 * 基本测试类，不含任何测试方法，主要用于被其他测试类继承
 *
 */
public class BaseTest2 {
	public WebDriver driver;
	
	@AfterSuite
	public void closeService() {
		DriverUtils.stopService();
	}
	
	@BeforeMethod()
	public void initDriver() {
		Config config = new Config("config.properties");
		// System.setProperty("webdriver.chrome.driver",
		// "D:\\chromedriver.exe");
		// driver = WebDriverManager.getWebdriver("chrome");
		System.setProperty("htyl.uc.browser", config.getConfig("htyl.uc.browser"));
		driver = DriverUtils.getDriver2();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	

	@AfterMethod
	public void quitDriver() {
		driver.quit();
	}
}
