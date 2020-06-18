package org.utility;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;

public class Reporting extends MainClass implements IReporter {

	/*
	 * This class is for the implementation of TestNG listeners. It will also generating reports in the console at the end of test execution.
	 */
	
	// Method to generate reports and update status of test cases as passed and failed.
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		for (ISuite isuite : suites) {
			Map<String, ISuiteResult> result = isuite.getResults();
			Set<String> keys = result.keySet();
			for (String key : keys) {
				ITestContext context = result.get(key).getTestContext();
				IResultMap resultMap = context.getPassedTests();
				Collection<ITestNGMethod> passedMethods = resultMap.getAllMethods();
				System.out.println("\n" + "Report Output Directory -> " + context.getOutputDirectory() + "\n");
				for (ITestNGMethod iTestNGMethod : passedMethods) {
					// Printing passed test cases detail
					System.out.println("<<<< PASSED TEST CASE >>>>");
					System.out.println("Suite Name -> " + context.getSuite().getName() + "\nDescription->"
							+ iTestNGMethod.getDescription() + "\nPriority->" + iTestNGMethod.getPriority()
							+ "\nTestCase Name -> " + iTestNGMethod.getMethodName() + "\nStart Date Time -> "
							+ context.getStartDate() + "\nEnd Date Time -> " + context.getEndDate() + "\nRun Id -> "
							+ property.getProperty("testrun_Id") + "\n");
				}

				IResultMap fresultMap = context.getFailedTests();
				Collection<ITestNGMethod> failedMethods = fresultMap.getAllMethods();
				for (ITestNGMethod iTestNGMethod : failedMethods) {
					// Printing failed test cases detail
					System.out.println("<<<< FAILED TEST CASE >>>>");
					System.out.println("TestCase Name->" + iTestNGMethod.getMethodName() + "\nDescription->"
							+ iTestNGMethod.getDescription() + "\nPriority->" + iTestNGMethod.getPriority()
							+ "\nStart Date Time -> " + context.getStartDate() + "\nEnd Date Time -> " + context.getEndDate()
							+ property.getProperty("testrun_Id") + "\nRun Id -> " + property.getProperty("testrun_Id") + "\n");
				}
			}
		}
	}
}
