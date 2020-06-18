package org.tests;

import java.io.IOException;

import org.pages.EALoginAPI;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utility.Reporting;
import org.utility.TestUpdater;

@Listeners(value = { TestUpdater.class, Reporting.class })
public class EALoginAPITest extends EALoginAPI {

	// API used to list all the activities the current user is enrolled in
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify GET /user_activities API With Cookie Parameter", priority = 0)
	@TestData(testId = 3)
	public void getUserActivities() throws IOException, InterruptedException {
		sendGetRequestToUserActivitiesAndVerify();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verify GET /user_activities API with Cookie & limit parameter", priority = 1)
	@TestData(testId = 3)
	public void verifyGetUserActivitiesWithLimitParameter() throws IOException, InterruptedException {
		sendGetRequestToUserActivitiesWithLimitParameterAndVerify();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verify GET /user_activities API with Cookie & offset parameter", priority = 2)
	@TestData(testId = 3)
	public void verifyGetUserActivitiesWithAnOffsetParameter() throws IOException, InterruptedException {
		sendGetRequestToUserActivitiesWithAnOffsetParameterAndVerify();
	}

	// Verifying if a user dis_enrolls an activity
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify DELETE And POST /user_activities API to dis_enroll an activity", priority = 3)
	@TestData(testId = 4)
	public void verifyGetUserActivitiesToDisenroll() throws IOException, InterruptedException {
		sendGetRequestToUserActivitiesToDisenrollUser();
	}

	// Verifying if a user enrolls an activity
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify DELETE And POST /user_activities API to enroll an activity", priority = 4)
	@TestData(testId = 4)
	public void verifyGetUserActivitiesToEnrol() throws IOException, InterruptedException {
		sendGetRequestToUserActivitiesToEnrolUser();
	}

	// Negative Scenarios
	// This scenario is failing as the status code is not coming as desired. Issue has already been raised.
	// Verifying an error message on enrolling user in an existing activity
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify an error message on enrolling user in an existing activity", priority = 5)
	@TestData(testId = 5)
	public void enrolAnExistingUserUsingGetUserActivitiesAPIAndVerifyError() throws IOException, InterruptedException {
		sendGetRequestToUserActvitiesForEnrolledActivityAndVerify();
	}

	// This scenario is failing as the status code is not coming as desired. Issue has already been raised.
	// Verifying an error message on deleting an activity which is not enrolled
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify an error message on dis-enrolling user in an existing activity")
	@TestData(testId = 5)
	public void deleteANewActivityAPIUsingGetUserActivitiesAPIAndVerifyError() throws IOException, InterruptedException {
		sendDeleteRequestToUserActivitiesAPIToDisenrollNewActivity();
	}

	// Verifying an error message on passing invalid cookie
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify GET /user_activities API with invalid cookie")
	@TestData(testId = 3)
	public void verifyGetUserActivitiesAPIOnPassingInvalidCookie() {
		sendGetRequestToUserActivitiesWithInvalidCookieAndVerify();
	}

	// Verifying an error message on passing invalid activity id
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify POST /user_activities{activityId} API with invalid cookie")
	@TestData(testId = 3)
	public void verifyPostUserActivitiesAPIOnPassingInvalidCookie() {
		sendPostRequestToUserActivitiesWithInvalidCookieAndVerify();
	}

	// Verifying an error message on passing invalid activity id
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify POST /user_activities{activityId} API with invalid Activity Id")
	@TestData(testId = 3)
	public void verifyPostUserActivitiesAPIOnPassingInvalidActivityId() throws IOException, InterruptedException {
		sendPostRequestToUserActivitiesWithInvalidActivityId();
	}

	// Verifying an error message on passing invalid cookie
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify DELETE /user_activities{activityId} API with invalid Auth token")
	@TestData(testId = 3)
	public void verifyDeleteUserActivitiesAPIOnPassingInvalidCookies() {
		sendDeleteRequestToUserActivitiesWithInvalidCookieAndVerify();
	}

	// Verifying an error message on passing invalid activity id
	@Test(groups = { "Smoke", "Regression" }, description = "-Verify DELETE /user_activities{activityId} API with invalid Activity Id")
	@TestData(testId = 3)
	public void verifyDeleteUserActivitiesAPIOnPassingInvalidActivityId() throws IOException, InterruptedException {
		sendDeleteRequestToUserActivitiesWithInvalidActivityId();
	}

}
