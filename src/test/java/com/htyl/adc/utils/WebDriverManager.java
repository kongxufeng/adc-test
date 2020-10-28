package com.htyl.adc.utils;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDriverManager {
	
	public static WebDriver driver;
	
	/**
	 * 接收一个浏览器的名字，根据名字返回不同的浏览器
	 * @return
	 */
	public static WebDriver getWebdriver(String browserName) {
		if ("chrome".equals(browserName)) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);

//			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			driver = new ChromeDriver(chromeOptions);

		} else if ("firefox".equals(browserName)) {
			System.setProperty("webdriver.gecko.driver", "C:/Program Files/geckodriver.exe");
			driver = new FirefoxDriver();
		} else if ("ie32".equals(browserName)) {
			System.setProperty("webdriver.ie.driver", "C:/Program Files/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if ("ie64".equals(browserName)) {
			System.setProperty("webdriver.ie.driver", "./driver/64/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else {
			System.out.println("只支持ie32，ie64，firefox和chrome浏览器");
		}
		return driver;
	}
	
	public static WebDriver getWebdriver() {
		return driver;
	}
}
