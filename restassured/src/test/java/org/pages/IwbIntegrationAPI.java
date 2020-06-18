package org.pages;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.http.HttpStatus;
import org.utility.RestAssuredMainClass;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

/*
 * Page classes will extend RestAssuredMainClass so as to take all the functions of the REST Assured.
 * Here we will use BDD capability of Rest Assured to get response.
 */
public class IwbIntegrationAPI extends RestAssuredMainClass {

	/* This method will extract response from the request and store it in the response object of Response type. 
	Also json path is getting stored in the Jsonpath variable so as to assert the result*/

	public static void sendPostRequestToAuthLoginAPIAndVerify() {
		Boolean result = null;
		Response respons = given().when().body(property.getProperty("auth_token_payload"))
				.post(property.getProperty("auth_login_path")).then().statusCode(HttpStatus.SC_OK).contentType(ContentType.JSON)
				.extract().response();
		JsonPath jp = respons.getBody().jsonPath();
		if (jp.get("data.token") != null) {
			result = true;
		} else {
			result = false;
		}
		Assert.assertTrue("Auth token was empty", result);
	}

	public static void sendGetRequestToAuthLogoutAPIAndVerify() {
		given().when().header("authorization", property.getProperty("auth_token")).get("/auth/logout").then()
				.statusCode(HttpStatus.SC_OK);
	}

	public static void sendPostRequestToAuthLogoutAPIAndVerify() {
		given().when().header("authorization", property.getProperty("auth_token")).post("/auth/logout").then()
				.statusCode(HttpStatus.SC_OK);
	}

	public static void sendPostRequestToPasswordResetAPIAndVerify() throws IOException, MessagingException, InterruptedException {
		given().when().body(createResetPasswordBody()).post("/auth/password/reset").then().statusCode(HttpStatus.SC_OK);
	}

	public static void sendPostRequestToPasswordSetAPIAndVerify() throws IOException, MessagingException, InterruptedException {
		sendPostRequestToPasswordResetAPIAndVerify();
		given().when().body(createSetPasswordBodyWithValidToken()).post("/auth/password/set").then().statusCode(HttpStatus.SC_OK);
	}

	public static void sendGetRequestToAuthRefreshAPIAndVerify() throws IOException, MessagingException, InterruptedException {
		Boolean result = null;
		Response respons = given().when().header("authorization", property.getProperty("auth_token")).get("/auth/refresh").then()
				.statusCode(HttpStatus.SC_OK).extract().response();
		JsonPath jp = respons.getBody().jsonPath();
		if (jp.get("data.token") != null) {
			result = true;
		} else {
			result = false;
		}
		Assert.assertTrue("Auth token was empty", result);
	}

	public static void sendPostRequestToAuthRefreshAPIAndVerify() {
		Boolean result = null;
		Response respons = given().when().header("authorization", property.getProperty("auth_token")).post("/auth/refresh")
				.then().statusCode(HttpStatus.SC_OK).extract().response();
		JsonPath jp = respons.getBody().jsonPath();
		if (jp.get("data.token") != null) {
			result = true;
		} else {
			result = false;
		}
		Assert.assertTrue("Auth token was empty", result);
	}

	public static void sendPostRequestToAuthLoginAPIWithoutBodyAndVerify() {
		Response respons = given().when().post(property.getProperty("auth_login_path")).then()
				.statusCode(HttpStatus.SC_BAD_REQUEST).contentType(ContentType.JSON).extract().response();
		Assert.assertEquals("Validation Error", respons.jsonPath().get("errors.title").toString().replaceAll("\\[", "")
				.replaceAll("\\]", ""));
	}

	public static void sendGetRequestToAuthLogoutAPIWithInvlaidTokenAndVerify() {
		String error_message = given().when().header("authorization", property.getProperty("invalid_auth_token"))
				.get("/auth/logout").then().statusCode(HttpStatus.SC_UNAUTHORIZED).extract().path("errors.detail").toString()
				.replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals("Invalid token", error_message);
	}

	public static void sendPostRequestToAuthLogoutAPIWithInvlaidTokenAndVerify() {
		String error_message = given().when().header("authorization", property.getProperty("invalid_auth_token"))
				.post("/auth/logout").then().statusCode(HttpStatus.SC_UNAUTHORIZED).extract().path("errors.detail").toString()
				.replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals("Invalid token", error_message);
	}

	public static void sendPostRequestToPasswordResetAPIWithoutBodyAndVerify() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().post("/auth/password/reset").then().statusCode(HttpStatus.SC_BAD_REQUEST).extract()
				.path("errors.title").toString().replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals("Validation Error", error_message);
	}

	public static void sendPostRequestToPasswordSetAPIWithoutBodyAndVerify() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().post("/auth/password/set").then().statusCode(HttpStatus.SC_BAD_REQUEST).extract()
				.path("errors.title").toString().replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals("Validation Error", error_message);
	}

	public static void sendPostRequestToPasswordResetAPIWithInvalidEmailAndVerify() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().body(createResetPasswordBodyWithInvalidEmail()).post("/auth/password/reset").then()
				.statusCode(HttpStatus.SC_BAD_REQUEST).extract().path("errors.title").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		Assert.assertEquals("Validation Error", error_message);
	}

	public static void sendPostRequestToPasswordSetAPIWithInvalidToken() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().body(createSetPasswordBodyWithInvalidToken()).post("/auth/password/set").then()
				.statusCode(HttpStatus.SC_BAD_REQUEST).extract().path("errors.detail").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		Assert.assertEquals(property.getProperty("error_on_invalid_token"), error_message);
	}

	public static void sendPostRequestToPasswordSetAPIWithExistingToken() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().body(createSetPasswordBodyWithExistingToken()).post("/auth/password/set").then()
				.statusCode(HttpStatus.SC_UNAUTHORIZED).extract().path("errors.detail").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		Assert.assertEquals(property.getProperty("error_on_existing_token"), error_message);
	}

	public static void sendPostRequestToPasswordSetAPIWithInvalidEmail() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().body(createSetPasswordBodyWithInValidEmail()).post("/auth/password/set").then()
				.statusCode(HttpStatus.SC_BAD_REQUEST).extract().path("errors.detail").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		Assert.assertEquals("child \"email\" fails because \"email\" must be a valid email", error_message);
	}

}
