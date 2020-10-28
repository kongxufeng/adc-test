package com.htyl.adc.developer.page;

import com.htyl.adc.comm.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StorgePage {
    WebDriver driver;

    public StorgePage(WebDriver driver) {
        this.driver = driver;
    }


    public void CreateStorgeFun(String name) throws Exception {
        driver.get(Constants.storage);
        //进入创建存储页面
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.hidden-xs:nth-child(2)")));
        driver.findElement(By.cssSelector("a.hidden-xs:nth-child(2)")).click();
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("detailEnv")));
        //存储名称
        driver.findElement(By.id("name")).sendKeys(name);
        //部署环境
        Select select = new Select(driver.findElement(By.id("detailEnv")));
        select.selectByVisibleText("默认开发环境");
        //容量配置
        driver.findElement(By.id("storageSize")).sendKeys("10");
        //绑定实例
        //创建
        driver.findElement(By.id("createEnv")).click();
        //断言
        new WebDriverWait(driver, 30).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("listBody")));
        String storgename = driver.findElement(By.cssSelector("#listBody > tr:nth-child(1) > td:nth-child(1)")).getText();

        Assert.assertEquals(name, storgename, "创建完成后列表存储名称不一致");

    }

    public void DeleteStorge(String name) throws Exception{
        driver.get(Constants.storage);
        //点击删除
        driver.findElement(By.xpath("//td[contains(text()," + name + ")]/..//button[contains(text(),\"删除\")]")).click();
        new WebDriverWait(driver, 20).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn"))
        );
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")).click();
        //断言
        new WebDriverWait(driver, 20).until(
                ExpectedConditions.visibilityOfElementLocated(By.className("ht-modal-default-text"))
        );
        String text = driver.findElement(By.className("ht-modal-default-text")).getText();
        Assert.assertEquals(text, "删除成功");
        driver.findElement(By.cssSelector("span.btn")).click();
        String storgename = driver.findElement(By.cssSelector("#listBody > tr:nth-child(1) > td:nth-child(1)")).getText();
        Assert.assertNotEquals(storgename, name);

    }


}
