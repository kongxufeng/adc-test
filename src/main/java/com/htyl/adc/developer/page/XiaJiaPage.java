package com.htyl.adc.developer.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htyl.adc.page.CommConfirmPage;

//下架应用
public class XiaJiaPage {
	WebDriver driver;

	public XiaJiaPage(WebDriver driver) {
		this.driver = driver;
	}

	public void xiaJiaFun(String appname) throws Exception {
		WebElement element = driver.findElement(By.partialLinkText(appname));
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(element));
		String dataId = element.getAttribute("data-id");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@data-id='" + dataId + "']/../../../a")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@data-id='" + dataId + "' and contains(text(),'下架')]")).click();

		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();
		new WebDriverWait(driver, 20).until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")));
		Thread.sleep(1000);
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		Assert.assertEquals("操作成功！", text);

	}
}
