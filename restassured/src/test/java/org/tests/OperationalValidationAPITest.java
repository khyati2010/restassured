package org.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import org.apache.http.HttpStatus;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utility.Reporting;
import org.utility.RestAssuredMainClass;
import org.utility.TestUpdater;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

@Listeners(value = { TestUpdater.class, Reporting.class })
public class OperationalValidationAPITest extends RestAssuredMainClass {

	// API used to find activities using different parameters

	@Test(groups = { "Smoke", "Regression" }, description = "C101467-Verify the API to find activities")
	@TestData(testId = 1)
	public void verifyGetActivityIdAPI() {
		given().when().get("/activities").then().statusCode(HttpStatus.SC_OK);
	}

	@Test(groups = { "Smoke", "Regression" }, description = "C101467-Verify the API to find activities using difficulties parameters")
	@TestData(testId = 1)
	public void getActivityIdWithDifficultyParameter() {
		given().when().queryParam("difficulties", property.getProperty("difficulty_level")).get("/activities").then()
				.statusCode(HttpStatus.SC_OK).body("data.activityId", hasItem(property.get("activity_id")));
	}

	@Test(groups = { "Smoke", "Regression" }, description = " -Verify the API to find activities using limit parameters")
	@TestData(testId = 1)
	public void getActivityIdWithLimitParameter() {
		given().queryParam("limit", property.getProperty("limit")).get("/activities").then().statusCode(HttpStatus.SC_OK)
				.extract().path("meta.pagination.limit").toString().equalsIgnoreCase(property.getProperty("limit"));

	}

	@Test(groups = { "Smoke", "Regression" }, description = " -Verify the API to find activities using offset parameters")
	@TestData(testId = 1)
	public void getActivityIdWithOffsetParameter() {
		given().queryParam("offset", property.getProperty("offset")).get("/activities").then().statusCode(HttpStatus.SC_OK)
				.extract().path("meta.pagination.offset").toString().equalsIgnoreCase(property.getProperty("offset"));

	}

	// API used to load an activity's info. If "level" is specified (1), the
	// whole activity is loaded with its level0 descendants and screens
	@Test(groups = { "Smoke", "Regression" }, description = "C101468-Verify an API to load activity's info")
	@TestData(testId = 2)
	public void getActivityWithAnActivityId() {
		String actual_course_names = given().queryParam("level", 1).get(property.getProperty("activity_id_path")).then()
				.statusCode(HttpStatus.SC_OK).extract().path("data.lvl0.name").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		String expected_course_names = property.get("course_names").toString();
		Assert.assertEquals(expected_course_names, actual_course_names);
	}

	@Test(groups = { "Smoke", "Regression" }, description = " -Verify an API to load activity's info without setting a value for level")
	@TestData(testId = 2)
	public void getActivityWithAnActivityIdWithoutLevel() {
		Boolean result = null;
		Response respons = given().get(property.getProperty("activity_id_path")).then().contentType(ContentType.JSON).extract()
				.response();
		JsonPath jp = respons.getBody().jsonPath();
		if (jp.get("data.lvl0") != null) {
			result = false;
		} else {
			result = true;
		}
		Assert.assertTrue("Activities at level 0 still exist", result);
	}

}
