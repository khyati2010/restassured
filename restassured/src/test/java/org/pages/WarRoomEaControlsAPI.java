package org.pages;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import javax.mail.MessagingException;
import org.apache.http.HttpStatus;
import org.utility.RestAssuredMainClass;
import junit.framework.Assert;


/*
 * This class represents a page which can have multiple services in it.
 */
public class WarRoomEaControlsAPI extends RestAssuredMainClass {
	public static void sendGetRequestWithActivityIdAndVerifyResult() throws IOException, InterruptedException {
		String act_id = given().when().header("cookie", fetchCookie()).pathParam("activityId", property.getProperty("id"))
				.get(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_OK).extract()
				.path("data.activityId").toString();
		Assert.assertEquals(property.getProperty("id"), act_id);
	}

	public static String getId() throws IOException, InterruptedException {
		return given().when().header("cookie", fetchCookie()).pathParam("activityId", property.getProperty("OWASP_Top_10"))
				.get(property.getProperty("level1_activity_result")).then().extract().path("data.id").toString()
				.replaceAll("\\[", "").replaceAll("\\]", "");
	}

	public static void sendPostRequestAsPassedWithActivityIdAndVerifyResult() throws IOException, MessagingException,
			InterruptedException {
		io.restassured.response.Response response = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("id")).body(property.getProperty("json_obj_passed"))
				.post(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_OK).extract().response();
		Assert.assertEquals(property.getProperty("id"), response.jsonPath().get("data.activityId"));
		Assert.assertEquals("passed", response.jsonPath().get("data.lastAttempt.status"));
	}

	public static void sendPostRequestAsFailedWithActivityIdAndVerifyResult() throws IOException, MessagingException,
			InterruptedException {
		io.restassured.response.Response response = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("id")).body(property.getProperty("json_obj_failed"))
				.post(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_OK).extract().response();
		Assert.assertEquals(property.getProperty("id"), response.jsonPath().get("data.activityId"));
		Assert.assertEquals("failed", response.jsonPath().get("data.lastAttempt.status"));
	}

	public static void sendPostRequestAsCompletedWithActivityIdAndVerifyResult() throws IOException, MessagingException,
			InterruptedException {
		io.restassured.response.Response response = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("id")).body(property.getProperty("json_obj_completed"))
				.post(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_OK).extract().response();
		Assert.assertEquals(property.getProperty("id"), response.jsonPath().get("data.activityId"));
		Assert.assertEquals("completed", response.jsonPath().get("data.lastAttempt.status"));
	}

	public static void sendPostRequestAsInCompleteWithActivityIdAndVerifyResult() throws IOException, MessagingException,
			InterruptedException {
		io.restassured.response.Response response = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("id")).body(property.getProperty("json_obj_incomplete"))
				.post(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_OK).extract().response();
		Assert.assertEquals(property.getProperty("id"), response.jsonPath().get("data.activityId"));
		Assert.assertEquals("incomplete", response.jsonPath().get("data.lastAttempt.status"));
	}

	public static void sendPostRequestAsNotAttemptedWithActivityIdAndVerifyResult() throws IOException, MessagingException,
			InterruptedException {
		io.restassured.response.Response response = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("id")).body(property.getProperty("json_obj_not_attempted"))
				.post(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_OK).extract().response();
		Assert.assertEquals(property.getProperty("id"), response.jsonPath().get("data.activityId"));
		Assert.assertEquals("not attempted", response.jsonPath().get("data.lastAttempt.status"));
	}

	public static void sendPostRequestAsBrowsedWithActivityIdAndVerifyResult() throws IOException, MessagingException,
			InterruptedException {
		io.restassured.response.Response response = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("id")).body(property.getProperty("json_obj_browsed"))
				.post(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_OK).extract().response();
		Assert.assertEquals(property.getProperty("id"), response.jsonPath().get("data.activityId"));
		Assert.assertEquals("browsed", response.jsonPath().get("data.lastAttempt.status"));
	}

	public static void sendGetRequestForAPILvl1ActivityAndVerifyResult() throws IOException, InterruptedException {
		String act_id = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("OWASP_Top_10"))
				.get(property.getProperty("level1_activity_result")).then().statusCode(HttpStatus.SC_OK).extract()
				.path("data.id").toString().replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals(property.getProperty("id"), act_id);
	}

	public static void sendInvalidCookieForGetAPIActivityResultsLvl1AndVerify() {
		String error_message = given().when().header("cookie", property.getProperty("invalid_cookie"))
				.pathParam("activityId", property.getProperty("OWASP_Top_10"))
				.get(property.getProperty("level1_activity_result")).then().extract().path("errors.detail").toString()
				.replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals("Invalid session", error_message);
	}

	public static void sendInvalidActivityIdForGetAPIActivityResultsLvl1AndVerify() throws IOException, InterruptedException {
		String error_message = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("wrong_activity_id"))
				.get(property.getProperty("level1_activity_result")).then().extract().path("errors.title").toString()
				.replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals("Validation Error", error_message);
	}

	public static void sendInvalidAuthTokenForGetAPIActivityResultsAndVerify() throws IOException, InterruptedException {
		String error_message = given().when().header("cookie", property.getProperty("invalid_cookie"))
				.pathParam("activityId", getId()).get(property.getProperty("activity_result")).then()
				.statusCode(HttpStatus.SC_UNAUTHORIZED).extract().path("errors.detail").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		Assert.assertEquals("Invalid session", error_message);
	}

	public static void sendInvalidActivityIdForGetAPIActivityResultsAndVerify() throws IOException, InterruptedException {
		String error_message = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("wrong_activity_id")).get(property.getProperty("activity_result"))
				.then().statusCode(HttpStatus.SC_BAD_REQUEST).extract().path("errors.title").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		Assert.assertEquals("Validation Error", error_message);
	}

	public static void sendBlankSuspendedDataForPostAPIActivityResultsAndVerify() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().header("cookie", fetchCookie()).pathParam("activityId", getId())
				.body(createInvalidActivityBody()).post(property.getProperty("activity_result")).then()
				.statusCode(HttpStatus.SC_BAD_REQUEST).extract().path("errors.title").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		Assert.assertEquals("Validation Error", error_message);
	}

	public static void sendInvalidActivityIdForPostAPIActivityResultsAndVerify() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().header("cookie", fetchCookie())
				.pathParam("activityId", property.getProperty("wrong_activity_id")).body(property.getProperty("json_obj_passed"))
				.post(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_BAD_REQUEST).extract()
				.path("errors.title").toString().replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals("Validation Error", error_message);
	}

	public static void sendInvalidAuthForPostAPIActivityResultsAndVerify() throws IOException, MessagingException,
			InterruptedException {
		String error_message = given().when().header("cookie", property.getProperty("invalid_cookie"))
				.pathParam("activityId", getId()).body(createActivityBody("passed"))
				.post(property.getProperty("activity_result")).then().statusCode(HttpStatus.SC_UNAUTHORIZED).extract()
				.path("errors.title").toString().replaceAll("\\[", "").replaceAll("\\]", "");
		Assert.assertEquals("Authentication Error", error_message);
	}
}
