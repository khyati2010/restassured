package org.tests;

import java.io.IOException;
import javax.mail.MessagingException;
import org.pages.WarRoomEaControlsAPI;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.utility.Reporting;
import org.utility.TestUpdater;

@Listeners(value = { TestUpdater.class, Reporting.class })
public class WarRoomEaControlsAPITest extends WarRoomEaControlsAPI {
	/*
	 * API used to get all the latest activity results for the lvl0 activities linked to a specific lvl1 activity
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the GET API '/activity_results/lvl1/{activityId}'")
	public static void verifyGetAPIActivityResultsLvl1() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendGetRequestForAPILvl1ActivityAndVerifyResult();
	}

	/*
	 * API used to get the latest activity result and attempt for the current user
	 */
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the GET API '/activity_results/{activityId}'")
	public static void verifyGetAPIActivityResult() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendGetRequestWithActivityIdAndVerifyResult();
	}

	/*
	 * API used to save an activity attempt for the current user
	 */
	// NEED TO FIX THESE COMMENTED TEST CASES
	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the POST API '/activity_results/{activityId}' with passed status")
	public static void verifyPostAPIActivityResultWithPassedStatus() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendPostRequestAsPassedWithActivityIdAndVerifyResult();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the POST API '/activity_results/{activityId}' with failed status")
	public static void verifyPostAPIActivityResultWithFailedStatus() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendPostRequestAsFailedWithActivityIdAndVerifyResult();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the POST API '/activity_results/{activityId}' with completed status")
	public static void verifyPostAPIActivityResultWithCompletedStatus() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendPostRequestAsCompletedWithActivityIdAndVerifyResult();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the POST API '/activity_results/{activityId}' with incomplete status")
	public static void verifyPostAPIActivityResultWithInCompleteStatus() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendPostRequestAsInCompleteWithActivityIdAndVerifyResult();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "Verifying 'not-attempted' status on the POST API '/activity_results/{activityId}'")
	public static void verifyPostAPIActivityResultWithNotAttemptedStatus() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendPostRequestAsNotAttemptedWithActivityIdAndVerifyResult();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the POST API '/activity_results/{activityId}' with browsed status")
	public static void verifyPostAPIActivityResultWithBrowseStatus() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendPostRequestAsBrowsedWithActivityIdAndVerifyResult();
	}

	/* Negative test cases */

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the error message on passing invalid cookie in the GET API '/activity_results/lvl1/{activityId}'")
	public static void verifyGetAPIActivityResultsLvl1WithInvalidAuth() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendInvalidCookieForGetAPIActivityResultsLvl1AndVerify();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the error message on passing invlaid Activity Id in the GET API '/activity_results/lvl1/{activityId}'")
	public static void verifyGetAPIActivityResultsLvl1WithInvalidActivityId() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendInvalidActivityIdForGetAPIActivityResultsLvl1AndVerify();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the error message on passing invalid AUTH token in the GET API '/activity_results/{activityId}'")
	public static void verifyGetAPIActivityResultsWithInvalidAuth() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendInvalidAuthTokenForGetAPIActivityResultsAndVerify();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the error message on passing invalid Activity Id in the GET API '/activity_results/{activityId}'")
	public static void verifyGetAPIActivityResultsWithInvalidActivityId() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendInvalidActivityIdForGetAPIActivityResultsAndVerify();
	}

	// @Test(groups = { "Smoke", "Regression" }, description = "-Verifying the error message on passing a blank value for suspendData in POST API '/activity_results/{activityId}'")
	// public static void verifyPostAPIActivityResultWithBlanksuspendData() throws IOException, MessagingException, InterruptedException {
	// ActivityResultsAPI.sendBlankSuspendedDataForPostAPIActivityResultsAndVerify();
	// }

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the error message on passing a invalid activity Id in POST API '/activity_results/{activityId}'")
	public static void verifyPostAPIActivityResultWithInvalidActivityId() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendInvalidActivityIdForPostAPIActivityResultsAndVerify();
	}

	@Test(groups = { "Smoke", "Regression" }, description = "-Verifying the error message on passing a invalid Cookie in POST API '/activity_results/{activityId}'")
	public static void verifyPostAPIActivityResultWithInvalidAuthToken() throws IOException, MessagingException, InterruptedException {
		WarRoomEaControlsAPI.sendInvalidAuthForPostAPIActivityResultsAndVerify();
	}
}
