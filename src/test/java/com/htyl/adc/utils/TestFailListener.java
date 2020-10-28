package com.htyl.adc.utils;

import java.lang.reflect.Field;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import io.qameta.allure.Attachment;

public class TestFailListener extends TestListenerAdapter {
	@Override
	public void onTestFailure(ITestResult result) {
		try {
			Field field = result.getTestClass().getRealClass().getField("driver");
			WrappedRemoteWebDriver driver = (WrappedRemoteWebDriver) field.get(result.getInstance());
			screenshot(driver);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	@Attachment(value = "失败截图如下：", type = "image/png")
	public byte[] screenshot(WrappedRemoteWebDriver driver) {
		byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		return screenshotAs;

	}

}
