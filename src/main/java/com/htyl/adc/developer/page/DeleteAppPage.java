package com.htyl.adc.developer.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htyl.adc.page.CommConfirmPage;

//删除应用
public class DeleteAppPage {
	WebDriver driver;

	public DeleteAppPage(WebDriver driver) {
		this.driver = driver;
	}

	public void deleteAppFun(String appname) throws Exception {
		WebElement element = driver.findElement(By.partialLinkText(appname));
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(element));
		String dataId = element.getAttribute("data-id");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@data-id='" + dataId + "']/../../../a")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@data-id='" + dataId + "' and contains(text(),'删除')]")).click();

		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();
		new WebDriverWait(driver, 60).until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ht-modal-default-text")));
		String text ="";
		for(int i=0;i<30;i++){
			text = driver.findElement(By.className("ht-modal-default-text")).getText();
			if (!text.contains("删除成功")){
				Thread.sleep(2000);
			}else {
				break;
			}
		}
		Assert.assertEquals("删除成功", text);
	}
}
