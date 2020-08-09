package com.example.repobrowser.remote

data class GithubRemoteRepo(
    val id: Long,
    val node_id: String,
    val name: String,
    val full_name: String,
    val private: Boolean,
    val owner: GithubRemoteRepoOwner,
    val html_url: String,
    val description: String,
    val fork: Boolean,
    val url: String,
    val created_at: String,
    val updated_at: String,
    val pushed_at: String,
    val homepage: String,
    val size: Long,
    val watchers: Int,
    val default_branch: String
)