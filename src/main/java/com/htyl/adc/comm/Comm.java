package com.htyl.adc.comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.htyl.adc.page.LoginPage;

public class Comm {
	public static void switchwindow(WebDriver driver) {
		//获取当前页面句柄
		String currentHandle = driver.getWindowHandle();
		//获取所有窗口句柄
		Set<String> allhandle = driver.getWindowHandles();
		//循环判断不是当前的句柄
		for (String winHandle : allhandle) {
			if (winHandle.equals(currentHandle))
				continue;
			driver.switchTo().window(winHandle);
			break;
		}

	}

	public static void init(WebDriver driver, String ulr) throws Exception {

		if (ulr.equals("应用管理") || ulr.equals("微服务管理")) {
			if (ulr.equals("应用管理")) {
				driver.get(Constants.APPURL);
				//getCookies(driver, "user");
				Thread.sleep(3000);
				//driver.get(Constants.APPURL);
			} else if (ulr.equals("微服务管理")) {
				driver.get(Constants.SERVICEURL);
				getCookies(driver, "user");
				Thread.sleep(2000);
				driver.get(Constants.SERVICEURL);
			}
			if (driver.getTitle().contains("航天云网登录")) {
				// 登录
				LoginPage LoginPage = new LoginPage(driver);
				LoginPage.login(Constants.USERNAME, Constants.USERPW);
				setCookies(driver, Constants.USERNAME, Constants.USERPW, "user");
			}
		} else {
			driver.get(Constants.APPManager);
			getCookies(driver, "admin");
			driver.get(Constants.APPManager);
			if (driver.getTitle().contains("航天云网登录")) {
				// 登录
				LoginPage LoginPage = new LoginPage(driver);
				LoginPage.login(Constants.AdminNAME, Constants.AdminPW);
				setCookies(driver, Constants.AdminNAME, Constants.AdminPW, "admin");
			}
		}


	}

	public static void init(WebDriver driver) {
		//driver.get(Constants.BasicURL);
		try {
			getCookies(driver, "user");
			//driver.findElement(By.cssSelector("li.swiper-slide:nth-child(8) > a:nth-child(1)")).click();
			driver.get(Constants.BasicURL);
			if (driver.getTitle().contains("航天云网登录")) {
				LoginPage LoginPage = new LoginPage(driver);
				LoginPage.login(Constants.USERNAME, Constants.USERPW);
				setCookies(driver, Constants.USERNAME, Constants.USERPW, "user");
			}
		} catch (Exception e) {

		}

	}

	public static void setCookies(WebDriver driver, String username, String pswd, String userinfo) throws Exception {

		File cookieFile = new File(userinfo + ".txt");
		try {
			cookieFile.delete();
			cookieFile.createNewFile();
			FileWriter fileWriter = new FileWriter(cookieFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (Cookie cookie : driver.manage().getCookies()) {
				bufferedWriter.write((cookie.getName() + ";" + cookie.getValue() + ";" + cookie.getDomain() + ";"
						+ cookie.getPath() + ";" + cookie.getExpiry() + ";" + cookie.isSecure()));
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getCookies(WebDriver driver, String userinfo) {
		BufferedReader bufferedReader;
		try {
			File cookieFile = new File(userinfo + ".txt");
			FileReader fileReader = new FileReader(cookieFile);
			bufferedReader = new BufferedReader(fileReader);

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				StringTokenizer stringTokenizer = new StringTokenizer(line, ";");
				while (stringTokenizer.hasMoreTokens()) {

					String name = stringTokenizer.nextToken();
					String value = stringTokenizer.nextToken();
					String domain = stringTokenizer.nextToken();
					String path = stringTokenizer.nextToken();
					Date expiry = null;
					String dt;

					if (!(dt = stringTokenizer.nextToken()).equals("null")) {
						expiry = new Date(dt);
					}

					boolean isSecure = new Boolean(stringTokenizer.nextToken()).booleanValue();
					Cookie cookie = new Cookie(name, value, domain, path, expiry, isSecure);
					driver.manage().addCookie(cookie);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 切换页签
	public static void switchToWindow(WebDriver driver, String titlestr) throws Exception {
		for (int a = 0; a < 9; a++) {
			Set<String> windowHandles = driver.getWindowHandles();
			for (String handler : windowHandles) {
				driver.switchTo().window(handler);
				String title = driver.getTitle();
				if (titlestr.equals(title)) {
					a = 10;
					break;
				}
			}
		}
	}

	public static int getStatusCode(String url) throws Exception {
		int statusCode = 0;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(request);
			statusCode = response.getStatusLine().getStatusCode();

		} catch (ClientProtocolException e) {
			System.out.println("获取" + url + "报错");

		}

		return statusCode;
	}

}
