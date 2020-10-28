package com.htyl.adc.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.htyl.adc.utils.TestFailListener;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.htyl.adc.comm.Comm;
import com.htyl.adc.developer.page.AddMiddleWarePage;
import com.htyl.adc.developer.page.CreateServicePage;
import com.htyl.adc.developer.page.OpenServicePage;
import com.htyl.adc.utils.BaseTest;
import com.test.sql.MySQLCON;

//创建中间件-创建服务流程
@Story("中间件测试")//测试case
public class AddMiddleWareTest extends BaseTest {
	private String middleWarename = "中间件名称zmr";
	private String environmentName = "默认开发环境";
	private String mqsqlurl = "";
	private Logger logger = LogManager.getLogger();
	@Test(description = "创建中间件")
	public void addMiddleWareTest() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");// 设置日期格式
		String time = df.format(new Date());
		middleWarename = "zmrdb" + time;
		logger.info("创建中间件名称是："+middleWarename);
		Comm.init(driver, "应用管理");
		AddMiddleWarePage addMiddleWarePage = new AddMiddleWarePage(driver);
		addMiddleWarePage.addMiddleWarefun(middleWarename, environmentName);
	}

	@Test(description = "创建服务",dependsOnMethods = "addMiddleWareTest")
	public void createServiceTest() throws Exception {
		//Comm.init(driver, "应用管理");
		CreateServicePage createServicePage = new CreateServicePage(driver);
		createServicePage.createServiceFun("servicezmr", "中间件", "mysql",middleWarename);

	}

	@Test(description = "打开服务",dependsOnMethods = "createServiceTest")
	public void openServiceTest() throws Exception {
		//Comm.init(driver, "应用管理");
		OpenServicePage openServicePage = new OpenServicePage(driver);
		//mqsqlurl = openServicePage.openServiceFun(middleWarename);
		openServicePage.openServiceFun("servicezmr");

	}

	@Test(description = "链接数据库", timeOut = 240000)
	public void MySQLCONTest() throws Exception {
		//Comm.init(driver, "应用管理");
		System.out.println(mqsqlurl);
		Thread.sleep(80000);
		MySQLCON.connectionFun(mqsqlurl);
	}

	@Test(description = "删除中间件",dependsOnMethods = "createServiceTest")
	public void deleteMiddleWareTest() throws Exception {
		//Comm.init(driver, "应用管理");
		AddMiddleWarePage dtMiddleWarePage = new AddMiddleWarePage(driver);
		dtMiddleWarePage.deleteMiddleWare(middleWarename);

	}

}
