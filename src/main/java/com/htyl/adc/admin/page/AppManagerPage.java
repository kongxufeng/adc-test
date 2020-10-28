package com.htyl.adc.admin.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.htyl.adc.comm.Constants;

public class AppManagerPage {
	WebDriver driver;

	public AppManagerPage(WebDriver driver) {
		this.driver = driver;
	}

	public void appManagerfun(String appname) throws Exception {
		driver.get(Constants.APPManager);
		WebElement element = driver.findElement(By.xpath("//a[contains(@title,'" + appname + "')]/../.."))
				.findElement(By.className("appv_audit"));
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		Select select = new Select(driver.findElement(By.id("auditStatus")));
		select.selectByVisibleText("审核通过");
		Thread.sleep(2000);
		driver.findElement(By.id("audit_save_btn")).click();
		Thread.sleep(1000);

	}

}
