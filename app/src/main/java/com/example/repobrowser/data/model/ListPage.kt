package com.example.repobrowser.data.model

import com.example.repobrowser.data.Pagination
import com.example.repobrowser.remote.PaginationInfo

data class ListPage(
    val repos: List<GithubRepository>,
    val paginationInfo: PaginationInfo?,
    val status: Status
)

enum class Status {
    SUCCESS,
    FAILURE
}