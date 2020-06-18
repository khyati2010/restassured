package org.tests;

import java.io.IOException;
import javax.mail.MessagingException;
import org.pages.IwbIntegrationAPI;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utility.Reporting;
import org.utility.RestAssuredMainClass;
import org.utility.TestUpdater;

@Listeners(value = { TestUpdater.class, Reporting.class })
public class IwbIntegrationAPITest extends RestAssuredMainClass {

	// AUTH API's

	/*
	 * POST API used for auth login.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/login API")
	@TestData(testId = 5)
	public static void verifyAuthLoginAPI() {
		IwbIntegrationAPI.sendPostRequestToAuthLoginAPIAndVerify();
	}

	/*
	 * GET API used to logout from gramLabs.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying GET /auth/logout API")
	@TestData(testId = 5)
	public static void verifyAuthLogoutGetAPI() {
		IwbIntegrationAPI.sendGetRequestToAuthLogoutAPIAndVerify();
	}

	/*
	 * POST API used to logout from gramLabs.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/logout API")
	@TestData(testId = 5)
	public static void verifyLogoutPostAPI() {
		IwbIntegrationAPI.sendPostRequestToAuthLogoutAPIAndVerify();
	}

	/*
	 * POST API used to reset a user's password.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/password/reset API")
	@TestData(testId = 5)
	public static void verifyAuthPasswordResetAPI() throws IOException, InterruptedException, MessagingException {
		IwbIntegrationAPI.sendPostRequestToPasswordResetAPIAndVerify();
	}

	/*
	 * POST API used to set a user's password.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/password/set API")
	@TestData(testId = 5)
	public static void verifyAuthPasswordSetAPI() throws IOException, MessagingException, InterruptedException {
		IwbIntegrationAPI.sendPostRequestToPasswordSetAPIAndVerify();
	}

	/*
	 * GET API used to generate a new JWT using an existing one.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying GET /auth/refresh API")
	@TestData(testId = 5)
	public static void verifyAuthRefreshGetAPI() throws IOException, MessagingException, InterruptedException {
		IwbIntegrationAPI.sendGetRequestToAuthRefreshAPIAndVerify();
	}

	/*
	 * POST API used to generate a new JWT using an existing one.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/refresh API")
	@TestData(testId = 5)
	public static void verifyAuthRefreshPostAPI() throws IOException, MessagingException, InterruptedException {
		IwbIntegrationAPI.sendPostRequestToAuthRefreshAPIAndVerify();
	}

	// Negative Scenarios

	/*
	 * POST API used for auth login with blank body and verified error message.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/login API")
	@TestData(testId = 5)
	public static void verifyAuthLoginAPIWithoutBody() {
		IwbIntegrationAPI.sendPostRequestToAuthLoginAPIWithoutBodyAndVerify();
	}

	/*
	 * GET API used to logout from gramLabs using invalid auth token and verified error message.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying GET /auth/logout API")
	@TestData(testId = 5)
	public static void verifyAuthLogoutGetAPIWithInvalidAuthToken() {
		IwbIntegrationAPI.sendGetRequestToAuthLogoutAPIWithInvlaidTokenAndVerify();
	}

	/*
	 * POST API used to logout from gramLabs using invalid auth token and verified error message.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/logout API")
	@TestData(testId = 5)
	public static void verifyAuthLogoutPostAPIWithInvalidAuthToken() {
		IwbIntegrationAPI.sendPostRequestToAuthLogoutAPIWithInvlaidTokenAndVerify();
	}

	/*
	 * POST API used to reset user's password with invalid email address and verify.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/password/reset API")
	@TestData(testId = 5)
	public static void verifyAuthPasswordResetAPIWithInvalidEmail() throws IOException, InterruptedException, MessagingException {
		IwbIntegrationAPI.sendPostRequestToPasswordResetAPIWithInvalidEmailAndVerify();
	}

	/*
	 * POST API used to set a user's password with invalid token.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the password set API using invalid token")
	@TestData(testId = 5)
	public static void verifyPasswordSetAPIUsingInvalidToken() throws IOException, MessagingException, InterruptedException {
		IwbIntegrationAPI.sendPostRequestToPasswordSetAPIWithInvalidToken();
	}

	/*
	 * POST API used to set a user's password with existing token.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the password set API using existing token")
	@TestData(testId = 5)
	public static void verifyPasswordSetAPIUsingExistingToken() throws IOException, MessagingException, InterruptedException {
		IwbIntegrationAPI.sendPostRequestToPasswordSetAPIWithExistingToken();
	}

	/*
	 * POST API used to set a user's password with invalid email and verified.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the password set API using invalid email")
	@TestData(testId = 5)
	public static void verifyPasswordSetAPIUsingInvalidEmail() throws IOException, MessagingException, InterruptedException {
		IwbIntegrationAPI.sendPostRequestToPasswordSetAPIWithInvalidEmail();
	}

	/*
	 * POST API used to reset user's password without sending body and verified. error message.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/password/reset API without body")
	@TestData(testId = 5)
	public static void verifyAuthPasswordResetAPIWithoutBody() throws IOException, InterruptedException, MessagingException {
		IwbIntegrationAPI.sendPostRequestToPasswordResetAPIWithoutBodyAndVerify();
	}

	/*
	 * POST API used to set a user's password without sending body and verified.
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying POST /auth/password/set API without body")
	@TestData(testId = 5)
	public static void verifyAuthPasswordSetAPIWithoutBody() throws IOException, MessagingException, InterruptedException {
		IwbIntegrationAPI.sendPostRequestToPasswordSetAPIWithoutBodyAndVerify();
	}

}
