package com.spotify.oauth2.test;

import org.testng.annotations.Test;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static com.spotify.oauth2.utils.FakerUtils.*;

public class PlayListTest extends BaseTest{
	
	public Playlist playlistBuilder(String name, String describtion, boolean _public) {
		return Playlist.builder()
				.name(name)
				.description(describtion)
				._public(_public)
				.build();
	}
	
	/*
	 * common method created for assertion 
	 * public void assertPlaylistEqual(Playlist responsePlaylist, Playlist reqPlaylist) {
		assertThat(responsePlaylist.getName(), equalTo(reqPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(), equalTo(reqPlaylist.getDescription()));
		assertThat(responsePlaylist.getPublic(), equalTo(reqPlaylist.getPublic()));
	}*/

	@Story("Create a playlist story")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("12345")
    @Issue("1234567")
    @Description("this is the description")
    @Test(description = "should be able to create a playlist")
	public void createPlaylist() {

		// using builder pattern

		Playlist reqPlaylist = playlistBuilder(generateName(), generateDescription(), false);

		Response response = PlaylistApi.post(reqPlaylist);
	
		assertThat(response.statusCode(), equalTo(StatusCode.CODE_201.code));
		
		Playlist responsePlaylist = response.as(Playlist.class);
		
		assertThat(responsePlaylist.getName(), equalTo(reqPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(), equalTo(reqPlaylist.getDescription()));
		assertThat(responsePlaylist.get_public(), equalTo(reqPlaylist.get_public()));

	}

	@Test(description = "get a playlist")
	public void getPlayList() {
		
		Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
		
		Playlist responsePlaylist = response.as(Playlist.class);
		
		assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.code));
		
		//assertThat(responsePlaylist.getName(), equalTo("aamu"));
		//assertThat(responsePlaylist.getDescription(), equalTo("kdflf"));
	}

	@Test(description = "update a playlist")
	public void updatePlayList() {

		// using builder pattern

		Playlist reqPlaylist = playlistBuilder(generateName(), generateDescription(), false);

		Response response = PlaylistApi.update(DataLoader.getInstance().getGetPlaylistId(),reqPlaylist);
		
		assertThat(response.statusCode(), equalTo(StatusCode.CODE_200.code));
		
		
	}

	// Negative Scenario

	@Test(description = "shuld not Create a playlist with empty name")
	public void shouldNotcreatePlaylist() {

		// using builder pattern

		Playlist reqPlaylist = playlistBuilder("", generateDescription(), false);

		Response response = PlaylistApi.post(reqPlaylist);
	
		assertThat(response.statusCode(), equalTo(StatusCode.CODE_400.code));
		
		Error error = response.as(Error.class);

		assertThat(error.getError().getStatus(), equalTo(StatusCode.CODE_400.code));
		assertThat(error.getError().getMessage(), equalTo(StatusCode.CODE_400.message));
		
	}

	@Test(description = "should not Create a playlist with token expired ")
	public void shouldNotcreatePlaylistWith_expiredToken() {
		
		String invalidToken = "1234";
		
		Playlist reqPlaylist = playlistBuilder(generateName(), generateDescription(), false);

		Response response = PlaylistApi.post(invalidToken,reqPlaylist);
	
		assertThat(response.statusCode(), equalTo(StatusCode.CODE_401.code));
		
		Error error = response.as(Error.class);
		assertThat(error.getError().getStatus(), equalTo(StatusCode.CODE_401.code));
		assertThat(error.getError().getMessage(), equalTo(StatusCode.CODE_401.message));
		
	}

}
