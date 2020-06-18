package org.utility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	private int retryCount = 0;
	private int maxRetryCount = 1;

	/*
	 * This class will implement IRetryAnalyzer interface of TestNg.
	 * And return true or false of result whenever retry will work
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 */
	@Override
	public boolean retry(ITestResult result) {

		if (retryCount < maxRetryCount) {
			retryCount++;
			return true;
		}
		return false;
	}
}