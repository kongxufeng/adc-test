package com.htyl.adc.developer.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htyl.adc.comm.Constants;
import com.htyl.adc.page.CommConfirmPage;

//创建镜像
public class CreateMirrorPage {
	WebDriver driver;

	public CreateMirrorPage(WebDriver driver) {
		this.driver = driver;
	}

	public void createMirrorFun(String name,String tag_name) throws Exception {
		// 访问网页
		driver.get(Constants.CREATEMIRROR);
		WebElement element = driver.findElement(By.id("name"));
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("name")));

		element.sendKeys(name);
		driver.findElement(By.id("tag_name")).sendKeys(tag_name);
		Thread.sleep(500);
		driver.findElement(By.id("deployFile")).sendKeys("C:/imge/LoginTest-archive.war");
		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("ht-modal-default-text")));
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		for (int i = 0; i < 20; i++) {
			if (!text.contains("上传成功")) {
				Thread.sleep(5000);
				text = driver.findElement(By.className("ht-modal-default-text")).getText();
			} else {
				break;
			}
		}
		Assert.assertEquals(text, "上传成功","上传镜像文件失败");
		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();
		Thread.sleep(1000);
		// 确定
		driver.findElement(By.id("createMirror")).click();
		driver.findElement(By.id("passwordShow")).click();
		driver.findElement(By.id("password")).sendKeys(Constants.USERPW);
		// 确定
		commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();
		new WebDriverWait(driver, 60)
				.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.cssSelector(".ht-modal-default-text")));
		for (int i = 0; i < 30; i++) {
			text = driver.findElement(By.className("ht-modal-default-text")).getText();
			if (!text.contains("创建成功！")) {
				Thread.sleep(2000);
			} else {
				break;
			}
		}
		Assert.assertEquals(text,"创建成功！","失败，镜像未创建成功");
		boolean dis = false;
		for (int i = 0; i < 10; i++) {
			Thread.sleep(1000);
			driver.get(Constants.MIRRORLIST);
			if (driver.findElement(By.xpath("//button[@data-name='" + name + "']")) != null) {
				dis = true;
				break;
			}
		}
		Assert.assertTrue(dis, "镜像列表显示");

	}

	public void deleteMirrorFun(String name) throws Exception {
		driver.get(Constants.MIRRORLIST);
		driver.findElement(By.xpath("//button[@data-name='" + name + "']")).click();
		Thread.sleep(500);
		// 确定
		CommConfirmPage commConfirmPage = new CommConfirmPage(driver);
		commConfirmPage.surefun();
		Thread.sleep(500);
		new WebDriverWait(driver, 20).until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")));
		driver.findElement(By.id("passwordShow")).click();
		driver.findElement(By.id("password")).sendKeys(Constants.USERPW);
		// 确定
		driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.btn-sure-us")).click();
		Thread.sleep(500);
		new WebDriverWait(driver, 40).until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")));
		Thread.sleep(1000);
		String text = driver.findElement(By.className("ht-modal-default-text")).getText();
		Assert.assertEquals(text,"刪除成功！");

	}


}
