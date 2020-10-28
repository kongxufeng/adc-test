package com.htyl.adc.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErroCode {
	private static List<String> erroList;
	private static List<String> rigthList;
	static {
		erroList = new ArrayList<String>();
		erroList.add("404");
		erroList.add("403");
		erroList.add("500");
		erroList.add("502");
		erroList.add("503");
		erroList.add("504");
		erroList.add("出错了");
		erroList.add("维护中");
		erroList.add("无法访问");
		erroList.add("Error report");
		erroList.add(".com");
		erroList.add(".cn");
		erroList.add(".org");
		erroList.add("Error");
		rigthList = new ArrayList<String>();
		rigthList.add("机械帝国|机械CAD图纸下载交流中心");
		rigthList.add("军工企业一站式采购平台-国防科技网");
		rigthList.add("500强");
	}

	public static List<String> getErroList() {

		return erroList;
	}

	public static String checktitle(String title, String text) {
		String erro = "";
		String right = text;
		if (!title.contains(right)) {
			erro = title;
		}

		return erro;

	}

	public static String checktitle1(String title, String text) {
		String erro = "";
		String right = text;
		if (!right.equalsIgnoreCase(title)) {
			erro = text;
			if (text.isEmpty()) {
				erro = "标题为空";
			}
		}
		return erro;

	}

	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static String checkErroPage(String title) {
		String erro = "";
		if (title.isEmpty()) {
			erro = "标题为空";
		} else {
			for (String erroc : erroList) {
				if (title.contains(erroc)) {
					erro = erroc;
					break;

				}
			}
			for (String ritc : rigthList) {
				if (title.contains(ritc)) {
					erro = "";
					break;

				}
			}

		}
		return erro;

	}

}
