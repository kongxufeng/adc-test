package com.htyl.adc.test;

import com.htyl.adc.utils.TestFailListener;
import io.qameta.allure.Story;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.htyl.adc.comm.Comm;
import com.htyl.adc.developer.page.ApplicationPage;
import com.htyl.adc.utils.BaseTest;


@Story("应用试用测试")
//应用商店
public class ApplicationTest extends BaseTest {
	private String appname = "";
	private String servicename = "";

	@Test(description = "应用商店推荐应用试用测试")
	public void applicationTest1() throws Exception {
		Comm.init(driver);
		ApplicationPage applicationPage = new ApplicationPage(driver);
		applicationPage.applicationFun("应用商店");
	}

    @Test(description = "首页推荐应用试用测试")
    public void applicationTest2() throws Exception {
        Comm.init(driver);
        ApplicationPage applicationPage = new ApplicationPage(driver);
        applicationPage.applicationFun("首页");
    }

    @Test(description = "研发设计试用测试")
    public void applicationTest3() throws Exception {
        Comm.init(driver);
        ApplicationPage applicationPage = new ApplicationPage(driver);
        applicationPage.applicationFun2("2");
    }

    @Test(description = "设备监控试用测试")
    public void applicationTest4() throws Exception {
        Comm.init(driver);
        ApplicationPage applicationPage = new ApplicationPage(driver);
        applicationPage.applicationFun2("3");
    }

    @Test(description = "能耗管理试用测试")
    public void applicationTest5() throws Exception {
        Comm.init(driver);
        ApplicationPage applicationPage = new ApplicationPage(driver);
        applicationPage.applicationFun2("4");
    }
    @Test(description = "计划排产试用测试")
    public void applicationTest6() throws Exception {
        Comm.init(driver);
        ApplicationPage applicationPage = new ApplicationPage(driver);
        applicationPage.applicationFun2("5");
    }
    @Test(description = "供应链管理试用测试")
    public void applicationTest7() throws Exception {
        Comm.init(driver);
        ApplicationPage applicationPage = new ApplicationPage(driver);
        applicationPage.applicationFun2("6");
    }
    @Test(description = "其它试用测试")
    public void applicationTest8() throws Exception {
        Comm.init(driver);
        ApplicationPage applicationPage = new ApplicationPage(driver);
        applicationPage.applicationFun2("7");
    }

//	@Test(description = "微服务商店")
//	public void applicationTest2() throws Exception {
//		Comm.init(driver);
//		ApplicationPage applicationPage = new ApplicationPage(driver);
//		applicationPage.serviceMarketFun(servicename);
//	}

}
