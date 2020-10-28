package com.htyl.adc.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * ReadExcel类使用POI-XSSF实现2007版本以后excel文件中指定Sheet页数据的读取
 *
 */
public class ReadExcel {
	/**
	 * getDataFromExcel静态方法用于读取在resource目录下的xlsx文件指定页的数据，第一行数据作为头信息，自动跳过
	 * @param excelFileName 在resource目录下的xlsx文件名，不需要指定路径
	 * @param sheetName 所需读取的数据所在的sheet页名称
	 * @return 将数据作为二维数组返回
	 */
	public static Object[][] getDataFromExcel(String excelFileName,String sheetName){
		Object[][] obj = null;
		InputStream is = ReadExcel.class.getClassLoader().getResourceAsStream(excelFileName); // 从类加载路径中读取指定的文件，获得其输入流
		Workbook wb = null;
		Sheet sheet = null;
		try {
			wb = new XSSFWorkbook(is); // 通过输入流读取excel文件到workbook对象
			sheet = wb.getSheet(sheetName); // 获取指定的sheet页对象
			int lastRowNum = sheet.getLastRowNum(); // 获取最后一行的编号
			int lastCellNum = sheet.getRow(lastRowNum).getLastCellNum(); // 获取最后一个单元格的编号（列号），需要减一
			obj = new Object[lastRowNum][lastCellNum]; // 通过循环进行赋值
			for (int i = 1; i <= lastRowNum; i++) {
				for (int j = 0; j < lastCellNum; j++) {
					String str = sheet.getRow(i).getCell(j).getStringCellValue();
					if(str.equalsIgnoreCase("<Empty>")) {
						obj[i-1][j] = "";
					}else {
						obj[i-1][j] = str;
					}
				} 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
