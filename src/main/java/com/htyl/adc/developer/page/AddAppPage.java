package com.htyl.adc.developer.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htyl.adc.comm.Constants;
import com.htyl.adc.page.CommConfirmPage;


//创建应用与微服务
public class AddAppPage {
	WebDriver driver;

	public AddAppPage(WebDriver driver) {
		this.driver = driver;
	}

	public void addAppfun(String appname) throws Exception {
		addAppComm(appname);
		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();
	}

	public void addServicefun(String appname) throws Exception {
		driver.get(Constants.SERVICEURL);
		WebElement Element = driver.findElement(By.partialLinkText("创建微服务"));
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(Element));
		Element.click();
		driver.findElement(By.id("name")).sendKeys(appname);
		driver.findElement(By.id("portUrl")).sendKeys("https://model.htres.cn/model/1/starter/calculate");
		// 请求方式
		driver.findElement(By.id("method")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[data-val='GET']")));
		driver.findElement(By.cssSelector("li[data-val='GET']")).click();
		Thread.sleep(1000);
		// 网络协议
		driver.findElement(By.id("protocol")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li[data-val='HTTPS']")));
		driver.findElement(By.cssSelector("li[data-val='HTTPS']")).click();
		Thread.sleep(500);
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-outline.add-row")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@class='input-validator form-control']")).sendKeys("n");
		driver.findElement(By.xpath("//input[@class='input-validator form-control']/../../../td[4]//input"))
				.sendKeys("100");
		driver.findElements(By.xpath("//input[@class='input-validator form-control']")).get(1).sendKeys("D");
		driver.findElements(By.xpath("//input[@class='input-validator form-control']")).get(1)
				.findElement(By.xpath("../../../td[4]//input"))
				.sendKeys("15");
		driver.findElement(By.id("createAppBtn")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.sureBtn")));
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		Assert.assertEquals(text,"创建微服务成功");
		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);

		commConfirmPage.surefun();
	}

	public void addAndRunAPPfun(String appname, String mirrorName, String environmentName) throws Exception {
		driver.get(Constants.APPURL);
		WebElement Element = driver.findElement(By.partialLinkText("创建应用"));
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(Element));
		Element.click();
		driver.findElement(By.id("name")).sendKeys(appname);
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("mirrorContBox")));
		driver.findElement(By.xpath("//div[contains(text(),'自定义')]")).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("customOpt")));
		// 选择
		driver.findElement(By.id("customOpt")).click();
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("customtype")));
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("input[data-value*='" + mirrorName + "']+span")).click();
		driver.findElement(By.id("confirm-custom")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[contains(text(),'自定义')]")).click();

		driver.findElement(By.id("createAppBtn")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.sureBtn")));
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		for (int i = 0; i < 10; i++) {
			if (!text.contains("创建应用成功")) {
				Thread.sleep(500);
			} else {
				break;
			}
		}
			Assert.assertEquals(text,"创建应用成功");

		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.runfun();
		Thread.sleep(1000);
		JavascriptExecutor jse2 = (JavascriptExecutor) driver;
		jse2.executeScript("window.scrollTo(0, 800)");
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id("instanceName")));
		driver.findElement(By.id("instanceName")).sendKeys("login" + appname.substring(5));
		new WebDriverWait(driver, 5, 20)
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(),'端口1')]")));
		driver.findElement(By.name("port")).sendKeys("8080");
		Select select = new Select(driver.findElement(By.id("detailEnv")));
		select.selectByVisibleText(environmentName);
		Thread.sleep(2000);
		// 确定
		driver.findElement(By.id("nextStep")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")));
		text = driver.findElement(By.className("ht-modal-default-text")).getText();
		for (int i = 0; i < 10; i++) {
			if (!text.contains("运行应用成功")) {
				Thread.sleep(500);
			} else {
				break;
			}
		}
		Assert.assertEquals( text,"运行应用成功");
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")).click();
		Thread.sleep(5000);

	}

	public void addAppComm(String appname) throws Exception {
		driver.get(Constants.APPURL);
		WebElement Element = driver.findElement(By.partialLinkText("创建应用"));
		new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(Element));
		Element.click();
		driver.findElement(By.id("name")).sendKeys(appname);
		driver.findElement(By.id("createAppBtn")).click();
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.sureBtn")));
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		for (int i = 0; i < 10; i++) {
			if (!text.contains("创建应用成功")) {
				Thread.sleep(500);
			} else {
				break;
			}
		}
		Assert.assertEquals( text,"创建应用成功");
	}

}
