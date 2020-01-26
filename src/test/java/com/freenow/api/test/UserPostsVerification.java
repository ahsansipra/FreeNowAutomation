package com.freenow.api.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.api.FreeNowGetAPICall;
import com.freenow.domain.comments.PostComments;
import com.freenow.domain.posts.Post;
import com.freenow.domain.user.User;
import com.freenow.utils.CommonUtility;
import com.freenow.utils.ConfigFileReader;
import com.freenow.utils.JSONReader;
import com.freenow.utils.LoggingDetails;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

public class UserPostsVerification {

	String userName = null;
	String responseBody;
	Response response;
	int statusCode;
	JsonPath jsonPathValue;
	int userID;
	ArrayList<Integer> fetchedUserId;
	ArrayList<String> fetchedUserName;
	ArrayList<Integer> userPostsIDs;
	ArrayList<String> getCommentEmailId;
	ArrayList<Integer> postCommentId;

	@Test(priority = 0)
	public void getUserInfo() {

		RestAssured.baseURI = ConfigFileReader.getInstance().getApplicationUrl();
		String userDetailUrl = RestAssured.baseURI + "users";
		userName = ConfigFileReader.getInstance().getUserName();

		response = FreeNowGetAPICall.getUserDetails(userName, userDetailUrl);

		responseBody = response.getBody().asString();
		// System.out.println("Response Body is: " + responseBody);

		statusCode = response.getStatusCode();

		LoggingDetails.loginfo("status code for getUserInfo is: " + statusCode);

		Assert.assertEquals(statusCode, 200);

		List<User> users = JSONReader.getJsonReader().getUser(responseBody);

		for (User user : users) {
			userID = user.getId();
			String UsrName = user.getUsername();
			Assert.assertEquals(UsrName, userName);

		}

	}

	@Test(priority = 1)
	public void getUserPosts() {

		RestAssured.baseURI = ConfigFileReader.getInstance().getApplicationUrl();
		String userPostsUrl = RestAssured.baseURI + "posts";
		userPostsIDs = new ArrayList<Integer>();
		// userId = fetchedUserId.get(0);

		response = FreeNowGetAPICall.getUserPosts(userID, userPostsUrl);
		responseBody = response.getBody().asString();
		// System.out.println("Response Body is: " + responseBody);
		LoggingDetails.loginfo("User Posts Details is: " + responseBody);

		statusCode = response.getStatusCode();
		LoggingDetails.loginfo("status code for getUserPosts is: " + statusCode);
		Assert.assertEquals(statusCode, 200);

		List<Post> postDetail = JSONReader.getJsonReader().getPost(responseBody);
		for (Post post : postDetail) {

			userPostsIDs.add(post.getId());
		}

	}

	@Test(priority = 2, dataProvider = "userPostIDs")
	public void getUserPostComments(Integer postIDs) {

		int postId = postIDs;
		SoftAssert softAssert = new SoftAssert();

		RestAssured.baseURI = ConfigFileReader.getInstance().getApplicationUrl();
		String commentsUrl = RestAssured.baseURI + "comments";

		response = FreeNowGetAPICall.getPostComments(postId, commentsUrl);
		responseBody = response.getBody().asString();
		// System.out.println("Response Body is: " + responseBody);
		LoggingDetails.loginfo("Post Comments Details is: " + responseBody);

		statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		LoggingDetails.loginfo("status code for getUserPostComments is: " + statusCode);

		List<PostComments> comments = JSONReader.getJsonReader().getPostComment(responseBody);

		if (comments == null || comments.isEmpty()) {
			LoggingDetails.loginfo("No comments found for postID: " + postId);
			softAssert.assertTrue(false);
		}

		for (PostComments comment : comments) {
			String emailInComments = comment.getEmail();
			if (StringUtils.isEmpty(emailInComments)) {

				LoggingDetails.loginfo("email is empty for postID: " + postId);
				softAssert.assertTrue(false);
			}
			Assert.assertEquals(true, CommonUtility.isValid(emailInComments));

		}
		softAssert.assertAll();
	}

	@DataProvider(name = "userPostIDs")
	public Iterator<Integer> userPostsIDs() {

		ArrayList<Integer> userPostIds = userPostsIDs;
		return userPostIds.iterator();
	}

}
