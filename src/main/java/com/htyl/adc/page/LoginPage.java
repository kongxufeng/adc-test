package com.htyl.adc.page;

import com.htyl.adc.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage {
	private WebDriver driver;
	private Logger logger = LogManager.getLogger();

	boolean tag = true;
	@FindBy(id = "login")
	WebElement loginlink;
	@FindBy(id = "li0")
	WebElement phonelink;
	@FindBy(id = "shortAccount")
	WebElement name;
	@FindBy(id = "password")
	WebElement password;
	@FindBy(css = "input[value='登 录']")
	WebElement longinbnt;



	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	//登录
	public void login(String name, String password) throws Exception {
		new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(phonelink));
		this.phonelink.click();
		this.name.sendKeys(name);
		this.password.sendKeys(password);
		this.longinbnt.click();
        Thread.sleep(2000);
        int i = 0;
		// 验证码失败重试
		while (tag && i <= 20) {
			if (!driver.getTitle().contains("登录")) {
				tag = false;
				break;
			}
			//验证码识别
			VerifyOcr run = new VerifyOcr(driver);
			run.verifyRun();
			Thread.sleep(5000);
			i++;
			if(i>5 && !driver.findElements(By.cssSelector(".layui-layer-btn0")).isEmpty()){
				driver.findElement(By.cssSelector(".layui-layer-btn0")).click();
				this.longinbnt.click();
				Thread.sleep(2000);
			}
		}

	}

}