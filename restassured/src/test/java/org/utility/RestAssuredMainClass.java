package org.utility;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import javax.mail.MessagingException;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.restassured.RestAssured;

public class RestAssuredMainClass extends MainClass {
	/*
	 * This class will configure all the properties required for the set up of rest assured. This will include host , port, cookies, auth token etc required to test any rest service.
	 */

	// This method is picking properties like base, host, port from the property file. And setting that to the rest assured class.
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "base", "host", "port" })
	public static void restSetup(@Optional String r_base, @Optional String r_host, @Optional String r_port) {
		String port = r_port;
		String base_path = r_base;
		String base_host = r_host;
		if (port == null) {
			if (!property.getProperty("base_port").isEmpty()) {
				RestAssured.port = Integer.parseInt(property.getProperty("base_port"));
			}
		} else {
			RestAssured.port = Integer.valueOf(port);
		}

		if (base_path == null) {
			if (!property.getProperty("base_path").isEmpty()) {
				RestAssured.basePath = property.getProperty("base_path");
			}
		} else {
			RestAssured.basePath = base_path;
		}

		if (base_host == null) {
			if (!property.getProperty("base_host").isEmpty()) {
				RestAssured.baseURI = property.getProperty("base_host");
			}
		} else {
			RestAssured.baseURI = base_host;
		}
	}

	// Generic methods
	// @BeforeMethod
	// Fetching authorization token and saving its value in the properties file
	public static void getAuthToken() {
		String auth_token = given().when().body(property.getProperty("auth_token_payload")).post("/auth/login").then().extract()
				.path("data.token").toString();
		property.setProperty("auth_token", auth_token);
		System.out.println(property.getProperty("auth_token"));
		Reporter.log(property.getProperty("auth_token"));
	}

	// This method is to create and Set Password Body With Invalid Token
	public static String createSetPasswordBodyWithInvalidToken() throws IOException, InterruptedException {
		String body = "{ \"email\": \"" + property.getProperty("email_address") + "\"," + "\"password\": \""
				+ property.getProperty("pass") + "\"," + "\"token\": \"" + property.getProperty("invalid_token") + "\" }";
		property.setProperty("password_set_body", body);
		return body;
	}

	// Method to create Set Password Body With Existing Token
	public static String createSetPasswordBodyWithExistingToken() throws IOException, InterruptedException {
		String body = "{ \"email\": \"" + property.getProperty("email_address") + "\"," + "\"password\": \""
				+ property.getProperty("pass") + "\"," + "\"token\": \"" + property.getProperty("existing_token") + "\" }";
		property.setProperty("password_set_body", body);
		return body;
	}

	// Method to create Set Password Body With InValid Email
	public static String createSetPasswordBodyWithInValidEmail() throws IOException, InterruptedException, MessagingException {
		String body = "{ \"email\": \"" + property.getProperty("invalid_email") + "\"," + "\"password\": \""
				+ property.getProperty("pass") + "\"," + "\"token\": \"" + property.getProperty("token") + "\" }";
		property.setProperty("password_set_body", body);
		return body;
	}

	// Method to create Set Password Body With Valid Token
	public static String createSetPasswordBodyWithValidToken() throws IOException, InterruptedException, MessagingException {
		//EmailFetcherAndReader.fetchVerificationCode(property.getProperty("email_address"));
		String body = "{ \"email\": \"" + property.getProperty("email_address") + "\"," + "\"password\": \""
				+ property.getProperty("pass") + "\"," + "\"token\": \"" + property.getProperty("token") + "\" }";
		property.setProperty("password_set_body", body);
		System.out.println(body);
		Reporter.log(body);
		return body;
	}

	// Method to create Change Password Body
	public static String createChangePasswordBody() throws IOException, InterruptedException {
		String body = "{ \"oldPassword\": \"" + property.getProperty("pass") + "\"," + "\"newPassword\": \""
				+ property.getProperty("pass") + "\" }";
		return body;
	}

	// Method to create User Info Body including first name and last name
	public static String createUserInfoBody() throws IOException, InterruptedException {
		String body = "{ \"firstName\": \"" + property.getProperty("firstname") + "\"," + "\"lastName\": \""
				+ property.getProperty("lastname") + "\" }";
		return body;
	}

	// Method to create Reset Password Body
	public static String createResetPasswordBody() throws IOException, InterruptedException {
		String body = "{ \"email\": \"" + property.getProperty("email_address") + "\" }";
		property.setProperty("password_set_body", body);
		return body;
	}

	// Method to create Reset Password Body With Invalid Email
	public static String createResetPasswordBodyWithInvalidEmail() throws IOException, InterruptedException {
		String body = "{ \"email\": \"" + property.getProperty("invalid_email") + "\" }";
		property.setProperty("password_set_body", body);
		return body;
	}

	// Method to create Activity Body
	public static String createActivityBody(String status) throws IOException, InterruptedException {
		String body = "{ \"suspendData\": \"" + "" + "\"," + "\"status\": \"" + "passed" + "\"," + "\"credit\": \"" + "true"
				+ "\"," + "\"timeSpent\": \"" + "0" + "\"," + "\"exitStatus\": \"" + "time-out" + "\"," + "\"minScore\": \""
				+ "0" + "\"," + "\"maxScore\": \"" + "0" + "\"," + "\"rawScore\": \"" + "0" + "\"," + "\"threshold\": \"" + "0"
				+ "\"," + "\"meta\": \"" + "{}" + "\" }";
		return body;
	}

	// Method to create Invalid Activity Body
	public static String createInvalidActivityBody() throws IOException, InterruptedException {
		String body = "{ \"suspendData\": \"" + "" + "\"," + "\"status\": \"" + "passed" + "\"," + "\"credit\": \"" + "true"
				+ "\"," + "\"timeSpent\": \"" + "0" + "\"," + "\"exitStatus\": \"" + "time-out" + "\"," + "\"minScore\": \""
				+ "0" + "\"," + "\"maxScore\": \"" + "0" + "\"," + "\"rawScore\": \"" + "0" + "\"," + "\"threshold\": \"" + "0"
				+ "\"," + "\"meta\": \"" + "{}" + "\" }";
		return body;
	}

	// Method to get ActivityId
	public static String getActivityId() {
		String Id = given().when().get("/activities").then().extract().path("data.activityId").toString().replaceAll("\\[", "")
				.replaceAll("\\]", "");
		String[] final_Id = Id.split(",");
		property.setProperty("ActivityId", final_Id[1]);
		return final_Id[1].trim();
	}

	// Method to create Body For Fetching Cookie
	public static String createBodyForFetchingCookie() throws IOException, InterruptedException {
		String body = "{ \"email\": \"" + property.getProperty("em") + "\"," + "\"password\": \"" + property.getProperty("passw")
				+ "\" }";
		property.setProperty("auth_login_body", body);
		return body;
	}

	// This method will fetch cookies
	public static String fetchCookie() throws IOException, InterruptedException {
		restSetup("", property.getProperty("base_host"), property.getProperty("base_port_Admin"));
		String cookie = given().when().body(createBodyForFetchingCookie()).post("/v1.0/auth/login").getHeaders().toString();
		String[] cookies = cookie.split(";");
		String[] cookies1 = cookies[1].split("=");
		String[] cookies2 = cookies[7].split("=");
		System.out.println("session_learn=" + cookies1[3] + ";" + "nonce=" + cookies2[3].trim());
		property.setProperty("cookie_value", "session_learn=" + cookies1[3] + ";" + "nonce=" + cookies2[3].trim());
		return "session_learn=" + cookies1[3] + ";" + "nonce=" + cookies2[3].trim();
	}

}