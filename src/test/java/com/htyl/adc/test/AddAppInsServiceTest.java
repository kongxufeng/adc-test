package com.htyl.adc.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.htyl.adc.utils.TestFailListener;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.htyl.adc.comm.Comm;
import com.htyl.adc.comm.Constants;
import com.htyl.adc.developer.page.AddAppPage;
import com.htyl.adc.developer.page.CreateServicePage;
import com.htyl.adc.developer.page.DeleteAppPage;
import com.htyl.adc.developer.page.OpenServicePage;
import com.htyl.adc.utils.BaseTest;

//创建应用与实例服务
@Story("应用测试")//测试case
public class AddAppInsServiceTest extends BaseTest {
	private String appname = "应用名称zmr";
	private String inname = "login";
	private String environmentName = "默认开发环境";

	@Test(description = "创建应用并运行")
	public void addAppTest() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");// 设置日期格式
		String time = df.format(new Date());
		appname = "应用zmr" + time;
		Comm.init(driver, "应用管理");
		AddAppPage addAppPage = new AddAppPage(driver);
		addAppPage.addAndRunAPPfun(appname, "login", environmentName);
	}

	@Test(description = "创建服务" ,dependsOnMethods = "addAppTest")
	public void createServiceTest() throws Exception {
		//Comm.init(driver, "应用管理");
		CreateServicePage createServicePage = new CreateServicePage(driver);
		inname = "login" + appname.substring(5);
        System.out.println("实例名称是"+inname);
		createServicePage.createServiceFun("appservicezmr", "应用", appname, inname);
	}

	@Test(description = "打开服务",dependsOnMethods = "createServiceTest")
	public void openServiceTest() throws Exception {
		//Comm.init(driver, "应用管理");
		OpenServicePage openServicePage = new OpenServicePage(driver);
		openServicePage.openServiceFun("appservicezmr");
		/*
		String text = openServicePage.openServiceFun(inname);
		driver.get("http://" + text);
		String title ="";
		for (int i=0;i<6;i++){
			if (title.isEmpty()){
				Thread.sleep(2000);
				title = driver.getTitle();
			}else {
				break;
			}
		}
		Assert.assertEquals(title,"LoginTest");

		 */

	}

	@Test(description = "删除服务" ,dependsOnMethods = "createServiceTest" )
	public void deleteServiceFun() throws Exception {
		//Comm.init(driver, "应用管理");
		OpenServicePage openServicePage = new OpenServicePage(driver);
		openServicePage.deleteServiceFun(inname);
	}

	@Test(description = "删除应用",dependsOnMethods = "deleteServiceFun")
	public void deleteAppFun() throws Exception {
		//Comm.init(driver, "应用管理");
		driver.get(Constants.APPURL);
		DeleteAppPage deleteAppPage = new DeleteAppPage(driver);
		deleteAppPage.deleteAppFun(appname);
	}

}
