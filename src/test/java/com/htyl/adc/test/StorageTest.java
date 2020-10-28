package com.htyl.adc.test;

import com.htyl.adc.comm.Comm;
import com.htyl.adc.developer.page.StorgePage;
import com.htyl.adc.utils.BaseTest;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

@Story("存储测试")//测试case
public class StorageTest extends BaseTest {

    @Test(description = "创建存储")
    public void createStorage() throws Exception {
        // 镜像名称
        SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");// 设置日期格式
        String tag_name = df.format(new Date());
        //name = name + time;
        Comm.init(driver, "应用管理");
        StorgePage createstorge = new StorgePage(driver);
        createstorge.CreateStorgeFun(tag_name);
    }

}
