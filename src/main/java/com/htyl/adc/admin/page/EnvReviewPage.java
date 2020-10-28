package com.htyl.adc.admin.page;

import com.htyl.adc.comm.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class EnvReviewPage {
    WebDriver driver;
    public EnvReviewPage(WebDriver driver) {
        this.driver = driver;
    }
    public void envreviewfun(String envid) throws Exception {
        driver.get(Constants.ENVURL);
        new WebDriverWait(driver, 30).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),环境Id:" + envid + ")]")));
        driver.findElement(By.xpath("//td[contains(text(),环境Id:" + envid + ")]" +
                "/following-sibling::td[@class='audit']/a[@class='audit_order']")).click();
        driver.findElement(By.id("edit_save_btn")).click();
        Thread.sleep(3000);
        String a = driver.switchTo().alert().getText();
        Assert.assertEquals(a, "审核成功");
        driver.switchTo().alert().accept();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//td[contains(text(),环境Id:" + envid + ")]" +
                "/following-sibling::td[@class='audit']/a[@class='update_resourcequota']")).click();
        Thread.sleep(3000);
        String b = driver.switchTo().alert().getText();
        Assert.assertEquals(b, "更新成功");
        driver.switchTo().alert().accept();
        String status = driver.findElement(By.xpath("//td[contains(text(),环境Id:" + envid + ")]" +
                "/following-sibling::td[@class='status']")).getText();
        Assert.assertEquals(status, "已发货");
    }

}
