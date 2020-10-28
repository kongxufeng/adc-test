package com.htyl.adc.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.htyl.adc.utils.TestFailListener;
import io.qameta.allure.Features;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.htyl.adc.comm.Comm;
import com.htyl.adc.developer.page.CreateMirrorPage;
import com.htyl.adc.utils.BaseTest;


//镜像测试
@Story("镜像测试")//测试case
public class CreatMirrorTest extends BaseTest {
	private String name = "login";
	@Test(description = "创建镜像")
	public void createMirrorTest() throws Exception {
		// 镜像名称
		SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");// 设置日期格式
		String tag_name = df.format(new Date());
		//name = name + time;
		Comm.init(driver, "应用管理");
		CreateMirrorPage createMirror = new CreateMirrorPage(driver);
		createMirror.createMirrorFun(name,tag_name);
	}

	@Test(dependsOnMethods = "createMirrorTest", description = "删除镜像")
	public void deleteMirrorTest() throws Exception {
		//Comm.init(driver, "应用管理");
		CreateMirrorPage createMirror = new CreateMirrorPage(driver);
		createMirror.deleteMirrorFun(name);
	}
}
