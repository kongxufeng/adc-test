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


//创建微服务
public class AddServiceTest extends BaseTest {
	private String servicename = "微服务名称zmr";

	@Test(description = "创建微服务")
	public void addServiceTest() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String time = df.format(new Date());
		servicename = "微服务zmr" + time;
		Comm.init(driver, "微服务管理");
		AddAppPage addServicePage = new AddAppPage(driver);
		addServicePage.addServicefun(servicename);
	}

	@Test(dependsOnMethods = "addServiceTest", description = "删除微服务")
	public void deleteAppTest() throws Exception {
		Comm.init(driver, "微服务管理");
		driver.get(Constants.SERVICEURL);
		DeleteAppPage deleteAppPage = new DeleteAppPage(driver);
		deleteAppPage.deleteAppFun(servicename);
	}

}
