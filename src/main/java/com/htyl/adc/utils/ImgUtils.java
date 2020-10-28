package com.htyl.adc.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ImgUtils{

	// 去除噪声
    public static void removeBackground(String imgUrl, String resUrl){
		// 定义一个邻域界值
		int threshold = 300;
        try{
			BufferedImage img = ImageIO.read(new File(imgUrl));
            int width = img.getWidth();
            int height = img.getHeight();
            for(int i = 1;i < width;i++){
                for (int x = 0; x < width; x++){
                    for (int y = 0; y < height; y++){
                        Color color = new Color(img.getRGB(x, y));
						// System.out.println("red:"+color.getRed()+" |
						// green:"+color.getGreen()+" | blue:"+color.getBlue());
                        int num = color.getRed()+color.getGreen()+color.getBlue();
                        if(num >= threshold){
                            img.setRGB(x, y, Color.WHITE.getRGB());
                        }
                    }
                }
            }
            for(int i = 1;i<width;i++){
                Color color1 = new Color(img.getRGB(i, 1));
                int num1 = color1.getRed()+color1.getGreen()+color1.getBlue();
                for (int x = 0; x < width; x++)
                {
                    for (int y = 0; y < height; y++)
                    {
                        Color color = new Color(img.getRGB(x, y));
 
                        int num = color.getRed()+color.getGreen()+color.getBlue();
                        if(num==num1){
                            img.setRGB(x, y, Color.BLACK.getRGB());
                        }else{
                            img.setRGB(x, y, Color.WHITE.getRGB());
                        }
                    }
                }
            }
            File file = new File(resUrl);
            if (!file.exists())
            {
                File dir = file.getParentFile();
                if (!dir.exists())
                {
                    dir.mkdirs();
                }
                try
                {
                    file.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
			ImageIO.write(img, "jpg", file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

	public static BufferedImage cropImage(BufferedImage bufferedImage, int startx, int starty, int endx, int endy) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		if (startx == -1) {
			startx = 0;
		}
		if (starty == -1) {
			starty = 0;
		}
		if (endx == -1) {
			endx = width - 1;
		}
		if (endy == -1) {
			endy = height - 1;
		}
		BufferedImage result = new BufferedImage(endx - startx, endy - starty, 4);
		for (int x = startx; x < endx; x++) {
			for (int y = starty; y < endy; y++) {
				int rgb = bufferedImage.getRGB(x, y);
				result.setRGB(x - startx, y - starty, rgb);
			}
		}
		return result;
	}

	// 识别图片
	public static String executeTess4J(String imgUrl) {
		String ocrResult = "";
		try {
			ITesseract instance = new Tesseract();
			// instance.setLanguage("chi_sim");
			File imgDir = new File(imgUrl);
			// long startTime = System.currentTimeMillis();
			ocrResult = instance.doOCR(imgDir);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		return ocrResult;
	}
}
