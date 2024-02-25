package edu.java.scrapper.data.dto.github;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryDTO(
	@JsonProperty("pushed_at")
	String pushedAt,

	@JsonProperty("subscribers_count")
	int subscribersCount,

	@JsonProperty("id")
	int id,

	@JsonProperty("forks")
	int forks,

	@JsonProperty("visibility")
	String visibility,

	@JsonProperty("network_count")
	int networkCount,

	@JsonProperty("full_name")
	String fullName,

	@JsonProperty("size")
	int size,

	@JsonProperty("allow_auto_merge")
	boolean allowAutoMerge,

	@JsonProperty("name")
	String name,

	@JsonProperty("default_branch")
	String defaultBranch,

	@JsonProperty("open_issues_count")
	int openIssuesCount,

	@JsonProperty("description")
	String description,

	@JsonProperty("created_at")
	String createdAt,

	@JsonProperty("updated_at")
	String updatedAt,

	@JsonProperty("owner")
    RepositoryOwnerDTO owner,

	@JsonProperty("topics")
	List<String> topics,

	@JsonProperty("fork")
	boolean fork,

	@JsonProperty("open_issues")
	int openIssues,

	@JsonProperty("watchers_count")
	int watchersCount,

	@JsonProperty("forks_count")
	int forksCount
) {
}
