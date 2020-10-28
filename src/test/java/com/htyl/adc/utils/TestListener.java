package com.htyl.adc.utils;

import java.lang.reflect.Field;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import io.qameta.allure.Attachment;

public class TestListener extends TestListenerAdapter {
	@Override
	public void onTestFailure(ITestResult tr) {
		try {
			Field field = tr.getTestClass().getRealClass().getField("driver");
			WrappedRemoteWebDriver driver;
			driver = (WrappedRemoteWebDriver) field.get(tr.getInstance());
			byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			screenshot(screenshotAs);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Attachment(value = "Page screenshot", type = "image/png")
	public byte[] screenshot(byte[] screenShot) {

		return screenShot;
	}
}
