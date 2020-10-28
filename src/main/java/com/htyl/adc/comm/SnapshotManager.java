package com.htyl.adc.comm;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import com.google.common.io.Files;

public class SnapshotManager {
	private static  Logger logger = LogManager.getLogger();
	public static void getScreenshot(String filename) {
		String path = "./image/" + filename + ".png";
		WebDriver driver = WebDriverManager.getWebdriver();
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File targetFile = new File(path);
		// 把保存在sourceFile中的图片内容，保存到targetFile路径下
		try {
			Files.copy(sourceFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getScreenshot(String filename, WebDriver driver) {
		String path = "./erropage/" + filename + ".png";
		File sourceFile = null;
		try {

			sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		} catch (TimeoutException e) {
			System.out.println("时间超时" + path + "失败");
			e.printStackTrace();
		}
		File targetFile = new File(path);
		// 把保存在sourceFile中的图片内容，保存到targetFile路径下
		if (sourceFile != null) {
			try {
				Files.copy(sourceFile, targetFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

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
