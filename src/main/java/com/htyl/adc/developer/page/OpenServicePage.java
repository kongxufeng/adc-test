package com.htyl.adc.developer.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htyl.adc.comm.Constants;

//创建服务
public class OpenServicePage {
	WebDriver driver;

	public OpenServicePage(WebDriver driver) {
		this.driver = driver;
	}

	public void openServiceFun(String inname) throws Exception {
		String st = "";
		driver.get(Constants.SERVICELISTURL);
		Thread.sleep(2000);
		for (int i = 0; i < 10; i++) {
			driver.findElement(By.cssSelector("li[tabid='外网']")).click();
			new WebDriverWait(driver, 20).until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text()," + inname + ")]")));
			st = driver.findElement(By.xpath("//td[contains(text()," + inname + ")]/../td[3]")).getText();
			if (!st.equals("启动")) {
				Thread.sleep(3000);
			} else {
				break;
			}
		}
		Assert.assertEquals(st,"启动","失败，未检测到服务实例启动");
		//String text = driver.findElement(By.xpath("//td[contains(text()," + inname + ")]/../td[5]/a")).getText();
		//return text;

	}

	public void deleteServiceFun(String inname) throws Exception {
		driver.get(Constants.SERVICELISTURL);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("li[tabid='外网']")).click();
		Thread.sleep(2000);
		new WebDriverWait(driver, 30).until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text()," + inname + ")]")));
		driver.findElement(By.xpath("//td[contains(text()," + inname + ")]/..//button[contains(text(),\"删除\")]"))
				.click();
		new WebDriverWait(driver, 30).until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")));
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")).click();
		new WebDriverWait(driver, 30).until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ht-modal-default-text")));
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		for (int i = 0; i < 10; i++) {
			if (!text.contains("删除成功")) {
				Thread.sleep(5000);
			} else {
				break;
			}
		}
		Assert.assertEquals(text,"删除成功","删除服务失败");
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")).click();

	}

}
