package org.pages;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.utility.RestAssuredMainClass;
import javax.mail.MessagingException;
import org.testng.annotations.Test;
import org.utility.MainClass.TestData;

public class CeoLoginAPI extends RestAssuredMainClass {
	
	/*
	 * GET API used to fetch user info using cookie.
	 */
	public static void verifyUserInfoGetAPI() throws IOException, MessagingException, InterruptedException {
		given().when().header("cookie", fetchCookie()).get("/users/27b75404-ddcf-4ade-9b35-2ddc6462a093").then()
				.statusCode(HttpStatus.SC_OK).extract().path("data.subjects.firstName").toString()
				.contains(property.getProperty("firstname"));
	}

	/*
	 * Negative Scenarios
	 */
	public static void verifyUserInfoGetAPIWithWrongUserId() throws IOException, MessagingException, InterruptedException {
		given().when().header("cookie", fetchCookie()).get("/users/27b75404-ddcf-4ade-9b35-2ddc6462a094").then()
				.statusCode(HttpStatus.SC_FORBIDDEN).extract().path("errors.detail").toString().contains("Action not authorized");
	}

	/*
	 * Negative Scenarios
	 */
	public static void verifyUserInfoGetAPIWithWrongCookie() throws IOException, MessagingException, InterruptedException {
		given().when().header("cookie", property.getProperty("invalid_cookie"))
				.get("/users/27b75404-ddcf-4ade-9b35-2ddc6462a093").then().statusCode(HttpStatus.SC_UNAUTHORIZED).extract()
				.path("errors.detail").toString().contains("Invalid session");
	}

	/* GET API used to fetch user info using auth token. */
	/*@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the GET API used to fetch user info")
	@TestData(testId = 5)
	public static void verifyUserInfoGetAPI() throws IOException, MessagingException, InterruptedException {
		given().when().header("cookie", fetchCookie()).get("/user/info").then().statusCode(HttpStatus.SC_OK).extract()
				.path("data.firstName").toString().contains(property.getProperty("firstname"));
	}*/

	/*
	 * POST API used to fetch user info using auth token.
	 */
	public static void verifyUserInfoPostAPI() throws IOException, MessagingException, InterruptedException {
		given().when().header("authorization", property.getProperty("auth_token")).post("/user/info").then()
				.statusCode(HttpStatus.SC_OK).extract().path("data.firstName").toString()
				.contains(property.getProperty("firstname"));
	}

	/*
	 * POST API used to change the password.
	 */
	public static void verifyUserPasswordChangePostAPI() throws IOException, MessagingException, InterruptedException {
		given().when().header("authorization", property.getProperty("auth_token")).body(createChangePasswordBody())
				.post("/user/password/change").then().statusCode(HttpStatus.SC_OK);
	}

	/*
	 * POST API used to update the user info.
	 */
	public static void verifyUserUpdatePostAPI() throws IOException, MessagingException, InterruptedException {
		given().when().header("authorization", property.getProperty("auth_token")).body(createUserInfoBody())
				.post("/user/update").then().statusCode(HttpStatus.SC_OK);
	}

}
