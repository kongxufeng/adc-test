package com.htyl.adc.developer.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htyl.adc.comm.Constants;
import com.htyl.adc.page.CommConfirmPage;

//上架应用
public class ShangJiaPage {
	WebDriver driver;

	public ShangJiaPage(WebDriver driver) {
		this.driver = driver;
	}

	public void shangJiaFun(String appname) throws Exception {
		WebElement element = driver.findElement(By.partialLinkText(appname));
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(element));
		String dataId = element.getAttribute("data-id");
		driver.get(Constants.BasicURL2 + "/developer/application/app/create3?id=" + dataId + "&pagetype=online");
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("desc")));
		Thread.sleep(2000);
		driver.findElement(By.id("desc")).sendKeys("应用描述");
		// 领域分类
		driver.findElement(By.id("categoryName")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("cascade-ul-box")));
		driver.findElement(By.xpath("//li[@title='基础服务层']")).click();
		driver.findElement(By.className("cascade-footer-confirm")).click();
		// 图片地址
		driver.findElement(By.name("file")).sendKeys("D:/imge/competition_img_2.png");
		Thread.sleep(2000);
		// 应用详情
		driver.findElement(By.id("editor")).sendKeys("应用详情");
		driver.findElement(By.id("homeUrl")).sendKeys("https://www.baidu.com/");
		driver.findElement(By.id("channel")).sendKeys("渠道来源");
		driver.findElement(By.id("createAppBtn")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.sureBtn")));
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		Assert.assertEquals("已经交由工作人员审核，请耐心等待", text);

		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();

	}

	public void shangJiaServiceFun(String appname) throws Exception {
		WebElement element = driver.findElement(By.partialLinkText(appname));
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(element));
		String dataId = element.getAttribute("data-id");
		driver.get(Constants.BasicURL2 + "/developer/application/app/create3?id=" + dataId
				+ "&pagetype=online&isfrom=microservice");
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("desc")));
		Thread.sleep(2000);
		driver.findElement(By.id("desc")).sendKeys("应用描述");
		// 领域分类
		driver.findElement(By.id("categoryName")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("cascade-ul-box")));
		driver.findElement(By.xpath("//li[@title='基础服务层']")).click();
		driver.findElement(By.className("cascade-footer-confirm")).click();
		// 图片地址
		driver.findElement(By.name("file")).sendKeys("D:/imge/dhtp.png");
		Thread.sleep(2000);
		// 应用详情
		driver.findElement(By.id("editor")).sendKeys("应用详情");
		driver.findElement(By.id("channel")).sendKeys("渠道来源");
		driver.findElement(By.id("createAppBtn")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.sureBtn")));
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		Assert.assertEquals("已经交由工作人员审核，请耐心等待", text);

		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();

	}

}
