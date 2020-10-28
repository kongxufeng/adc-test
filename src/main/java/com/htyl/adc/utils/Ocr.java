package com.htyl.adc.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Ocr {
    private WebDriver driver;
    @FindBy(className = "bock-backImg")
    WebElement img1;//滑动验证码验证图元素
    @FindBy(css = ".backImg")
    WebElement img2;//滑动验证码背景图元素
    @FindBy(css = ".verify-move-block")
    WebElement move;//滑块
    private static Logger logger = LogManager.getLogger();
    public Ocr(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private static String filename(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        String time = df.format(new Date());
        String random = Integer.toString(new Random().nextInt((100) + 5));
        return time+random;
    }

    //运行
    public void run() throws Exception {
        //截图image1验证图
        String base64 = img1.getAttribute("src").substring(22);
        String imgName = filename();
        decodeBase64ToImage(base64, "screenshots/","base64"+imgName+".png");
        File image1 = new File("screenshots/base64"+imgName+".png");
        File verifyBinaryImgFile = new File("screenshots/"+imgName+".png");
        binaryImage(image1, verifyBinaryImgFile, false);

        //去掉滑块截图image1背景图
        driver.findElement(By.tagName("img"));
        String js = "document.getElementsByClassName(\"bock-backImg\")[0].style.display=\"none\"";
        ((JavascriptExecutor) driver).executeScript(js);
        File image2 = new File(VerifyOcr.ImgSave(driver, img2));
        File bgImgBinaryFile = new File("screenshots/"+filename()+".png");
        binaryImage(image2, bgImgBinaryFile, true);

        int distance = searchLocation(bgImgBinaryFile,verifyBinaryImgFile);

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

/**
       * 将Base64位编码的图片进行解码，并保存到指定目录
       *
       * @param base64
       *            base64编码的图片信息
       * @return
       */
    public static void decodeBase64ToImage(String base64, String path,
             String imgName) {
                 BASE64Decoder decoder = new BASE64Decoder();
                 try {
                         FileOutputStream write = new FileOutputStream(new File(path
                                         + imgName));
                         byte[] decoderBytes = decoder.decodeBuffer(base64);
                         write.write(decoderBytes);
                         write.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
             }

    /**

     * 对图片进行二值化

     * @author

     * @param imageFile 原图

     * @param descFile 二值化之后存储的文件

     * @param bgImage 是否为背景图(背景图与验证图处理方式不同)

     * @throws IOException

     */

    public void binaryImage(File imageFile, File descFile, boolean bgImage) throws IOException {

        String fileName=imageFile.getName();

        String fileType=imageFile.getName().substring(fileName.lastIndexOf(".")+1);

        BufferedImage image = ImageIO.read(imageFile);

        int width = image.getWidth();

        int height = image.getHeight();

        BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for(int i= 0 ; i < width ; i++){

            for(int j = 0 ; j < height; j++){

                int rgb=image.getRGB(i, j);

                if(bgImage) {

                    rgb=getBgImageRgb(rgb);

                }else {

                    rgb = getImageRgb(rgb);

                }

                binaryImage.setRGB(i, j, rgb);

            }

        }

        ImageIO.write(binaryImage, fileType, descFile);

    }

    /**

     * 如果使用该方法二值化不行，需要调整参数r>200&g>200&b>200的参数

     * @author

     * @param i

     * @return

     */

    private static int getImageRgb(int i) {

        String data=Integer.toHexString(i)+"";

        if(data.length()==6) {

            return 0x00;

        }

        int rgb =i& 0xFFFFFF;

        int r=(rgb& 0xff0000) >> 16;

        int g=(rgb& 0xff00) >> 8;

        int b=(rgb& 0xff);

        if(r>200&g>200&b>200) {

            return 0xFFFFFF;

        }

        return 0x00;

    }


    /**

     * 如果使用该方法二值化不行，需要调整参数avg>120&avg<160的参数

     * @author

     * @param i

     * @return

     */

    private static int getBgImageRgb(int i) {

        int rgb =i& 0xFFFFFF;

        int r=(rgb& 0xff0000) >> 16;

        int g=(rgb& 0xff00) >> 8;

        int b=(rgb& 0xff);

        int avg=(r+g+b)/3;

        if(avg>200) {

            return 0xFFFFFF;

        }

        return 0x00;

    }

    /**

     *

     * 搜索图片位子

     * @param bgImgBinaryFile 二值化之后的背景图

     * @param verifyBinaryImgFile 二值化之后的校验图

     * @return

     * @throws IOException

     */

    public int searchLocation(File bgImgBinaryFile,File verifyBinaryImgFile) throws IOException {

        BufferedImage bgImg = ImageIO.read(bgImgBinaryFile);

        BufferedImage vrImg = ImageIO.read(verifyBinaryImgFile);

        int[][] bgRgb=getImageGRB(bgImg);

        int[][] vrRgb=getImageGRB(vrImg);

        return searchImage(bgRgb,vrRgb);

    }

    /**

     * 这里非黑即白 值为0或1,1表示白色,0表示黑色

     * @param bfImage

     * @return

     */

    public static int[][] getImageGRB(BufferedImage bfImage) {

        int width = bfImage.getWidth();

        int height = bfImage.getHeight();

        int[][] result = new int[height][width];//这里就是y,x才跟图一样

        for (int h = 0; h < height; h++) {

            for (int w = 0; w <width ; w++) {

                result[h][w] = getZeroOrOne(bfImage.getRGB(w, h));

                System.out.print(result[h][w]+" ");

            }

            System.out.println();

        }

        System.out.println("===================================");

        return result;

    }

    /**

     * 将图片像素变成0或1，阈值可以根据实际情况调整

     * @param i bfImage.getRGB(w, h)

     * @return

     */

    private static int getZeroOrOne(int i) {

        int rgb =i& 0xFFFFFF;

        int r=(rgb& 0xff0000) >> 16;

        int g=(rgb& 0xff00) >> 8;

        int b=(rgb& 0xff);

        if(r>210&g>210&b>210) {

            return 1;

        }

        return 0x00;

    }

    /**

     * 遍历图片，进行比较,得出相似度最高的位置

     这里是一个像素一个像素去比较的,具体比较方法为compare(y,x,bgRgb,vrRgb)

     * @param bgRgb bfImage

     * @return

     */

    private static int searchImage(int[][] bgRgb,int[][] vrRgb) {

        int bgY=bgRgb.length;

        int bgX=bgRgb[0].length;

        int vrY=vrRgb.length;

        int vrX=vrRgb[0].length;

        int xLocation=0;

        int yLocation=0;

        int maxCount=0;

        for(int y=1;y<bgY-vrY-1;y++) {

            for(int x=1;x<bgX-vrX-1;x++) {

                int count=compare(y,x,bgRgb,vrRgb);

                if(count>maxCount) {

                    maxCount=count;

                    yLocation=y;

                    xLocation=x;

                }

            }

        }

        logger.info("最佳位置为({},{}),相同数比例为={}",new Object[] {xLocation,yLocation,maxCount});

        return xLocation;

    }


    /**

     * 针对背景图中的像素位置(Y,X),那验证图与原图做比较

     1、比较边框，及比较验证图中值为1的点

     2、争夺当前验证图中1的点，比较它四周的节点，是否相同，相同就相似度加一

     PS:其实就是比较框框

     * @param bgY bfImage

     * @return

     */

    private static int compare(int bgY,int bgX,int[][] bgRgb,int[][] vrRgb) {

        int count=0;

        int vrY=vrRgb.length;

        int vrX=vrRgb[0].length;

        //遍历小图节点

        for(int y=0;y<vrY;y++) {

            for(int x=0;x<vrX;x++) {

                //只对1做比较

                if(vrRgb[y][x]!=1){

                    continue;

                }

                boolean isRight=true;

                //比较当前点四周的节点是否相同

                for(int i=-1;i<2;i++) {

                    for(int j=-1;j<2;j++) {

                        int tempX=x+i;

                        int tempY=y+j;

                        int tempBgY=y+bgY+j;

                        int tempBgX=x+bgX+i;

                        if(tempY<0||tempX<0||tempY>vrY-1||tempX>vrX-1) {

                            continue;

                        }

                        if(tempBgY<0||tempBgX<0||tempBgY>bgRgb.length-1||tempBgX>bgRgb[0].length-1) {

                            continue;

                        }

                        if(vrRgb[tempY][tempX]!=bgRgb[tempBgY][tempBgX]) {

                            isRight=false;

                        }

                    }

                }

                if(isRight) {

                    count++;

                }

            }

        }

        return count;

    }

}
