package com.freenow.api.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freenow.api.FreeNowGetAPICall;
import com.freenow.utils.ConfigFileReader;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Iterator;

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
	ArrayList<String> getCommentEmailId;
	ArrayList<Integer> postCommentId;


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

	@Test(priority = 2, dataProvider = "userPostIDs")
	public void getUserPostComments(Integer postIDs) {
		
		int postId=postIDs;

		RestAssured.baseURI = ConfigFileReader.getInstance().getApplicationUrl();
		String getCommentsUrl=RestAssured.baseURI +"comments";
			
		response = FreeNowGetAPICall.getPostComments(postId, getCommentsUrl);
		responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);

		statusCode = response.getStatusCode();
		System.out.println("the status code is: " + statusCode);

		jsonPathValue = response.jsonPath();
		getCommentEmailId = jsonPathValue.get("email");
		postCommentId = jsonPathValue.get("id");

		for (int i = postId; i <= postId; i++) {
			// for (String emailId : getUserPostComments)
			for (int y = 0; y < postCommentId.size(); y++) {
				if (getCommentEmailId.get(y).toString().contains("@")) {
					System.out.println("valid email " + getCommentEmailId.get(y).toString());
				} else {

					System.out.println(postCommentId.get(y));

				}
			}

		}

	}

	@DataProvider(name = "userPostIDs")
	public Iterator<Integer> userPostsID() {
		System.out.println(getUserPostsId);

		ArrayList<Integer> userPostIds = getUserPostsId;

		return userPostIds.iterator();
	}

}
