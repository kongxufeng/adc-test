package com.htyl.adc.developer.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htyl.adc.comm.Constants;

//创建服务
public class CreateServicePage {
	WebDriver driver;
	private Logger logger = LogManager.getLogger();
	public CreateServicePage(WebDriver driver) {
		this.driver = driver;
	}

	public void createServiceFun(String serviceName, String kind, String instanceType, String instanceName)
			throws Exception {
		// 访问网页
		driver.get(Constants.CREATSERVICEURL);
		WebElement element = driver.findElement(By.id("name"));
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("name")));
		element.sendKeys(serviceName);
		driver.findElement(By.cssSelector("[name='type'][value='1']+span")).click();
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("appType")));
		Select select = new Select(driver.findElement(By.id("appType")));
		select.selectByVisibleText(kind);
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("instanceType")));
		select = new Select(driver.findElement(By.id("instanceType")));
		select.selectByVisibleText(instanceType);
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("instanceList")));
		select = new Select(driver.findElement(By.id("instanceList")));
		logger.info("获取中间件名称是："+instanceName);
		select.selectByVisibleText(instanceName+"[开发环境-默认开发环境]");

		driver.findElement(By.id("createService")).click();
		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ht-modal-default-text")));
		String text ="";
		for (int i = 0; i < 20; i++) {
			text = driver.findElement(By.className("ht-modal-default-text")).getText();
			if (!text.contains("服务创建成功")) {
				Thread.sleep(3000);
			} else {
				break;
			}
		}

		Assert.assertEquals( text,"服务创建成功","失败，服务未成功创建");

	}



}
