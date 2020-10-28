package com.test.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.Assert;

public class MySQLCON {

	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	//static String DB_URL = "jdbc:mysql://172.20.4.2:11741/zmrdb";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "zmr";
	static final String PASS = "123456";

	public static void connectionFun(String ulr) {
		Connection conn = null;
		Statement stmt = null;
		String DB_URL = "jdbc:mysql://" + ulr + "/zmrdb";
		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("连接数据库成功");
			// 执行查询
			Assert.assertTrue(true, "数据库链接成功");
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, "数据库链接失败");
		} finally {
			// 关闭资源
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // 什么都不做
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}