package com.freenow.api;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FreeNowGetAPICall {
	static RequestSpecification httpRequest;
	
	public static Response getUserDetails(String username,String url)
	{
		
		httpRequest = RestAssured.given();
		httpRequest.param("username", username);
		Response response = httpRequest.request(Method.GET,url);
		
		return response;
	}
	
	public static Response getUserPosts(int userId,String url)
	{
		
		httpRequest = RestAssured.given();
		httpRequest.param("userId", userId);
		Response response = httpRequest.request(Method.GET,url);
		return response;
	}
	
	public static Response getPostComments(int postID,String url)
	{
		
		httpRequest = RestAssured.given();
		httpRequest.param("postId", postID);
		Response response = httpRequest.request(Method.GET,url);
		return response;
	}

}
