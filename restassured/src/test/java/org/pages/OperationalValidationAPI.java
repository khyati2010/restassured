package org.pages;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.utility.RestAssuredMainClass;

public class OperationalValidationAPI extends RestAssuredMainClass {

	public void verifyGetActivityIdAPI() {
		given().when().get("/activities").then().statusCode(HttpStatus.SC_OK);
	}

	public void getActivityIdWithDifficultyParameter() {
		given().when().queryParam("difficulties", property.getProperty("difficulty_level")).get("/activities").then()
				.statusCode(HttpStatus.SC_OK).body("data.activityId", hasItem(property.get("activity_id")));
	}

	public void getActivityIdWithLimitParameter() {
		given().queryParam("limit", property.getProperty("limit")).get("/activities").then().statusCode(HttpStatus.SC_OK)
				.extract().path("meta.pagination.limit").toString().equalsIgnoreCase(property.getProperty("limit"));

	}

	public void getActivityIdWithOffsetParameter() {
		given().queryParam("offset", property.getProperty("offset")).get("/activities").then().statusCode(HttpStatus.SC_OK)
				.extract().path("meta.pagination.offset").toString().equalsIgnoreCase(property.getProperty("offset"));

	}

	// API used to load an activity's info. If "level" is specified (1), the
	// whole activity is loaded with its level0 descendants and screens
	public void getActivityWithAnActivityId() {
		String actual_course_names = given().queryParam("level", 1).get(property.getProperty("activity_id_path")).then()
				.statusCode(HttpStatus.SC_OK).extract().path("data.lvl0.name").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		String expected_course_names = property.get("course_names").toString();
		Assert.assertEquals(expected_course_names, actual_course_names);
	}

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
