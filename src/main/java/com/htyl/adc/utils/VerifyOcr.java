package com.htyl.adc.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static com.htyl.adc.utils.ChaoJiYing.PostPic;

public class VerifyOcr {
    private static String username ="kxfhao111";
    private static String password ="kxflltt111";
    private static String softid ="906515";
    private static String codetype ="9103";
    private static String len_min ="0";
    private WebDriver driver;
    @FindBy(css = ".back-img")
    WebElement img;//字符验证码图片元素
    @FindBy(css = ".backImg")
    WebElement img2;//滑动验证码图片元素
    @FindBy(css = ".verifybox-bottom > div:nth-child(1)")
    WebElement img3;//点选验证码图片元素
    @FindBy(css = ".verify-bar-area > span:nth-child(1)")
    WebElement verifyele;//点选验证码文字元素
    @FindBy(css = ".verify-img-out")
    WebElement verify_img;//统一验证元素
    @FindBy(css = ".verify-move-block")
    WebElement move;//滑块


    public VerifyOcr(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // 截取图片
    public static String ImgSave(WebDriver driver,WebElement ele) {
        String originalImg ="";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {// Get entire page screenshot
            BufferedImage fullImg;
            fullImg = ImageIO.read(screenshot);
            Point point = ele.getLocation();
            int eleWidth = ele.getSize().getWidth();
            int eleHeight = ele.getSize().getHeight();
            BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", screenshot);
            originalImg = "screenshots/" +filename() + ".png";
            File screenshotLocation = new File(originalImg);
            FileUtils.copyFile(screenshot, screenshotLocation.getCanonicalFile());
            return screenshotLocation.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return originalImg;
    }
    private static String filename(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        String time = df.format(new Date());
        String random = Integer.toString(new Random().nextInt((100) + 5));
        return time+random;
    }

    //字符验证码识别
    public  void ocrString(){
        try {
        // 原始地址
        String originalImg = ImgSave(driver, img);
        // 识别样本输出地址
        String ocrResult = "screenshots/" +filename() + ".jpg";
        File screenshotLocation = new File(ocrResult);
        // 去除噪点
        ImgUtils.removeBackground(originalImg, screenshotLocation.getCanonicalPath());
        String code = "";
        // ocr识别
        code = ImgUtils.executeTess4J(ocrResult);
        code.replaceAll("[^a-zA-Z0-9]", "");
        driver.findElement(By.id("verifycode_img")).sendKeys(code);
        } catch (Exception e) {
        }
        driver.findElement(By.cssSelector(".verify-button")).click();
    }

    //滑动验证码识别
    public  void ocrSlide(){
        //去掉滑块截图1
        driver.findElement(By.tagName("img"));
        String js = "document.getElementsByClassName(\"bock-backImg\")[0].style.display=\"none\"";
        ((JavascriptExecutor) driver).executeScript(js);
        String image1 = ImgSave(driver, img2);
        //背景变黑截图2
        js = "document.getElementsByClassName(\"backImg\")[0].style.background=\"#0e0e0e\"";
        ((JavascriptExecutor) driver).executeScript(js);
        String image2 = ImgSave(driver, img2);
        //恢复背景色
        js = "document.getElementsByClassName(\"backImg\")[0].style.background=\"\"";
        ((JavascriptExecutor) driver).executeScript(js);
        //恢复滑块
        js = "document.getElementsByClassName(\"bock-backImg\")[0].style.display=\"block\"";
        ((JavascriptExecutor) driver).executeScript(js);
        //对比图片1、2颜色差值，计算需要平移的距离
        int distance = 0;
        try {
            BufferedImage fullBI = ImageIO.read(new File(image2));
            BufferedImage bgBI = ImageIO.read(new File(image1));
            a:for (int i = 55; i < bgBI.getWidth(); i++){
                for (int j = 1; j < bgBI.getHeight(); j++) {
                    int[] fullRgb = new int[3];
                    fullRgb[0] = (fullBI.getRGB(i, j)  & 0xff0000) >> 16;
                    fullRgb[1] = (fullBI.getRGB(i, j)  & 0xff00) >> 8;
                    fullRgb[2] = (fullBI.getRGB(i, j)  & 0xff);

                    int[] bgRgb = new int[3];
                    bgRgb[0] = (bgBI.getRGB(i, j)  & 0xff0000) >> 16;
                    bgRgb[1] = (bgBI.getRGB(i, j)  & 0xff00) >> 8;
                    bgRgb[2] = (bgBI.getRGB(i, j)  & 0xff);
                    if(difference(fullRgb, bgRgb) == 582){
                         distance = i;
                         break a;
                    }
                }
            }
        }catch (Exception e){e.printStackTrace();}
        //移动
        try {
            int xDis = distance;
            //System.out.println("应平移距离：" + distance);
            Actions actions = new Actions(driver);
            new Actions(driver).clickAndHold(move).perform();
            Thread.sleep(200);
            int s = 5;
            actions.moveToElement(move, s, 1).perform();
            Thread.sleep(new Random().nextInt(100) + 50);
            xDis = xDis-10;
            System.out.println(xDis + "--" + 1);
            actions.moveByOffset(xDis, 1).perform();
            Thread.sleep(200);
            actions.release(move).perform();
        }catch (Exception e){e.printStackTrace();}
    }

    private  int difference(int[] a, int[] b){
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) + Math.abs(a[2] - b[2]);
    }

    //点选验证码识别，调用超级鹰接口需付费
    public  void ocrClick(){
        //截图,调用超级鹰接口
        String result = PostPic(username,password,softid,codetype,len_min,ImgSave(driver,img3));
        JsonParser jp = new JsonParser();
        //将json字符串转化成json对象
        JsonObject jo = jp.parse(result).getAsJsonObject();
        //获取pic_str字符串
        String pic_str = jo.get("pic_str").getAsString();
        System.out.println(pic_str);
        //处理返回值坐标
        String[] list= pic_str.split("\\|");
        ArrayList<Point> points = new ArrayList<>();
        int[] x =new int[3];
        int[] y =new int[3];
        for (int i =0;i<list.length;i++){
            String[] list2 =list[i].split("\\,");
            x[i]=Integer.valueOf(list2[0]);
            y[i]=Integer.valueOf(list2[1]);
            Point point = new Point(x[i],y[i]);
            points.add(point);
        }
        //点选3个坐标
        Actions actions = new Actions(driver);
        for (int i =0;i<3;i++){
            new Actions(driver).moveToElement(img3, -149, -103).perform();
            actions.moveByOffset(points.get(i).x,points.get(i).y).perform();
            actions.click().perform();
        }
    }

    //判断验证码类型并执行，1字符验证码、2滑动验证码、3点选验证码
    public  void verifyRun(){
        int height = verify_img.getSize().height;
        if (height<155){//字符验证码
            ocrString();
        }else if(!verifyele.getText().contains("请依次点击")){//滑动验证码
            ocrSlide();
        }else {//点选验证码
            ocrClick();
        }
    }

}