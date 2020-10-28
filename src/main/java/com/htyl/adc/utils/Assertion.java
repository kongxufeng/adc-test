package com.htyl.adc.utils;

import com.htyl.adc.comm.SnapshotManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Assertion {
    public static boolean flag = true;
    private static Logger logger = LogManager.getLogger();
    public static void verifyEquals(Object actual, Object expected) {
        try {
            Assert.assertEquals(expected, actual);
        } catch (Error e) {
            flag = false;
            logger.error("数据对比错误-> 期望值为:" + expected + "实际值为:" + actual);
        }
    }

    public static void verifyEquals(Object actual, Object expected,
                                    String message) {
        try {
            Assert.assertEquals(expected, actual, message);
        } catch (Error e) {
            flag = false;
            logger.error("数据对比错误-> 期望值为:" + expected + "实际值为:" + actual);
        }
    }

    public static void verifyTrue(boolean var0, String var1) {
        try {
            Assert.assertTrue(var0, var1);
            logger.info(var1);
        } catch (Error e) {
            flag = false;
            logger.error(var1);
        }
    }


}

