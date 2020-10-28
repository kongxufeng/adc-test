package com.htyl.adc.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

/**
 * 基本测试类，不含任何测试方法，主要用于被其他测试类继承
 *
 */
@Listeners({TestFailListener.class})
public class BaseTest {
	public WebDriver driver;
	
	@AfterSuite
	public void closeService() {
		DriverUtils.stopService();
	}

	@BeforeClass
	public void initDriver() {
		Config config = new Config("config.properties");
		// System.setProperty("webdriver.chrome.driver",
		// "D:\\chromedriver.exe");
		// driver = WebDriverManager.getWebdriver("chrome");
		System.setProperty("htyl.uc.browser", config.getConfig("htyl.uc.browser"));
		driver = DriverUtils.getDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		//driver.set_window_size(win32api.GetSystemMetrics(win32con.SM_CXSCREEN),
				//win32api.GetSystemMetrics(win32con.SM_CYSCREEN));
		Dimension targetSize = new Dimension(1600, 900);
		driver.manage().window().setSize(targetSize);

		//driver.manage().window().maximize();
	}

	@AfterClass
	public void quitDriver() {
		driver.quit();
	}
}
