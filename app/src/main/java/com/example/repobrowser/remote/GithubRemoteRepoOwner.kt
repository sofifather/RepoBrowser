package com.example.repobrowser.remote

data class GithubRemoteRepoOwner(
    val login: String,
    val id: Long,
    val node_id: String,
    val avatar_url:String,
    val type: String
)