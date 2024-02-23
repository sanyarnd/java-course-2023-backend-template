package edu.java.scrapper.data.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Owner(

	@JsonProperty("gists_url")
	String gistsUrl,

	@JsonProperty("repos_url")
	String reposUrl,

	@JsonProperty("following_url")
	String followingUrl,

	@JsonProperty("starred_url")
	String starredUrl,

	@JsonProperty("login")
	String login,

	@JsonProperty("followers_url")
	String followersUrl,

	@JsonProperty("type")
	String type,

	@JsonProperty("url")
	String url,

	@JsonProperty("subscriptions_url")
	String subscriptionsUrl,

	@JsonProperty("received_events_url")
	String receivedEventsUrl,

	@JsonProperty("avatar_url")
	String avatarUrl,

	@JsonProperty("events_url")
	String eventsUrl,

	@JsonProperty("html_url")
	String htmlUrl,

	@JsonProperty("site_admin")
	boolean siteAdmin,

	@JsonProperty("id")
	int id,

	@JsonProperty("gravatar_id")
	String gravatarId,

	@JsonProperty("node_id")
	String nodeId,

	@JsonProperty("organizations_url")
	String organizationsUrl
) {
}
