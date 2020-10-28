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
import com.htyl.adc.page.CommConfirmPage;

//创建中间件
public class AddMiddleWarePage {
	WebDriver driver;
	public AddMiddleWarePage(WebDriver driver) {
		this.driver = driver;
	}

	public void addMiddleWarefun(String middleWarename, String environmentName) throws Exception {
		addMiddleWareComm(middleWarename, environmentName);
		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();
	}


	public void addMiddleWareComm(String middleWarename, String environmentName) throws Exception {
		driver.get(Constants.CREATMiddleWare);
		new WebDriverWait(driver, 60)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-name='MySQL']/div")));
		WebElement findElement = driver.findElement(By.xpath("//div[@data-name='MySQL']/div"));
		findElement.click();

		driver.findElement(By.id("dbname")).sendKeys("zmrdb");
		driver.findElement(By.cssSelector(".record-box.MySQL-box")).findElement(By.id("userName")).sendKeys("zmr");
		driver.findElement(By.cssSelector(".record-box.MySQL-box")).findElement(By.name("dbPwd-text")).click();
		driver.findElement(By.cssSelector(".record-box.MySQL-box")).findElement(By.name("dbPwd")).sendKeys("123456");
		driver.findElement(By.cssSelector(".record-box.MySQL-box")).findElement(By.name("againdbPwd-text")).click();
		driver.findElement(By.cssSelector(".record-box.MySQL-box")).findElement(By.name("againdbPwd"))
				.sendKeys("123456");
		driver.findElement(By.cssSelector(".btn.btn-primary.next-step")).click();
		Thread.sleep(2000);
		// 数据库
		findElement = driver.findElement(By.id("twoStep")).findElement(By.cssSelector(".record-box.MySQL-box"));
		// 配置信息

		Select select = new Select(findElement.findElement(By.id("mysqlDetailEnv")));
		Thread.sleep(2000);
		select.selectByVisibleText(environmentName);
		findElement.findElement(By.id("instanceName")).sendKeys(middleWarename);
		driver.findElement(By.cssSelector("[class='btn btn-primary next-step'][data-val='2']")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".btn.btn-primary.deployBtn")).click();

		new WebDriverWait(driver, 60)
				.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")));
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		for (int i = 0; i < 10; i++) {
			if (!text.contains("创建成功")) {
				Thread.sleep(500);
			} else {
				break;
			}
		}
		Assert.assertEquals(text,"创建成功");
	}

	public void deleteMiddleWare(String name) throws Exception {
		driver.get(Constants.MiddleWareList);
		Thread.sleep(500);
		driver.findElement(By.xpath("//span[@title='" + name + "']/../..//span[@title='删除']")).click();
		Thread.sleep(500);
		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();
		new WebDriverWait(driver, 60).until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ht-modal-default-text")));
		Thread.sleep(1000);
		String text ="";
		for(int i=0;i<20;i++){
			text = driver.findElement(By.className("ht-modal-default-text")).getText();
			if (!text.contains("删除成功")){
				Thread.sleep(3000);
			}else {
				break;
			}
		}
		Assert.assertEquals( text,"删除成功");
	}

}
