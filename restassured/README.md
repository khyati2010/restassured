# gramLabs - Services Test Automation

For new automated test project - [create new IntelliJ/eclipse project

To run test suite from command line use the commands below:

```
$ mvn clean test
```

Set eclipse run configuration:

* Tasks: clean test

```
--refresh-dependencies


Main Class
propertyInit -  This will read the configuration properties and load.
runIDUpdate - For updating test Rail id.
testRailInit - property to take username, password and application name.
addProject - TestRail Method for adding project
addSuite - TestRail Method for adding suite
addTestRun - TestRail Method for adding test run

EmailFetcherAndReader Class



ExcelReader Class
setExcelFile -  //Method will create new file and read sheets with XSSF workbook library
getCellData-// Method to read rows and columns from the sheet from above method




Reporting Class
 This class is for the implementation of TestNG listeners. It will also generating reports in the console at the end of test execution.
	
	// Method to generate reports and update status of test cases as passed and failed.


TestUpdater Class
Functionality for updating the result on TestRail has been added here.
getTestCaseList - This method will take property from property file and basis on that property it will fetch the list of the test cases.
onTestSuccess - 


RestAssuredMainClass Class
This class will configure all the properties required for the set up of rest assured.  This will include host , port, cookies, auth token etc required to test any rest service.
restSetup - This method is picking properties like base, host, port from the property file. And setting that to the rest assured class.
getAuthToken - Fetching authorization token and saving its value in the properties file
createSetPasswordBodyWithIvalidToken - 

All the other methods are project specific ?	
Retry Class
/*
	 * This class will implement IRetryAnalyzer interface of TestNg.
	 * And return true or false of result whenever retry will work
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 */
RetryListener CLass
 This class implements IAnnotationTransformer of TestNg which will call retry class. (non-Javadoc)


package org.pages
ActivityResultsAPI extends RestAssuredMainClass
