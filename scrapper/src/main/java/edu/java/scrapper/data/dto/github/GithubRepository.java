package edu.java.scrapper.data.dto.github;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubRepository(
	@JsonProperty("stargazers_count")
	int stargazersCount,

	@JsonProperty("is_template")
	boolean isTemplate,

	@JsonProperty("pushed_at")
	String pushedAt,

	@JsonProperty("subscription_url")
	String subscriptionUrl,

	@JsonProperty("branches_url")
	String branchesUrl,

	@JsonProperty("issue_comment_url")
	String issueCommentUrl,

	@JsonProperty("allow_rebase_merge")
	boolean allowRebaseMerge,

	@JsonProperty("labels_url")
	String labelsUrl,

	@JsonProperty("subscribers_url")
	String subscribersUrl,

	@JsonProperty("temp_clone_token")
	String tempCloneToken,

	@JsonProperty("releases_url")
	String releasesUrl,

	@JsonProperty("svn_url")
	String svnUrl,

	@JsonProperty("subscribers_count")
	int subscribersCount,

	@JsonProperty("id")
	int id,

	@JsonProperty("has_discussions")
	boolean hasDiscussions,

	@JsonProperty("forks")
	int forks,

	@JsonProperty("archive_url")
	String archiveUrl,

	@JsonProperty("allow_merge_commit")
	boolean allowMergeCommit,

	@JsonProperty("git_refs_url")
	String gitRefsUrl,

	@JsonProperty("forks_url")
	String forksUrl,

	@JsonProperty("visibility")
	String visibility,

	@JsonProperty("statuses_url")
	String statusesUrl,

	@JsonProperty("network_count")
	int networkCount,

	@JsonProperty("ssh_url")
	String sshUrl,

	@JsonProperty("full_name")
	String fullName,

	@JsonProperty("size")
	int size,

	@JsonProperty("allow_auto_merge")
	boolean allowAutoMerge,

	@JsonProperty("languages_url")
	String languagesUrl,

	@JsonProperty("html_url")
	String htmlUrl,

	@JsonProperty("collaborators_url")
	String collaboratorsUrl,

	@JsonProperty("clone_url")
	String cloneUrl,

	@JsonProperty("name")
	String name,

	@JsonProperty("pulls_url")
	String pullsUrl,

	@JsonProperty("default_branch")
	String defaultBranch,

	@JsonProperty("hooks_url")
	String hooksUrl,

	@JsonProperty("trees_url")
	String treesUrl,

	@JsonProperty("tags_url")
	String tagsUrl,

	@JsonProperty("private")
	boolean jsonMemberPrivate,

	@JsonProperty("contributors_url")
	String contributorsUrl,

	@JsonProperty("has_downloads")
	boolean hasDownloads,

	@JsonProperty("notifications_url")
	String notificationsUrl,

	@JsonProperty("open_issues_count")
	int openIssuesCount,

	@JsonProperty("description")
	String description,

	@JsonProperty("watchers")
	int watchers,

	@JsonProperty("created_at")
	String createdAt,

	@JsonProperty("deployments_url")
	String deploymentsUrl,

	@JsonProperty("keys_url")
	String keysUrl,

	@JsonProperty("has_projects")
	boolean hasProjects,

	@JsonProperty("archived")
	boolean archived,

	@JsonProperty("has_wiki")
	boolean hasWiki,

	@JsonProperty("updated_at")
	String updatedAt,

	@JsonProperty("comments_url")
	String commentsUrl,

	@JsonProperty("stargazers_url")
	String stargazersUrl,

	@JsonProperty("disabled")
	boolean disabled,

	@JsonProperty("delete_branch_on_merge")
	boolean deleteBranchOnMerge,

	@JsonProperty("git_url")
	String gitUrl,

	@JsonProperty("has_pages")
	boolean hasPages,

	@JsonProperty("owner")
	Owner owner,

	@JsonProperty("allow_squash_merge")
	boolean allowSquashMerge,

	@JsonProperty("commits_url")
	String commitsUrl,

	@JsonProperty("compare_url")
	String compareUrl,

	@JsonProperty("git_commits_url")
	String gitCommitsUrl,

	@JsonProperty("topics")
	List<String> topics,

	@JsonProperty("blobs_url")
	String blobsUrl,

	@JsonProperty("git_tags_url")
	String gitTagsUrl,

	@JsonProperty("merges_url")
	String mergesUrl,

	@JsonProperty("downloads_url")
	String downloadsUrl,

	@JsonProperty("has_issues")
	boolean hasIssues,

	@JsonProperty("url")
	String url,

	@JsonProperty("contents_url")
	String contentsUrl,

	@JsonProperty("mirror_url")
	String mirrorUrl,

	@JsonProperty("milestones_url")
	String milestonesUrl,

	@JsonProperty("teams_url")
	String teamsUrl,

	@JsonProperty("fork")
	boolean fork,

	@JsonProperty("issues_url")
	String issuesUrl,

	@JsonProperty("events_url")
	String eventsUrl,

	@JsonProperty("issue_events_url")
	String issueEventsUrl,

	@JsonProperty("assignees_url")
	String assigneesUrl,

	@JsonProperty("open_issues")
	int openIssues,

	@JsonProperty("watchers_count")
	int watchersCount,

	@JsonProperty("node_id")
	String nodeId,

	@JsonProperty("homepage")
	String homepage,

	@JsonProperty("forks_count")
	int forksCount
) {
}
