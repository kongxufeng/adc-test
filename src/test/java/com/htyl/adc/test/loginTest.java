package com.htyl.adc.test;

import com.htyl.adc.comm.Comm;
import com.htyl.adc.utils.BaseTest;
import org.testng.annotations.Test;

public class loginTest extends BaseTest {
    @Test(description = "登录")
    public void main() throws Exception{
        Comm.init(driver, "应用管理");
    }
}
