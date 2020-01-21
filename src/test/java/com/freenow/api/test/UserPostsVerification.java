package com.freenow.api.test;

import org.testng.annotations.Test;

import com.freenow.api.FreeNowGetAPICall;
import com.freenow.utils.ConfigFileReader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.ArrayList;

import org.testng.Assert;


public class UserPostsVerification {

	String userName = null;
	String responseBody;
	Response response;
	int statusCode;
	JsonPath jsonPathValue;
	int userId;
	ArrayList<Integer> fetchedUserId;
	ArrayList<String> fetchedUserName;
	ArrayList<Integer> getUserPostsId;


	@Test(priority = 0)
	public void getUserInfo() {

		RestAssured.baseURI = ConfigFileReader.getInstance().getApplicationUrl();
		String getUserDetailsUrl = RestAssured.baseURI + "users";
		userName = ConfigFileReader.getInstance().getUserName();

		response = FreeNowGetAPICall.getUserDetails(userName, getUserDetailsUrl);

		responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);

		statusCode = response.getStatusCode();
		System.out.println("the status code is: " + statusCode);

		Assert.assertEquals(statusCode, 200);

		// get the key value by using JsonPath:
		jsonPathValue = response.jsonPath();
		fetchedUserId = jsonPathValue.get("id");
		for (int userID : fetchedUserId)
			System.out.println("user ID is: " + userID);

		fetchedUserName = jsonPathValue.get("username");
		System.out.println("fetched user name is: " + fetchedUserName.get(0).toString());
		Assert.assertEquals(fetchedUserName.get(0).toString(), userName);

	}

	@Test(priority = 1)
	public void getUserPosts() {

		RestAssured.baseURI = ConfigFileReader.getInstance().getApplicationUrl();
		String getUserPostsUrl = RestAssured.baseURI + "posts";

		userId = fetchedUserId.get(0);

		response = FreeNowGetAPICall.getUserPosts(userId, getUserPostsUrl);
		responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);

		statusCode = response.getStatusCode();
		System.out.println("the status code is: " + statusCode);

		jsonPathValue = response.jsonPath();
		getUserPostsId = jsonPathValue.get("id");

		for (int userPosts : getUserPostsId) {
			System.out.println("user post IDs are : " + userPosts);

		}
	}



}
