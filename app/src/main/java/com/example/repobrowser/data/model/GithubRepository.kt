package com.example.repobrowser.data.model

data class GithubRepository(
    val id: Long,
    val nodeId: String,
    val name: String,
    val private: Boolean,
    val ownerAvatar: String,
    val ownerName: String,
    val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    val createdAt: String,
    val updatedAt: String,
    val pushedAt: String,
    val homepage: String?,
    val size: Long,
    val watchers: Int,
    val defaultBranch: String
)