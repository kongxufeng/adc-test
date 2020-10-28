package com.htyl.adc.utils;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;
/**
 * DriverUtils类用于提供service的单例启动，并提供匹配不同浏览器的driver对象
 *
 */
public class DriverUtils {
	/**
	 * service对象，用于保存根据用户设置的系统属性来创建的不同浏览器的driverservice
	 */
	private static DriverService service;
	/**
	 * browser字符串，用于获取并保持用户设置在系统属性htyl.uc.browser中的值
	 */
	private static String browser;
	/**
	 *  定义日志对象
	 */
	private static final Logger logger = LogManager.getLogger();
	/**
	 * 静态加载（单例的饿汉模式）service对象，并启动
	 */
	static {
		browser = System.getProperty("htyl.uc.browser", "firefox"); // 获取htyl.uc.browser系统属性，如果未设置，则默认为firefox
		logger.info("获取系统属性htyl.uc.browser，值为" + browser); // 使用日志记录获取的参数值
		/*
		 * 根据browser保存的浏览器类型，分别创建DriverService对象，目前chrome和ie尚未完工
		 */
		Config config = new Config("config.properties");
		String driverFile = config.getConfig("driver"); 
		logger.info("获取配置文件中driver所在路径和文件名，值为"+driverFile); // 使用日志记录获取的参数值
		if("firefox".equalsIgnoreCase(browser)) {
			service = new GeckoDriverService.Builder()
				.usingFirefoxBinary(new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe"))) // 指定火狐浏览器执行文件
				.usingAnyFreePort() // 指定使用任一闲置端口
				.usingDriverExecutable(new File(driverFile)) // 指定driver驱动程序的位置
				.build(); // 将Builder对象打造成DriverService对象
		}else if("chrome".equalsIgnoreCase(browser)) {
			service = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File(driverFile))
					.usingAnyFreePort() // 指定使用任一闲置端口
					.build();
		}else {
			logger.error("未支持的浏览器类型，请检查系统属性htyl.uc.browser"); // 通过日志记录浏览器配置信息是否
			throw new RuntimeException("未支持的浏览器类型，请检查系统属性htyl.uc.browser");
		}
		try {
			service.start(); // 启动service
			logger.info("driver服务启动"); // 通过日志记录服务是否正确启动
		} catch (IOException e) {
			logger.error("未能成功启动service，原因为："+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * getDriver静态方法用于向使用者提供方便的获取不同类型浏览器driver对象
	 * @return driver
	 */
	public static WebDriver getDriver() {
		
		WebDriver driver = null;

		DesiredCapabilities caps = null;
		/*
		 * 根据browser的值判断启动的browser类型
		 */
		if("firefox".equalsIgnoreCase(browser)) {
			caps = DesiredCapabilities.firefox();
		}else if("chrome".equalsIgnoreCase(browser)) {
			caps = DesiredCapabilities.chrome();
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("disable-infobars", "headless", "disable-gpu", "--user-data-dir=C:\\Users\\xiejun.HTYUNLU\\AppData\\Local\\Google\\Chrome\\User Data\\Default");//正式用，后台静默运行
			//chromeOptions.addArguments("disable-infobars", "disable-gpu", "--user-data-dir=C:\\Users\\ggh\\AppData\\Local\\Google\\Chrome\\User Data1\\Default");//调试用，取消后台静默运行,浏览器缓存
			chromeOptions.addArguments("disable-infobars", "disable-gpu", "headless");
			caps.setCapability("chromeOptions", chromeOptions);

		}else {
		logger.error("未支持的浏览器类型，请检查系统属性htyl.uc.browser");
		throw new RuntimeException("未支持的浏览器类型，请检查系统属性htyl.uc.browser");
		}

		driver = new WrappedRemoteWebDriver(service.getUrl(), caps);
		return driver;
	}
	
	/**
	 * getDriver静态方法用于向使用者提供方便的获取不同类型浏览器driver对象
	 * 
	 * @return driver
	 */
	public static WebDriver getDriver2() {

		WebDriver driver = null;
		DesiredCapabilities caps = null;
		/*
		 * 根据browser的值判断启动的browser类型
		 */
		if ("firefox".equalsIgnoreCase(browser)) {
			caps = DesiredCapabilities.firefox();
		} else if ("chrome".equalsIgnoreCase(browser)) {
			caps = DesiredCapabilities.chrome();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
			// chromeOptions.addArguments("--user-data-dir=C:\\Users\\llll\\AppData\\Local\\Google\\Chrome\\User
			// Data");

			caps.setCapability("chromeOptions", chromeOptions);

		} else {
			logger.error("未支持的浏览器类型，请检查系统属性htyl.uc.browser");
			throw new RuntimeException("未支持的浏览器类型，请检查系统属性htyl.uc.browser");
		}
		driver = new WrappedRemoteWebDriver(service.getUrl(), caps);
		return driver;
	}

	/**
	 * 停止服务
	 */
	public static void stopService() {
		logger.info("driver服务停止");
		service.stop();
	}
}
