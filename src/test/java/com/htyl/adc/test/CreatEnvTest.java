package com.htyl.adc.test;

import com.htyl.adc.comm.Comm;
import com.htyl.adc.developer.page.CreateEnvPage;
import com.htyl.adc.developer.page.OpenServicePage;
import com.htyl.adc.utils.BaseTest;
import com.htyl.adc.utils.TestFailListener;
import io.qameta.allure.Story;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

//环境测试
@Story("环境测试")//测试case
public class CreatEnvTest extends BaseTest {
    private String name = "env";

    @Test(description = "创建环境")
    public void creatEnv() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");// 设置日期格式
        String time = df.format(new Date());
        name = "env" + time;
        Comm.init(driver, "应用管理");
        CreateEnvPage CreateEnv = new CreateEnvPage(driver);
        CreateEnv.createEnvFun(name);
    }

    @Test(dependsOnMethods = "creatEnv",description = "审核环境")
    public void reviewEnv() throws Exception {
    

    }
    @Test(dependsOnMethods = "creatEnv", description = "删除环境")
    public void deleteEnv() throws Exception {
        //Comm.init(driver, "应用管理");
        CreateEnvPage deleteEnv = new CreateEnvPage(driver);
        deleteEnv.deleteEnvfun(name);
    }
}
