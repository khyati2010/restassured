package org.tests;

import java.io.IOException;

import javax.mail.MessagingException;

import org.pages.CeoLoginAPI;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utility.Reporting;
import org.utility.TestUpdater;

@Listeners(value = { TestUpdater.class, Reporting.class })
public class CeoLoginAPITest extends CeoLoginAPI {

	// USER API's

	/*
	 * GET API used to fetch user info using cookie.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the GET API used to fetch user info")
	@TestData(testId = 5)
	public static void verifyUserInfoGetAPI() throws IOException, MessagingException, InterruptedException {
		verifyUserInfoGetAPI();
	}

	/*
	 * Negative Scenarios
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the GET API with wrong user_id")
	@TestData(testId = 5)
	public static void verifyUserInfoGetAPIWithWrongUserId() throws IOException, MessagingException, InterruptedException {
		verifyUserInfoGetAPIWithWrongUserId();
	}

	/*
	 * Negative Scenarios
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the GET API with invalid cookie")
	@TestData(testId = 5)
	public static void verifyUserInfoGetAPIWithWrongCookie() throws IOException, MessagingException, InterruptedException {
		verifyUserInfoGetAPIWithWrongCookie();
	}

	/* GET API used to fetch user info using auth token. */
	/*
	 * @Test(groups = { "Smoke", "Regression" }, description = "-Verifying the GET API used to fetch user info")
	 * 
	 * @TestData(testId = 5) public static void verifyUserInfoGetAPI() throws IOException, MessagingException, InterruptedException { given().when().header("cookie", fetchCookie()).get("/user/info").then().statusCode(HttpStatus.SC_OK).extract() .path("data.firstName").toString().contains(property.getProperty("firstname")); }
	 */

	/*
	 * POST API used to fetch user info using auth token.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the POST API used to fetch user info")
	@TestData(testId = 5)
	public static void verifyUserInfoPostAPI() throws IOException, MessagingException, InterruptedException {
		verifyUserInfoPostAPI();
	}

	/*
	 * POST API used to change the password.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the POST API used to change the password")
	@TestData(testId = 5)
	public static void verifyUserPasswordChangePostAPI() throws IOException, MessagingException, InterruptedException {
		verifyUserPasswordChangePostAPI();
	}

	/*
	 * POST API used to update the user info.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the POST API used update the user info")
	@TestData(testId = 5)
	public static void verifyUserUpdatePostAPI() throws IOException, MessagingException, InterruptedException {
		verifyUserUpdatePostAPI();
	}

}
