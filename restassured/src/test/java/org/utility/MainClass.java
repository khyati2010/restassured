package org.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Case;
import com.codepine.api.testrail.model.CaseField;
import com.codepine.api.testrail.model.Project;
import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.ResultField;
import com.codepine.api.testrail.model.Run;
import com.codepine.api.testrail.model.Section;
import com.codepine.api.testrail.model.Suite;

/* This is the MainClass and is being inherited in the pages.
 * TestRail integration functionality has been added in this class.
 * It also contains generic methods.
 */

public class MainClass {

	protected static TestRail testrail;
	protected static Project project;
	protected static Suite suite;
	protected static Section section;
	protected static Case testCase;
	protected static Run run;
	protected static Properties property;
	protected static Properties commonProperty;
	protected static InputStream input;
	static String workingDir = System.getProperty("user.dir");
	static Logger logger = Logger.getLogger(MainClass.class);
	protected static String browse;
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface TestData {
		public int[]testId() default { 0 };

		int runId() default 0;
	}

	//This method will read the configuration properties and load.
	@BeforeSuite(alwaysRun = true)
	public void propertyInit() throws IOException {
		input = new FileInputStream(workingDir + "//resources//config.properties");
		property = new Properties();
		property.load(input);
		BasicConfigurator.configure();
	}

	//Method for updating test Rail id.
	@BeforeClass(alwaysRun = true)
	@Parameters({ "testrun_Id" })
	public void runIDUpdate(@Optional("1") String runid) {
		{
			if (property.getProperty("testrun_Id").isEmpty()) {
				property.setProperty("testrun_Id", runid);
			}
		}
	}
// Method to take username, password and application name from property file.
	@BeforeTest(alwaysRun = true)
	public void testRailInit() throws IOException {
		testrail = TestRail
				.builder(property.getProperty("testrail_link"), property.getProperty("testrail_username"),
						property.getProperty("testrail_password"))
				.applicationName(property.getProperty("application_name")).build();
	}

	@AfterMethod(alwaysRun = true)
	protected void testMethodEnd() throws InterruptedException, IOException {

	}
	
	
	// TestRail Method for adding project
	public void addProject(String proj) {
		testrail.projects().add(new Project().setName(proj)).execute();
	}

	// TestRail Method for adding suite
	public Suite addSuite(int projectId, String suiteName) {
		return testrail.suites().add(projectId, new Suite().setName(suiteName)).execute();
	}

	// TestRail Method for adding test run
	public Run addTestRun(int projectId, int suiteId, String testName) {
		return testrail.runs().add(projectId, new Run().setSuiteId(suiteId).setName(testName)).execute();
	}

	// TestRail Method for adding section
	public Section addSection(int projectId, int suiteId, String sectionName) {
		return testrail.sections().add(projectId, new Section().setSuiteId(suiteId).setName(sectionName)).execute();
	}
	

	// TestRail Method for adding test case
	public Case addTestCase(Integer sectionId, String testcasename) {
		java.util.List<CaseField> customCaseFields = testrail.caseFields().list().execute();
		return testrail.cases().add(sectionId, new Case().setTitle(testcasename), customCaseFields).execute();
	}

	// TestRail Method for adding test result
	public void addTestResults(int runId, int id, int statusId) {
		java.util.List<ResultField> customResultFields = testrail.resultFields().list().execute();
		testrail.results().addForCase(runId, id, new Result().setStatusId(statusId), customResultFields).execute();
	}

	// TestRail Method for closing run
	public void closeRun(int runId) {
		testrail.runs().close(runId).execute();
	}

	// TestRail Method for setting the project as complete
	public void completeProject(String pName) {
		project = project.setName(pName);
		testrail.projects().update(project.setCompleted(true)).execute();
	}
	
	

	// testing excel work
	public static String getTriggerDataFromExcel(String sheetName, int rowNumber, int columnNumber) throws Exception {
		ExcelReader.setExcelFile(workingDir + "/testdata/Workbook.xlsx", sheetName);
		String Data = ExcelReader.getCellData(rowNumber, columnNumber);
		return Data;
	}

	// method to return current date
	public static String returnDate() {
		DateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dformat.format(date);
	}
}