package com.htyl.adc.developer.page;

import java.util.List;

import com.htyl.adc.comm.Constants;
import com.htyl.adc.comm.ErroCode;
import com.htyl.adc.utils.Assertion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htyl.adc.comm.Comm;
import org.testng.annotations.Listeners;

@Listeners
//应用商店
public class ApplicationPage {
	WebDriver driver;
	Logger logger = LogManager.getLogger();
	public ApplicationPage(WebDriver driver) {
		this.driver = driver;
	}

	public void applicationFun(String name) throws Exception {
		List<WebElement> findElements = null;
		if (name =="首页"){
			new WebDriverWait(driver, 5).until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".demonstrate-right-list-title")));
			findElements = driver.findElements(By.cssSelector(".demonstrate-right-list-title"));
		}else if(name == "应用商店"){
			driver.get(Constants.appstore);
			new WebDriverWait(driver, 5).until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.col-md-3.major-recommend")));
			findElements = driver.findElements(By.cssSelector("a.col-md-3.major-recommend,a.single-recommend"));
		}else {
			driver.get(Constants.APPURL);
		}
		String winHandleBefore = driver.getWindowHandle();
		for (int i=0;i<findElements.size();i++){
			findElements.get(i).click();
			for (String winHandle : driver.getWindowHandles()) {
				if (winHandle.equals(winHandleBefore)) {
					driver.switchTo().window(winHandle);
					Thread.sleep(500);
					continue;
				}
				// 切换浏览器页签
				driver.switchTo().window(winHandle);
				break;
			}
			new WebDriverWait(driver, 5).until(
					ExpectedConditions.visibilityOfElementLocated(By.id("applyUse")));
			driver.findElement(By.id("applyUse")).click();
			String erroPage = "";
			String title = "";
			try {
				Thread.sleep(2000);
				title = driver.getTitle();
				erroPage = ErroCode.checkErroPage(title);
			} catch (TimeoutException e) {
				erroPage = "时间超时";
			}
			if (!erroPage.isEmpty()) {
				Assert.assertTrue(false, "应用访问失败");
			} else {
				Assert.assertTrue(true, "应用访问成功");

			}
			driver.close();
			for (String winHandle : driver.getWindowHandles()) {
				if (winHandle.equals(winHandleBefore)) {
					driver.switchTo().window(winHandle);
					continue;
				}
				// 切换浏览器页签
				driver.switchTo().window(winHandle);
				driver.close();
				break;
			}
			driver.switchTo().window(winHandleBefore);
			Thread.sleep(1000);
			continue;
		}
	}

    public void applicationFun2(String type) throws Exception {
        Assertion.flag =true;
        driver.get(Constants.appstorelist);
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ringBox > li:nth-child(2)")));
        driver.findElement(By.cssSelector("#ringBox > li:nth-child("+type+")")).click();
        String current ="1";
        for(int j=0;j<15;j++){
            //获取应用进入详情页点击试用
            new WebDriverWait(driver, 10).until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.image-box")));
            List<WebElement> findElements = driver.findElements(By.cssSelector("div.image-box"));
            new WebDriverWait(driver, 10).until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.total.total_page > span:nth-child(1)")));
            current = driver.findElement(By.cssSelector(".current.num")).getText();
            String winHandleBefore = driver.getWindowHandle();
            for (int i=0;i<findElements.size();i++){
                findElements.get(i).click();
                int num = i+1;
                logger.info("当前是第"+current+"页，"+"点击第"+num+"个应用");
                Comm.switchwindow(driver);

				new WebDriverWait(driver, 10).until(
						ExpectedConditions.visibilityOfElementLocated(By.id("applyUse")));
				String expected = driver.findElement(By.className("tit")).getText();
				String appType = driver.findElement(By.id("appType")).getText();
				driver.findElement(By.id("applyUse")).click();
				driver.close();
                for (String winHandle : driver.getWindowHandles()) {
                    if (winHandle.equals(winHandleBefore)) {
                        driver.switchTo().window(winHandle);
                        continue;
                    }
                    // 切换浏览器页签
                    driver.switchTo().window(winHandle);
                    break;
                }
                //断言
                String erroPage = "";
                String title = "";
                try {
                    if (appType.contains("第三方SaaS")){//判断嵌入应用的title
                        new WebDriverWait(driver, 10).until(
                                ExpectedConditions.presenceOfElementLocated(By.id("curIframe")));
                        driver.switchTo().frame("curIframe");
                    }
                    for (int m=0;m<8;m++){
                        title = driver.getTitle();
                        if (title.isEmpty()){
                            Thread.sleep(500);
                        }else {
                            break;
                        }
                    }
                    erroPage = ErroCode.checkErroPage(title);
                } catch (TimeoutException e) {
                    erroPage = "未获取到页面标题，页面加载超过5秒";
                }
                if (!erroPage.isEmpty()) {

                    Assertion.verifyTrue(false, "应用访问失败-> 访问应用为:" +expected+"。实际页面显示："+erroPage);

                } else {

                    Assertion.verifyTrue(true, expected+"应用访问成功");
                }
                driver.close();
                driver.switchTo().window(winHandleBefore);
            }
            //翻页
            new WebDriverWait(driver, 10).until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.total.total_page > span:nth-child(1)")));
            current = driver.findElement(By.cssSelector(".current.num")).getText();
            String total = driver.findElement(By.cssSelector("span.total.total_page > span:nth-child(1)")).getText();
            if(!current.equals(total)){
                driver.findElement(By.className("next")).click();
                logger.info("当前列表为第"+current+"页,进行翻页");
            } else{
                j =j+15;
                logger.info("当前列表为第"+current+"页,最大页为"+total+"*****结束测试");
                break;
            }
            continue;

        }
        Assert.assertTrue(Assertion.flag,"应用试用测试已完成，出现应用访问异常,请查看日志文件error.log");

    }

	public void serviceMarketFun(String appname) throws Exception {
		Thread.sleep(1000);
		driver.findElement(By.linkText("工业微服务组件")).click();
		Thread.sleep(2000);
		if (appname.isEmpty()) {
			driver.findElement(By.cssSelector("a[spm='market.applist_detai.view']")).click();
		} else {
			WebElement Element = driver.findElement(By.xpath("//div[@title='" + appname + "']/../div/a"));
			new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(Element));
			Element.click();
		}
		Comm.switchwindow(driver);
		driver.findElement(By.id("applyUseNew")).click();
		Comm.switchwindow(driver);
		driver.findElement(By.id("postRequest")).click();
		String text = "";
		for (int i = 0; i < 10; i++) {
			if (text.isEmpty()) {
				Thread.sleep(1000);
				text = driver.findElement(By.id("json-input")).getText();
			} else {
				break;
			}

		}

		boolean startsWith = text.contains("successful");
		if (!text.isEmpty()) {
			Assert.assertTrue(true, "应用访问成功");
		} else {
			Assert.assertTrue(false, "应用访问失败");
		}

	}

}
