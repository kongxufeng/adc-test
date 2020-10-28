package com.htyl.adc.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * 弹出框页面
 */
public class CommConfirmPage {
	WebDriver driver;

	@FindBy(css = ".btn.btn-primary.sureBtn")
	private static WebElement sure_btn;
	@FindBy(css = ".btn.btn-default.delBtn")
	private static WebElement del_btn;
	@FindBy(css = ".btn.btn-primary.btn-lg.sureBtn")
	private static WebElement comp_btn;
	@FindBy(css = ".btn.btn-default.btn-lg.delBtn")
	private static WebElement run_btn;
	/**
	 * 使用页面工程构造自己
	 * @param driver 传入webdriver对象
	 */
	public CommConfirmPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * 点击确定
	 */
	public void surefun() {
		sure_btn.click();
	}

	/**
	 * 点击完成
	 */
	public void compfun() {
		comp_btn.click();
	}
	
	/**
	 * 点击取消
	 */
	public static void delfun() {
		del_btn.click();
	}

	/**
	 * 点击运行
	 */
	public void runfun() {
		run_btn.click();
	}
	
}
