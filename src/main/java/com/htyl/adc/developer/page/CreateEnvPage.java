package com.htyl.adc.developer.page;

import com.htyl.adc.comm.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CreateEnvPage {
    WebDriver driver;

    public CreateEnvPage(WebDriver driver) {
        this.driver = driver;
    }


    public void createEnvFun(String name) throws Exception {
        driver.get(Constants.CREATEENV);
        WebElement element = driver.findElement(By.id("description"));
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("description")));
        element.sendKeys(name);
        driver.findElement(By.id("createEnv")).click();
        new WebDriverWait(driver, 40)
                .until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(By.cssSelector("span.btn:nth-child(1)")));
        String text = driver.findElement(By.className("ht-modal-default-text")).getText();

        Assert.assertEquals( text,"环境创建成功!请等待管理员审核","失败，未检测到环境创建成功");

    }

    public void deleteEnvfun(String name) throws Exception {
        driver.get(Constants.ENVLIST);
        //点击删除
        new WebDriverWait(driver, 30).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text()," + name + ")]")));
        driver.findElement(By.xpath("//td[contains(text()," + name + ")]/..//button[contains(text(),\"删除\")]"))
                .click();

        //删除确认
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")).click();

        //删除成功
        new WebDriverWait(driver, 15).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")));
        String text = driver.findElement(By.className("ht-modal-default-text")).getText();
        if(text.contains("删除成功")){
            driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg.sureBtn")).click();

            text = driver.findElement(By.cssSelector("#listBody > tr:nth-child(1) > td:nth-child(1)")).getText();
        }else {
            text = name;
        }

        Assert.assertNotEquals(text, name);

    }

    public void reviewEnvFun(String name) throws Exception{




    }
}
