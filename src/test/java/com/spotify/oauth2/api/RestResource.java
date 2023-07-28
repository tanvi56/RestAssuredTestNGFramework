package com.spotify.oauth2.api;

import static com.spotify.oauth2.api.SpecBuilder.getAccountRequestSpecification;
import static com.spotify.oauth2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import java.util.HashMap;
import io.restassured.response.Response;
import static com.spotify.oauth2.api.Route.*;



public class RestResource {
	
	 public static Response post(String path, String token, Object requestPlaylist){
	        return given(getRequestSpecification()).
	                body(requestPlaylist).
	                auth().oauth2(token).
	        when().post(path).
	        then().spec(getResponseSpec()).
	                extract().
	                response();
	    }

	    public static Response postAccount(HashMap<String, String> formParams){
	        return given(getAccountRequestSpecification()).
	                formParams(formParams).
	        when().post(API + TOKEN).
	        then().spec(getResponseSpec()).
	                extract().
	                response();
	    }

	    public static Response get(String path, String token){
	        return given(getRequestSpecification()).
	                auth().oauth2(token).
	        when().get(path).
	        then().spec(getResponseSpec()).
	                extract().
	                response();
	    }

	    public static Response update(String path, String token, Object requestPlaylist){
	        return given(getRequestSpecification()).
	                auth().oauth2(token).
	                body(requestPlaylist).
	        when().put(path).
	        then().spec(getResponseSpec()).
	                extract().
	                response();
	    }
}
