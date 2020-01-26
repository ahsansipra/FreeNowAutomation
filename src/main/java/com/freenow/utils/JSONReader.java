package com.freenow.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.domain.comments.PostComments;
import com.freenow.domain.posts.Post;
import com.freenow.domain.user.User;

public class JSONReader {

	ObjectMapper mapper;
	static JSONReader jsonReader;

	public List<User> getUser(String json) {

		try {
			List<User> users = getMapper().readValue(json, new TypeReference<List<User>>() {
			});
			return users;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public List<Post> getPost(String json) {

		try {
			List<Post> postDetails = getMapper().readValue(json, new TypeReference<List<Post>>() {
			});
			return postDetails;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<PostComments> getPostComment(String json) {

		try {
			List<PostComments> comments = getMapper().readValue(json, new TypeReference<List<PostComments>>() {
			});
			return comments;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	public static JSONReader getJsonReader() {
		if (jsonReader == null) {
			jsonReader = new JSONReader();
		}
		return jsonReader;
	}
}
