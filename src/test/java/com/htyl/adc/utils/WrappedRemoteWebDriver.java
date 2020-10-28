package com.htyl.adc.utils;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * 包装的RemoteWebDriver，继承了RemoteWebDriver，重新findElements和findElement方法，
 * 实现元素查找时的日志记录
 *
 */
public class WrappedRemoteWebDriver extends RemoteWebDriver {

	private Logger logger = LogManager.getLogger();

	public WrappedRemoteWebDriver(URL remoteAddress, Capabilities capabilities) {
		super(remoteAddress, capabilities);
	}

	@Override
	protected List<WebElement> findElements(String by, String using) {
		List<WebElement> elements = null;
		try {
			elements = super.findElements(by, using);
			 logger.info("通过" + by + "方法查找" + using + "元素，已找到" +
			 elements.size() + "个");
		} catch (Exception e) {
			logger.error("通过" + by + "方法查找" + using + "元素时发生异常，原因：" + e.getMessage());
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			String time = df.format(new Date());
			takeScreenShot(time);
		}
		return elements;
	}

	@Override
	protected WebElement findElement(String by, String using) {
		WebElement element = null;
		try {
			element = super.findElement(by, using);
			 logger.info("通过"+by+"方法查找"+using+"元素，已找到");
		} catch (Exception e) {
			logger.error("通过" + by + "方法查找" + using + "元素时发生异常，原因：" + e.getMessage());
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
			String time = df.format(new Date());
			takeScreenShot(time);
		}
		return element;
	}


	public void takeScreenShot(String filename) {
		File screenShot = null;
		try {
			screenShot = ((TakesScreenshot) this).getScreenshotAs(OutputType.FILE);
		} catch (TimeoutException e) {
			System.out.println("时间超时" + filename + "失败");
			e.printStackTrace();
		}
		File directory = new File("screenshots");
		if (!directory.exists() || !directory.isDirectory()) {
			logger.info("screenshots目录不存在，创建该目录");
			directory.mkdir();
		}
		File file = new File(directory, filename);
		screenShot.renameTo(file);
		logger.info("截屏保存成功，保存在" + file.getAbsolutePath());
	}
}
