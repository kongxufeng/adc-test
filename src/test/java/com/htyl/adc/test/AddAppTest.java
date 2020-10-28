package com.htyl.adc.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.htyl.adc.utils.TestFailListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.htyl.adc.comm.Comm;
import com.htyl.adc.comm.Constants;
import com.htyl.adc.developer.page.AddAppPage;
import com.htyl.adc.developer.page.DeleteAppPage;
import com.htyl.adc.utils.BaseTest;

//创建应用
public class AddAppTest extends BaseTest {
	private String appname = "应用名称zmr";

	@Test(description = "创建应用")
	public void addAppTest() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String time = df.format(new Date());
		appname = "应用zmr" + time;

		Comm.init(driver, "应用管理");
		AddAppPage addAppPage = new AddAppPage(driver);
		addAppPage.addAppfun(appname);
	}


	@Test(dependsOnMethods = "addAppTest", description = "删除应用")
	public void deleteAppTest() throws Exception {
		Comm.init(driver, "应用管理");
		driver.get(Constants.APPURL);
		DeleteAppPage deleteAppPage = new DeleteAppPage(driver);
		deleteAppPage.deleteAppFun(appname);
	}

}
