package com.htyl.adc.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalysis implements IRetryAnalyzer {
	
	private static final int MAX = 1;
	private int count;

	@Override
	public boolean retry(ITestResult result) {
		if(count <= MAX) {
			count ++;
			return true;
		}else {
			return false;
			
		}
	}

}
