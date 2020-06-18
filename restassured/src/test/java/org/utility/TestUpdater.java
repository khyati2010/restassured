package org.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.codepine.api.testrail.TestRailException;
import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.CaseField;

/*
 * Functionality for updating the result on TestRail has been added here.
 */

public class TestUpdater extends MainClass implements ITestListener {

	boolean update_result;
	static List<Case> cases;
	static String testcases;
	static Map<String, Integer> testcase = new HashMap<String, Integer>();

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
	}

	// This method will take property from property file and basis on that property it will fetch the list of the test cases.
	public static void getTestCaseList() {
		// System.out.println("********* "
		// +testrail.sections().list(3).execute());
		if (property.getProperty("update_result").equals(true)) {
			List<CaseField> casefiel = testrail.caseFields().list().execute();
			cases = testrail.cases().list(3, casefiel).execute();
			for (int i = 0; i < cases.size(); i++) {
				testcase.put(cases.get(i).getTitle(), cases.get(i).getId());
			}
			testcases = testrail.cases().list(3, casefiel).execute().toString();
		}
	}

	//This method will call the getTestCaseList and then perform actions on the test success, the pass id is 1 for success
	@Override
	public void onTestSuccess(ITestResult result) {
		getTestCaseList();
		update_result = Boolean.valueOf(property.getProperty("update_result"));
		if (update_result) {
			try {
				addTestResults(Integer.parseInt(property.getProperty("testrun_Id")),
						testcase.get(result.getMethod().getDescription()), 1);
			} catch (TestRailException ex) {
				logger.info("Could not update the test result " + ex);
			}
			if (!testcases.contains(result.getMethod().getDescription())) {
				System.out.println("New Test case has been added:- " + result.getMethod());
				addTestCase(3, result.getMethod().getDescription());
			}
		}
	}

	//This method will call the getTestCaseList and then perform actions on the test success, the pass id is 5 for failure
	@Override
	public void onTestFailure(ITestResult result) {
		update_result = Boolean.valueOf(property.getProperty("update_result"));
		if (update_result) {
			try {
				addTestResults(Integer.parseInt(property.getProperty("testrun_Id")),
						testcase.get(result.getMethod().getDescription()), 5);
			} catch (TestRailException ex) {
				logger.info("Could not update the test result " + ex);
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}

}
