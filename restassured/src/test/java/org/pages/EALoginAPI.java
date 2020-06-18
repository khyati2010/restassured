package org.pages;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.utility.RestAssuredMainClass;

import io.restassured.authentication.OAuthSignature;

public class EALoginAPI extends RestAssuredMainClass {

	public static void sendGetRequestToUserActivitiesAndVerify() throws IOException, InterruptedException {
		Assert.assertEquals(given().when().header("cookie", fetchCookie()).get(property.getProperty("user_activities")).then()
				.statusCode(HttpStatus.SC_OK).extract().body().path("meta.pagination.total").toString().matches("2"), true);
	}

	public static void sendGetRequestToUserActivitiesWithLimitParameterAndVerify() throws IOException, InterruptedException {
		Assert.assertEquals(
				given().when().header("cookie", fetchCookie()).queryParam("limit", "1")
						.get(property.getProperty("user_activities")).then().statusCode(HttpStatus.SC_OK).extract().body()
						.path("meta.pagination.limit").toString().matches("1"), true);
	}

	public static void sendGetRequestToUserActivitiesWithAnOffsetParameterAndVerify() throws IOException, InterruptedException {
		Assert.assertEquals(
				given().when().header("cookie", fetchCookie()).queryParam("offset", "2")
						.get(property.getProperty("user_activities")).then().statusCode(HttpStatus.SC_OK).extract().body()
						.path("meta.pagination.offset").toString().matches("2"), true);
	}

	public static void sendGetRequestToUserActivitiesToEnrolUser() throws IOException, InterruptedException {
		getActivityId();
		given().when().header("cookie", fetchCookie()).pathParam("activityId", property.getProperty("ActivityId").trim())
				.post(property.getProperty("user_activities_path")).then().statusCode(HttpStatus.SC_OK).extract()
				.path("data.createdOn").toString().contains(returnDate());
	}

	public static void sendGetRequestToUserActivitiesToDisenrollUser() throws IOException, InterruptedException {
		getActivityId();
		given().when().header("cookie", fetchCookie()).pathParam("activityId", property.getProperty("ActivityId").trim())
				.delete(property.getProperty("user_activities_path")).then().statusCode(HttpStatus.SC_OK).extract()
				.path("data.createdOn").toString().contains(returnDate());
	}

	public static void sendGetRequestToUserActvitiesForEnrolledActivityAndVerify() throws IOException, InterruptedException {
		given().when().header("cookie", fetchCookie()).post(property.getProperty("existing_user_activity_path")).then()
				.statusCode(HttpStatus.SC_BAD_REQUEST).extract().path("errors.detail").toString()
				.contains(property.get("error_detail").toString());
	}

	public static void sendDeleteRequestToUserActivitiesAPIToDisenrollNewActivity() throws IOException, InterruptedException {
		fetchCookie();
		restSetup("", property.getProperty("base_host"), property.getProperty("base_port"));
		given().when().header("cookie", property.getProperty("cookie_value"))
				.pathParam("activityId", property.getProperty("activity_id")).post(property.getProperty("user_activities_path"))
				.then().statusCode(HttpStatus.SC_OK).extract().path("data.createdOn").toString().contains(returnDate());
		given().when().header("cookie", property.getProperty("cookie_value"))
				.pathParam("activityId", property.getProperty("activity_id"))
				.delete(property.getProperty("user_activities_path")).then().statusCode(HttpStatus.SC_OK).extract()
				.path("data.createdOn").toString().contains(returnDate());
		given().when().header("cookie", property.getProperty("cookie_value"))
				.pathParam("activityId", property.getProperty("activity_id"))
				.delete(property.getProperty("user_activities_path")).then().statusCode(HttpStatus.SC_NOT_FOUND).extract()
				.path("errors.detail").toString().contains("User not enrolled");
	}

	public static void sendGetRequestToUserActivitiesWithInvalidCookieAndVerify() {
		Assert.assertEquals(
				given().header("cookie", property.getProperty("invalid_cookie")).get(property.getProperty("user_activities"))
						.then().statusCode(HttpStatus.SC_UNAUTHORIZED).extract().body().path("errors.detail").toString()
						.replaceAll("\\[", "").replaceAll("\\]", ""), "Invalid session");
	}

	public static void sendPostRequestToUserActivitiesWithInvalidCookieAndVerify() {
		Assert.assertEquals(
				given().header("cookie", property.getProperty("invalid_cookie")).pathParam("activityId", getActivityId())
						.post(property.getProperty("user_activities_path")).then().statusCode(HttpStatus.SC_UNAUTHORIZED)
						.extract().body().path("errors.detail").toString().replaceAll("\\[", "").replaceAll("\\]", ""),
				"Invalid session");
	}

	public static void sendPostRequestToUserActivitiesWithInvalidActivityId() throws IOException, InterruptedException {
		Assert.assertEquals(
				given().header("cookie", fetchCookie()).pathParam("activityId", property.getProperty("wrong_activity_id"))
						.post(property.getProperty("user_activities_path")).then().statusCode(HttpStatus.SC_BAD_REQUEST)
						.extract().body().path("errors.title").toString().replaceAll("\\[", "").replaceAll("\\]", ""),
				"Validation Error");
	}

	public static void sendDeleteRequestToUserActivitiesWithInvalidCookieAndVerify() {
		Assert.assertEquals(
				given().header("cookie", property.getProperty("invalid_cookie")).pathParam("activityId", getActivityId())
						.delete(property.getProperty("user_activities_path")).then().statusCode(HttpStatus.SC_UNAUTHORIZED)
						.extract().body().path("errors.detail").toString().replaceAll("\\[", "").replaceAll("\\]", ""),
				"Invalid session");
	}

	public static void sendDeleteRequestToUserActivitiesWithInvalidActivityId() throws IOException, InterruptedException {
		Assert.assertEquals(
				given().header("cookie", fetchCookie()).pathParam("activityId", property.getProperty("wrong_activity_id"))
						.delete(property.getProperty("user_activities_path")).then().statusCode(HttpStatus.SC_BAD_REQUEST)
						.extract().body().path("errors.title").toString().replaceAll("\\[", "").replaceAll("\\]", ""),
				"Validation Error");
	}

}
